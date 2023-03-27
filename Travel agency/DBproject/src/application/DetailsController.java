package application;
//Yazan Daibes 1180414

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.sql.Statement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DetailsController implements Initializable{

	@FXML
	private DatePicker checkInDate=new DatePicker();

	@FXML
	private DatePicker checkOutDate=new DatePicker();

	@FXML
	private ComboBox<String> numOfDoubleRooms=new ComboBox<>();

	@FXML
	private ComboBox<String> numOfSingleRooms=new ComboBox<>();

	@FXML
	private ComboBox<String> numOfSweetRooms=new ComboBox<>();

	@FXML
	private TextField guestName=new TextField();

	@FXML
	private TextField guestLastName=new TextField();

	@FXML
	private TextField guestPhone=new TextField();

	@FXML
	private TextField guestID=new TextField();

	@FXML
	private Button bookButton;

	@FXML
	private Text hNameText;

	@FXML
	private Text cNameText;

	@FXML
	private Button mainHotel= new Button();

	@FXML
	private Text singleTextPrice=new Text();

	@FXML
	private Text doubleTextPrice=new Text();

	@FXML
	private Text sweetTextPrice=new Text();

	@FXML
	private Text totalPriceText=new Text();
	@FXML
	private Text PriceCode;
	@FXML
	private Text FirstName;

	@FXML
	private Text LastName;

	@FXML
	private Text PassportNumber;

	@FXML
	private Text Email;

	@FXML
	private Text signInSignUp;
	private double basePrice;
	private int hotelID;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	static ArrayList<Hotel> hotelData=new ArrayList<Hotel>();
	static ArrayList<String> cityNamesList=new ArrayList<String>();
	private static Connection con;
	static double price =0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		guestPhone.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				guestPhone.setText(oldValue);
			}
		});
		guestID.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				guestID.setText(oldValue);
			}
		});

		// filling the customer details
		FirstName.setText(SampleController.currentUser.getFirst_Name());
		LastName.setText(SampleController.currentUser.getLast_Name());
		PassportNumber.setText(String.valueOf(SampleController.currentUser.getPassport_Number()));
		Email.setText(SampleController.currentUser.getEmail());
		//SampleController.currentUser.getFirst_Name()

		if(SampleController.currentUser.getPassport_Number() == 0)
		{
			bookButton.setDisable(true);
			signInSignUp.setVisible(true); // sign up or sign in

		}
		else { signInSignUp.setVisible(false);}



	}


	@FXML
	void bookDetails(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {

		LocalDate fromDate = checkInDate.getValue();
		LocalDate toDate = checkOutDate.getValue();
		String name,lastName;
		String phoneNumber;
		String id;
		int numOfDouble,numOfSingle,numOfSweets;
		if (numOfDoubleRooms.getValue()==null)numOfDouble = 0;
		else numOfDouble = Integer.parseInt(numOfDoubleRooms.getValue());

		if(numOfSingleRooms.getValue()==null)numOfSingle = 0;
		else numOfSingle = Integer.parseInt(numOfSingleRooms.getValue());

		if(numOfSweetRooms.getValue()==null)numOfSweets=0;
		else numOfSweets = Integer.parseInt(numOfSweetRooms.getValue());

		if(guestName.getText().isEmpty())name = "";
		else name=  guestName.getText();

		if(guestLastName.getText().isEmpty())lastName = "";
		else lastName=  guestLastName.getText();

		if(guestPhone.getText().isEmpty())phoneNumber="";
		else phoneNumber = guestPhone.getText();

		if(guestID.getText().isEmpty())id="";
		else id = guestID.getText();


		StringBuilder errorMessage = new StringBuilder("Error! ");
		boolean existError = false;
		if (fromDate==null) {
			errorMessage.append("You Should Add a Check In Date!\n");
			existError = true;
		}
		if(toDate == null) {
			errorMessage.append("You Should Add a Check Out Date!\n");
			existError=true;
		}
		if(numOfDouble == 0 && numOfSingle==0 && numOfSweets ==0) {
			errorMessage.append("You Should At Least Book One Room!\n");
			existError=true;
		}


		if(fromDate!=null && toDate != null)
		{
			if(toDate.isBefore(fromDate)) {
				errorMessage.append("Check In Date Should Be Before Check Out Date!\n");
				existError=true;
			}else if(fromDate.isBefore(LocalDate.now())) {
				errorMessage.append("You Can't Book For Days Before Today!\n");
				existError=true;
			}
		}
		if(existError) {
			Alert error = new Alert(AlertType.ERROR);
			error.setHeaderText("Missing Inputs");
			error.setContentText(errorMessage.toString());
			error.showAndWait();
		}else {

			price += numOfSingle * basePrice + numOfDouble * basePrice*1.5 + numOfSweets * basePrice * 2.5;
			System.out.println("Correct, Total Price Is "+String.format("%.2f", price) + "$");
			connectToHotel(numOfSingle,numOfDouble,numOfSweets);

			// go to invoice
			FXMLLoader loader = new FXMLLoader(getClass().getResource("invoices.fxml"));
			Scene di = new Scene(loader.load());
			Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
			current.setScene(di);
			InvoicesController controller =loader.getController();
			controller.insertData(price,1);
		}
	}

	private void connectToHotel(int numOfSingle, int numOfDouble, int numOfSweets) throws SQLException, ClassNotFoundException {

		connectDB();

		String query = "UPDATE hotel "
				+ "SET numberOfSweets = numberOfSweets - "+numOfSweets+" "
				+ ", numberOfDoubleRooms = numberOfDoubleRooms - "+numOfDouble+" "
				+", numberOfSingleRooms =numberOfSingleRooms - "+numOfSingle+" "
				+" WHERE hotelId = "+hotelID+";";
		
		Statement stmt = con.createStatement();
		stmt.execute(query);
		System.out.println(query);

		stmt.close();
		con.close();
	}


	public void initData(Hotel data) {
		// TODO Auto-generated method stub
		hotelID=data.getHotelId();
		hNameText.setText(data.getHotelName()+" Hotel");
		cNameText.setText(data.getCityIdHotel());
		//filling in the Number of sweets Combo box
		ArrayList<String> nos = new ArrayList<String>();		//number of sweets
		for (int i = 0; i < data.getSweetNum()+1; i++) {
			nos.add(Integer.toString(i));										
		}
		ObservableList<String> sol;			//sweets observable list
		sol = FXCollections.observableArrayList(nos);
		numOfSweetRooms.setItems(sol);

		//filling in the Number of Double Rooms Combo box
		ArrayList<String> nodr = new ArrayList<String>();		//number of double rooms
		for (int i = 0; i < data.getDoubleNum()+1; i++) {
			nodr.add(Integer.toString(i));										
		}
		ObservableList<String> drol;			//double rooms observable list
		drol = FXCollections.observableArrayList(nodr);
		numOfDoubleRooms.setItems(drol);


		//filling in the Number of Single rooms Combo box
		ArrayList<String> nosr = new ArrayList<String>();		//number of single rooms
		for (int i = 0; i < data.getSingleNum()+1; i++) {
			nosr.add(Integer.toString(i));										
		}
		ObservableList<String> srol;			//single rooms observable list
		srol = FXCollections.observableArrayList(nosr);
		numOfSingleRooms.setItems(srol);

		//setting the prices
		basePrice = data.getBasePrice();

		//Setting Single Room Prices Information
		singleTextPrice.setText("Single Room Price From 1 Night = "+String.format("%.2f", basePrice)+"$");

		//Setting Double Room Prices Information
		doubleTextPrice.setText("Double Room Price From 1 Night = "+String.format("%.2f", basePrice*1.5)+"$");

		//Setting Sweet Room Prices Information
		sweetTextPrice.setText("Sweet Room Price From 1 Night = "+String.format("%.2f",basePrice*2.5) +"$");

	}
	@FXML
	void changePrice(ActionEvent event) {
		int numOfDouble,numOfSingle,numOfSweets;
		if (numOfDoubleRooms.getValue()==null)numOfDouble = 0;
		else numOfDouble = Integer.parseInt(numOfDoubleRooms.getValue());

		if(numOfSingleRooms.getValue()==null)numOfSingle = 0;
		else numOfSingle = Integer.parseInt(numOfSingleRooms.getValue());

		if(numOfSweetRooms.getValue()==null)numOfSweets=0;
		else numOfSweets = Integer.parseInt(numOfSweetRooms.getValue());

		double price = numOfSingle * basePrice + numOfDouble * basePrice*1.5 + numOfSweets * basePrice * 2.5;
		totalPriceText.setText("Total Price: "+String.format("%.2f", price)+"$");
		System.out.println("Total Price: "+price);

	}

	@FXML
	void changeToHotel(ActionEvent event) throws IOException {
		System.out.println("test");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/HotelReservation.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);
	}


	@FXML
	void changeToFlight(ActionEvent event) throws IOException {
		System.out.println("test");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/flightsController.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);
	}

	@FXML
	void changeToMainPage(ActionEvent event) throws IOException {
		System.out.println("test");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainPage.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);
	}
	@FXML
	void OpenInvoice(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("invoices.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

	}

	@FXML
	public void LogOut(ActionEvent event)throws IOException {

		// clear the previous customer info. who was using the website 
    	SampleController.currentUser.setFirst_Name(null);
    	SampleController.currentUser.setLast_Name(null);
    	SampleController.currentUser.setPassport_Number(0);
    	SampleController.currentUser.setEmail(null);
    	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

	}

	static boolean SignFirst = false;
	@FXML
	public void goToSignInOrSignUp(MouseEvent event) throws IOException
	{
		SignFirst = true;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

	}
	@FXML
	public void OpenCarRental(ActionEvent event)throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Cars.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

	}

	private void connectDB() throws ClassNotFoundException, SQLException {

		String dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
		Properties p = new Properties();
		p.setProperty("user", dbUsername);
		p.setProperty("password", dbPassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");

		con = DriverManager.getConnection (dbURL, p);

	}


}
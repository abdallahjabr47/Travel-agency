package application;
//Yazan Daibes 1180414

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class CarsDetailsController implements Initializable {

	@FXML
	private Button maincar;

	@FXML
	private ComboBox<String> numofdays;

	@FXML
	private Text num;

	@FXML
	private TextField guestName;

	@FXML
	private TextField guestPhone;

	@FXML
	private TextField guestID;

	@FXML
	private Button bookButton;

	@FXML
	private Text hNameText;

	@FXML
	private TextField guestLastName;

	@FXML
	private Text funFact;

	@FXML
	private Text picklocation = new Text();


    @FXML
    private Text PickUpText;
	@FXML
	private TextField droplocation;

	@FXML
	private DatePicker pickupdate;

	@FXML
	private Label baseprice;

	@FXML
	private Label totalprice;

	@FXML
	private DatePicker dropdate;
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
	String cityNameD ;
	private double basePrice;
	private String CarName;
	private int  CarID;
	static double price = 0;
    @FXML
    private ComboBox<String> DropLocation;

    
	static ArrayList<String> cityNamesList=new ArrayList<String>();

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	private Connection con;

	static ArrayList<Car> CarData=new ArrayList<Car>();
	static ArrayList<String> cityNamesListInDetails=new ArrayList<String>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FirstName.setText(SampleController.currentUser.getFirst_Name());
		LastName.setText(SampleController.currentUser.getLast_Name());
		PassportNumber.setText(String.valueOf(SampleController.currentUser.getPassport_Number()));
		Email.setText(SampleController.currentUser.getEmail());

		// fill drop location
		
		if(SampleController.currentUser.getPassport_Number() == 0)
		{
			bookButton.setDisable(true);
			signInSignUp.setVisible(true); // sign up or sign in

		}
		
		else { 
			signInSignUp.setVisible(false);}
		System.out.println("Here");
		//mainHotel.defaultButtonProperty().addListener(O);


	}
	

	@FXML
	void bookDetails(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {

		LocalDate fromDate = pickupdate.getValue();
		//LocalDate toDate = dropdate.getValue();
		int numdays;
		if (numofdays.getValue()==null)numdays = 0;
		else numdays = Integer.parseInt(numofdays.getValue());


		StringBuilder errorMessage = new StringBuilder("Error! ");
		boolean existError = false;
		if (fromDate==null) {
			errorMessage.append("You Should Add a Check pickup  Date!\n");
			existError = true;
		}
		
		if(numdays == 0 ) {
			errorMessage.append("You Should At Least Book One car!\n");
			existError=true;
		}
	
		if(fromDate!=null )
		{
			
			 if(fromDate.isBefore(LocalDate.now())) {
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
			price = numdays * basePrice ;
			System.out.println("Correct, Total Price Is "+String.format("%.2f", price) + "$");
			connectToCars(numdays,DropLocation.getValue());


			// go to invoice
			FXMLLoader loader = new FXMLLoader(getClass().getResource("invoices.fxml"));
			Scene di = new Scene(loader.load());
			Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
			current.setScene(di);
			InvoicesController controller =loader.getController();
			controller.insertData(price,2);




		}
	}
	private void connectToCars(int numdays,String dropLoc) throws SQLException, ClassNotFoundException {

		connectDB();

		String query = "UPDATE car "
				+ "SET noOfDays = "+numdays+
				"  WHERE plate_no = "+CarID+";";
		
		Statement stmt = con.createStatement();
		stmt.execute(query);
		stmt.close();

		
		String query1 = "UPDATE car "
				+ "SET  drop_location = ( Select cityID from City WHERE cityname =  '"+dropLoc+ "') WHERE plate_no = "+CarID+";";

		Statement stmt1 = con.createStatement();
		stmt1.execute(query1);
		stmt1.close();

		
		
		System.out.println(query);
		
		
		con.close();

	}


	@FXML
	void changePrice(ActionEvent event) {
		int numdays;
		if (numofdays.getValue()==null)numdays = 0;
		else numdays = Integer.parseInt(numofdays.getValue());



		double price = numdays * basePrice ;
		totalprice.setText("Total Price: "+String.format("%.2f", price)+"$");
		System.out.println("Total Price: "+price);

	}
	public void initDataCar(Car data,String Location) {
		// TODO Auto-generated method stub
		
		picklocation.setText(Location);
		try {
			connectToCities(data.getPickup_location());
			// for combobox
			ArrayList<String> d2 = new ArrayList<String>();
			for (int i = 0; i < cityNamesListInDetails.size(); i++) {
				d2.add(cityNamesListInDetails.get(i));
			}		
			ObservableList<String> dataFROM;
			dataFROM = FXCollections.observableArrayList(d2);
			DropLocation.setItems(dataFROM);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		CarName=data.getCar_brand();
		CarID=data.getPlate_no();
		hNameText.setText(CarName+" Car");


		//filling in the Number of sweets Combo box
		ArrayList<String> nos = new ArrayList<String>();		//number of cars
		for (int i = 0; i < 61 ; i++) {
			nos.add(Integer.toString(i));										
		}
		ObservableList<String> sol;			//cars observable list
		sol = FXCollections.observableArrayList(nos);
		numofdays.setItems(sol);



		//setting the prices
		basePrice = data.getBasePrice();

		//Setting cars Prices Information
		baseprice.setText("Car price for 1 day  = "+String.format("%.2f", basePrice)+"$");	
	}





	@FXML
	void BackToFlights(ActionEvent event) throws IOException {

		//URL cardURL = getClass().getResource("/application/Cards.fxml");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("flightsController.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

	}


	@FXML
	void BackToMainPage(ActionEvent event) throws IOException {

		//URL cardURL = getClass().getResource("/application/Cards.fxml");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

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
	void changeToCarRental(ActionEvent event) throws IOException {
		System.out.println("test");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Cars.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
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
	
	private void connectToCities(int cityID) throws ClassNotFoundException, SQLException {
		connectDB();
		
		String SQL2 = "SELECT cityName FROM CITY  WHERE countryName = ( Select countryName from city  where cityId ="+ cityID+");";

		Statement stmt2 = con.createStatement();
		ResultSet rs2 = stmt2.executeQuery(SQL2);
		cityNamesListInDetails.clear();
	
		while(rs2.next())
		{
			
			cityNamesListInDetails.add(rs2.getString(1));
		}
			
			
			
		
	
		rs2.close();
		stmt2.close();
		con.close();
		
		System.out.println("Connection Closed");

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

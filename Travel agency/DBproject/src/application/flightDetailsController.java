package application;
//Yazan Daibes 1180414

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;


public class flightDetailsController implements Initializable{

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	private Connection con;


	@FXML
	private DatePicker checkInDate=new DatePicker();

	@FXML
	private DatePicker checkOutDate=new DatePicker();

	@FXML
	private ComboBox<String> numOfDoubleRooms = new ComboBox<>();

	@FXML
	private ComboBox<String> numOfSingleRooms = new ComboBox<>();

	@FXML
	private ComboBox<String> numOfSweetRooms = new ComboBox<>();

	@FXML
	private TextField guestName=new TextField();

	@FXML
	private TextField guestPhone=new TextField();

	@FXML
	private TextField guestID=new TextField();

	@FXML
	private Button bookButton;



	@FXML
	private Button mainHotel= new Button();
	@FXML
	private Button buttonHome;

	@FXML
	private Button flights;

	@FXML
	private Text AirlineNameText;

	@FXML
	private Text DepartureDate;

	@FXML
	private Text flightDate1;

	@FXML
	private Text fromCityD;


	@FXML
	private Text ToCityID;

	@FXML
	private Text ReturnDate;

	@FXML
	private ComboBox<String> ticketTypeCode = new ComboBox<String>();

	@FXML
	private ComboBox<String> ageCode = new ComboBox<String>();
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

	@FXML
	private Button openInvoiceButton;

	airplane currentAirplane = new airplane();
	static double Ticketprice;
	static ArrayList<airplane> Allaiplane=new ArrayList<airplane>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Ticketprice =0;

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

		System.out.println("IsEmpty =  "+Allaiplane.isEmpty());

		if(SampleController.currentUser.getPassport_Number() == 0)
		{
			bookButton.setDisable(true);
			signInSignUp.setVisible(true); // sign up or sign in

		}
		else { signInSignUp.setVisible(false);

		}
	}

	public void initData(airplane currentAirplane) {

		this.currentAirplane=currentAirplane;

		ArrayList<String> d2 = new ArrayList<String>();
		d2.add("Economy");
		d2.add("First Class");
		d2.add("Adults (12+)");
		d2.add("Childern (2-11)");
		d2.add("Infants (0-1)");

		ObservableList<String> ticketType;
		ticketType = FXCollections.observableArrayList(d2.get(0),d2.get(1));
		ticketTypeCode.setItems(ticketType);



		ObservableList<String> dataTo;
		dataTo = FXCollections.observableArrayList(d2.get(2),d2.get(3),d2.get(4));
		ageCode.setItems(dataTo);
		// set the Economy and Adult to Default
		ticketTypeCode.getSelectionModel().selectFirst();
		ageCode.getSelectionModel().selectFirst();

		// filling the ticket details from the airplane chosen
		AirlineNameText.setText(currentAirplane.getAirlineName());
		fromCityD.setText(currentAirplane.getDEPCity());
		ToCityID.setText(currentAirplane.getArrivalCity());
		DepartureDate.setText(String.valueOf(currentAirplane.getDepartureDate()));
		ReturnDate.setText(String.valueOf(currentAirplane.getReturnDate()));
		PriceCode.setText("Ticket Price for Economy & Adult is "+String.valueOf(currentAirplane.getBasePriceOfTicket())+"$"); // the default price
		Ticketprice = currentAirplane.getBasePriceOfTicket();
		
		// filling the customer details
		FirstName.setText(SampleController.currentUser.getFirst_Name());
		LastName.setText(SampleController.currentUser.getLast_Name());
		PassportNumber.setText(String.valueOf(SampleController.currentUser.getPassport_Number()));
		Email.setText(SampleController.currentUser.getEmail());

	}



	@FXML 
	public void changePrice()
	{
		if(ticketTypeCode.getValue() == "First Class" && ageCode.getValue() == "Adults (12+)")
		{
			PriceCode.setText("Ticket Price for First Class & Adult is "+String.valueOf(currentAirplane.getBasePriceOfTicket() + 100)+"$");
			Ticketprice = currentAirplane.getBasePriceOfTicket() + 100;

		}
		else if (ticketTypeCode.getValue() == "Economy" && ageCode.getValue() == "Adults (12+)")
		{
			PriceCode.setText("Ticket Price for Economy & Adult is "+String.valueOf(currentAirplane.getBasePriceOfTicket())+"$");
			Ticketprice = currentAirplane.getBasePriceOfTicket();

		}
		if (ticketTypeCode.getValue() == "First Class" && ageCode.getValue() == "Childern (2-11)")
		{
			PriceCode.setText("Ticket Price for First Class & Childern (2-11) is "+String.valueOf(currentAirplane.getBasePriceOfTicket() + 50)+"$");
			Ticketprice = currentAirplane.getBasePriceOfTicket() + 50;

		}
		else if(ticketTypeCode.getValue() == "Economy" && ageCode.getValue() == "Childern (2-11)")
		{
			PriceCode.setText("Ticket Price for Economy & Childern (2-11) is "+String.valueOf(currentAirplane.getBasePriceOfTicket() -50)+"$");
			Ticketprice=currentAirplane.getBasePriceOfTicket() -50;
		}
		if(ticketTypeCode.getValue() == "First Class" && ageCode.getValue() == "Infants (0-1)")
		{
			PriceCode.setText("Ticket Price for First Class & Infants (0-1) is "+String.valueOf(currentAirplane.getBasePriceOfTicket() + 10)+"$");
			Ticketprice=currentAirplane.getBasePriceOfTicket() + 10;
		}
		if(ticketTypeCode.getValue() == "Economy" && ageCode.getValue() == "Infants (0-1)")
		{
			PriceCode.setText("Ticket Price for Economy & Infants (0-1) is "+String.valueOf(currentAirplane.getBasePriceOfTicket() - 100)+"$");		
			Ticketprice=currentAirplane.getBasePriceOfTicket() - 100;

		}
	}

	// book and update the airplane_tickets in DB
	@FXML
	void bookAndConfirm(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

		System.out.println("Book and confirm");
		// to show the information of tickets bought on the invoice 
		currentAirplane.setBasePriceOfTicket(Ticketprice);
		Allaiplane.add(currentAirplane);

		JOptionPane.showMessageDialog(null,"Thank you for purchasing from us :)");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("invoices.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

		InvoicesController controller =loader.getController();
		controller.insertData(currentAirplane.getBasePriceOfTicket(),0);
		insertData();
		//openInvoiceButton.setDisable(false);

	}
	// open invoice interface
	@FXML
	void OpenInvoice(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("invoices.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

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
	public void OpenCarRental(ActionEvent event)throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Cars.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

	}

	private void insertData() {
		Random random = new Random();



		try {
			System.out.println("Insert into airPlane_tickets (Travel_Date,FlightNumber,return_Date,seat_number,ticket_type,selling_Price,ageCatagory,invoiceidTicket) values("+
					currentAirplane.getDepartureDate()+",'"+currentAirplane.getReturnDate()+"','"+random.nextInt(400)  +"','"+ ticketTypeCode.getValue()+"','"
					+ ageCode.getValue() +SampleController.invoiceID+ ");");

			connectDB();

			if(ticketTypeCode.getValue() == "First Class" && ageCode.getValue() == "Adults (12+)")
				ExecuteStatement("Insert into airPlane_tickets (Travel_Date,return_Date,seat_number,ticket_type,FlightNoAPT,selling_Price,ageCatagory,invoiceidTicket) VALUES ("
						+"'" + currentAirplane.getDepartureDate() +"','" +currentAirplane.getReturnDate() + "'," + random.nextInt(400) +",'" + ticketTypeCode.getValue() +"'," +currentAirplane.getFlightNo()+ "," + (currentAirplane.getBasePriceOfTicket() + 100)
						+",'" + ageCode.getValue()+"',"+SampleController.invoiceID+")");

			else if (ticketTypeCode.getValue() == "Economy" && ageCode.getValue() == "Adults (12+)")
				ExecuteStatement("Insert into airPlane_tickets (Travel_Date,return_Date,seat_number,ticket_type,FlightNoAPT,selling_Price,ageCatagory,invoiceidTicket) VALUES ("
						+"'" + currentAirplane.getDepartureDate() +"','" +currentAirplane.getReturnDate() + "'," + random.nextInt(400) +",'" + ticketTypeCode.getValue() +"'," +currentAirplane.getFlightNo()+ "," + currentAirplane.getBasePriceOfTicket()
						+",'" + ageCode.getValue()+"',"+SampleController.invoiceID+")");

			else if (ticketTypeCode.getValue() == "First Class" && ageCode.getValue() == "Childern (2-11)")
				ExecuteStatement("Insert into airPlane_tickets (Travel_Date,return_Date,seat_number,ticket_type,FlightNoAPT,selling_Price,ageCatagory,invoiceidTicket) VALUES ("
						+"'" + currentAirplane.getDepartureDate() +"','" +currentAirplane.getReturnDate() + "'," + random.nextInt(400) +",'" + ticketTypeCode.getValue() +"'," +currentAirplane.getFlightNo()+ "," + (currentAirplane.getBasePriceOfTicket()+50)
						+",'" + ageCode.getValue()+"',"+SampleController.invoiceID+")");

			else if(ticketTypeCode.getValue() == "Economy" && ageCode.getValue() == "Childern (2-11)")
				ExecuteStatement("Insert into airPlane_tickets (Travel_Date,return_Date,seat_number,ticket_type,FlightNoAPT,selling_Price,ageCatagory,invoiceidTicket) VALUES ("
						+"'" + currentAirplane.getDepartureDate() +"','" +currentAirplane.getReturnDate() + "'," + random.nextInt(400) +",'" + ticketTypeCode.getValue() +"'," +currentAirplane.getFlightNo()+ "," + (currentAirplane.getBasePriceOfTicket()-50)
						+",'" + ageCode.getValue()+"',"+SampleController.invoiceID+")");	

			else if(ticketTypeCode.getValue() == "First Class" && ageCode.getValue() == "Infants (0-1)")
				ExecuteStatement("Insert into airPlane_tickets (Travel_Date,return_Date,seat_number,ticket_type,FlightNoAPT,selling_Price,ageCatagory,invoiceidTicket) VALUES ("
						+"'" + currentAirplane.getDepartureDate() +"','" +currentAirplane.getReturnDate() + "'," + random.nextInt(400) +",'" + ticketTypeCode.getValue() +"'," +currentAirplane.getFlightNo()+ "," + (currentAirplane.getBasePriceOfTicket()+10)
						+",'" + ageCode.getValue()+"',"+SampleController.invoiceID+")");	

			else if(ticketTypeCode.getValue() == "Economy" && ageCode.getValue() == "Infants (0-1)")
				ExecuteStatement("Insert into airPlane_tickets (Travel_Date,return_Date,seat_number,ticket_type,FlightNoAPT,selling_Price,ageCatagory,invoiceidTicket) VALUES ("
						+"'" + currentAirplane.getDepartureDate() +"','" +currentAirplane.getReturnDate() + "'," + random.nextInt(400) +",'" + ticketTypeCode.getValue() +"'," +currentAirplane.getFlightNo()+ "," + (currentAirplane.getBasePriceOfTicket()-100)
						+",'" + ageCode.getValue()+"',"+SampleController.invoiceID+")");	

			con.close();		
			System.out.println("Connection closed" );

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
	private void connectDB() throws ClassNotFoundException, SQLException {

		dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
		Properties p = new Properties();
		p.setProperty("user", dbUsername);
		p.setProperty("password", dbPassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");

		con = DriverManager.getConnection (dbURL, p);

	}

	public void ExecuteStatement(String SQL) throws SQLException {

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();


		}
		catch(SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");

		}


	}


}
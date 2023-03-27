package application;
//Yazan Daibes 1180414

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InvoicesController implements Initializable{

	@FXML
	private Label invoiceID;

	@FXML
	private Label date;

	@FXML
	private Text priceForFlight;

	@FXML
	private Label priceForHotel;

	@FXML
	private Label priceForCar;

	@FXML
	private Label TotalPrice;

	@FXML
	private Label FirstName;

	@FXML
	private Label LastName;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";

	static ArrayList<Invoices> invoiceData = new ArrayList<Invoices>();
	private static Connection con;



	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub


		FirstName.setText(SampleController.currentUser.getFirst_Name());
		LastName.setText(SampleController.currentUser.getLast_Name());

		//if (SampleController.invoiceID != -1)

		String text="";
		double totalTicketPrices = 0;
		for (int i=0;i<flightDetailsController.Allaiplane.size();i++) {
			text = text+(i+1) +": "+" Price = "+
					flightDetailsController.Allaiplane.get(i).getBasePriceOfTicket()+
					"$\tDeparture city: "+flightDetailsController.Allaiplane.get(i).getDEPCity()+ ". Arrival City: "+flightDetailsController.Allaiplane.get(i).getArrivalCity()
					+"\t\n Departure Date: "+flightDetailsController.Allaiplane.get(i).getDepartureDate()
					+"  Return Date: "+flightDetailsController.Allaiplane.get(i).getReturnDate()
					+" From  " +flightDetailsController.Allaiplane.get(i).getAirlineName()+
					" \n\n";
			totalTicketPrices += flightDetailsController.Allaiplane.get(i).getBasePriceOfTicket();

		}

		//			priceForFlight.setText(String.valueOf(flightDetailsController.Ticketprice));
		priceForFlight.setText(text);

		priceForHotel.setText(String.valueOf(DetailsController.price)+"$");
		priceForCar.setText(String.valueOf(CarsDetailsController.price)+"$");
		TotalPrice.setText(String.valueOf(totalTicketPrices +DetailsController.price+CarsDetailsController.price )+"$");
		date.setText(String.valueOf(java.time.LocalDate.now()));

		if(SampleController.invoiceID !=-1 )
		invoiceID.setText(String.valueOf(SampleController.invoiceID)); 
		





	}
	public void insertData(double price, int determine) throws SQLException, ClassNotFoundException { // to database

		if(SampleController.invoiceID == -1)
		{

			try {

				connectDB();		     			

				if(determine == 0) // airplane tickets
				{
					ExecuteStatement("Insert into invoice (Currentdate,priceFlight,priceHotel,priceCar,toatlPrice,Passport_NumberINV) values('"+java.time.LocalDate.now()+"',"+price+
							","+0+","+0+","+price + ","+ SampleController.currentUser.getPassport_Number()+")");
				}
				else if (determine == 1)	// hotels
				{

					ExecuteStatement("Insert into invoice (Currentdate,priceFlight,priceHotel,priceCar,toatlPrice,Passport_NumberINV) values('"+java.time.LocalDate.now()+"',"+0+
							","+price+","+0+","+price + ","+SampleController.currentUser.getPassport_Number()+")");

				}
				else if (determine == 2)
				{
					ExecuteStatement("Insert into invoice (Currentdate,priceFlight,priceHotel,priceCar,toatlPrice,Passport_NumberINV) values('"+java.time.LocalDate.now()+"',"+0+
							","+0+","+price+","+price + ","+SampleController.currentUser.getPassport_Number()+")");

				}

				SampleController.invoiceID= ExecuteStatementInvoice("SELECT inv_id_number FROM INVOICE order by inv_id_number desc limit 1;");	
				System.out.println("SampleController.invoiceID = "+ SampleController.invoiceID);

				con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			invoiceID.setText(String.valueOf(SampleController.invoiceID)); 

		}
		else {		
			connectDB();		     			
			if(determine == 0)
			{
				ExecuteStatement("update  invoice set priceFlight = priceFlight + "+price + " where inv_id_number = "+SampleController.invoiceID+";");
				ExecuteStatement("update  invoice set toatlPrice = priceFlight+priceHotel + priceCar where inv_id_number = "+SampleController.invoiceID+";");

			}
			else  if (determine == 1)
			{
				ExecuteStatement("update  invoice set priceHotel = "+price + " where inv_id_number = "+SampleController.invoiceID+";");
				ExecuteStatement("update  invoice set toatlPrice = priceFlight+priceHotel + priceCar  where inv_id_number = "+SampleController.invoiceID+";");

			}

			else if (determine == 2)
			{
				ExecuteStatement("update  invoice set priceCar = "+price + " where inv_id_number = "+SampleController.invoiceID+";");
				ExecuteStatement("update  invoice set toatlPrice = priceFlight+priceHotel + priceCar where inv_id_number = "+SampleController.invoiceID+";");

			}
			invoiceID.setText(String.valueOf(SampleController.invoiceID)); 

			con.close();

		}

	}
	private void connectDB() throws ClassNotFoundException, SQLException {

		dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
		Properties p = new Properties();
		p.setProperty("user", dbUsername);
		p.setProperty("password", dbPassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(dbURL, p);

	}

	public void ExecuteStatement(String SQL) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");

		}
	}

	public int ExecuteStatementInvoice(String SQL) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery(SQL);
			rs.next();
			int result = Integer.parseInt(rs.getString(1));
			stmt.close();
			rs.close();

			return result;
		} catch (SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");
			return -1;
		}
	}


	@FXML
	public void OpenMainPage(ActionEvent event)throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);


	}

	@FXML
	void changeToHotelF(ActionEvent event) throws IOException {
		System.out.println("test");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/HotelReservation.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
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
	public void OpenCarRental(ActionEvent event)throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Cars.fxml"));
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
	void OpenInvoice(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("invoices.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

	}

}

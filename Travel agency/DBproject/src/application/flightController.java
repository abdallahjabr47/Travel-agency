package application;
// Yazan Daibes 1180414

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;

import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class flightController implements Initializable {

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	private Connection con;
	
	ArrayList<String> dComboFrom = new ArrayList<String>();

    @FXML
    private static Label welcome_label;
    @FXML
	private ScrollPane scrollPaneContent;
	@FXML
	private VBox vboxData ;
	@FXML
	private AnchorPane cardAnchor;
	@FXML
	private HBox cardHBox;
	@FXML
	private Text cardTitle;
	 @FXML
	    private Button buttonHome;

	    @FXML
	    private Button flights;
	    @FXML
	    private HBox searchHbox;
	    @FXML
	    private ComboBox<String> FromComboBox = new ComboBox<>();

	    @FXML
	    private ComboBox<String> ToComboBox  = new ComboBox<>();
	     
	    @FXML
	    private DatePicker DepDate = new DatePicker();;

	    @FXML
	    private DatePicker ReturnDate = new DatePicker();;
	    @FXML
	    private Button searchButton;
	    @FXML
	    private Button openInvoiceButton;

	    
	    
		static int cardNo=-1;
		static String sqlQuery= " ";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		// filling in the Cities combo box
		cardNo = -1;

		try {
			URL cardURL = getClass().getResource("cards.fxml");
			sqlQuery=  "SELECT a.FlightNo,a.DepartureDate,a.returnDate,a.number_of_seats,a.Number_of_passengers,a.airlineName,a.BasePriceOfTicket, c.cityName cityIDFROM, cc.cityName cityIDTo  from airplane a, city c , city cc where a.cityIDfrom = c.cityID and a.cityIdto = cc.cityID;";
			  
	
			
			 FXMLLoader.load(cardURL);
			
			System.out.println("size = "+CardsController.cityNamesList.size());

			ArrayList<String> d2 = new ArrayList<String>();
			for (int i = 0; i < CardsController.cityNamesList.size(); i++) {
				d2.add(CardsController.cityNamesList.get(i));
			}
			
			
			ObservableList<String> dataFROM;
			dataFROM = FXCollections.observableArrayList(d2);
			FromComboBox.setItems(dataFROM);
			
			ObservableList<String> dataTo;
			dataTo = FXCollections.observableArrayList(d2);
			ToComboBox.setItems(dataTo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		vboxData.setSpacing(5);
		
		//fill the cards
		cardNo = -1;
		URL cardURL = getClass().getResource("cards.fxml");

		sqlQuery=  "SELECT a.FlightNo,a.DepartureDate,a.returnDate,a.number_of_seats,a.Number_of_passengers,a.airlineName,a.BasePriceOfTicket, c.cityName cityIDFROM, cc.cityName cityIDTo  from airplane a, city c , city cc where a.cityIDfrom = c.cityID and a.cityIdto = cc.cityID;";

		try {
			if (cardNo == -1) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("cards.fxml"));
				loader.load();
				++cardNo;
			}
			System.out.println(CardsController.aiplaneData.size());

			for (; cardNo < CardsController.aiplaneData.size(); ++cardNo) {
				Parent cardAnchor = FXMLLoader.load(cardURL);
			//	cardAnchor = FXMLLoader.load(cardURL);
				vboxData.getChildren().add(cardAnchor);

			}
			System.out.println("VBOX SIZE = "+vboxData.getChildren().size());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

		@FXML
		public void flights() throws IOException, ClassNotFoundException, SQLException {
			// TODO Auto-generated method stub
			vboxData.setSpacing(5);
			
			cardNo = -1;
			URL cardURL = getClass().getResource("cards.fxml");
	
			
			sqlQuery=  "SELECT a.FlightNo,a.DepartureDate,a.returnDate,a.number_of_seats,a.Number_of_passengers,a.airlineName,a.BasePriceOfTicket, c.cityName cityIDFROM, cc.cityName cityIDTo  from airplane a, city c , city cc where a.cityIDfrom = c.cityID and a.cityIdto = cc.cityID;";

			try {
				if (cardNo == -1) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("cards.fxml"));
					loader.load();
					++cardNo;
				}
				System.out.println(CardsController.aiplaneData.size());

				for (; cardNo < CardsController.aiplaneData.size(); ++cardNo) {
					Parent cardAnchor = FXMLLoader.load(cardURL);
				//	cardAnchor = FXMLLoader.load(cardURL);
					vboxData.getChildren().add(cardAnchor);

				}
				System.out.println("VBOX SIZE = "+vboxData.getChildren().size());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
		}
			
	
		@FXML
		void search(ActionEvent event) {
			LocalDate DepartureDate = DepDate.getValue();
			LocalDate RDate = ReturnDate.getValue();
			String DepartureCity = FromComboBox.getValue();
			String ArrivalCity = ToComboBox.getValue();

			System.out.println("In search");
			StringBuilder errorMessage = new StringBuilder("Error! ");
			boolean existError = false;
			if (DepartureDate==null) {
				errorMessage.append("You Should Add Departure Date!\n");
				existError = true;
			}
			if(RDate == null) {
				errorMessage.append("You Should Add Return Date!\n");
				existError=true;
			}
			if(DepartureCity ==null) {
				errorMessage.append("You Should Add Departure City!\n");
				existError = true;
			}
			
			if(ArrivalCity ==null) {
				errorMessage.append("You Should Add Arrival City!\n");
				existError = true;
			}
			if(DepartureCity ==ArrivalCity) {
				errorMessage.append("Departure City and Arrival City should NOT be the same!\n");
				existError = true;
			}
			
		
			
			if(DepartureDate!=null && RDate != null)
			{
				if(RDate.isBefore(DepartureDate)) {
				errorMessage.append("Departure Date Should Be Before Return Date!\n");
				existError=true;}
				else if(DepartureDate.isBefore(LocalDate.now())) {
					errorMessage.append("No flights available For days before the today's date!\n");
					existError=true;
				}

				
			}
			if(existError) {
				Alert error = new Alert(AlertType.ERROR);
				error.setHeaderText("Missing Inputs");
				error.setContentText(errorMessage.toString());
				error.showAndWait();
			}else {
				
				vboxData.getChildren().clear();								
				cardNo = -1;
				URL cardURL = getClass().getResource("cards.fxml");


				sqlQuery ="SELECT a.FlightNo,a.DepartureDate,a.returnDate,a.number_of_seats,a.Number_of_passengers,a.airlineName,a.BasePriceOfTicket, c.cityName cityIDFROM, cc.cityName cityIDTo  from airplane a, city c , city cc where a.cityIDfrom = c.cityID and a.cityIdto = cc.cityID"
						+ " and c.cityName = '"+ DepartureCity +"'"+ " AND cc.cityName = '"+ArrivalCity + "'" + " AND a.DepartureDate = '" + DepartureDate +"'" + " AND a.returnDate = '"+RDate+"';";
				
				
			try {
					if (cardNo == -1) {
						System.out.println("In first if");
						FXMLLoader loader = new FXMLLoader(getClass().getResource("cards.fxml"));
						loader.load();
						++cardNo;
					}
					System.out.println("card Number = "+cardNo);
					for (; cardNo < CardsController.aiplaneData.size(); ++cardNo) {
						System.out.println("airplaneDataSIZE = "+CardsController.aiplaneData.size());

						Parent cardAnchor = FXMLLoader.load(cardURL);
						vboxData.getChildren().add(cardAnchor);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		@FXML
		void searchForCheapFlights(ActionEvent event) {
			LocalDate DepartureDate = DepDate.getValue();
			LocalDate RDate = ReturnDate.getValue();
			String DepartureCity = FromComboBox.getValue();
			String ArrivalCity = ToComboBox.getValue();

			System.out.println("In search");
			StringBuilder errorMessage = new StringBuilder("Error! ");
			boolean existError = false;
			if (DepartureDate==null) {
				errorMessage.append("You Should Add Departure Date!\n");
				existError = true;
			}
			if(RDate == null) {
				errorMessage.append("You Should Add Return Date!\n");
				existError=true;
			}
			if(DepartureCity ==null) {
				errorMessage.append("You Should Add Departure City!\n");
				existError = true;
			}
			
			if(ArrivalCity ==null) {
				errorMessage.append("You Should Add Arrival City!\n");
				existError = true;
			}
			if(DepartureCity ==ArrivalCity) {
				errorMessage.append("Departure City and Arrival City should NOT be the same!\n");
				existError = true;
			}
			
		
			
			if(DepartureDate!=null && RDate != null)
			{
				if(RDate.isBefore(DepartureDate)) {
				errorMessage.append("Departure Date Should Be Before Return Date!\n");
				existError=true;
			}
				else if(DepartureDate.isBefore(LocalDate.now())) {
					errorMessage.append("No flights available For days before the today's date!\n");
					existError=true;
				}
				
			}
			if(existError) {
				Alert error = new Alert(AlertType.ERROR);
				error.setHeaderText("Missing Inputs");
				error.setContentText(errorMessage.toString());
				error.showAndWait();
			}else {
				
				vboxData.getChildren().clear();								
				cardNo = -1;
				URL cardURL = getClass().getResource("cards.fxml");


				sqlQuery ="SELECT a.FlightNo,a.DepartureDate,a.returnDate,a.number_of_seats,a.Number_of_passengers,a.airlineName,a.BasePriceOfTicket, c.cityName cityIDFROM, cc.cityName cityIDTo  from airplane a, city c , city cc where a.cityIDfrom = c.cityID and a.cityIdto = cc.cityID"
						+ " and BasePriceOfTicket  <=300 and c.cityName = '"+ DepartureCity +"'"+ " AND cc.cityName = '"+ArrivalCity + "'" + " AND a.DepartureDate = '" + DepartureDate +"'" + " AND a.returnDate = '"+RDate+"';";
				
				
			try {
					if (cardNo == -1) {
						System.out.println("In first if");
						FXMLLoader loader = new FXMLLoader(getClass().getResource("cards.fxml"));
						loader.load();
						++cardNo;
					}
					System.out.println("card Number = "+cardNo);
					for (; cardNo < CardsController.aiplaneData.size(); ++cardNo) {
						System.out.println("airplaneDataSIZE = "+CardsController.aiplaneData.size());

						Parent cardAnchor = FXMLLoader.load(cardURL);
						vboxData.getChildren().add(cardAnchor);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
			// open main page
		   @FXML
			 public void OpenMainPage(ActionEvent event)throws IOException {
			 
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
	        Scene di = new Scene(loader.load());
			Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
	        current.setScene(di);
	        
	        
			 }
		   
			@FXML
			void changeToHotelF(ActionEvent event) throws IOException {
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
		    @FXML
			public void OpenCarRental(ActionEvent event)throws IOException {
		    	
			    FXMLLoader loader = new FXMLLoader(getClass().getResource("Cars.fxml"));
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

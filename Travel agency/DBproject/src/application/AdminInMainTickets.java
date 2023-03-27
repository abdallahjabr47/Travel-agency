package application;

//Yazan Daibes 1180414


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.mysql.jdbc.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class AdminInMainTickets implements Initializable{


	private ArrayList<airplane> data;
	private ObservableList<airplane> dataList;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	private Connection con;
	@FXML
	private TableColumn<airplane, Integer> col_FlightNumber;

	@FXML
	private TableColumn<airplane, Date> col_DepDate;

	@FXML
	private TableColumn<airplane, Date> Col_ReturnDate;

	@FXML
	private TableColumn<airplane, String> Col_NumOfSeats;

	@FXML
	private TableColumn<airplane, String> col_NumOfPassengers;

	@FXML
	private TableColumn<airplane, String> col_ArrivalCity;

	@FXML
	private TableColumn<airplane, String> col_DepartureCity;

	@FXML
	private TableColumn<airplane, String> col_Airlines;

	@FXML
	private TableColumn<airplane, Double> col_Ticket_Price;

	@FXML
	private TableView<airplane> table_ticket;
	@FXML
	private TextField txt_numOfPass;

	@FXML
	private TextField txt_NumOfSeats;

	@FXML
	private TextField txt_DepCity;

	@FXML
	private DatePicker txt_DepDate;

	@FXML
	private DatePicker txt_ReturnDate;

	@FXML
	private TextField txt_ArrivalCity;

	@FXML
	private TextField txt_Airlines;

	@FXML
	private TextField txt_TicketPrice;

	int size;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// add listener to the below field to let the user enter only numbers
		txt_TicketPrice.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_TicketPrice.setText(oldValue);
			}
		});

		txt_numOfPass.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_numOfPass.setText(oldValue);
			}
		});

		txt_NumOfSeats.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_NumOfSeats.setText(oldValue);
			}
		});

		txt_ArrivalCity.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_ArrivalCity.setText(oldValue);
			}
		});
		


		txt_DepCity.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_DepCity.setText(oldValue);
			}
		});

		data = new ArrayList<>();
		try {
			getData();	
			dataList = FXCollections.observableArrayList(data); 



		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		EditTable();
		showPerson();


	}
	public void showPerson()
	{

		col_FlightNumber.setCellValueFactory(new PropertyValueFactory<airplane, Integer>("FlightNo")); 
		col_DepDate.setCellValueFactory(new PropertyValueFactory<airplane, Date>("DepartureDate"));
		Col_ReturnDate.setCellValueFactory(new PropertyValueFactory<airplane, Date>("ReturnDate"));
		Col_NumOfSeats.setCellValueFactory(new PropertyValueFactory<airplane, String>("Number_of_seats"));       
		col_NumOfPassengers.setCellValueFactory(new PropertyValueFactory<airplane, String>("Number_of_passengers"));
		col_DepartureCity.setCellValueFactory(new PropertyValueFactory<airplane, String>("dEPCity"));
		col_ArrivalCity.setCellValueFactory(new PropertyValueFactory<airplane, String>("ArrivalCity")); 
		col_Airlines.setCellValueFactory(new PropertyValueFactory<airplane, String>("airlineName"));
		col_Ticket_Price.setCellValueFactory(new PropertyValueFactory<airplane, Double>("BasePriceOfTicket"));

	}

	private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL = "SELECT FlightNo,DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket, c.CityName dEPCity, cc.cityName ArrivalCity FROM airplane a JOIN city c on c.cityId=a.cityIDFrom JOIN city cc on cc.cityId=a.cityIDTo ORDER BY FlightNo";

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);



		while ( rs.next() ) 
			data.add(new airplane(
					Integer.parseInt(rs.getString(1)),
					Date.valueOf(rs.getString(2)),
					Date.valueOf(rs.getString(3)),
					rs.getString(4),
					rs.getString(5),
					rs.getString(6),
					Double.parseDouble(rs.getString(7)),
					rs.getString(8),
					rs.getString(9)
					));


		rs.close();
		stmt.close();
		size = data.size();
		con.close();
		System.out.println("Connection closed" + data.size());


	}

	public  void EditTable()
	{
		table_ticket.setEditable(true);
		Col_NumOfSeats.setCellFactory(TextFieldTableCell.<airplane>forTableColumn());
		col_NumOfPassengers.setCellFactory(TextFieldTableCell.<airplane>forTableColumn());
		col_DepartureCity.setCellFactory(TextFieldTableCell.<airplane>forTableColumn());
		col_ArrivalCity.setCellFactory(TextFieldTableCell.<airplane>forTableColumn());
		col_Airlines.setCellFactory(TextFieldTableCell.<airplane>forTableColumn());
		col_Ticket_Price.setCellFactory(TextFieldTableCell.<airplane,Double>forTableColumn(new DoubleStringConverter()));

		// update ticket price
		col_Ticket_Price.setOnEditCommit(        
				// on double click
				(CellEditEvent<airplane, Double> t) -> {
					((airplane) t.getTableView().getItems().get(
							t.getTablePosition().getRow())
							).setBasePriceOfTicket(t.getNewValue()); //display only
					updateBasePrice( t.getRowValue().getFlightNo(),t.getNewValue()); // update on DB 
				});
		// update number of seats
		Col_NumOfSeats.setOnEditCommit(        
				// on double click
				(CellEditEvent<airplane, String> t) -> {
					((airplane) t.getTableView().getItems().get(
							t.getTablePosition().getRow())
							).setNumber_of_seats(t.getNewValue()); //display only
					updateNumberOfSeats( t.getRowValue().getFlightNo(),t.getNewValue()); // update on DB 
				});
		// update number of passengers
		col_NumOfPassengers.setOnEditCommit(        
				// on double click
				(CellEditEvent<airplane, String> t) -> {
					((airplane) t.getTableView().getItems().get(
							t.getTablePosition().getRow())
							).setNumber_of_passengers(t.getNewValue()); //display only
					updateNumberOfPassenger( t.getRowValue().getFlightNo(),t.getNewValue()); // update on DB 
				});


		// update departure city
		col_DepartureCity.setOnEditCommit(        
				(CellEditEvent<airplane, String> t) -> {
					((airplane) t.getTableView().getItems().get(
							t.getTablePosition().getRow())
							).setDEPCity(t.getNewValue()); //display only
					updateDepCity( t.getRowValue().getFlightNo(),t.getNewValue()); // update on DB 
				});
		// UPDATE ARRIVAL CITY
		col_ArrivalCity.setOnEditCommit(        
				(CellEditEvent<airplane, String> t) -> {
					((airplane) t.getTableView().getItems().get(
							t.getTablePosition().getRow())
							).setArrivalCity(t.getNewValue()); //display only
					updateArrivalCity( t.getRowValue().getFlightNo(),t.getNewValue()); // update on DB 
				});

		// update Airlines
		col_Airlines.setOnEditCommit(        
				(CellEditEvent<airplane, String> t) -> {
					((airplane) t.getTableView().getItems().get(
							t.getTablePosition().getRow())
							).setAirlineName(t.getNewValue()); //display only
					updateAirlineName( t.getRowValue().getFlightNo(),t.getNewValue()); // update on DB 
				});



		table_ticket.setItems(dataList);
	}
	@FXML
	void Add_airplane() {
		
	if((txt_DepDate.getValue() != null && txt_ReturnDate.getValue() !=null))
	{
		if( (txt_DepDate.getValue()).isBefore(LocalDate.now()) || txt_ReturnDate.getValue().isBefore(txt_DepDate.getValue())) {
			JOptionPane.showMessageDialog(null," Adding flights For days before the today's date is NOT allowed!\n Or Arrival Date shouldn't be before Departure Date");
			txt_NumOfSeats.clear();
			txt_numOfPass.clear();
			txt_DepCity.clear();
			txt_ArrivalCity.clear();
			txt_Airlines.clear();
			txt_TicketPrice.clear();
		}
	
		else {
			try {
				airplane rc;
				rc = new airplane(
						size+1,
						Date.valueOf(txt_DepDate.getValue()),
						Date.valueOf(txt_ReturnDate.getValue()),
						txt_NumOfSeats.getText(),             
						txt_numOfPass.getText(),
						txt_Airlines.getText(),
						Double.parseDouble(txt_TicketPrice.getText()),
						txt_DepCity.getText(),
						txt_ArrivalCity.getText()					
						);


				connectDB();
				System.out.println("Connection established");

				con.close();
				System.out.println("Connection closed" + data.size());


				dataList.add(rc);
				table_ticket.setItems(dataList);

				insertData(rc);


				txt_NumOfSeats.clear();
				txt_numOfPass.clear();
				txt_DepCity.clear();
				txt_ArrivalCity.clear();
				txt_Airlines.clear();
				txt_TicketPrice.clear();
				JOptionPane.showMessageDialog(null,"A airplane information has been added successfully");

				//table_ticket.refresh();



			}

			catch(Exception e)
			{
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null,"Ops:Enter the data Correctly please");
				txt_NumOfSeats.clear();
				txt_numOfPass.clear();
				txt_DepCity.clear();
				txt_ArrivalCity.clear();
				txt_Airlines.clear();
				txt_TicketPrice.clear();
			}
		}
	}else JOptionPane.showMessageDialog(null," the dates fields are empty ");

	}
	private void insertData(airplane rc) { // to database

		try {


			connectDB();	 
			++size;
			ExecuteStatement("Insert into airplane (FlightNo,DepartureDate,returnDate,Number_of_seats , Number_of_passengers, cityIDFrom, cityIDTo,airlineName,BasePriceOfTicket) values("+size+","+"'"+rc.getDepartureDate()+"','"+rc.getReturnDate()+
					"','"+rc.getNumber_of_seats()+"','"+rc.getNumber_of_passengers()+"',"+Integer.parseInt(rc.getDEPCity())+ ","+Integer.parseInt(rc.getArrivalCity()) + ",'"+rc.getAirlineName()+"',"+rc.getBasePriceOfTicket()+")");

			con.close();
			System.out.println("Connection closed" + data.size());




		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void updateBasePrice(int flightNo, Double BasePrice) {

		try {
			System.out.println("update  airplane set BasePriceOfTicket = '"+ BasePrice + "' where FlightNo = "+flightNo);
			connectDB();
			ExecuteStatement("update  airplane set BasePriceOfTicket = "+BasePrice + " where FlightNo = "+flightNo+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	public void updateNumberOfSeats(int flightNo, String NumbOfSeats) {

		try {
			System.out.println("update  airplane set Number_of_seats = "+NumbOfSeats + " FlightNo  = "+flightNo);
			connectDB();
			ExecuteStatement("update  airplane set Number_of_seats = "+NumbOfSeats + " where FlightNo = "+flightNo+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	public void updateNumberOfPassenger(int flightNo, String numberOfPass) {

		try {
			System.out.println("update  airplane set Number_of_passengers = "+numberOfPass + " FlightNo  = "+flightNo);
			connectDB();
			ExecuteStatement("update  airplane set Number_of_passengers = "+numberOfPass + " where FlightNo = "+flightNo+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void updateDepCity(int flightNo, String DepCity) {
		try {
			System.out.println("update  airplane set cityIdFrom = "+ DepCity + " where FlightNo = "+flightNo);
			connectDB();
			ExecuteStatement("update  airplane set cityIdFrom = "+DepCity + " where FlightNo = "+flightNo+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null,"please enter the CITY ID instead ( check the city table for CITY IDs).");

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,"please enter the CITY ID instead ( check the city table for CITY IDs).");
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null,"please enter the CITY ID instead ( check the city table for CITY IDs).");

		}
	}

	public void updateArrivalCity(int flightNo, String ArrivalCity) {

		try {
			System.out.println("update  airplane set cityIDTo = "+ ArrivalCity + " where FlightNo = "+flightNo);
			connectDB();
			ExecuteStatement("update  airplane set cityIDTo = "+ArrivalCity + " where FlightNo = "+flightNo+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"please enter the CITY ID instead ( check the city table for CITY IDs).");


		} catch (ClassNotFoundException e) {

			JOptionPane.showMessageDialog(null,"please enter the CITY ID instead ( check the city table for CITY IDs).");

		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null,"please enter the CITY ID instead ( check the city table for CITY IDs).");

		}
	}

	public void updateAirlineName(int flightNo, String airlineName) {

		try {
			System.out.println("update  airplane set airlineName = '"+ airlineName + "' where flightNo = "+flightNo);
			connectDB();
			ExecuteStatement("update  airplane set airlineName = '"+airlineName + "' where flightNo = "+flightNo+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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

		con = DriverManager.getConnection (dbURL, p);


	}

	public void ExecuteStatement(String SQL) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();


		}
		catch(SQLException s) {
			//s.printStackTrace();
			JOptionPane.showMessageDialog(null,"Please check that you entered the CITY ID instead ( check the city table for CITY IDs).");
			System.out.println("SQL statement is not executed!");

		}
	}

	@FXML
	void LogOutBackToUser(ActionEvent event) throws IOException {

		//URL cardURL = getClass().getResource("/application/Cards.fxml");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

	}

	@FXML
	void GoToHotel(ActionEvent event) throws IOException {

		//URL cardURL = getClass().getResource("/application/Cards.fxml");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHotel.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

	}

	@FXML
	void GoToAirplaneAdmin(ActionEvent event) throws IOException {

		//URL cardURL = getClass().getResource("/application/Cards.fxml");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminInMain.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load(),1200,550);
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

	}
	@FXML
	void goToAdminCars(ActionEvent event) throws IOException {
		//URL cardURL = getClass().getResource("/application/Cards.fxml");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCars.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load(),1200,550);
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);
	}

	@FXML
	void goToAdminCities(ActionEvent event) throws IOException {
		//URL cardURL = getClass().getResource("/application/Cards.fxml");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCity.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);

	}

	@FXML
    void goToAdminInvoices(ActionEvent event) throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminInvoice.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);
    }

}

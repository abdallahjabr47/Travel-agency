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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class AdminInInvoice implements Initializable{
	
	private ArrayList<AdminInv> data;
	private ObservableList<AdminInv> dataList;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	private Connection con;
	int size;
	
	


	@FXML
	private TableView<AdminInv> table_Inv;

	@FXML
	private TableColumn<AdminInv, Integer> col_invId;

	@FXML
	private TableColumn<AdminInv, Date> col_CurrentDate;

	@FXML
	private TableColumn<AdminInv, Double> col_FlightPrice;

	@FXML
	private TableColumn<AdminInv, Double> col_HotelPrice;

	@FXML
	private TableColumn<AdminInv, Double> col_CarPrice;

	@FXML
	private TableColumn<AdminInv, Double> col_totalPrice;

	@FXML
	private TableColumn<AdminInv, Integer> col_PassportNumber;

	@FXML
	private TableColumn<AdminInv, String> col_FirstName;

	@FXML
	private TableColumn<AdminInv, String> col_LastName;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	
		data = new ArrayList<>();
		try {
			getData();	
			dataList = FXCollections.observableArrayList(data); 

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//EditTable();
		table_Inv.setItems(dataList);

		showInvoice();

	}

	
	private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL ="SELECT i.inv_id_number,i.Currentdate,i.priceFlight,i.priceHotel,i.priceCar,i.toatlPrice,i.Passport_NumberINV, c.First_Name,c.Last_name "
				+ " FROM invoice i ,customer c"
				+ " where c.passport_Number = i.passport_NumberINV";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);






		while (rs.next()) {
			data.add(new AdminInv(
					Integer.parseInt(rs.getString(1)), 
					Date.valueOf(rs.getString(2)),
					Double.parseDouble(rs.getString(3)), 
					Double.parseDouble(rs.getString(4)), 
					Double.parseDouble(rs.getString(5)), 
					Double.parseDouble(rs.getString(6)),
					Integer.parseInt(rs.getString(7)),
						rs.getString(8),
						rs.getString(9)));
		}


		rs.close();
		stmt.close();
		size = data.size();
		con.close();
		System.out.println("Connection closed" + data.size());


	}

	public void showInvoice()
	{

		col_invId.setCellValueFactory(new PropertyValueFactory<AdminInv, Integer>("invId")); 
		col_CurrentDate.setCellValueFactory(new PropertyValueFactory<AdminInv, Date>("currentDate"));
		col_FlightPrice.setCellValueFactory(new PropertyValueFactory<AdminInv, Double>("flightPr"));
		col_HotelPrice.setCellValueFactory(new PropertyValueFactory<AdminInv,Double>("hotelPr"));       
		col_CarPrice.setCellValueFactory(new PropertyValueFactory<AdminInv, Double>("carPr"));
		col_totalPrice.setCellValueFactory(new PropertyValueFactory<AdminInv,Double>("totPr")); 
		col_PassportNumber.setCellValueFactory(new PropertyValueFactory<AdminInv, Integer>("pasNum"));
		col_FirstName.setCellValueFactory(new PropertyValueFactory<AdminInv, String>("fname"));
		col_LastName.setCellValueFactory(new PropertyValueFactory<AdminInv, String>("lname"));

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




	
	@FXML
	void GoToAdminCities(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCity.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);
	}

	@FXML
	void GoToAdminTickets(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminInMain.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);
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
	void goToAdminCars(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCars.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);
	}

	@FXML
	void goToAdminHotels(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHotel.fxml"));
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

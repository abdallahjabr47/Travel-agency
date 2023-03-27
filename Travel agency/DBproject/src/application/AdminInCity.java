package application;
//Yazan Daibes 1180414

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class AdminInCity implements Initializable{

	private ArrayList<AdminCity> data;
	private ObservableList<AdminCity> dataList;
	int size;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	private Connection con;

	@FXML
	private TextField txt_cityName;

	@FXML
	private TextField txt_countryName;

	@FXML
	private Button AddButton;

	@FXML
	private Button delete_Button;

	@FXML
	private DatePicker txt_rentDate;

	@FXML
	private TableView<AdminCity> table_car;

	@FXML
	private TableColumn<AdminCity, Integer> col_CityId;

	@FXML
	private TableColumn<AdminCity, String> col_CityName;

	@FXML
	private TableColumn<AdminCity, String> col_CountryName;


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

		EditTable();
		showCity();
	}

	private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL ="SELECT * FROM city ORDER BY CITYNAME";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while(rs.next())
			data.add(new AdminCity(
					Integer.parseInt(rs.getString(1)), 
					rs.getString(2),
					rs.getString(3)
					));




		rs.close();
		stmt.close();
		size = data.size()+1;
		con.close();
		System.out.println("Connection closed" + data.size());


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


	public void showCity()
	{

		col_CityId.setCellValueFactory(new PropertyValueFactory<AdminCity, Integer>("cityId")); 
		col_CityName.setCellValueFactory(new PropertyValueFactory<AdminCity, String>("cityName"));
		col_CountryName.setCellValueFactory(new PropertyValueFactory<AdminCity, String>("countryName"));


	}
	public  void EditTable()
	{
		table_car.setEditable(true);

		col_CityName.setCellFactory(TextFieldTableCell.<AdminCity>forTableColumn());
		col_CountryName.setCellFactory(TextFieldTableCell.<AdminCity>forTableColumn());


		col_CityName.setOnEditCommit(        
				(CellEditEvent<AdminCity, String> t) -> {
					((AdminCity) t.getTableView().getItems().get(
							t.getTablePosition().getRow())
							).setCityName(t.getNewValue()); //display only
							updateCityName( t.getRowValue().getCityId(),t.getNewValue()); // update on DB 
				});


		col_CountryName.setOnEditCommit(        
				(CellEditEvent<AdminCity, String> t) -> {
					((AdminCity) t.getTableView().getItems().get(
							t.getTablePosition().getRow())
							).setCountryName(t.getNewValue()); //display only
					updateCountryName( t.getRowValue().getCityId(),t.getNewValue()); // update on DB 
				});


		table_car.setItems(dataList);
	}

	@FXML
	void Add_City() {

		try {
			AdminCity rc;
			rc = new AdminCity(
					size+1,
					txt_cityName.getText(),
					txt_countryName.getText()						
					);


			connectDB();
			System.out.println("Connection established");

			con.close();
			System.out.println("Connection closed" + data.size());


			dataList.add(rc);
			table_car.setItems(dataList);

			insertData(rc);

			txt_cityName.clear();
			txt_countryName.clear();
			JOptionPane.showMessageDialog(null,"A City information has been added successfully");


		}

		catch(Exception e)
		{
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Ops:Enter the data Correctly please");
			txt_cityName.clear();
			txt_countryName.clear();
		}
	}

	private void insertData(AdminCity rc) { // to database

		try {


			connectDB();	 
			++size;
			ExecuteStatement("Insert into city (cityid,CityName,CountryName) values("+size+","+"'"+rc.getCityName()+"','"+rc.getCountryName()+"')");

			con.close();
			System.out.println("Connection closed" + data.size());




		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void updateCityName(int cityID, String CityName) {

		try {
			System.out.println("update  city set CityName = '"+ CityName + "' where cityid = "+cityID);
			connectDB();
			ExecuteStatement("update  city set CityName = '"+CityName + "' where cityid = "+cityID+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateCountryName(int cityID, String CountryName) {

		try {
			System.out.println("update  city set CountryName = '"+ CountryName + "' where cityid = "+cityID);
			connectDB();
			ExecuteStatement("update  city set CountryName = '"+CountryName + "' where cityid = "+cityID+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}



	


	@FXML
	void GoToAdminTickets(ActionEvent event) throws IOException {
		//URL cardURL = getClass().getResource("/application/Cards.fxml");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminInMain.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load(),1200,550);
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
		//URL cardURL = getClass().getResource("/application/Cards.fxml");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCars.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);
	}

	@FXML
	void goToAdminHotels(ActionEvent event) throws IOException {
		//URL cardURL = getClass().getResource("/application/Cards.fxml");
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

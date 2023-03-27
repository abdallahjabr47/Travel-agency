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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.fxml.Initializable;

public class AdminInCars implements Initializable {
	
	private ArrayList<AdminCars> data;
	private ObservableList<AdminCars> dataList;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	private Connection con;
	int size;

    @FXML
    private DatePicker txt_rentDate;

    @FXML
    private TextField txt_mileage;

    @FXML
    private TextField txt_plateNo;

    @FXML
    private TextField txt_noOfDays;

    @FXML
    private TextField txt_carBrand;

    @FXML
    private TextField txt_basePrice;

    @FXML
    private TextField txt_dropLocation;

    @FXML
    private TextField txt_pickupLocation;

    @FXML
    private Button AddButton;

    @FXML
    private Button delete_Button;

    @FXML
    private TableView<AdminCars> table_car;
    

    @FXML
    private TableColumn<AdminCars, Integer> col_PlateNo;

    @FXML
    private TableColumn<AdminCars,Double > col_mileage;

    @FXML
    private TableColumn<AdminCars, Date> col_rentalDate;

    @FXML
    private TableColumn<AdminCars, Integer> col_numOfDays;

    @FXML
    private TableColumn<AdminCars, String> col_carBrand;

    @FXML
    private TableColumn<AdminCars, Double> col_basePrice;

    @FXML
    private TableColumn<AdminCars, String> col_dropLocation;

    @FXML
    private TableColumn<AdminCars, String> col_pickupLocation;
	
	
    
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txt_noOfDays.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_noOfDays.setText(oldValue);
			}
		});
		txt_mileage.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_mileage.setText(oldValue);
			}
		});
		txt_plateNo.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_plateNo.setText(oldValue);
			}
		});
		txt_basePrice.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_basePrice.setText(oldValue);
			}
		});
		
		data = new ArrayList<>();
		try {
			getData();	
			dataList = FXCollections.observableArrayList(data); 


			//	table_persons.getColumns().addAll(col_PassportNumber, col_FirstName, col_LastName,col_DoB, col_Email,col_Phone,col_Country);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		EditTable();
		showCar();
	}

	
	private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;

		connectDB();
		System.out.println("Connection established");

		SQL ="SELECT plate_no,mileage,rental_date,noOfDays,car_brand,basePrice,c.cityName DropLocation,cc.cityName PickupLocation "
				+ "FROM car a "
				+ "JOIN city c ON c.cityId = a.drop_Location "
				+ "JOIN city cc ON cc.cityid = a.pickup_location;";
		
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);



		ArrayList<String> cols = new ArrayList<>();
		while (rs.next()) {
			for (int i = 1; i <= 8; i++) {
				cols.add(rs.getString(i));
			}
		}for (int i = 0; i < cols.size(); i=i+8) {
			data.add(new AdminCars(
					Integer.parseInt(cols.get(i)), 
					Double.parseDouble(cols.get(i+1)), 
					Date.valueOf(cols.get(i+2)), 
					Integer.parseInt(cols.get(i+3)), 
					cols.get(i+4), 
					Double.parseDouble(cols.get(i+5)),
					cols.get(i+6),
					cols.get(i+7)));
		}


		rs.close();
		stmt.close();
		size = data.size();
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

	public void showCar()
	{

		col_PlateNo.setCellValueFactory(new PropertyValueFactory<AdminCars, Integer>("plate_no")); 
		col_mileage.setCellValueFactory(new PropertyValueFactory<AdminCars, Double>("mileage"));
		col_numOfDays.setCellValueFactory(new PropertyValueFactory<AdminCars, Integer>("noOfDays"));
		col_carBrand.setCellValueFactory(new PropertyValueFactory<AdminCars,String>("car_brand"));       
		col_basePrice.setCellValueFactory(new PropertyValueFactory<AdminCars, Double>("basePrice"));
		col_dropLocation.setCellValueFactory(new PropertyValueFactory<AdminCars,String>("dropLocation"));
		col_pickupLocation.setCellValueFactory(new PropertyValueFactory<AdminCars,String>("pickupLocation")); 
		col_rentalDate.setCellValueFactory(new PropertyValueFactory<AdminCars, Date>("rentalDate"));

	}
	public  void EditTable()
	{
		table_car.setEditable(true);

		col_mileage.setCellFactory(TextFieldTableCell.<AdminCars,Double>forTableColumn(new DoubleStringConverter()));
		col_numOfDays.setCellFactory(TextFieldTableCell.<AdminCars,Integer>forTableColumn(new IntegerStringConverter()));
		col_carBrand.setCellFactory(TextFieldTableCell.<AdminCars>forTableColumn());
		col_basePrice.setCellFactory(TextFieldTableCell.<AdminCars,Double>forTableColumn(new DoubleStringConverter()));
		col_dropLocation.setCellFactory(TextFieldTableCell.<AdminCars>forTableColumn());
		col_pickupLocation.setCellFactory(TextFieldTableCell.<AdminCars>forTableColumn());

		// update milage
		col_mileage.setOnEditCommit(        
	        		// on double click
	        		(CellEditEvent<AdminCars, Double> t) -> {
	                       ((AdminCars) t.getTableView().getItems().get(
	        	                        t.getTablePosition().getRow())
	        	                        ).setMileage(t.getNewValue()); //display only
	                       updateMilage( t.getRowValue().getPlate_no(),t.getNewValue()); // update on DB 
	        		});
		
		// update number of days
		col_numOfDays.setOnEditCommit(        
        		// on double click
        		(CellEditEvent<AdminCars, Integer> t) -> {
                       ((AdminCars) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setNoOfDays(t.getNewValue()); //display only
                       updateNumOfDays( t.getRowValue().getPlate_no(),t.getNewValue()); // update on DB 
        		});
		
		col_carBrand.setOnEditCommit(        
        		// on double click
        		(CellEditEvent<AdminCars, String> t) -> {
                       ((AdminCars) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setCar_brand(t.getNewValue()); //display only
                       updateCarBrand( t.getRowValue().getPlate_no(),t.getNewValue()); // update on DB 
        		});
		
		col_basePrice.setOnEditCommit(        
        		// on double click
        		(CellEditEvent<AdminCars, Double> t) -> {
                       ((AdminCars) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setBasePrice(t.getNewValue()); //display only
                       updateBaePrice( t.getRowValue().getPlate_no(),t.getNewValue()); // update on DB 
        		});
		
		col_dropLocation.setOnEditCommit(        
        		// on double click
        		(CellEditEvent<AdminCars, String> t) -> {
                       ((AdminCars) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setDropLocation(t.getNewValue()); //display only
                       updateDropLocation( t.getRowValue().getPlate_no(),t.getNewValue()); // update on DB 
        		});
		
		col_pickupLocation.setOnEditCommit(        
        		// on double click
        		(CellEditEvent<AdminCars, String> t) -> {
                       ((AdminCars) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setPickupLocation(t.getNewValue()); //display only
                       updatePickUpLocation( t.getRowValue().getPlate_no(),t.getNewValue()); // update on DB 
        		});
		

		table_car.setItems(dataList);
	}

	public void updateMilage(int plate_no, Double milage) {

		try {
			System.out.println("update  car set mileage = '"+ milage + "' where plate_no = "+plate_no);
			connectDB();
			ExecuteStatement("update  car set mileage = '"+milage + "' where plate_no = "+plate_no+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateNumOfDays(int plate_no, int numOfDays) {

		try {
			System.out.println("update  car set noOfDays = '"+ numOfDays + "' where plate_no = "+plate_no);
			connectDB();
			ExecuteStatement("update  car set noOfDays = '"+numOfDays + "' where plate_no = "+plate_no+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void updateCarBrand(int plate_no, String carBrand) {

		try {
			System.out.println("update  car set car_brand = '"+ carBrand + "' where plate_no = "+plate_no);
			connectDB();
			ExecuteStatement("update  car set car_brand = '"+carBrand + "' where plate_no = "+plate_no+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void updateBaePrice(int plate_no, Double price) {

		try {
			System.out.println("update  car set basePrice = '"+ price + "' where plate_no = "+plate_no);
			connectDB();
			ExecuteStatement("update  car set basePrice = '"+price + "' where plate_no = "+plate_no+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void updateDropLocation(int plate_no, String dropLocation) {

		try {
			System.out.println("update  car set drop_location = '"+ dropLocation + "' where plate_no = "+plate_no);
			connectDB();
			ExecuteStatement("update  car set drop_location = '"+dropLocation + "' where plate_no = "+plate_no+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePickUpLocation(int plate_no, String dpickupLocation) {

		try {
			System.out.println("update  car set pickup_location = '"+ dpickupLocation + "' where plate_no = "+plate_no);
			connectDB();
			ExecuteStatement("update  car set pickup_location = '"+dpickupLocation + "' where plate_no = "+plate_no+";");
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
    void goToCars(ActionEvent event) throws IOException {

		//URL cardURL = getClass().getResource("/application/Cards.fxml");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCars.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
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
    @FXML
	void Add_car() {

		 if(txt_rentDate.getValue()!=null && (txt_rentDate.getValue()).isBefore(LocalDate.now())) {
				JOptionPane.showMessageDialog(null," Adding pick up dates For days before the today's date is NOT allowed!\n");
				txt_mileage.clear();
				txt_noOfDays.clear();
				txt_carBrand.clear();
				txt_basePrice.clear();
				txt_pickupLocation.clear();
				txt_plateNo.clear();
		}
		 else {
		try {
			AdminCars rc;
			rc = new AdminCars(
					Integer.parseInt(txt_plateNo.getText()),
					Double.parseDouble(txt_mileage.getText()),
					Date.valueOf(txt_rentDate.getValue()),
					Integer.parseInt(txt_noOfDays.getText()),
					txt_carBrand.getText(),             
					Double.parseDouble(txt_basePrice.getText()),
					txt_dropLocation.getText(),
					txt_pickupLocation.getText()
				
					);


			connectDB();
			System.out.println("Connection established");

			con.close();
			System.out.println("Connection closed" + data.size());


			dataList.add(rc);
			table_car.setItems(dataList);

			insertData(rc);

			txt_mileage.clear();
			txt_noOfDays.clear();
			txt_carBrand.clear();
			txt_basePrice.clear();
			txt_pickupLocation.clear();
			txt_plateNo.clear();
			txt_dropLocation.clear();

			JOptionPane.showMessageDialog(null,"A car information has been added successfully");

			//table_ticket.refresh();



		}		catch(Exception e)
		{
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Ops:Enter the data Correctly please");
			txt_mileage.clear();
			txt_noOfDays.clear();
			txt_carBrand.clear();
			txt_basePrice.clear();
			txt_pickupLocation.clear();
			txt_plateNo.clear();
			txt_dropLocation.clear();
		}
	}
	}
    private void insertData(AdminCars rc) { // to database

		try {


			connectDB();	 
			++size;
			ExecuteStatement("Insert into car (plate_no,mileage,rental_date,noOfDays , car_brand, basePrice, pickup_location,drop_location) values("+rc.getPlate_no()+",'"+rc.getMileage()+"','"+rc.getRentalDate()+
					"',"+rc.getNoOfDays()+",'"+rc.getCar_brand()+"',"+rc.getBasePrice()+ ","+Integer.parseInt(rc.getPickupLocation()) + ","+Integer.parseInt(rc.getDropLocation())+")");


			con.close();
			System.out.println("Connection closed" + data.size());




		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	@FXML
	void DeleteCars(ActionEvent event) {//from scene builder
		ObservableList<AdminCars> selectedRows = table_car.getSelectionModel().getSelectedItems();
		ArrayList<AdminCars> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			table_car.getItems().remove(row); 
			deleteRow(row); // to delete from DB
			table_car.refresh();
		}); 

	}

	private void deleteRow(AdminCars row) { // from the database
		// TODO Auto-generated method stub

		try {
			System.out.println("delete from  car where plate_no="+row.getPlate_no() + ";");
			connectDB();
			ExecuteStatement("delete from  car where plate_no="+row.getPlate_no() + ";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}	

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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class AdminInHotel implements Initializable {
	private ArrayList<Hotel> data;
	private ObservableList<Hotel> dataList;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	private Connection con;

	@FXML
	private TableView<Hotel> table_hotel;

	@FXML
	private TableColumn<Hotel, Integer> col_HotelId;

	@FXML
	private TableColumn<Hotel, String> col_HotelName;

	@FXML
	private TableColumn<Hotel, String> Col_CityID;

	@FXML
	private TableColumn<Hotel, Integer> Col_NumberOfRESERVED;

	@FXML
	private TableColumn<Hotel, Integer> col_NumOfSingleRooms;

	@FXML
	private TableColumn<Hotel, Integer> col_NumberOfDoubleRooms;

	@FXML
	private TableColumn<Hotel, Integer> col_NumberOfSweets;

	@FXML
	private TableColumn<Hotel, Double> col_BasePrice;
	  @FXML
	    private DatePicker txt_checkIn;

	    @FXML
	    private DatePicker txt_checkout;

	    @FXML
	    private TextField txt_cityId;

	    @FXML
	    private TextField txt_HotelName;

	    @FXML
	    private TextField txt_NumOFsingle;

	    @FXML
	    private TextField txt_NumOFdOUBLE;

	    @FXML
	    private TextField txt_NumOFsweets;

	    @FXML
	    private TextField txt_price;

	    @FXML
	    private Button AddButton;

	    @FXML
	    private Button delete_Button;
		int size;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)  {
		txt_cityId.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_cityId.setText(oldValue);
			}
		});

		txt_NumOFsingle.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_NumOFsingle.setText(oldValue);
			}
		});
		
		txt_NumOFdOUBLE.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_NumOFdOUBLE.setText(oldValue);
			}
		});
		txt_NumOFsweets.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_NumOFsweets.setText(oldValue);
			}
		});
		
		txt_price.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_price.setText(oldValue);
			}
		});
		
		
		data = new ArrayList();
		try {
			getData();
			 dataList = FXCollections.observableArrayList(data);
			 //table_hotel.getColumns().addAll(col_HotelId,Col_NumberOfRESERVED,col_NumOfSingleRooms,col_NumberOfDoubleRooms,col_NumberOfSweets,col_HotelName,Col_CityID,col_BasePrice);
		} catch (Exception e) {
			// TODO: handle exception
		}

		EditTable();
		showHotel();

	}

	public void showHotel()
	{

		col_HotelId.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("hotelId")); 
		Col_NumberOfRESERVED.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("reservedNum"));
		col_NumOfSingleRooms.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("singleNum"));
		col_NumberOfDoubleRooms.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("doubleNum"));       
		col_NumberOfSweets.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("sweetNum"));
		col_HotelName.setCellValueFactory(new PropertyValueFactory<Hotel, String>("hotelName"));
		Col_CityID.setCellValueFactory(new PropertyValueFactory<Hotel, String>("cityIdHotel")); 
		col_BasePrice.setCellValueFactory(new PropertyValueFactory<Hotel, Double>("basePrice"));
		//	EditTable();

	}

	public  void EditTable()
	{
		table_hotel.setEditable(true);


		Col_NumberOfRESERVED.setCellFactory(TextFieldTableCell.<Hotel,Integer>forTableColumn(new IntegerStringConverter()));
		col_NumOfSingleRooms.setCellFactory(TextFieldTableCell.<Hotel,Integer>forTableColumn(new IntegerStringConverter()));
		col_NumberOfDoubleRooms.setCellFactory(TextFieldTableCell.<Hotel,Integer>forTableColumn(new IntegerStringConverter()));
		col_NumberOfSweets.setCellFactory(TextFieldTableCell.<Hotel,Integer>forTableColumn(new IntegerStringConverter()));
		col_HotelName.setCellFactory(TextFieldTableCell.<Hotel>forTableColumn());
		Col_CityID.setCellFactory(TextFieldTableCell.<Hotel>forTableColumn());
		col_BasePrice.setCellFactory(TextFieldTableCell.<Hotel,Double>forTableColumn(new DoubleStringConverter()));


//		Col_NumberOfRESERVED.setOnEditCommit(        
//	        		// on double click
//	        		(CellEditEvent<Hotel, Integer> t) -> {
//	                       ((Hotel) t.getTableView().getItems().get(
//	        	                        t.getTablePosition().getRow())
//	        	                        ).setReservedNum((t.getNewValue())); //display only
//	                       updateReservedNum( t.getRowValue().getHotelId(),t.getNewValue()); // update on DB 
//	        		});
		
		col_NumOfSingleRooms.setOnEditCommit(        
        		// on double click
        		(CellEditEvent<Hotel, Integer> t) -> {
                       ((Hotel) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setSingleNum(((t.getNewValue()))); //display only
                       updateSingleNum( t.getRowValue().getHotelId(),t.getNewValue()); // update on DB 
        		});
		
		
		col_NumberOfDoubleRooms.setOnEditCommit(        
        		// on double click
        		(CellEditEvent<Hotel, Integer> t) -> {
                       ((Hotel) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setDoubleNum(((t.getNewValue()))); //display only
                       updateDoubleNum( t.getRowValue().getHotelId(),t.getNewValue()); // update on DB 
        		});
		
		col_NumberOfSweets.setOnEditCommit(        
        		// on double click
        		(CellEditEvent<Hotel, Integer> t) -> {
                       ((Hotel) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setSweetNum(((t.getNewValue()))); //display only
                       updateSweetNum( t.getRowValue().getHotelId(),t.getNewValue()); // update on DB 
        		});
		
		col_HotelName.setOnEditCommit(        
        		// on double click
        		(CellEditEvent<Hotel, String> t) -> {
                       ((Hotel) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setHotelName(((t.getNewValue()))); //display only
                       updateHotelName( t.getRowValue().getHotelId(),t.getNewValue()); // update on DB 
        		});
		
		
		Col_CityID.setOnEditCommit(        
        		// on double click
        		(CellEditEvent<Hotel, String> t) -> {
                       ((Hotel) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setCityIdHotel(((t.getNewValue()))); //display only
                       updateCityID( t.getRowValue().getHotelId(),t.getNewValue()); // update on DB 
        		});
		
		
		
		col_BasePrice.setOnEditCommit(        
        		// on double click
        		(CellEditEvent<Hotel, Double> t) -> {
                       ((Hotel) t.getTableView().getItems().get(
        	                        t.getTablePosition().getRow())
        	                        ).setBasePrice(((t.getNewValue()))); //display only
                       updatePrice( t.getRowValue().getHotelId(),t.getNewValue()); // update on DB 
        		});

		
		table_hotel.setItems(dataList);
	}

	  @FXML
	    void GoToHotel(ActionEvent event) throws IOException {
	    	//URL cardURL = getClass().getResource("/application/Cards.fxml");
	    			FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHotel.fxml"));
	    			//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
	    			Scene di = new Scene(loader.load(),1200,550);
	    			Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
	    			current.setScene(di);
	    }

	
	public void updateReservedNum(int hotelID, int reservedNum) {

		try {
			System.out.println("update  hotel set numberOfReservedRooms = '"+ reservedNum + "' where Hotelid = "+hotelID);
			connectDB();
			ExecuteStatement("update  hotel set numberOfReservedRooms = '"+reservedNum + "' where Hotelid = "+hotelID+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void updateSingleNum(int hotelID, int singleNum) {

		try {
			System.out.println("update  hotel set numberOfSingleRooms = '"+ singleNum + "' where Hotelid = "+hotelID);
			connectDB();
			ExecuteStatement("update  hotel set numberOfSingleRooms = '"+singleNum + "' where Hotelid = "+hotelID+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void updateDoubleNum(int hotelID, int doubleNum) {

		try {
			System.out.println("update  hotel set numberOfDoubleRooms = '"+ doubleNum + "' where Hotelid = "+hotelID);
			connectDB();
			ExecuteStatement("update  hotel set numberOfDoubleRooms = '"+doubleNum + "' where Hotelid = "+hotelID+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void updateHotelName(int hotelID, String hotelName) {

		try {
			System.out.println("update  hotel set hotel_name = '"+ hotelName + "' where Hotelid = "+hotelID);
			connectDB();
			ExecuteStatement("update  hotel set hotel_name = '"+hotelName + "' where Hotelid = "+hotelID+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void updateSweetNum(int hotelID, int sweetNum) {

		try {
			System.out.println("update  hotel set numberOfSweets = '"+ sweetNum + "' where Hotelid = "+hotelID);
			connectDB();
			ExecuteStatement("update  hotel set numberOfSweets = "+sweetNum + " where Hotelid = "+hotelID+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void updateCityID(int hotelID, String cityID) {

		try {
			System.out.println("update  hotel set cityidHotel = '"+ cityID + "' where Hotelid = "+hotelID);
			connectDB();
			ExecuteStatement("update  hotel set cityidHotel = "+cityID + " where Hotelid = "+hotelID+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public void updatePrice(int hotelID, Double price) {

		try {
			System.out.println("update  hotel set base_price = '"+ price + "' where Hotelid = "+hotelID);
			connectDB();
			ExecuteStatement("update  hotel set base_price = "+price + " where Hotelid = "+hotelID+";");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@FXML
	void Add_Hotel() {
		
	
			try {
				Hotel rc;
				rc = new Hotel(
						size+1,
						txt_cityId.getText(),
						txt_HotelName.getText(),
						Integer.parseInt(txt_NumOFsweets.getText()),
						Integer.parseInt(txt_NumOFdOUBLE.getText()),
						Integer.parseInt(txt_NumOFsingle.getText()),
						Double.parseDouble(txt_price.getText())
						
						);


				connectDB();
				System.out.println("Connection established");

				con.close();
				System.out.println("Connection closed" + data.size());


				dataList.add(rc);
				table_hotel.setItems(dataList);

				insertData(rc);

				txt_cityId.clear();
				txt_HotelName.clear();
				txt_NumOFsingle.clear();
				txt_NumOFdOUBLE.clear();
				txt_NumOFsweets.clear();
				txt_price.clear();
				JOptionPane.showMessageDialog(null,"A hotel information has been added successfully");




			}

			catch(Exception e)
			{
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null,"Ops:Enter the data Correctly please");
				txt_cityId.clear();
				txt_HotelName.clear();
				txt_NumOFsingle.clear();
				txt_NumOFdOUBLE.clear();
				txt_NumOFsweets.clear();
				txt_price.clear();
			}
		}

	
	
	private void insertData(Hotel rc) { // to database

		try {


			connectDB();	 
			++size;
			ExecuteStatement("Insert into Hotel (Hotelid,numberOfReservedRooms,cityidHotel,hotel_name , numberOfSingleRooms, numberOfDoubleRooms, numberOfSweets,base_price) values("+size+","+rc.getReservedNum()+","+rc.getCityIdHotel()+
					",'"+rc.getHotelName()+"',"+rc.getSingleNum()+","+rc.getDoubleNum()+ ","+rc.getSweetNum() + ","+rc.getBasePrice()+")");

			con.close();
			System.out.println("Connection closed" + data.size());




		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	private void getData() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String SQL;
		ArrayList<String> cols = new ArrayList<>();
		connectDB();
		System.out.println("Connection established");
		SQL = "select h.hotelid,h.numberOfReservedRooms,c.cityName,h.hotel_name,h.numberOfSweets,h.numberOfDoubleRooms,h.numberOfSingleRooms,h.base_price from hotel h join City c on h.cityidhotel=c.cityid;";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			for (int i = 1; i <= 8; i++) {
				cols.add(rs.getString(i));
			}
		}for (int i = 0; i < cols.size(); i=i+8) {
			System.out.println(Integer.parseInt(cols.get(i))+" " + Integer.parseInt(cols.get(i+1))+" "+cols.get(i+2)+ " "+ cols.get(i+3)+ " "+ Integer.parseInt(cols.get(i+4))+" "+ Integer.parseInt(cols.get(i+5))+" "+ Integer.parseInt(cols.get(i+6))+" "+Double.parseDouble(cols.get(i+7)));
			data.add(new Hotel(Integer.parseInt(cols.get(i)), Integer.parseInt(cols.get(i+1)), cols.get(i+2), cols.get(i+3), Integer.parseInt(cols.get(i+4)), Integer.parseInt(cols.get(i+5)), Integer.parseInt(cols.get(i+6)),Double.parseDouble(cols.get(i+7))));
		}


		rs.close();
		stmt.close();
		size = data.size()+1;

		con.close();
		System.out.println("Connection closed" + data.size());


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

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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CardsControllerForCars implements Initializable {
	

    @FXML
    private HBox cardHBox;

    @FXML
    private Text carTitle;

    @FXML
    private Text carLocation;

    @FXML
    private Text carMileage;

    @FXML
    private Text carPrice;

    @FXML
    private Button carDetails;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	private Connection con;
	static ArrayList<Car> carData=new ArrayList<Car>();
	static ArrayList<String> cityNamesList=new ArrayList<String>();
	private Car currentCar=new Car();
	private String pickLoc = new String();


	public CardsControllerForCars() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void initData() throws ClassNotFoundException, SQLException  {
		connectToCars();
		if(cityNamesList.isEmpty())
			connectToCities();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if(CarsController.cardNo==-1)
			try {
				initData();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(CarsController.cardNo!=-1)
		{			
			currentCar = carData.get(CarsController.cardNo);
			carTitle.setText(currentCar.getCar_brand());
			carMileage.setText("Mileage: "+Double.toString(currentCar.getMileage()));
			carPrice.setText("Cost Per Day: "+currentCar.getBasePrice()) ;
			try {
				connectToCities(currentCar.getPickup_location());
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		//	carLocation.setText(currentCar.getPickup_location());
		}

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

	public void connectToCars() throws SQLException, ClassNotFoundException{
		ArrayList<String> cols = new ArrayList<>();
		connectDB();
		carData.clear();
		System.out.println("Connection Established");
		//String SQL = "SELECT * FROM Hotel";
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(CarsController.sqlQuery);
		while (rs.next()) {
			for (int i = 1; i <= 6; i++) {
				cols.add(rs.getString(i));
			}
		}for (int i = 0; i < cols.size(); i=i+6) {
		carData.add(new Car(cols.get(i), Integer.parseInt(cols.get(i+1)), Integer.parseInt(cols.get(i+2)), Double.parseDouble(cols.get(i+3)), Double.parseDouble(cols.get(i+4)), Integer.parseInt(cols.get(i+5))));
		
		

		}
		rs.close();
		stmt.close();
		con.close();
		System.out.println("Connection Closed");
	}

	private void connectToCities() throws ClassNotFoundException, SQLException {
		connectDB();
		System.out.println("Connection Established");
		String SQL = "SELECT * FROM City";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			cityNamesList.add(rs.getString(2));
			System.out.println(rs.getString(2));

		}
		
		
	
		rs.close();
		stmt.close();
		con.close();
		System.out.println("Connection Closed");

	}
	
	private void connectToCities(int cityID) throws ClassNotFoundException, SQLException {
		connectDB();
		System.out.println("Connection Established");
		String SQL = "SELECT * FROM City";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			cityNamesList.add(rs.getString(2));
			System.out.println(rs.getString(2));

		}
		
		
		String SQL2 = "SELECT cityName FROM CITY WHERE  cityID = "+ cityID +";"; // to get the pcik up location
		Statement stmt2 = con.createStatement();
		ResultSet rs2 = stmt2.executeQuery(SQL2);
		rs2.next();
		carLocation.setText(rs2.getString(1));
		pickLoc = carLocation.getText();
		rs2.close();
		stmt2.close();
		rs.close();
		stmt.close();
		con.close();
		System.out.println("Connection Closed");

	}

	@FXML
	void handleDetails(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/CarDetails.fxml"));
        Scene di = new Scene(loader.load());
        Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
        current.setScene(di);
		CarsDetailsController controller =loader.getController();
		controller.initDataCar(currentCar,pickLoc);
	}

}

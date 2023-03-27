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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CardsControllerForHotels implements Initializable{
	@FXML
	private Text cardTitle;
	@FXML
	private Text cardLocation;
	@FXML
	private Text cardRating;
	@FXML
	private Text cardDescription;
	@FXML
	private Button cardDetails;
	@FXML
	private ImageView cardPhoto=new ImageView();
	@FXML
	private HBox cardHBox;
	@FXML
	private AnchorPane cardAnchor;

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	static ArrayList<Hotel> hotelData=new ArrayList<Hotel>();
	static ArrayList<String> cityNamesList=new ArrayList<String>();
	private static Connection con;
	private Hotel currentHotel=new Hotel();


	public CardsControllerForHotels() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void initData() throws ClassNotFoundException, SQLException  {
		connectToHotel();
		if(cityNamesList.isEmpty())
			connectToCities();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
				// TODO Auto-generated method stub
				if(HotelReservationController.cardNo==-1)
					try {
						initData();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(HotelReservationController.cardNo!=-1)
				{
					currentHotel = hotelData.get(HotelReservationController.cardNo);
					cardTitle.setText(currentHotel.getHotelName());
					cardLocation.setText(currentHotel.getCityIdHotel());
					cardRating.setText(Integer.toString(currentHotel.getDoubleNum()+currentHotel.getSingleNum()+currentHotel.getSweetNum())+" Rooms Left");
					cardDescription.setText("Price For a Single Room For 1 Night = "+String.format("%.2f", currentHotel.getBasePrice())+"$\n"
							+ "Prie For a Double Room For 1 Night = "+String.format("%.2f", currentHotel.getBasePrice()*1.5)+"$\n"
							+ "Price For a Sweet For 1 Night = "+String.format("%.2f", currentHotel.getBasePrice()*2.5)+"$");

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

	public void connectToHotel() throws SQLException, ClassNotFoundException{
		ArrayList<String> cols = new ArrayList<>();
		connectDB();
		hotelData.clear();
		System.out.println("Connection Established");
		//String SQL = "SELECT * FROM Hotel";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(HotelReservationController.sqlQuery);
		while (rs.next()) {
			for (int i = 1; i <= 8; i++) {
				cols.add(rs.getString(i));
			}
		}for (int i = 0; i < cols.size(); i=i+8) {
			System.out.println(Integer.parseInt(cols.get(i))+" " + Integer.parseInt(cols.get(i+1))+" "+cols.get(i+2)+ " "+ cols.get(i+3)+ " "+ Integer.parseInt(cols.get(i+4))+" "+ Integer.parseInt(cols.get(i+5))+" "+ Integer.parseInt(cols.get(i+6))+" "+Double.parseDouble(cols.get(i+7)));
			hotelData.add(new Hotel(Integer.parseInt(cols.get(i)), Integer.parseInt(cols.get(i+1)), cols.get(i+2), cols.get(i+3), Integer.parseInt(cols.get(i+4)), Integer.parseInt(cols.get(i+5)), Integer.parseInt(cols.get(i+6)),Double.parseDouble(cols.get(i+7))));
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

	@FXML
	void handleDetails(ActionEvent event) throws IOException {
		//URL cardURL = getClass().getResource("/application/Cards.fxml");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/HotelDetails.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);
		DetailsController controller =loader.getController();
		controller.initData(currentHotel);
	}
}

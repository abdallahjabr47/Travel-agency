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

public class CardsController implements Initializable{
	@FXML
	private Text cardTitle;
	@FXML
	private Text cardFromCity;
	@FXML
	private Text cardToCity;
	@FXML
	private Text cardDescription;
	@FXML
	private Button cardDetails;
	@FXML
	private ImageView cardPhoto;
	@FXML
	private HBox cardHBox;
	@FXML
	private AnchorPane cardAnchor;

	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	private Connection con;
	//static ArrayList<Hotel> hotelData=new ArrayList<Hotel>();
	static ArrayList<airplane> aiplaneData=new ArrayList<airplane>();
	static ArrayList<String> cityNamesList=new ArrayList<String>();
	airplane currentAirplane = new airplane();


	public CardsController() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void initData() throws ClassNotFoundException, SQLException  {
		connectToAirplane();
		if(cityNamesList.isEmpty())
			connectToCities();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		if(flightController.cardNo==-1)
		{
			try {
				initData();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(flightController.cardNo!=-1)
		{
			currentAirplane = aiplaneData.get(flightController.cardNo);

			cardTitle.setText(aiplaneData.get(flightController.cardNo).getAirlineName());
			cardFromCity.setText("From: "+(aiplaneData.get(flightController.cardNo).getDEPCity()));
			cardToCity.setText("To: "+(aiplaneData.get(flightController.cardNo).getArrivalCity()));
			cardDescription.setText("Departure Date: "+(aiplaneData.get(flightController.cardNo).getDepartureDate())+"\n"
					+ "Return Date: "+aiplaneData.get(flightController.cardNo).getReturnDate() + "\n\n Price = "+aiplaneData.get(flightController.cardNo).getBasePriceOfTicket() +"$");

		}

	}

	@FXML
	void AirplaneDetails(ActionEvent event) throws IOException {

		System.out.println("Test Controller "+cardTitle.getText());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/flightDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);
		flightDetailsController controller =loader.getController();
		controller.initData(currentAirplane);

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
	public void connectToAirplane() throws SQLException, ClassNotFoundException{
		//ArrayList<String> cols = new ArrayList<>();
		connectDB();
		aiplaneData.clear();
		System.out.println("Connection Established");


		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(flightController.sqlQuery);


		while (rs.next()) {
			aiplaneData.add(new airplane(
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
}

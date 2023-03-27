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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CarsController implements Initializable {

	@FXML
	private ComboBox<String> cbCar=new ComboBox<String>();

	@FXML
	private Button buttom;

	@FXML
	private VBox vbCar;

	static int cardNo=-1;
	static String sqlQuery= " ";

	
	String cityNameD;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initData();

	}
	private void initData() {

		// filling in the cards
		cardNo = -1;
		URL cardURL = getClass().getResource("/application/CardsForCars.fxml");
		sqlQuery = "select c.car_brand,c.plate_no,c.pickup_location,c.mileage,c.basePrice,c.noOfDays from car c join City ci on c.pickup_location=ci.cityid ;";


		try {
			if (cardNo == -1) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/CardsForCars.fxml"));
				loader.load();
				++cardNo;
			}

			for (; cardNo < CardsControllerForCars.carData.size(); ++cardNo) {
				Parent cardAnchor = FXMLLoader.load(cardURL);
				cardAnchor = FXMLLoader.load(cardURL);
				vbCar.getChildren().add(cardAnchor);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// filling in the Cities combo box
		ArrayList<String> d2 = new ArrayList<String>();
		for (int i = 0; i < CardsControllerForCars.cityNamesList.size(); i++) {
			d2.add(CardsControllerForCars.cityNamesList.get(i));
		}
		ObservableList<String> data2;
		data2 = FXCollections.observableArrayList(d2);
		cbCar.setItems(data2);


	}



	@FXML
	void searchForCity(ActionEvent event) {

		String hCar = cbCar.getValue();
		StringBuilder errorMessage = new StringBuilder("Error! ");
		boolean existError = false;
		if(hCar ==null) {
			errorMessage.append("You Should Add a City!\n");
			existError = true;
		}
		if(existError) {
			Alert error = new Alert(AlertType.ERROR);
			error.setHeaderText("Missing Inputs");
			error.setContentText(errorMessage.toString());
			error.showAndWait();
		}else {
			vbCar.getChildren().clear();
			cardNo = -1;
			URL cardURL = getClass().getResource("/application/CardsForCars.fxml");
			sqlQuery = "select c.car_brand,c.plate_no,c.pickup_location,c.mileage,c.basePrice,c.noOfDays from car c join City ci on c.pickup_location=ci.cityid WHERE ci.cityName = '"+ hCar+"' ;";
			try {
				if (cardNo == -1) {
					FXMLLoader load = new FXMLLoader(getClass().getResource("/application/CardsForCars.fxml"));
					load.load();
					++cardNo;
				}

				for (; cardNo < CardsControllerForCars.carData.size(); ++cardNo) {
					Parent cardAnchor = FXMLLoader.load(cardURL);
					cardAnchor = FXMLLoader.load(cardURL);
					vbCar.getChildren().add(cardAnchor);

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}



	}
	public void OpenMainPage(ActionEvent event)throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
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
	public void OpenCar(ActionEvent event)throws IOException {


		FXMLLoader loader = new FXMLLoader(getClass().getResource("Cars.fxml"));
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
	void changeToFlights(ActionEvent event) throws IOException {
		System.out.println("test");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/flightsController.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);
	}
	
	
	
}


package application;
//Yazan Daibes 1180414

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HotelReservationController implements Initializable{


	@FXML
	private AnchorPane cardAnchor;
	@FXML
	private Button buttonHome;

	@FXML
	private ScrollPane scrollPaneContent;

	@FXML
	private VBox vboxData;

	@FXML
	private ComboBox<String> hotelCity=new ComboBox<>();

	@FXML
	private DatePicker hotelDateFrom=new DatePicker();

	@FXML
	private DatePicker hotelDateTo=new DatePicker();

	@FXML
	private ComboBox<String> hotelNumOfRooms=new ComboBox<>();

	@FXML
	private Button hotelSearch;

	static int cardNo=-1;
	static String sqlQuery= " ";
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initData();

	}
	private void initData() {
		// filling in the Number Of Rooms combo box
		ArrayList<String> d = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			d.add(Integer.toString(i));
		}
		ObservableList<String> data;
		data = FXCollections.observableArrayList(d);
		hotelNumOfRooms.setItems(data);


		// filling in the cards
		cardNo = -1;
		URL cardURL = getClass().getResource("CardsForHotels.fxml");
		sqlQuery = "select h.hotelid,h.numberOfReservedRooms,c.cityName,h.hotel_name,h.numberOfSweets,h.numberOfDoubleRooms,h.numberOfSingleRooms,h.base_price from hotel h join City c on h.cityidhotel=c.cityid;";
		try {
			if (cardNo == -1) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("CardsForHotels.fxml"));
				loader.load();
				++cardNo;
			}

			for (; cardNo < CardsControllerForHotels.hotelData.size(); ++cardNo) {
				Parent cardAnchor = FXMLLoader.load(cardURL);
				cardAnchor = FXMLLoader.load(cardURL);
				vboxData.getChildren().add(cardAnchor);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// filling in the Cities combo box
		ArrayList<String> d2 = new ArrayList<String>();
		for (int i = 0; i < CardsControllerForHotels.cityNamesList.size(); i++) {
			d2.add(CardsControllerForHotels.cityNamesList.get(i));
		}
		ObservableList<String> data2;
		data2 = FXCollections.observableArrayList(d2);
		hotelCity.setItems(data2);


	}



	@FXML
	void openCity(ActionEvent event) {

	}

	@FXML
	void openRooms(ActionEvent event) {

	}

	@FXML
	void search(ActionEvent event) {
		String hCity = hotelCity.getValue();
		System.out.println("hCity= "+hCity);
		StringBuilder errorMessage = new StringBuilder("Error! ");
		boolean existError = false;
		if(hCity ==null) {
			errorMessage.append("You Should Add a City!\n");
			existError = true;
		}
		if(existError) {
			Alert error = new Alert(AlertType.ERROR);
			error.setHeaderText("Missing Inputs");
			error.setContentText(errorMessage.toString());
			error.showAndWait();
		}else {
			vboxData.getChildren().clear();
			cardNo = -1;
			URL cardURL = getClass().getResource("/application/CardsForHotels.fxml");
			sqlQuery = "select h.hotelid,h.numberOfReservedRooms,c.cityName,h.hotel_name,h.numberOfSweets,h.numberOfDoubleRooms,h.numberOfSingleRooms,h.base_price from hotel h join City c on h.cityidhotel=c.cityid WHERE c.cityName = '"+ hCity+"' ;";
			try {
				if (cardNo == -1) {
					FXMLLoader load = new FXMLLoader(getClass().getResource("/application/CardsForHotels.fxml"));
					load.load();
					++cardNo;
				}

				for (; cardNo < CardsControllerForHotels.hotelData.size(); ++cardNo) {

					Parent cardAnchor = FXMLLoader.load(cardURL);
					cardAnchor = FXMLLoader.load(cardURL);
					vboxData.getChildren().add(cardAnchor);

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}


	}

	@FXML
	void changeToFlight(ActionEvent event) throws IOException {
		System.out.println("test");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/flightsController.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
		Scene di = new Scene(loader.load());
		Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
		current.setScene(di);
	}

	@FXML
	void changeToMainPage(ActionEvent event) throws IOException {
		System.out.println("test");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainPage.fxml"));
		//AnchorPane detailsInterface = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/HotelDetails.fxml"));
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
	public void OpenCarRental(ActionEvent event)throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Cars.fxml"));
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

}

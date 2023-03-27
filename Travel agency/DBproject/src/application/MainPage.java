package application;
//Yazan Daibes 1180414


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainPage implements Initializable {

	  @FXML
	    private Button buttonHome;

	    @FXML
	    private Button buttonHotels;

	    @FXML
	    private Button flights;

	    @FXML
	    private Button buttonCarRentals;

	    @FXML
	    private Text welcome;
	    @FXML
	    private Button signIn;
	    
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			
			if(SampleController.currentUser.getFirst_Name() != null && SampleController.currentUser.getLast_Name() != null)
				welcome.setText("Welcome Dear "+SampleController.currentUser.getFirst_Name()+" "+SampleController.currentUser.getLast_Name());
		
		}
		
	    
	    @FXML
		public void OpenFlights(ActionEvent event)throws IOException {
	    	
	    	
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("flightsController.fxml"));
	        Scene di = new Scene(loader.load());
			Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
	        current.setScene(di);
        
		 }

	    
	    @FXML
		public void Openhotels(ActionEvent event)throws IOException {
	    	
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("HotelReservation.fxml"));
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

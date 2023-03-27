package application;
//Yazan Daibes 1180414

import java.io.IOException;
import javafx.scene.Node;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

public class SampleController implements Initializable{
	

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	private Connection con;
	@FXML
    private Button signUp;
	 @FXML
	    private Button signIn;

	    @FXML
	    private TextField passportLogin_txt;

	    @FXML
	    private PasswordField passwordLogin_txt;
	    
	    @FXML
	    private Label welcome_label;

	    @FXML
	    private Button cardButton;
	    static Customer currentUser = new Customer(0, null,null, null);
	    static int invoiceID = -1;
		boolean signed = false; // user didn't sign 

	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		passportLogin_txt.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				passportLogin_txt.setText(oldValue);
			}
		});
			
	}
	

	@FXML
	private void getPassport(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub

		
		System.out.println(	passportLogin_txt.getText().isEmpty());
		//{
		if (!passportLogin_txt.getText().isEmpty() &&  !passwordLogin_txt.getText().isEmpty())
		{
			try {
				
				String SQL;
			connectDB();
			System.out.println("Connection established");
			SQL = "SELECT Passport_Number,First_Name,Last_Name,Passwordd,Email FROM customer WHERE Passport_Number = "+ Integer.parseInt(passportLogin_txt.getText())+
					" AND PASSWORDD = '" +passwordLogin_txt.getText() + "'"  ;	
																													
		
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			 String firstName = null;
			String lastName =null;
	
			int passport = -1;
			String password = null;
			String email = null;
			while ( rs.next() ) 
			{			
				 passport =Integer.parseInt(rs.getString(1));
				 firstName = String.valueOf(rs.getString(2)); 
				 lastName =String.valueOf(rs.getString(3));
				 password = rs.getString(4);
				 System.out.println("First Name = "+ firstName+" Last Name = "+lastName);
				 email = rs.getString(5);
			}
			 currentUser = new Customer (passport,firstName,lastName,email);
	
			rs.close();
			stmt.close();
			
			con.close();
			
			if(passport == -1 || password == null)
				JOptionPane.showMessageDialog(null,"The passport you’ve entered doesn’t match any account.\n Sign up for an account please.");
			
			else {
				System.out.println("PassportNumber = "+passport);
				System.out.println("Password = "+password);		
				
				if(flightDetailsController.SignFirst == false) // the user signed in from the beginning 
				{
					 FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
				     Scene di = new Scene(loader.load());
					 Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
				     current.setScene(di);
				}
				
				else { // the user pressed Sign In or Sign up in the flighDetails ( didn't sign in/up from the beginning )
					FXMLLoader loader = new FXMLLoader(getClass().getResource("flightDetails.fxml"));
				     Scene di = new Scene(loader.load());
					 Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
				     current.setScene(di);
				}
				
			} 
			
			System.out.println("Connection closed");
	
			
			}catch(NullPointerException e)
			{
				JOptionPane.showMessageDialog(null,"Please Enter your passport Number and Password Or sign up .");
				con.close();
				System.out.println("Connection closed");
				e.printStackTrace();
			}
			
		}else JOptionPane.showMessageDialog(null,"Please Enter your passport Number and Password Or sign up .");

		
}
		
	
	static Stage stageSignUp = new Stage();
	// open sign up window
	@FXML
	 public void OpenSignUpWindow()throws IOException {

		
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("SignUpWindow.fxml"));
			stageSignUp.setTitle("Sign up");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stageSignUp.setScene(scene);
			stageSignUp.show();		    
			
		}

	
	@FXML
	 public void OpenMainMenuWindowSkipped(MouseEvent event)throws IOException {
		
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
	     Scene di = new Scene(loader.load());
		 Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
	     current.setScene(di);
	     
	}
	
	@FXML
	 public void OpenSiteAsAdmin(MouseEvent event)throws IOException {
		
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminMainPage.fxml"));
	     Scene di = new Scene(loader.load());
		 Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
	     current.setScene(di);
	     
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
	
	
	
	
	
}

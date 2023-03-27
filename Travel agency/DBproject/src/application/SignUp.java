package application;
//Yazan Daibes 1180414

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUp implements Initializable {
	

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	private Connection con;
    @FXML
    private TextField txt_FirstName;

    @FXML
    private TextField txt_LastName;

    @FXML
    private TextField txt_PassportNumber;

    @FXML
    private DatePicker txt_DOB;

    @FXML
    private TextField txt_Country;

    @FXML
    private TextField txt_nationality;

    @FXML
    private TextField txt_Phone;

    @FXML
    private TextField txt_Email;

    @FXML
    private PasswordField txt_Password;

    @FXML
    private Button signUp_btn;

	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		// takes only numbers
		txt_PassportNumber.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_PassportNumber.setText(oldValue);
			}
		});
		txt_Phone.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				txt_Phone.setText(oldValue);
			}
		});
		
	}
	    @FXML
	    void Add_Customer() throws SQLException, ClassNotFoundException, IOException {
	    	int indicate = 0;
	 try {
		   Customer rc;
       	rc = new Customer(
                   Integer.valueOf(txt_PassportNumber.getText()),
                   txt_FirstName.getText(),
                   txt_LastName.getText(),
                   txt_nationality.getText(),
                   Date.valueOf(txt_DOB.getValue()),        
                   txt_Email.getText(),
                   Integer.valueOf(txt_Phone.getText()),
                   txt_Country.getText(),
                   txt_Password.getText()
                   );
       
       	String SQL;
		
		connectDB();
		System.out.println("Connection established");

		SQL = "SELECT Passport_Number FROM Customer ORDER BY Passport_Number";
		//SQL = "select * from student";

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while ( rs.next() )  //while loop to check if a person has the same id as the entered.
			if (Integer.valueOf(txt_PassportNumber.getText()) ==Integer.parseInt(rs.getString("Passport_Number")))
					{
					
						JOptionPane.showMessageDialog(null,"You already have an account with this passport number");					 	
					 	txt_PassportNumber.clear();
					 	txt_FirstName.clear();
					 	txt_LastName.clear();
					 	txt_Email.clear();
					 	txt_Phone.clear();
					 	txt_Country.clear();
					 	txt_Password.clear();
					 	txt_nationality.clear();
						++indicate;
						SampleController.stageSignUp.close();// close the sign up page.
						break;
					}		
		
						
		rs.close();
		stmt.close();

		con.close();
		System.out.println("Connection closed" );
		if(indicate == 0) // no customer has the entered passport Number then you can insert
		{
		 	insertData(rc);
			
		 	txt_PassportNumber.clear();
		 	txt_FirstName.clear();
		 	txt_LastName.clear();
		 	txt_Email.clear();
		 	txt_Phone.clear();
		 	txt_Country.clear();
		 	txt_Password.clear();
		 	txt_nationality.clear();
			JOptionPane.showMessageDialog(null,"Success: Now Sign in please");
			SampleController.stageSignUp.close();// close the sign up page.
			
		}
		
	 }catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Ops: Please check that the Passport Number, Phone Number are numbers");
			txt_PassportNumber.clear();
		 	txt_FirstName.clear();
		 	txt_LastName.clear();
		 //	txt_DOB.clear();
		 	txt_Email.clear();
		 	txt_Phone.clear();
		 	txt_Country.clear();
		 	txt_Password.clear();
		 	txt_nationality.clear();
		}
 
 }
	    
 private void insertData(Customer rc) { // to database
	 
	     	try {
	     		System.out.println("Insert into Customer values("+rc.getPassport_Number()+",'"+rc.getFirst_Name()+
	    				"','"+rc.getLast_Name()+"','"+rc.getNationality()+"','"+rc.getDoB()+"','"+rc.getEmail()+ "',"+rc.getPhone() + ",'"+rc.getCountry()+"','"+rc.getPassword()+"')");
	     	    			
	    			connectDB();
	  ExecuteStatement("Insert into Customer values("+rc.getPassport_Number()+",'"+rc.getFirst_Name()+
	     				"','"+rc.getLast_Name()+"','"+rc.getNationality()+"','"+rc.getDoB()+"','"+rc.getEmail()+ "',"+rc.getPhone() + ",'"+rc.getCountry()+"','"+rc.getPassword()+"')");
	
	     			con.close();
	     			System.out.println("Connection closed" );

	     			} catch (SQLException e) {
	     				e.printStackTrace();
	     			} catch (ClassNotFoundException e1) {
	     				e1.printStackTrace();
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

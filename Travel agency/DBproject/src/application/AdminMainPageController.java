package application;
//Yazan Daibes 1180414

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AdminMainPageController implements Initializable{

    @FXML
    private TextField ADMINID;

    @FXML
    private PasswordField adminpassword;
    
	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "root1234554321";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "Travel_Agency";
	private Connection con;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ADMINID.textProperty().addListener((observable,oldValue,newValue)->{
			if (!newValue.matches("\\d*")) {
				ADMINID.setText(oldValue);
			}
		});
			
	}
	
	
	
	@FXML
	private void getAdminID(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub

		
		System.out.println(	ADMINID.getText().isEmpty());
		//{
		if (!ADMINID.getText().isEmpty() && !adminpassword.getText().isEmpty())
		{
			try {
				
				String SQL;
			connectDB();
			System.out.println("Connection established");
			SQL = "SELECT employeeNum,employeePassword FROM adminEmployee WHERE employeeNum = "+ Integer.parseInt(ADMINID.getText())+
					" AND employeePassword = '" +adminpassword.getText() + "'"  ;	
																													
		
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			 String firstName = null;
			String lastName =null;
	
			int ADMINIDNUMBER = -1;
			String password = null;
			String email = null;
			while ( rs.next() ) 
			{			
				 ADMINIDNUMBER =Integer.parseInt(rs.getString(1));
				 password = rs.getString(2);
				 System.out.println("First Name = "+ firstName+" Last Name = "+lastName);
			}
	
			rs.close();
			stmt.close();
			
			con.close();
			
			if(ADMINIDNUMBER == -1 || password == null)
				JOptionPane.showMessageDialog(null,"The passport you’ve entered doesn’t match any account.\n Sign up for an account please.");
			
			else {
				System.out.println("PassportNumber = "+ADMINIDNUMBER);
				System.out.println("Password = "+password);		
				
				
					 FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminInMain.fxml"));
				     Scene di = new Scene(loader.load(),1200,550);
					 Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
				     current.setScene(di);
				
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
	
	
	
	
	
	
	
	
	@FXML
	 public void OpenSiteAsUser(MouseEvent event)throws IOException {
		
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
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

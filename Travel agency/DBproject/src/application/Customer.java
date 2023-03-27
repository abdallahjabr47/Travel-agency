package application;
//Yazan Daibes 1180414

import java.sql.Date;



public class Customer {

	
	
	private int Passport_Number;
	private String First_Name;
	private String Last_Name;
	private Date DoB;

	private String Email;
	private int Phone;
	private String Country;
	private String nationality;
	private String passwordd;
	

	
	public Customer(int passport_Number, String first_Name, String last_Name,String email) {
		
		Passport_Number = passport_Number;
		First_Name = first_Name;
		Last_Name = last_Name;
		Email = email;

		
	}
	public Customer(int passport_Number, String first_Name, String last_Name,String nationality,Date doB,String email, int phone,
			String country,String password) {
		super();
		Passport_Number = passport_Number;
		First_Name = first_Name;
		Last_Name = last_Name;
		DoB = doB;
		Email = email;
		Phone = phone;
		Country = country;
		this.nationality = nationality;
		this.passwordd = password;

	}
	public int getPassport_Number() {
		return Passport_Number;
	}
	public void setPassport_Number(int passport_Number) {
		Passport_Number = passport_Number;
	}
	public String getFirst_Name() {
		return First_Name;
	}
	public void setFirst_Name(String first_Name) {
		First_Name = first_Name;
	}
	public String getLast_Name() {
		return Last_Name;
	}
	public void setLast_Name(String last_Name) {
		Last_Name = last_Name;
	}
	public Date getDoB() {
		return DoB;
	}
	public void setDoB(Date doB) {
		DoB = doB;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public int getPhone() {
		return Phone;
	}
	public void setPhone(int phone) {
		Phone = phone;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getPassword() {
		return passwordd;
	}
	public void setPassword(String password) {
		this.passwordd = password;
	}
	

	
}

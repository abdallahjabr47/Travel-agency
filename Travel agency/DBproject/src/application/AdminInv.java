package application;
//Yazan Daibes 1180414

import java.sql.Date;

public class AdminInv {
	int invId;
	Date currentDate;
	double flightPr;
	double hotelPr;
	double carPr;
	double totPr;
	int pasNum;
	String fname;
	String lname;
	public AdminInv() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public AdminInv( int invId,Date currentDate, double flightPr, double hotelPr, double carPr, double totPr,
			int pasNum, String fname, String lname) {
		super();
		this.invId = invId;
		this.currentDate = currentDate;
		this.flightPr = flightPr;
		this.hotelPr = hotelPr;
		this.carPr = carPr;
		this.totPr = totPr;
		this.pasNum = pasNum;
		this.fname = fname;
		this.lname = lname;
	}
	public int getInvId() {
		return invId;
	}
	public void setInvId(int invId) {
		this.invId = invId;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	public double getFlightPr() {
		return flightPr;
	}
	public void setFlightPr(double flightPr) {
		this.flightPr = flightPr;
	}
	public double getHotelPr() {
		return hotelPr;
	}
	public void setHotelPr(double hotelPr) {
		this.hotelPr = hotelPr;
	}
	public double getCarPr() {
		return carPr;
	}
	public void setCarPr(double carPr) {
		this.carPr = carPr;
	}
	public double getTotPr() {
		return totPr;
	}
	public void setTotPr(double totPr) {
		this.totPr = totPr;
	}
	public int getPasNum() {
		return pasNum;
	}
	public void setPasNum(int pasNum) {
		this.pasNum = pasNum;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	
}

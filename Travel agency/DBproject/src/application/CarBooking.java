package application;
//Yazan Daibes 1180414

import java.sql.Date;

public class CarBooking {

	String pickUpLocation;
	String dropLocation;
	double totalPrice;
	Date pickupDate;
	Date dropDate;
	int numOfDays;
	public CarBooking(String pickUpLocation,String dropLocation,double totalPrice, Date pickupDate, int numOfDays,Date dropDate) {
		super();
		this.pickUpLocation=pickUpLocation;
		this.dropLocation=dropLocation;
		this.totalPrice = totalPrice;
		this.pickupDate = pickupDate;
		this.dropDate = dropDate;
		this.numOfDays = numOfDays;
	}
	public CarBooking() {
		super();
		// TODO Auto-generated constructor stub
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getPickupDate() {
		return pickupDate;
	}
	public Date getDropDate() {
		return dropDate;
	}
	public void setDropDate(Date dropDate) {
		this.dropDate = dropDate;
	}
	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}
	public int getNumOfDays() {
		return numOfDays;
	}
	public void setNumOfDays(int numOfDays) {
		this.numOfDays = numOfDays;
	}
	public String getPickUpLocation() {
		return pickUpLocation;
	}
	public void setPickUpLocation(String pickUpLocation) {
		this.pickUpLocation = pickUpLocation;
	}
	public String getDropLocation() {
		return dropLocation;
	}
	public void setDropLocation(String dropLocation) {
		this.dropLocation = dropLocation;
	}
	
	
}

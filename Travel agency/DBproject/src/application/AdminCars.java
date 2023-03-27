package application;
//Yazan Daibes 1180414

import java.sql.Date;

public class AdminCars {
	int plate_no;
	double mileage;
	Date rentalDate;
	int noOfDays;
	String car_brand;
	double basePrice;
	String dropLocation;
	String pickupLocation;
	
	public AdminCars() {}

	
	
	
	public AdminCars(int plate_no, double mileage, Date rentalDate, int noOfDays, String car_brand, double basePrice,
			String dropLocation, String pickupLocation) {
		super();
		this.plate_no = plate_no;
		this.mileage = mileage;
		this.rentalDate = rentalDate;
		this.noOfDays = noOfDays;
		this.car_brand = car_brand;
		this.basePrice = basePrice;
		this.dropLocation = dropLocation;
		this.pickupLocation = pickupLocation;
	}




	public int getPlate_no() {
		return plate_no;
	}

	public void setPlate_no(int plate_no) {
		this.plate_no = plate_no;
	}

	public double getMileage() {
		return mileage;
	}

	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	public Date getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(Date rentalDate) {
		this.rentalDate = rentalDate;
	}

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getCar_brand() {
		return car_brand;
	}

	public void setCar_brand(String car_brand) {
		this.car_brand = car_brand;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public String getDropLocation() {
		return dropLocation;
	}

	public void setDropLocation(String dropLocation) {
		this.dropLocation = dropLocation;
	}

	public String getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	};
	

	
	
}

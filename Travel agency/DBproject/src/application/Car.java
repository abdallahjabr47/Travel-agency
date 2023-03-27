package application;
//Yazan Daibes 1180414

public class Car {
	String car_brand;
	int plate_no;
	int pickup_location;
	double mileage;
	double basePrice;
	int noOfDays;
	
	public Car() {};
	
	public Car(String car_brand, int plate_no, int pickUpLocation, double mileage, double basePrice,
			int numofdays) {
		super();
		this.car_brand = car_brand;
		this.plate_no = plate_no;
		this.pickup_location = pickUpLocation;
		this.mileage = mileage;
		this.basePrice = basePrice;
		this.noOfDays = numofdays;
	}

	public String getCar_brand() {
		return car_brand;
	}

	public void setCar_brand(String car_brand) {
		this.car_brand = car_brand;
	}


	public int getPlate_no() {
		return plate_no;
	}

	public void setPlate_no(int plate_no) {
		this.plate_no = plate_no;
	}


	public int getPickup_location() {
		return pickup_location;
	}

	public void setPickup_location(int pickup_location) {
		this.pickup_location = pickup_location;
	}

	public double getMileage() {
		return mileage;
	}
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}
	public double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	public int getNumofdays() {
		return noOfDays;
	}
	public void setNumofdays(int numofdays) {
		this.noOfDays = numofdays;
	}
	
	
}

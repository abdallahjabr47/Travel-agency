package application;
//Yazan Daibes 1180414

import java.util.Date;

public class Invoices {
	
	String FirstName;
	String LastName;
	int inv_id_number;
	Date Currentdate;	
	double priceFlight;
	double priceHotel;	
	double priceCar;
	double totalPrice;
	
	public Invoices(String FirstName, String LastName, int invoiceID, Date date, double priceFlight, double priceHotel, double priceCar,
			int totalPrice) {
		super();
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.inv_id_number = invoiceID;
		this.Currentdate = date;
		this.priceFlight = priceFlight;
		this.priceHotel = priceHotel;
		this.priceCar = priceCar;
		this.totalPrice = totalPrice;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String FirsttName) {
		this.FirstName = FirstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public int getInvoiceID() {
		return inv_id_number;
	}

	public void setInvoiceID(int invoiceID) {
		this.inv_id_number = invoiceID;
	}

	public Date getDate() {
		return Currentdate;
	}

	public void setDate(Date date) {
		this.Currentdate = date;
	}

	public double getPriceFlight() {
		return priceFlight;
	}

	public void setPriceFlight(double priceFlight) {
		this.priceFlight = priceFlight;
	}

	public double getPriceHotel() {
		return priceHotel;
	}

	public void setPriceHotel(double priceHotel) {
		this.priceHotel = priceHotel;
	}

	public double getPriceCar() {
		return priceCar;
	}

	public void setPriceCar(double priceCar) {
		this.priceCar = priceCar;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
}

// Yazan Daibes 1180414
package application;

import java.sql.Date;

public class HotelBooking {
	int reservationId;
	Date bookingDate;
	Date checkIn;
	Date checkOut;
	int NumOfRooms;
	double cost;
	int numOfGuests;
	int hotelId;
	int invoiceId;
	
	public HotelBooking() {}
	public HotelBooking(int reservationId, Date bookingDate, Date checkIn, Date checkOut, int numOfRooms, double cost,
			int numOfGuests, int hotelId, int invoiceId) {
		super();
		this.reservationId = reservationId;
		this.bookingDate = bookingDate;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		NumOfRooms = numOfRooms;
		this.cost = cost;
		this.numOfGuests = numOfGuests;
		this.hotelId = hotelId;
		this.invoiceId = invoiceId;
	}
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public Date getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}
	public Date getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}
	public int getNumOfRooms() {
		return NumOfRooms;
	}
	public void setNumOfRooms(int numOfRooms) {
		NumOfRooms = numOfRooms;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getNumOfGuests() {
		return numOfGuests;
	}
	public void setNumOfGuests(int numOfGuests) {
		this.numOfGuests = numOfGuests;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public int getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	
	
}

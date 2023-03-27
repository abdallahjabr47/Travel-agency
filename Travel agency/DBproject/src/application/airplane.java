package application;
//Yazan Daibes 1180414

import java.sql.Date;

public class airplane {

	private int FlightNo;
	private Date DepartureDate;
	private Date ReturnDate;
	private String DEPCity;
	private String ArrivalCity;
	//private int cityIDFrom;
	//private int cityIDTo;

	private String Number_of_seats;
	private String Number_of_passengers;
	private String airlineName;
	private Double BasePriceOfTicket;
	


	public airplane() {
	}


	

		public airplane(int flightNo, Date departureDate, Date returnDate,String number_of_seats, String number_of_passengers,
				
			 String airlineName, double basePriceOfTicket,String dEPCity, String arrivalCity) {
		super();
		FlightNo = flightNo;
		DepartureDate = departureDate;
		ReturnDate = returnDate;
		DEPCity = dEPCity;
		ArrivalCity = arrivalCity;
		
		Number_of_seats = number_of_seats;
		Number_of_passengers = number_of_passengers;
		this.airlineName = airlineName;
		BasePriceOfTicket = basePriceOfTicket;
	}

	public airplane( Date departureDate, Date returnDate,String number_of_seats, String number_of_passengers,
			
		 String airlineName, double basePriceOfTicket,String dEPCity, String arrivalCity) {
		super();
		DepartureDate = departureDate;
		ReturnDate = returnDate;
		Number_of_seats = number_of_seats;
		Number_of_passengers = number_of_passengers;
		DEPCity = dEPCity;
		ArrivalCity = arrivalCity;
		this.airlineName = airlineName;
		BasePriceOfTicket = basePriceOfTicket;
	}




	public int getFlightNo() {
		return FlightNo;
	}


	public void setFlightNo(int flightNo) {
		FlightNo = flightNo;
	}


	public Date getDepartureDate() {
		return DepartureDate;
	}


	public void setDepartureTime(Date departureTime) {
		DepartureDate = departureTime;
	}


	public Date getReturnDate() {
		return ReturnDate;
	}


	public void setReturnDate(Date arrivalTime) {
		ReturnDate = arrivalTime;
	}





	public String getDEPCity() {
		return DEPCity;
	}




	public void setDEPCity(String dEPCity) {
		DEPCity = dEPCity;
	}




	public String getArrivalCity() {
		return ArrivalCity;
	}




	public void setArrivalCity(String arrivalCity) {
		ArrivalCity = arrivalCity;
	}




	public String getNumber_of_seats() {
		return Number_of_seats;
	}


	public void setNumber_of_seats(String number_of_seats) {
		Number_of_seats = number_of_seats;
	}


	public String getNumber_of_passengers() {
		return Number_of_passengers;
	}


	public void setNumber_of_passengers(String number_of_passengers) {
		Number_of_passengers = number_of_passengers;
	}


	public String getAirlineName() {
		return airlineName;
	}


	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}




	public double getBasePriceOfTicket() {
		return BasePriceOfTicket;
	}




	public void setBasePriceOfTicket(double basePriceOfTicket) {
		BasePriceOfTicket = basePriceOfTicket;
	}
		
	
	
}

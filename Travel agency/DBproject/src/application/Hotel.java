package application;
//Yazan Daibes 1180414

public class Hotel {
	int hotelId;
	int reservedNum;//# of reserved rooms
	String cityIdHotel;
	String hotelName;
	int sweetNum;	//# of sweets
	int doubleNum;	//# of double rooms
	int singleNum;	//# of single rooms
	double basePrice;
	
	public Hotel() {}
	
	public Hotel(int hotelId, int reservedNum, String cityIdHotel, String hotelName, int sweetNum, int doubleNum,
			int singleNum,double basePrice) {
		super();
		this.hotelId = hotelId;
		this.reservedNum = reservedNum;
		this.cityIdHotel = cityIdHotel;
		this.hotelName = hotelName;
		this.sweetNum = sweetNum;
		this.doubleNum = doubleNum;
		this.singleNum = singleNum;
		this.basePrice=basePrice;
	}
	public Hotel(int hotelId, String cityIdHotel, String hotelName, int sweetNum, int doubleNum,
			int singleNum,double basePrice) {
		this.hotelId = hotelId;
		this.cityIdHotel = cityIdHotel;
		this.hotelName = hotelName;
		this.sweetNum = sweetNum;
		this.doubleNum = doubleNum;
		this.singleNum = singleNum;
		this.basePrice=basePrice;
		
		
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public int getReservedNum() {
		return reservedNum;
	}
	public void setReservedNum(int reservedNum) {
		this.reservedNum = reservedNum;
	}
	public String getCityIdHotel() {
		return cityIdHotel;
	}
	public void setCityIdHotel(String cityIdHotel) {
		this.cityIdHotel = cityIdHotel;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public int getSweetNum() {
		return sweetNum;
	}
	public void setSweetNum(int sweetNum) {
		this.sweetNum = sweetNum;
	}
	public int getDoubleNum() {
		return doubleNum;
	}
	public void setDoubleNum(int doubleNum) {
		this.doubleNum = doubleNum;
	}
	public int getSingleNum() {
		return singleNum;
	}
	public void setSingleNum(int singleNum) {
		this.singleNum = singleNum;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	
	
	
}
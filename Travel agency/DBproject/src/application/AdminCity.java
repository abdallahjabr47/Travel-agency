package application;
//Yazan Daibes 1180414

public class AdminCity {
	int cityId;
	String CityName;
	String countryName;
	public AdminCity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AdminCity(int cityId, String cityName, String countryName) {
		super();
		this.cityId = cityId;
		CityName = cityName;
		this.countryName = countryName;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
}

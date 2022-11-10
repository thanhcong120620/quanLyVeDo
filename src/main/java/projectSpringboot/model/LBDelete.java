package projectSpringboot.model;

public class LBDelete {

	private String day;
	private String month;
	private String year;
	private String regionCode;
	
	private LBDelete() {
	}
	
	private LBDelete(String day, String month, String year, String regionCode) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.regionCode = regionCode;
	}	

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	
}

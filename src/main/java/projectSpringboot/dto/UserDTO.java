package projectSpringboot.dto;

public class UserDTO extends AbstractDTO<UserDTO>{

	//cusHistory
	private Long chID;
	private String codeDrawCH;
	private String province;
	private String dayCH;
	private String monthCH;
	private String yearCH;
	private String status;
	private String dayDraw;
	private String monthDraw;
	private String yearDraw;;
	
	//user
	private String nameUser;
	private String genderUser;
	private String dayBD;
	private String monthBD;
	private String yearBD;
	private String phone;
	private String addressUser;
	private String mailUser;
	private String passwordUser;
	private String roleUser;
	private String userNameOrPhone;
	private String oldPassword;
	private String passwordRandom;

	
	
	public Long getChID() {
		return chID;
	}
	public void setChID(Long chID) {
		this.chID = chID;
	}
	public String getCodeDrawCH() {
		return codeDrawCH;
	}
	public void setCodeDrawCH(String codeDrawCH) {
		this.codeDrawCH = codeDrawCH;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDayCH() {
		return dayCH;
	}
	public void setDayCH(String dayCH) {
		this.dayCH = dayCH;
	}
	public String getMonthCH() {
		return monthCH;
	}
	public void setMonthCH(String monthCH) {
		this.monthCH = monthCH;
	}
	public String getYearCH() {
		return yearCH;
	}
	public void setYearCH(String yearCH) {
		this.yearCH = yearCH;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDayDraw() {
		return dayDraw;
	}
	public void setDayDraw(String dayDraw) {
		this.dayDraw = dayDraw;
	}
	public String getMonthDraw() {
		return monthDraw;
	}
	public void setMonthDraw(String monthDraw) {
		this.monthDraw = monthDraw;
	}
	public String getYearDraw() {
		return yearDraw;
	}
	public void setYearDraw(String yearDraw) {
		this.yearDraw = yearDraw;
	}
	
	
	
	
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public String getGenderUser() {
		return genderUser;
	}
	public void setGenderUser(String genderUser) {
		this.genderUser = genderUser;
	}
	public String getDayBD() {
		return dayBD;
	}
	public void setDayBD(String dayBD) {
		this.dayBD = dayBD;
	}
	public String getMonthBD() {
		return monthBD;
	}
	public void setMonthBD(String monthBD) {
		this.monthBD = monthBD;
	}
	public String getYearBD() {
		return yearBD;
	}
	public void setYearBD(String yearBD) {
		this.yearBD = yearBD;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddressUser() {
		return addressUser;
	}
	public void setAddressUser(String addressUser) {
		this.addressUser = addressUser;
	}
	public String getMailUser() {
		return mailUser;
	}
	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}
	public String getPasswordUser() {
		return passwordUser;
	}
	public void setPasswordUser(String passwordUser) {
		this.passwordUser = passwordUser;
	}
	public String getRoleUser() {
		return roleUser;
	}
	public void setRoleUser(String roleUser) {
		this.roleUser = roleUser;
	}
	
	public String getUserNameOrPhone() {
		return userNameOrPhone;
	}
	public void setUserNameOrPhone(String userNameOrPhone) {
		this.userNameOrPhone = userNameOrPhone;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getPasswordRandom() {
		return passwordRandom;
	}
	public void setPasswordRandom(String passwordRandom) {
		this.passwordRandom = passwordRandom;
	}
	@Override
	public String toString() {
		return "UserDTO [chID=" + chID + ", codeDrawCH=" + codeDrawCH + ", province=" + province + ", dayCH=" + dayCH
				+ ", monthCH=" + monthCH + ", yearCH=" + yearCH + ", status=" + status + ", dayDraw=" + dayDraw
				+ ", monthDraw=" + monthDraw + ", yearDraw=" + yearDraw + ", nameUser=" + nameUser + ", genderUser="
				+ genderUser + ", dayBD=" + dayBD + ", monthBD=" + monthBD + ", yearBD=" + yearBD + ", phone=" + phone
				+ ", addressUser=" + addressUser + ", mailUser=" + mailUser + ", passwordUser=" + passwordUser
				+ ", roleUser=" + roleUser + ", userNameOrPhone=" + userNameOrPhone + ", oldPassword=" + oldPassword
				+ ", passwordRandom=" + passwordRandom + "]";
	}
	
	
	
	
	

	


	
	
	
	
	
	

	
	
	
}

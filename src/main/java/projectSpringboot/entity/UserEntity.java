package projectSpringboot.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class UserEntity extends BaseEntity{
	
	@Column(name="nameuser")
	private String nameUser;
	
	@Column(name="genderuser")
	private String genderUser;
	
	@Column(name="birthday")
	private String birthDay;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="addressuser")
	private String addressUser;
	
	@Column(name="mailuser")
	private String mailUser;
	
	@Column(name="passworduser")
	private String passwordUser;
	
	@Column(name="roleuser")
	private String roleUser;
	
	@OneToMany(mappedBy = "user",  orphanRemoval = true, cascade={CascadeType.ALL})
	private List<CusHistoryEntity> CusHistoryList = new ArrayList<>();

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

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
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

	public List<CusHistoryEntity> getCusHistoryList() {
		return CusHistoryList;
	}

	public void setCusHistoryList(List<CusHistoryEntity> cusHistoryList) {
		CusHistoryList = cusHistoryList;
	}

	@Override
	public String toString() {
		return "UserEntity [nameUser=" + nameUser + ", genderUser=" + genderUser + ", birthDay=" + birthDay + ", phone="
				+ phone + ", addressUser=" + addressUser + ", mailUser=" + mailUser + ", passwordUser=" + passwordUser
				+ ", roleUser=" + roleUser + ", CusHistoryList=" + CusHistoryList + "]";
	}

	
	
	
}

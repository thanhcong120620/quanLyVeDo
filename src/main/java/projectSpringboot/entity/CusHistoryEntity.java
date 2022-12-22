package projectSpringboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cushistory")
public class CusHistoryEntity extends BaseEntity{
	
	@Column(name="codedrawch")
	private String codeDrawCH;
	
//	@Column(name="province")
//	private String province;
	
	@Column(name="datech")
	private String dateCH; 		//ngày xổ
	
	@Column(name="result")
	private String result;
	
	@Column(name="date_draw")
	private String dateDraw;	//ngày dò
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;	
	
	@ManyToOne
    @JoinColumn(name = "region_id")
    private RegionEntity region;

	public String getCodeDrawCH() {
		return codeDrawCH;
	}

	public void setCodeDrawCH(String codeDrawCH) {
		this.codeDrawCH = codeDrawCH;
	}

//	public String getProvince() {
//		return province;
//	}
//
//	public void setProvince(String province) {
//		this.province = province;
//	}

	public String getDateCH() {
		return dateCH;
	}

	public void setDateCH(String dateCH) {
		this.dateCH = dateCH;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
	public String getDateDraw() {
		return dateDraw;
	}

	public void setDateDraw(String dateDraw) {
		this.dateDraw = dateDraw;
	}

	public RegionEntity getRegion() {
		return region;
	}

	public void setRegion(RegionEntity region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "CusHistoryEntity [codeDrawCH=" + codeDrawCH + ", dateCH=" + dateCH + ", result=" + result
				+ ", dateDraw=" + dateDraw + ", user=" + user + ", region=" + region + "]";
	}

	


	
	
	

}

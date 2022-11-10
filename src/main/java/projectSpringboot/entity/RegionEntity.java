package projectSpringboot.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="region")
public class RegionEntity extends BaseEntity{

	@Column(name="regioncode")
	private String regionCode;
	
	@Column(name="nameregion")
	private String nameRegion;
	
	@Column(name="nameprovince")
	private String nameProvince;
	
//	@Column(name="date")
//	private String date;
	
	
	
	@OneToMany(mappedBy = "region", orphanRemoval = true, cascade={CascadeType.ALL})
	private List<LotteryBoardEntity> lotteryBoardList = new ArrayList<>();

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getNameRegion() {
		return nameRegion;
	}

	public void setNameRegion(String nameRegion) {
		this.nameRegion = nameRegion;
	}

	public String getNameProvince() {
		return nameProvince;
	}

	public void setNameProvince(String nameProvince) {
		this.nameProvince = nameProvince;
	}

//	public String getDate() {
//		return date;
//	}
//
//	public void setDate(String date) {
//		this.date = date;
//	}

	public List<LotteryBoardEntity> getLotteryBoardList() {
		return lotteryBoardList;
	}

	public void setLotteryBoardList(List<LotteryBoardEntity> lotteryBoardList) {
		this.lotteryBoardList = lotteryBoardList;
	}

	@Override
	public String toString() {
		return "RegionEntity [regionCode=" + regionCode + ", nameRegion=" + nameRegion + ", nameProvince="
				+ nameProvince + ", lotteryBoardList=" + lotteryBoardList + "]";
	}


	
	

	
	
	
	
	
}

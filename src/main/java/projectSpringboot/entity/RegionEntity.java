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
	
	@Column(name="draw_time")
	private String drawTime;  // sửa thành khung giờ xổ
	
	@Column(name="nameprovince")
	private String nameProvince;
	
//	@Column(name="date")
//	private String date;orphanRemoval = true,
	
	
	
	@OneToMany(mappedBy = "region",cascade={CascadeType.ALL})
	private List<LotteryBoardEntity> lotteryBoardList = new ArrayList<>();

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(String drawDay) {
		this.drawTime = drawDay;
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
		return "RegionEntity [regionCode=" + regionCode + ", drawTime=" + drawTime + ", nameProvince="
				+ nameProvince + ", lotteryBoardList=" + lotteryBoardList + "]";
	}


	
	

	
	
	
	
	
}

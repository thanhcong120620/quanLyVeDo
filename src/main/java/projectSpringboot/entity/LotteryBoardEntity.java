package projectSpringboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="lotteryboard")
public class LotteryBoardEntity extends BaseEntity{

//	@Column(name="codedrawlb")
//	private String codeDrawLB;
	
	@Column(name="date")
	private String date;
	
	@Column(name="regioncode")
	private String regionCode;
	
	@Column(name="g0")
	private String g0;
	
	@Column(name="g1")
	private String g1;
	
	@Column(name="g2")
	private String g2;
	
	@Column(name="g31")
	private String g31;
	
	@Column(name="g32")
	private String g32;
	
	@Column(name="g41")
	private String g41;
	
	@Column(name="g42")
	private String g42;
	
	@Column(name="g43")
	private String g43;
	
	@Column(name="g44")
	private String g44;
	
	@Column(name="g45")
	private String g45;
	
	@Column(name="g46")
	private String g46;
	
	@Column(name="g47")
	private String g47;
	
	@Column(name="g5")
	private String g5;
	
	@Column(name="g61")
	private String g61;
	
	@Column(name="g62")
	private String g62;
	
	@Column(name="g63")
	private String g63;
	
	@Column(name="g7")
	private String g7;
	
	@Column(name="g8")
	private String g8;
	
	@ManyToOne
    @JoinColumn(name = "region_id")
    private RegionEntity region;

	public RegionEntity getRegion() {
		return region;
	}

	public void setRegion(RegionEntity region) {
		this.region = region;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	

	public String getG0() {
		return g0;
	}

	public void setG0(String g0) {
		this.g0 = g0;
	}

	public String getG1() {
		return g1;
	}

	public void setG1(String g1) {
		this.g1 = g1;
	}

	public String getG2() {
		return g2;
	}

	public void setG2(String g2) {
		this.g2 = g2;
	}

	public String getG31() {
		return g31;
	}

	public void setG31(String g31) {
		this.g31 = g31;
	}

	public String getG32() {
		return g32;
	}

	public void setG32(String g32) {
		this.g32 = g32;
	}

	public String getG41() {
		return g41;
	}

	public void setG41(String g41) {
		this.g41 = g41;
	}

	public String getG42() {
		return g42;
	}

	public void setG42(String g42) {
		this.g42 = g42;
	}

	public String getG43() {
		return g43;
	}

	public void setG43(String g43) {
		this.g43 = g43;
	}

	public String getG44() {
		return g44;
	}

	public void setG44(String g44) {
		this.g44 = g44;
	}

	public String getG45() {
		return g45;
	}

	public void setG45(String g45) {
		this.g45 = g45;
	}

	public String getG46() {
		return g46;
	}

	public void setG46(String g46) {
		this.g46 = g46;
	}

	public String getG47() {
		return g47;
	}

	public void setG47(String g47) {
		this.g47 = g47;
	}

	public String getG5() {
		return g5;
	}

	public void setG5(String g5) {
		this.g5 = g5;
	}

	public String getG61() {
		return g61;
	}

	public void setG61(String g61) {
		this.g61 = g61;
	}

	public String getG62() {
		return g62;
	}

	public void setG62(String g62) {
		this.g62 = g62;
	}

	public String getG63() {
		return g63;
	}

	public void setG63(String g63) {
		this.g63 = g63;
	}

	public String getG7() {
		return g7;
	}

	public void setG7(String g7) {
		this.g7 = g7;
	}

	public String getG8() {
		return g8;
	}

	public void setG8(String g8) {
		this.g8 = g8;
	}

	@Override
	public String toString() {
		return "LotteryBoardEntity [date=" + date + ", regionCode=" + regionCode + ", g0=" + g0 + ", g1=" + g1 + ", g2="
				+ g2 + ", g31=" + g31 + ", g32=" + g32 + ", g41=" + g41 + ", g42=" + g42 + ", g43=" + g43 + ", g44="
				+ g44 + ", g45=" + g45 + ", g46=" + g46 + ", g47=" + g47 + ", g5=" + g5 + ", g61=" + g61 + ", g62="
				+ g62 + ", g63=" + g63 + ", g7=" + g7 + ", g8=" + g8 + ", region=" + region + "]\n";
	}


	
	
	
	
	
}

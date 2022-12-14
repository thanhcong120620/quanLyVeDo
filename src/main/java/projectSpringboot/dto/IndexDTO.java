package projectSpringboot.dto;

import java.util.List;

import projectSpringboot.entity.LotteryBoardEntity;

public class IndexDTO extends AbstractDTO<IndexDTO>{

	private String province;
	private String codeDraw;
	private String status;
	private String dayDraw;
	private String monthDraw;
	private String yearDraw;
	
	private List<LotteryDTO> LbList;
	private List<LotteryDTO> NorthLbList;
	private List<LotteryDTO> CentralLbList;
	private List<LotteryDTO> SouthLbList;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCodeDraw() {
		return codeDraw;
	}
	public void setCodeDraw(String codeDraw) {
		this.codeDraw = codeDraw;
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public List<LotteryDTO> getLbList() {
		return LbList;
	}
	public void setLbList(List<LotteryDTO> lbList) {
		LbList = lbList;
	}
	public List<LotteryDTO> getNorthLbList() {
		return NorthLbList;
	}
	public void setNorthLbList(List<LotteryDTO> northLbList) {
		NorthLbList = northLbList;
	}
	public List<LotteryDTO> getCentralLbList() {
		return CentralLbList;
	}
	public void setCentralLbList(List<LotteryDTO> centralLbList) {
		CentralLbList = centralLbList;
	}
	public List<LotteryDTO> getSouthLbList() {
		return SouthLbList;
	}
	public void setSouthLbList(List<LotteryDTO> southLbList) {
		SouthLbList = southLbList;
	}
	@Override
	public String toString() {
		return "IndexDTO [province=" + province + ", codeDraw=" + codeDraw + ", status=" + status + ", dayDraw="
				+ dayDraw + ", monthDraw=" + monthDraw + ", yearDraw=" + yearDraw + ", LbList=" + LbList
				+ ",\n NorthLbList=" + NorthLbList + ",\n CentralLbList=" + CentralLbList + ",\n SouthLbList=" + SouthLbList
				+ "]";
	}
	
	
	
}
	

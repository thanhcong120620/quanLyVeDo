package projectSpringboot.converter;

import org.springframework.stereotype.Component;

import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.CusHistoryEntity;

@Component
public class CusHistoryConverter {

	
	public CusHistoryEntity toEntity(UserDTO dto) {
		CusHistoryEntity entity = new CusHistoryEntity();
		
		entity.setCodeDrawCH(dto.getCodeDrawCH());
		entity.setDateCH(""+dto.getDayCH()+"-"+dto.getMonthCH()+"-"+dto.getYearCH());
//		entity.setProvince(dto.getProvince());
		entity.setResult(dto.getStatus());
		entity.setDateDraw(""+dto.getDayDraw()+"-"+dto.getMonthDraw()+"-"+dto.getYearDraw());
		
		return entity;
	}
	
	public CusHistoryEntity toEntity(UserDTO dto, CusHistoryEntity entity) {
		
		//because to update a old data with new data from DTO, we use old Object to change data
		entity.setCodeDrawCH(dto.getCodeDrawCH());
		entity.setDateCH(""+dto.getDayCH()+"-"+dto.getMonthCH()+"-"+dto.getYearCH());
//		entity.setProvince(dto.getProvince());
		entity.setResult(dto.getStatus());
		entity.setDateDraw(""+dto.getDayDraw()+"-"+dto.getMonthDraw()+"-"+dto.getYearDraw());
		
		return entity;
	}
	
	public UserDTO toDTO(CusHistoryEntity entity) {
		UserDTO dto = new UserDTO();
		
		dto.setChID(entity.getId());
		dto.setCodeDrawCH(entity.getCodeDrawCH());
//		dto.setProvince(entity.getProvince());
		dto.setProvince(entity.getRegion().getNameProvince());
		dto.setStatus(entity.getResult());
		
		String dmyCH = entity.getDateCH();
		String[] output = dmyCH.split("-");
		dto.setDayCH(output[0]);
		dto.setMonthCH(output[1]);
		dto.setYearCH(output[2]);
		
		String dmyDraw = entity.getDateDraw();
		String[] outputDr = dmyDraw.split("-");
		dto.setDayDraw(outputDr[0]);
		dto.setMonthDraw(outputDr[1]);
		dto.setYearDraw(outputDr[2]);
		
		return dto;
	}
	
	
}

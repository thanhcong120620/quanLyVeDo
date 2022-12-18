package projectSpringboot.converter;

import org.springframework.stereotype.Component;

import projectSpringboot.dto.LotteryDTO;
import projectSpringboot.entity.RegionEntity;

@Component
public class RegionConverter {

	public RegionEntity toEntity(LotteryDTO dto) {
		
		RegionEntity entity = new RegionEntity();//because to add new Region, we create new Object

//		entity.setId(dto.getId());//Id of primary key - in region table
		entity.setNameProvince(dto.getNameProvince());
		entity.setDrawTime(dto.getDrawTime());	
		entity.setRegionCode(dto.getRegionCode());
		
		return entity;
	} 
	
	public RegionEntity toEntity(LotteryDTO dto, RegionEntity entity) {
		
		//because to update a old News with new data from DTO, we use old Object to change data
		entity.setNameProvince(dto.getNameProvince());
		entity.setDrawTime(dto.getDrawTime());	
		entity.setRegionCode(dto.getRegionCode());
		
		return entity;
	}
	
	public LotteryDTO toDTO(RegionEntity entity) {
		LotteryDTO dto = new LotteryDTO();
		
		if(entity.getId() != null) {//Entity already existed in database
			dto.setId(entity.getId());
		}
		
		dto.setRegionCode(entity.getRegionCode());
		dto.setNameProvince(entity.getNameProvince());
		dto.setDrawTime(entity.getDrawTime());
		
		return dto;
	}
	
	
}

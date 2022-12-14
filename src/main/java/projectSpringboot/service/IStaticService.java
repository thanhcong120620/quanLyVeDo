package projectSpringboot.service;

import projectSpringboot.dto.IndexDTO;
import projectSpringboot.dto.StaticDTO;

public interface IStaticService {

	IndexDTO draw(IndexDTO indexDTO);
	StaticDTO staticLottery();
	IndexDTO displayYesterday(String day);
	IndexDTO displayRegion(String regionCode);
	
	
	
}

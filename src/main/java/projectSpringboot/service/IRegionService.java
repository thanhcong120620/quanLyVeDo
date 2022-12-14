package projectSpringboot.service;

import java.util.List;

import projectSpringboot.dto.LotteryDTO;

public interface IRegionService {
	
	LotteryDTO save(LotteryDTO lotteryDTO);
	void delete(String[] regionCode);
	List<LotteryDTO> findAll();
	LotteryDTO getRegionById(long id);
	int totalItem();

}

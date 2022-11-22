package projectSpringboot.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import projectSpringboot.dto.LotteryDTO;
import projectSpringboot.entity.LotteryBoardEntity;

public interface ILotteryBoardService {

	LotteryDTO save(LotteryDTO lotteryDTO);
	void deleteMany(String ids);
	void deleteOne(long id);
	List<LotteryDTO> findAllLB();
	List<LotteryDTO> searchList(LotteryDTO lotteryDTO, int pageNo, int pageSize);
	LotteryDTO getLotteryBoardById(long idList);
	int totalItem();
	
	List<LotteryDTO> findAllByRegion_id(String region_id);
	
	//Pagination
	List<LotteryDTO> findAllLotteryBoardPagination(int pageNo, int pageSize);
	Page<LotteryBoardEntity> findPaginated(int pageNo, int pageSize);
	Page<LotteryBoardEntity> searchPaginated(LotteryDTO lotteryDTO, int pageNo, int pageSize);
}

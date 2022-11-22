package projectSpringboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import projectSpringboot.entity.LotteryBoardEntity;

//@Repository
public interface LotteryBoardRepository extends JpaRepository<LotteryBoardEntity, Long>{

	List<LotteryBoardEntity> findByRegionCode(String regionCode);
	List<LotteryBoardEntity> findByDate(String date);
//	LotteryBoardEntity findOneById(long id);
	
	 @Query(value ="SELECT * FROM lotteryboard as e WHERE e.region_id = ?1", nativeQuery = true)
	  List<LotteryBoardEntity> findByRegion_id(String region_id);
	
	 //pagination
	 Page<LotteryBoardEntity> findAll(Pageable pageable);
	 Page<LotteryBoardEntity> findByRegionCode(String regionCode, Pageable pageable);
	 
}

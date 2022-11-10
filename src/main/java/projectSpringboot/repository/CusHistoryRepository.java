package projectSpringboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import projectSpringboot.entity.CusHistoryEntity;
import projectSpringboot.entity.LotteryBoardEntity;


public interface CusHistoryRepository extends JpaRepository<CusHistoryEntity, Long> {
	 
	 @Query(value ="SELECT user_id FROM cushistory as e WHERE e.id = ?1", nativeQuery = true)
	  long getUser_id(long id);
	 
	 @Query(value ="DELETE FROM cushistory as e WHERE e.user_id = ?1", nativeQuery = true)
	  void deleteUser_id(long id);
	 
	 @Query(value ="SELECT * FROM cushistory as e WHERE e.user_id = ?1", nativeQuery = true)
	  List<CusHistoryEntity> findByUser_id(long region_id);
	
}

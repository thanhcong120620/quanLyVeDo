package projectSpringboot.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.CusHistoryEntity;

public interface ICusHistoryService {

	UserDTO save(UserDTO userDTO);
	void deleteOne(long id);
	void deleteMany(String idList);
	List<UserDTO> findAll();
	List<UserDTO> findAllByUser_id(long user_id);
	
	List<UserDTO> findAllByUser_id2(long user_id, int pageNo, int pageSize);
	Page<CusHistoryEntity> findPaginated(long user_id, int pageNo, int pageSize);
	
	
}

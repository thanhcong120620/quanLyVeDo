package projectSpringboot.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.CusHistoryEntity;
import projectSpringboot.exceptionAndError.UserNotFoundException;

public interface ICusHistoryService {

	UserDTO save(UserDTO userDTO);
	UserDTO draw(UserDTO userDTO);
	
	void deleteOne(long id);
	void deleteMany(String idList, long user_id) throws UserNotFoundException;
	List<UserDTO> findAll();
	List<UserDTO> findAllByUserid(long user_id);
	
	List<UserDTO> findAllByUserid2(long user_id, int pageNo, int pageSize);
	Page<CusHistoryEntity> findPaginated(long user_id, int pageNo, int pageSize);
	
	
}

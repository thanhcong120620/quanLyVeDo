package projectSpringboot.service;

import java.util.List;

import projectSpringboot.dto.UserDTO;

public interface ICusHistoryService {

	UserDTO save(UserDTO userDTO);
	void delete(long id);
	List<UserDTO> findAll();
	List<UserDTO> findAllByUser_id(long user_id);
	
}

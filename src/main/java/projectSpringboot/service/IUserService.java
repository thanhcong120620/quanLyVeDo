package projectSpringboot.service;

import java.util.List;

import projectSpringboot.dto.UserDTO;
import projectSpringboot.service.implement.UserCanNotDeleteException;

public interface IUserService {
	
	UserDTO save(UserDTO userDTO);
	void deleteMany(String ids);
	void deleteOne(long id) throws UserCanNotDeleteException;
	UserDTO getUserById(long id);
	List<UserDTO> findAllUser();
	List<UserDTO> searchList(UserDTO userDTO);
//	UserDTO getUserById(long idList);
	
}

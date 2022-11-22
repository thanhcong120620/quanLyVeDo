package projectSpringboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.CusHistoryEntity;
import projectSpringboot.entity.UserEntity;
import projectSpringboot.exception.CanNotCreateUserException;
import projectSpringboot.exception.CanNotFindUserException;
import projectSpringboot.exception.UserCanNotDeleteException;
import projectSpringboot.exception.UserCanNotUpdateAdminException;

public interface IUserService {
	
	UserDTO save(UserDTO userDTO) throws UserCanNotUpdateAdminException;
	void deleteMany(String ids) throws UserCanNotDeleteException;
	void deleteOne(long id) throws UserCanNotDeleteException;
	UserDTO getUserById(long id);
	UserDTO getUserByMailUser(UserDTO userDTO) throws CanNotFindUserException;
	String getNameByMailUser(UserDTO userDTO);
	UserDTO updateUserPasswordByMailUser(UserDTO userDTO) throws CanNotFindUserException;
	UserDTO createNewUserByRegister(UserDTO userDTO) throws CanNotCreateUserException;
	UserDTO changeAndSendPwdByMail(UserDTO userDTO) throws CanNotFindUserException;
	
	UserDTO resetPassword(long id);
	List<UserDTO> findAllUser();
	List<UserDTO> searchList(UserDTO userDTO, int pageNo, int pageSize);
//	UserDTO getUserById(long idList);
	
	//Pagination
	List<UserDTO> findAllUserPagination(int pageNo, int pageSize);
	Page<UserEntity> findPaginated(int pageNo, int pageSize);
	Page<UserEntity> searchPaginated(String userRole, int pageNo, int pageSize);
	
}

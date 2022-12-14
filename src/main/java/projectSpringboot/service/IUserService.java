package projectSpringboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.CusHistoryEntity;
import projectSpringboot.entity.UserEntity;
import projectSpringboot.exceptionAndError.CanNotCreateUserException;
import projectSpringboot.exceptionAndError.CanNotFindUserException;
import projectSpringboot.exceptionAndError.UserCanNotDeleteException;
import projectSpringboot.exceptionAndError.UserCanNotUpdateAdminException;
import projectSpringboot.exceptionAndError.UserNotFoundException;

public interface IUserService {
	
	UserDTO save(UserDTO userDTO) throws UserCanNotUpdateAdminException;
	void deleteMany(String ids) throws UserCanNotDeleteException, UserNotFoundException;//test
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

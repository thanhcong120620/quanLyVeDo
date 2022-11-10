package projectSpringboot.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projectSpringboot.converter.UserConverter;
import projectSpringboot.dto.LotteryDTO;
import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.CusHistoryEntity;
import projectSpringboot.entity.LotteryBoardEntity;
import projectSpringboot.entity.RegionEntity;
import projectSpringboot.entity.UserEntity;
import projectSpringboot.repository.CusHistoryRepository;
import projectSpringboot.repository.UserRepository;
import projectSpringboot.service.IUserService;

@Service
@Transactional
public class UserService implements IUserService{
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CusHistoryRepository CHRepository;
	
	@Autowired
	private UserConverter userConverter;
	
	
	

	@Override
	public UserDTO save(UserDTO userDTO) {
		UserEntity userEntity = new UserEntity();
		if(userDTO.getId() != null) {
			UserEntity oldEntity = userRepository.findById(userDTO.getId()).get();
			userEntity = userConverter.toEntity(userDTO, oldEntity);
		} else {
			userEntity = userConverter.toEntity(userDTO);
		}
		System.out.println("entity: ");
		System.out.println(userEntity.toString());
		userEntity = userRepository.save(userEntity);
		
		return userConverter.toDTO(userEntity);
	}



	@Override
	public void deleteMany(String idList) {
		
		String[] output = idList.split("-");
		long[] ids = new long[output.length];
		for(int i=0;i<output.length;i++) {
			ids[i] = Long.parseLong(output[i]);
		}	
		
		//Step 1: Delete cusHistory, query native with user_id
//		for(long item : ids) {
//			CHRepository.deleteUser_id(item);
//		}
		
		//step 2: delete User
		for(long item : ids) {
			userRepository.deleteById(item);
		}
		
	}
	
	

	@Override
	public void deleteOne(long id) throws UserCanNotDeleteException {
		UserEntity entity = userRepository.findOneById(id);
		String role = entity.getRoleUser();
		System.out.println("role: "+role);
		//Step 1: Delete cusHistory 
//		CHRepository.deleteUser_id(id);
		//step 2: delete User
		if(role.equals("customer")) {
			System.out.println("run delete ");
			userRepository.deleteById(id);
		}
		else if(role.equals("admin")){
			System.out.println("run exception !  ");
			throw new UserCanNotDeleteException("message");
		}
		
		
	}
	
	

	@Override
	public List<UserDTO> findAllUser() {
		List<UserDTO> UserDTOList = new ArrayList<>();
		List<UserEntity> userEntityList = userRepository.findAll();
		
		for(UserEntity item : userEntityList) {
			UserDTO dto = new UserDTO();
			dto = userConverter.toDTO(item);
			UserDTOList.add(dto);
		}
		
		return UserDTOList;
	}
	
	

	@Override
	public List<UserDTO> searchList(UserDTO userDTO) {
		List<UserDTO> UserDTOList = new ArrayList<>();
		List<UserEntity> userListNoRole = new ArrayList<>();
		
		System.out.println(userDTO.toString());
		
		//Step 1: Check phone or userName
		String input = userDTO.getUserNameOrPhone();
		String userNameDTO = "";
		String phoneDTO = "";
		String output = "";
		
		if(input.contains("@")) {
			userNameDTO = input;
			System.out.println(userNameDTO);
		} else if(!input.contains("@")){
			phoneDTO = input;
			System.out.println(phoneDTO);
		} else {
			output = null;
			System.out.println(output);
		}
		
		
		//Step 2: Query User by role to get List with phone/userName and address
		userListNoRole = userRepository.findByRoleUser(userDTO.getRoleUser());
//		for(UserEntity item : userListNoRole) {
//			System.out.println(item.toString());
//		}
		
		System.out.println(userDTO.getAddressUser());
		//Step 3: Use address with if and else to get List with phone/userName
		//Convert to new list if address not null
		List<UserEntity> userListNoRoleAddress = new ArrayList<>();

		
		
		if(!userDTO.getAddressUser().isEmpty()) {
			for(UserEntity item : userListNoRole) {
				if(item.getAddressUser().equals(userDTO.getAddressUser())) {
					userListNoRoleAddress.add(item);
				}
			}
			//check ok -----
			
			//Use phone/userName with if and else to get final List (Step 4)
			//check with userName
			if(!userNameDTO.equals("")) {
				System.out.println("---> username run");
				for(UserEntity item : userListNoRoleAddress) {
					if(item.getMailUser().equals(input)) {
						UserDTO dto = new UserDTO();
						dto = userConverter.toDTO(item);
						UserDTOList.add(dto);
					}
				}
				System.out.println("check userName: ");
				for(UserDTO item : UserDTOList) {
					System.out.println(item.toString());
				}
			} 
			//check with phone	
			else if(!phoneDTO.equals("")) {
				System.out.println("phone run");
				for(UserEntity item : userListNoRoleAddress) {
					if(item.getPhone().equals(input)) {
						UserDTO dto = new UserDTO();
						dto = userConverter.toDTO(item);
						UserDTOList.add(dto);
					}
				}
			}
			//No: phone, user name
			else {
				for(UserEntity item : userListNoRoleAddress) {
					UserDTO dto = new UserDTO();
					dto = userConverter.toDTO(item);
					UserDTOList.add(dto);
				}
			}
		}

		
		//Step 4: Use phone/userName with if and else to get final List
		//check with userName
		if(!userNameDTO.equals("")) {
			for(UserEntity item : userListNoRole) {
				if(item.getMailUser().equals(input)) {
					UserDTO dto = new UserDTO();
					dto = userConverter.toDTO(item);
					UserDTOList.add(dto);
				}
			}
		}
		//check with phone
		else if(!phoneDTO.equals("")) {
			for(UserEntity item : userListNoRole) {
				if(item.getPhone().equals(input)) {
					UserDTO dto = new UserDTO();
					dto = userConverter.toDTO(item);
					UserDTOList.add(dto);
				}
			}
		//No: Phone, user name, address
		} else {
			for(UserEntity item : userListNoRole) {
				UserDTO dto = new UserDTO();
				dto = userConverter.toDTO(item);
				UserDTOList.add(dto);
			}
		}
		
		
		return UserDTOList;
	}



	@Override
	public UserDTO getUserById(long id) {
		UserEntity userEntity = userRepository.findById(id).get();
		String user_id = ""+id;
		System.out.println("user_id = "+user_id);
		
		
		UserDTO dto = userConverter.toDTO(userEntity);
		
		return dto;
	}




}

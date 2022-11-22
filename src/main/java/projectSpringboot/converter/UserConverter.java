package projectSpringboot.converter;

import org.springframework.stereotype.Component;

import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.UserEntity;

@Component
public class UserConverter {

	public UserEntity toEntity(UserDTO dto) {

		UserEntity entity = new UserEntity();// because to add new User, we create new Object

//		entity.setId(dto.getId());//Id of primary key - in region table
		entity.setNameUser(dto.getNameUser());
		entity.setGenderUser(dto.getGenderUser());
		entity.setBirthDay(""+dto.getDayBD()+"-"+dto.getMonthBD()+"-"+dto.getYearBD());
		entity.setPhone(dto.getPhone());
		entity.setAddressUser(dto.getAddressUser());
		entity.setMailUser(dto.getMailUser());
		entity.setPasswordUser(dto.getPasswordUser());
		entity.setRoleUser(dto.getRoleUser());

		return entity;
	}

	public UserEntity toEntity(UserDTO dto, UserEntity entity) {

		// because to update a old News with new data from DTO, we use old Object to
		// change data
		entity.setNameUser(dto.getNameUser());
		entity.setGenderUser(dto.getGenderUser());
		entity.setBirthDay(""+dto.getDayBD()+"-"+dto.getMonthBD()+"-"+dto.getYearBD());
		entity.setPhone(dto.getPhone());
		entity.setAddressUser(dto.getAddressUser());
		entity.setMailUser(dto.getMailUser());
		entity.setPasswordUser(dto.getPasswordUser());
		entity.setRoleUser(dto.getRoleUser());

		return entity;
	}

	public UserDTO toDTO(UserEntity entity) {
		UserDTO dto = new UserDTO();

		if (entity.getId() != null) {
			dto.setId(entity.getId());
		}

		String dmyUser = entity.getBirthDay();
		String[] output = dmyUser.split("-");
		dto.setDayBD(output[0]);
		dto.setMonthBD(output[1]);
		dto.setYearBD(output[2]);
		
		dto.setNameUser(entity.getNameUser());
		dto.setGenderUser(entity.getGenderUser());
		dto.setPhone(entity.getPhone());
		dto.setAddressUser(entity.getAddressUser());
		dto.setMailUser(entity.getMailUser());
		dto.setPasswordUser(entity.getPasswordUser());
		dto.setRoleUser(entity.getRoleUser());
		
	

		return dto;
	}

}

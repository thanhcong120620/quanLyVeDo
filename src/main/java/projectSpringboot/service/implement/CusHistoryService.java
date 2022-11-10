package projectSpringboot.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projectSpringboot.converter.CusHistoryConverter;
import projectSpringboot.converter.UserConverter;
import projectSpringboot.dto.LotteryDTO;
import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.CusHistoryEntity;
import projectSpringboot.entity.LotteryBoardEntity;
import projectSpringboot.entity.UserEntity;
import projectSpringboot.repository.CusHistoryRepository;
import projectSpringboot.repository.UserRepository;
import projectSpringboot.service.ICusHistoryService;

@Service
@Transactional
public class CusHistoryService implements ICusHistoryService{
	
	@Autowired
	CusHistoryRepository chRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private CusHistoryConverter chConverter;
	
	@Autowired
	private UserConverter userConverter;
	
	

	@Override
	public UserDTO save(UserDTO userDTO) {
		CusHistoryEntity chEntity = new CusHistoryEntity();
		UserEntity userEntity = new UserEntity();
		
		chEntity = chConverter.toEntity(userDTO);
		userEntity = userRepository.findOneById(userDTO.getId());
		chEntity.setUser(userEntity);
		chEntity = chRepository.save(chEntity);
		
		return chConverter.toDTO(chEntity);
	}

	@Override
	public void delete(long id) {
		chRepository.deleteById(id);
		
	}

	@Override
	public List<UserDTO> findAll() {
		List<UserDTO> CusHistoryDTOList = new ArrayList<>();
		List<CusHistoryEntity> lbEntityList = chRepository.findAll();
		for(CusHistoryEntity item : lbEntityList) {
			long id =  chRepository.getUser_id(item.getId());
			UserEntity userEntity = userRepository.findOneById(id);
			UserDTO dto = new UserDTO();
			dto = userConverter.toDTO(userEntity);
			dto.setCodeDrawCH(item.getCodeDrawCH());
			dto.setProvince(item.getProvince());
			dto.setStatus(item.getResult());
			
			String dmyCH = item.getDateCH();
			String[] output = dmyCH.split("-");
			dto.setDayCH(output[0]);
			dto.setMonthCH(output[1]);
			dto.setYearCH(output[2]);
			
			CusHistoryDTOList.add(dto);
			
		}
		
		return CusHistoryDTOList;
	}

	@Override
	public List<UserDTO> findAllByUser_id(long user_id) {
		List<UserDTO> dtoList = new ArrayList<>();
		List<CusHistoryEntity> chList = new ArrayList<>();
		chList = chRepository.findByUser_id(user_id);

		if(chList.isEmpty()) {
			System.out.println("chList empty !");
		}
		
		for(CusHistoryEntity item : chList) {
			System.out.println(item.toString());
			UserDTO dto = chConverter.toDTO(item);
			dtoList.add(dto);
		}
		return dtoList;
	}




}

package projectSpringboot.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projectSpringboot.converter.CusHistoryConverter;
import projectSpringboot.converter.UserConverter;
import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.CusHistoryEntity;
import projectSpringboot.entity.LotteryBoardEntity;
import projectSpringboot.entity.RegionEntity;
import projectSpringboot.entity.UserEntity;
import projectSpringboot.model.drawLottery;
import projectSpringboot.repository.CusHistoryRepository;
import projectSpringboot.repository.LotteryBoardRepository;
import projectSpringboot.repository.RegionRepository;
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
	
	//lottery
	@Autowired
	LotteryBoardRepository lotteryBoardRepository;
	
	@Autowired
	RegionRepository regionRepository;
	

	

	
	@Override
	public Page<CusHistoryEntity> findPaginated(long user_id, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
//		System.out.println("run findPaginated (Cus service) ");
		return this.chRepository.findByUser_id(user_id, pageable);
	}
	
	@Override
	public List<UserDTO> findAllByUser_id2(long user_id, int pageNo, int pageSize) {
		List<UserDTO> dtoList = new ArrayList<>();
		List<CusHistoryEntity> chListOld = new ArrayList<>();
		
//		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		chListOld = findPaginated(user_id, pageNo, pageSize).getContent();
		List<CusHistoryEntity> chList = new ArrayList<>();
		
		//Transfer due to stack over flow
		for(CusHistoryEntity item : chListOld) {
			String CodeDrawCH = item.getCodeDrawCH();
			String DateCH = item.getDateCH();
			Long Id = item.getId();
			String Province = item.getProvince();
			String Result = item.getResult();
//			UserEntity User = item.getUser();

			CusHistoryEntity chEntity = new CusHistoryEntity();
			chEntity.setCodeDrawCH(CodeDrawCH);
			chEntity.setDateCH(DateCH);
			chEntity.setId(Id);
			chEntity.setProvince(Province);
			chEntity.setResult(Result);
//			chEntity.setUser(User);
			chList.add(chEntity);
		}

		if(chList.isEmpty()) {
			System.out.println("chList empty !");
		}
		
		UserEntity userEntityOld = new UserEntity();
		userEntityOld = userRepository.findById(user_id).get();
		
		UserEntity userEntity = new UserEntity();

		userEntity.setAddressUser(userEntityOld.getAddressUser());
		userEntity.setNameUser(userEntityOld.getNameUser());
		userEntity.setGenderUser(userEntityOld.getGenderUser());
		userEntity.setBirthDay(userEntityOld.getBirthDay());
		userEntity.setPhone(userEntityOld.getPhone());
		userEntity.setMailUser(userEntityOld.getMailUser());
		userEntity.setPasswordUser(userEntityOld.getPasswordUser());
		userEntity.setRoleUser(userEntityOld.getRoleUser());
		userEntity.setId(userEntityOld.getId());
		
//		System.out.println("userEntity: "+userEntity.toString());
		
		for(CusHistoryEntity item : chList) {
//			System.out.println(item.toString());
			UserDTO dto = chConverter.toDTO(item);
			
			dto.setId(userEntity.getId());
			dto.setAddressUser(userEntity.getAddressUser());
			dto.setNameUser(userEntity.getNameUser());
			dto.setGenderUser(userEntity.getGenderUser());
			dto.setPhone(userEntity.getPhone());
			dto.setMailUser(userEntity.getMailUser());
			dto.setPasswordUser(userEntity.getPasswordUser());
			dto.setRoleUser(userEntity.getRoleUser());
			String dmyBD= userEntity.getBirthDay();
			String[] output = dmyBD.split("-");
			dto.setDayBD(output[0]);
			dto.setMonthBD(output[1]);
			dto.setYearBD(output[2]);
			
			dtoList.add(dto);
		}
//		System.out.println("run findAllByUser_id2 (Cus service) ");
		return dtoList;
	}
	
	
	@Override
	public UserDTO save(UserDTO userDTO) {
		UserDTO dto = new UserDTO();
		CusHistoryEntity chEntity = new CusHistoryEntity();
		UserEntity userEntity = new UserEntity();
		
		chEntity = chConverter.toEntity(userDTO);
//		userEntity = userRepository.findOneById(userDTO.getId());
		userEntity = userRepository.findById(userDTO.getId()).get();
		chEntity.setUser(userEntity);

		//Find lotteryBoard with province and date----------
		List<LotteryBoardEntity> lbEntityList = new ArrayList<>();
		LotteryBoardEntity lotteryBoard = new LotteryBoardEntity();
		RegionEntity regionEntityByCity = regionRepository.findOneByNameProvince(userDTO.getProvince());
		System.out.println(regionEntityByCity.getRegionCode());
		//List LotteryBoard with city
		lbEntityList = lotteryBoardRepository.findByRegionCode(regionEntityByCity.getRegionCode());
		//Check condition with date in dto to choose with date
		String dateCH= userDTO.getDayCH()+"-"+userDTO.getMonthCH()+"-"+userDTO.getYearCH();
			for(LotteryBoardEntity lbEntity : lbEntityList) {
				String dateLB= lbEntity.getDate();
				System.out.println("dateCH: "+dateCH+", dateLB: "+dateLB);				
				if(dateCH.equals(dateLB)) {
					lotteryBoard = lbEntity;
					break;
				} 
			}		
		
		//check code draw
		drawLottery dl = new drawLottery();
		String status = dl.drawWithLottery(userDTO.getCodeDrawCH(), lotteryBoard);
		System.out.println("status: "+status);
		chEntity.setResult(status);
		chEntity = chRepository.save(chEntity);
		
		dto = userConverter.toDTO(userEntity);
		dto.setProvince(chEntity.getProvince());
		dto.setStatus(chEntity.getResult());
		String dmyCH= chEntity.getDateCH();
		String[] output = dmyCH.split("-");
		dto.setDayCH(output[0]);
		dto.setMonthCH(output[1]);
		dto.setYearCH(output[2]);
		dto.setCodeDrawCH(chEntity.getCodeDrawCH());
		
		
		return dto;
	}

	@Override
	public void deleteOne(long id) {
		chRepository.deleteById(id);
		
	}

	@Override
	public void deleteMany(String idList) {
		//Separate id string
		String[] output = idList.split("-");
		long[] ids = new long[output.length];
		for (int i = 0; i < output.length; i++) {
			ids[i] = Long.parseLong(output[i]);
		}

		//Then delete
		for (long item : ids) {
			System.out.println("ID: "+item);
			//Check with id, if not existed to throw can find
			CusHistoryEntity chEnity = chRepository.findById(item).get();
//			String codeDraw = chEnity.getCodeDrawCH();
//			System.out.println("codeDraw: "+codeDraw);
//			System.out.println("chEnity.getCodeDrawCH(): "+chEnity.getCodeDrawCH());
//			if(!chEnity.getCodeDrawCH().isEmpty()) {
//				throw new CanNotFindUserException("Fail, Your history "+item+" is not exist !");
//			}
			chRepository.deleteById(item);
		}
		
	}
	
	

	@Override
	public List<UserDTO> findAllByUser_id(long user_id) {
		List<UserDTO> dtoList = new ArrayList<>();
		List<CusHistoryEntity> chListOld = new ArrayList<>();
		
		chListOld = chRepository.findByUser_id(user_id);
		List<CusHistoryEntity> chList = new ArrayList<>();
		
		//Transfer due to stack over flow
		for(CusHistoryEntity item : chListOld) {
			String CodeDrawCH = item.getCodeDrawCH();
			String DateCH = item.getDateCH();
			Long Id = item.getId();
			String Province = item.getProvince();
			String Result = item.getResult();
//			UserEntity User = item.getUser();

			CusHistoryEntity chEntity = new CusHistoryEntity();
			chEntity.setCodeDrawCH(CodeDrawCH);
			chEntity.setDateCH(DateCH);
			chEntity.setId(Id);
			chEntity.setProvince(Province);
			chEntity.setResult(Result);
//			chEntity.setUser(User);
			chList.add(chEntity);
		}

		if(chList.isEmpty()) {
			System.out.println("chList empty !");
		}
		
		UserEntity userEntityOld = new UserEntity();
		userEntityOld = userRepository.findById(user_id).get();
		
		UserEntity userEntity = new UserEntity();

		userEntity.setAddressUser(userEntityOld.getAddressUser());
		userEntity.setNameUser(userEntityOld.getNameUser());
		userEntity.setGenderUser(userEntityOld.getGenderUser());
		userEntity.setBirthDay(userEntityOld.getBirthDay());
		userEntity.setPhone(userEntityOld.getPhone());
		userEntity.setMailUser(userEntityOld.getMailUser());
		userEntity.setPasswordUser(userEntityOld.getPasswordUser());
		userEntity.setRoleUser(userEntityOld.getRoleUser());
		userEntity.setId(userEntityOld.getId());
		
//		System.out.println("userEntity: "+userEntity.toString());
		
		for(CusHistoryEntity item : chList) {
//			System.out.println(item.toString());
			UserDTO dto = chConverter.toDTO(item);
			
			dto.setId(userEntity.getId());
			dto.setAddressUser(userEntity.getAddressUser());
			dto.setNameUser(userEntity.getNameUser());
			dto.setGenderUser(userEntity.getGenderUser());
			dto.setPhone(userEntity.getPhone());
			dto.setMailUser(userEntity.getMailUser());
			dto.setPasswordUser(userEntity.getPasswordUser());
			dto.setRoleUser(userEntity.getRoleUser());
			String dmyBD= userEntity.getBirthDay();
			String[] output = dmyBD.split("-");
			dto.setDayBD(output[0]);
			dto.setMonthBD(output[1]);
			dto.setYearBD(output[2]);
			
			dtoList.add(dto);
		}
		
		return dtoList;
	}


	/*Hàm này chưa cần dùng đến*/
	@Override
	public List<UserDTO> findAll() {
		List<UserDTO> CusHistoryDTOList = new ArrayList<>();
		List<CusHistoryEntity> lbEntityList = chRepository.findAll();
//		for(CusHistoryEntity item : lbEntityList) {
//			long id =  chRepository.getUser_id(item.getId());
////			UserEntity userEntity = userRepository.findOneById(id);
//			UserEntity userEntity = userRepository.findById(id).get();
//			UserDTO dto = new UserDTO();
//			dto = userConverter.toDTO(userEntity);
//			dto.setCodeDrawCH(item.getCodeDrawCH());
//			dto.setProvince(item.getProvince());
//			dto.setStatus(item.getResult());
//			
//			String dmyCH = item.getDateCH();
//			String[] output = dmyCH.split("-");
//			dto.setDayCH(output[0]);
//			dto.setMonthCH(output[1]);
//			dto.setYearCH(output[2]);
//			
//			CusHistoryDTOList.add(dto);
//			
//		}
		
		return CusHistoryDTOList;
	}






}

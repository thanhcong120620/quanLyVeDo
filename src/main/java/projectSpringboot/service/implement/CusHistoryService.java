package projectSpringboot.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projectSpringboot.algorithm.GetCurrentDate;
import projectSpringboot.algorithm.drawLottery;
import projectSpringboot.converter.CusHistoryConverter;
import projectSpringboot.converter.UserConverter;
import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.CusHistoryEntity;
import projectSpringboot.entity.LotteryBoardEntity;
import projectSpringboot.entity.RegionEntity;
import projectSpringboot.entity.UserEntity;
import projectSpringboot.exceptionAndError.UserNotFoundException;
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
		return this.chRepository.findByUser_id(user_id, pageable);
	}
	
	@Override
	public List<UserDTO> findAllByUserid2(long user_id, int pageNo, int pageSize) {//sửa lại tên hàm
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
			String dateDraw = item.getDateDraw();
//			UserEntity User = item.getUser();

			CusHistoryEntity chEntity = new CusHistoryEntity();
			chEntity.setCodeDrawCH(CodeDrawCH);
			chEntity.setDateCH(DateCH);
			chEntity.setId(Id);
			chEntity.setProvince(Province);
			chEntity.setResult(Result);
			chEntity.setDateDraw(dateDraw);
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
		userEntity.setActive(userEntityOld.isActive());
		userEntity.setPasswordRandom(userEntityOld.getPasswordRandom());
		
		
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
			dto.setPasswordRandom(userEntity.getPasswordRandom());
			
			String dmyBD= userEntity.getBirthDay();
			String[] output = dmyBD.split("-");
			dto.setDayBD(output[0]);
			dto.setMonthBD(output[1]);
			dto.setYearBD(output[2]);
			
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	
	
	/*
	 * Draw and save data
	 * */
	@Override
	public UserDTO save(UserDTO userDTO) {
		UserDTO dto = new UserDTO();
		CusHistoryEntity chEntity = new CusHistoryEntity();
		UserEntity userEntity = new UserEntity();
		
		System.out.println("1");
		chEntity = chConverter.toEntity(userDTO);
//		userEntity = userRepository.findOneById(userDTO.getId());
		userEntity = userRepository.findById(userDTO.getId()).get();
		chEntity.setUser(userEntity);
		
		System.out.println("2");
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
			
		System.out.println("3");
		//check and save code draw
		drawLottery dl = new drawLottery();
		String status = dl.drawWithLottery(userDTO.getCodeDrawCH(), lotteryBoard);
		System.out.println("status: "+status);
		chEntity.setResult(status);
		//set current day
		chEntity.setDateDraw(GetCurrentDate.getCurrentDate());
		//save data
		chEntity = chRepository.save(chEntity);
		
		System.out.println("4");
		dto = userConverter.toDTO(userEntity);
		dto.setProvince(chEntity.getProvince());
		dto.setStatus(chEntity.getResult());
		dto.setCodeDrawCH(chEntity.getCodeDrawCH());
		String dmyCH= chEntity.getDateCH();
		String[] output = dmyCH.split("-");
		dto.setDayCH(output[0]);
		dto.setMonthCH(output[1]);
		dto.setYearCH(output[2]);
		String dmyDr= chEntity.getDateDraw();
		String[] outputDr = dmyDr.split("-");
		dto.setDayCH(outputDr[0]);
		dto.setMonthCH(outputDr[1]);
		dto.setYearCH(outputDr[2]);
		System.out.println("5");
		return dto;
	}

	@Override
	public void deleteOne(long id) {
		System.out.println("id service: "+id);
		chRepository.deleteById(id);
		
	}

	@Override
	public void deleteMany(String idList, long user_id) throws UserNotFoundException {
		//Separate id string
		System.out.println("0"); 
		String[] output = idList.split("-");
		long[] idDTO = new long[output.length];
		for (int i = 0; i < output.length; i++) {
			idDTO[i] = Long.parseLong(output[i]);
		}
		System.out.println("1");//----------------
		//Then delete
		List<Long> idEntity = new ArrayList<>();
//		List<CusHistoryEntity> CHList = chRepository.findAll();//error
		List<CusHistoryEntity> CHList = chRepository.findByUser_id(user_id);
		for(CusHistoryEntity item : CHList) {
			idEntity.add(item.getId());
		}
		System.out.println("2");//----------------
		boolean success = true;
		for(long itemDTO : idDTO) {
			boolean notExist = true;
			for(long itemEntity : idEntity) {
				//Check id is existed --> delete
				if(itemDTO == itemEntity) {
					notExist = false;
				} 
			}
			//Check id not exited --> throw exception
			if(notExist) {
				success = false;
				throw new  UserNotFoundException("ID "+itemDTO+" không tồn tại" );
			} 
		}
		System.out.println("3");
		if(success) {
			for(long itemDTO : idDTO) {
				chRepository.deleteById(itemDTO);
			}
		}
			
		
		
	}
	
	

	@Override
	public List<UserDTO> findAllByUserid(long user_id) {
		List<UserDTO> dtoList = new ArrayList<>();
		List<CusHistoryEntity> chListOld = new ArrayList<>();
		
		chListOld = chRepository.findByUser_id(user_id);
		List<CusHistoryEntity> chList = new ArrayList<>();
		
		//Transfer due to stack over flow
		for(CusHistoryEntity item : chListOld) {
			String CodeDrawCH = item.getCodeDrawCH();
			String DateCH = item.getDateCH();
			String DateDraw = item.getDateDraw();
			Long Id = item.getId();
			String Province = item.getProvince();
			String Result = item.getResult();
//			UserEntity User = item.getUser();

			CusHistoryEntity chEntity = new CusHistoryEntity();
			chEntity.setCodeDrawCH(CodeDrawCH);
			chEntity.setDateCH(DateCH);
			chEntity.setDateDraw(DateDraw);
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
		userEntity.setActive(userEntityOld.isActive());
		userEntity.setPasswordRandom(userEntityOld.getPasswordRandom());
		
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
			dto.setPasswordRandom(userEntity.getPasswordRandom());
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

	@Override
	public UserDTO draw(UserDTO userDTO) {
		UserDTO dto = new UserDTO();
		CusHistoryEntity chEntity = new CusHistoryEntity();
		UserEntity userEntity = new UserEntity();
		List<LotteryBoardEntity> lbEntityList = new ArrayList<>();
		
		System.out.println("1");
		//Find all lotteryBoard----------

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
			
		System.out.println("2");
		//check and save code draw
		drawLottery dl = new drawLottery();
		String status = dl.drawWithLottery(userDTO.getCodeDrawCH(), lotteryBoard);
		System.out.println("status: "+status);
		chEntity.setResult(status);
		
		//set current day
		chEntity.setDateDraw(GetCurrentDate.getCurrentDate());
		dto.setStatus(status);		
		
		return dto;
	}






}

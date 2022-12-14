package projectSpringboot.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projectSpringboot.converter.LotteryBoardConverter;
import projectSpringboot.converter.RegionConverter;
import projectSpringboot.dto.LotteryDTO;
import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.LotteryBoardEntity;
import projectSpringboot.entity.RegionEntity;
import projectSpringboot.entity.UserEntity;
import projectSpringboot.exceptionAndError.UserNotFoundException;
import projectSpringboot.repository.LotteryBoardRepository;
import projectSpringboot.repository.RegionRepository;
import projectSpringboot.service.ILotteryBoardService;

@Service
@Transactional
public class LotteryBoardService implements ILotteryBoardService{

	
	@Autowired
	LotteryBoardRepository lotteryBoardRepository;
	
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	private LotteryBoardConverter lotteryBoardConverter;
	
	@Autowired
	private RegionConverter regionConverter;
	

	@Override
	public LotteryDTO save(LotteryDTO lotteryDTO) {
		
		RegionEntity regionEntity = new RegionEntity();
		
		RegionEntity oldEntity = regionRepository.findOneByRegionCode(lotteryDTO.getRegionCode());
		
		if(oldEntity != null) {
			RegionEntity oldEntity2 = new RegionEntity();
			oldEntity2.setId(oldEntity.getId());
			oldEntity2.setNameProvince(oldEntity.getNameProvince());
			oldEntity2.setNameRegion(oldEntity.getNameRegion());
			oldEntity2.setRegionCode(oldEntity.getRegionCode());
			
			regionEntity = regionConverter.toEntity(lotteryDTO, oldEntity2);
		} else {
			regionEntity = regionConverter.toEntity(lotteryDTO);
		}
		
		regionEntity = regionRepository.save(regionEntity);
		
		
		
		LotteryBoardEntity lotteryBoardEntity = new LotteryBoardEntity();
		if(lotteryDTO.getId() != null) {
			System.out.println("test start: "+lotteryDTO.getId());
			LotteryBoardEntity oldEntityLB1 = lotteryBoardRepository.findById(lotteryDTO.getId()).get();
			LotteryBoardEntity oldEntityLB = new LotteryBoardEntity();
			oldEntityLB.setId(oldEntityLB1.getId());
			oldEntityLB.setDate(oldEntityLB1.getDate());
			oldEntityLB.setRegionCode(oldEntityLB1.getRegionCode());
			oldEntityLB.setG0(oldEntityLB1.getG0());
			oldEntityLB.setG1(oldEntityLB1.getG1());
			oldEntityLB.setG2(oldEntityLB1.getG2());
			oldEntityLB.setG31(oldEntityLB1.getG31());
			oldEntityLB.setG32(oldEntityLB1.getG32());
			oldEntityLB.setG41(oldEntityLB1.getG41());
			oldEntityLB.setG42(oldEntityLB1.getG42());
			oldEntityLB.setG43(oldEntityLB1.getG43());
			oldEntityLB.setG44(oldEntityLB1.getG44());
			oldEntityLB.setG45(oldEntityLB1.getG45());
			oldEntityLB.setG46(oldEntityLB1.getG46());
			oldEntityLB.setG47(oldEntityLB1.getG47());
			oldEntityLB.setG5(oldEntityLB1.getG5());
			oldEntityLB.setG61(oldEntityLB1.getG61());
			oldEntityLB.setG62(oldEntityLB1.getG62());
			oldEntityLB.setG63(oldEntityLB1.getG63());
			oldEntityLB.setG7(oldEntityLB1.getG7());
			oldEntityLB.setG8(oldEntityLB1.getG8());
			
			System.out.println("oldEntityLB: "+oldEntityLB.toString());
			System.out.println("test end");
			
			lotteryBoardEntity = lotteryBoardConverter.toEntity(lotteryDTO, oldEntityLB);
		} else {
			lotteryBoardEntity = lotteryBoardConverter.toEntity(lotteryDTO);
		}
		
		//set up and save to database
//		long region_id = lotteryBoardRepository.findRegion_idById(0);
		RegionEntity regionEntityLB = regionRepository.findOneByRegionCode(lotteryBoardEntity.getRegionCode());
		
		lotteryBoardEntity.setRegion(regionEntityLB);
		lotteryBoardEntity = lotteryBoardRepository.save(lotteryBoardEntity);
		
		return lotteryBoardConverter.toDTO(lotteryBoardEntity);
	}

	@Override
	public void deleteMany(String idList) throws UserNotFoundException {
		String[] output = idList.split("-");
		long[] idDTO = new long[output.length];
		for(int i=0;i<output.length;i++) {
			idDTO[i] = Long.parseLong(output[i]);
		}

		List<Long> idEntity = new ArrayList<>();
		List<LotteryBoardEntity> LbList = lotteryBoardRepository.findAll();
		for(LotteryBoardEntity item : LbList) {
			idEntity.add(item.getId());
		}
		
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
		
		if(success) {
			for(long itemDTO : idDTO) {
				lotteryBoardRepository.deleteById(itemDTO);
			}
		}
		

		
	}
	
	@Override
	public void deleteOne(long id) {
		lotteryBoardRepository.deleteById(id);
	}

	@Override
	public LotteryDTO getLotteryBoardById(long id) {
		LotteryBoardEntity lbEntity = lotteryBoardRepository.findById(id).get();
		RegionEntity regionEntity = regionRepository.findOneByRegionCode(lbEntity.getRegionCode());
		LotteryDTO dto = lotteryBoardConverter.toDTO(lbEntity);
		dto.setId(lbEntity.getId());
		dto.setNameProvince(regionEntity.getNameProvince());
		dto.setNameRegion(regionEntity.getNameRegion());
		
		return dto;
	}
	

	@Override
	public List<LotteryDTO> findAllLB() {
		List<LotteryDTO> LotteryDTOList = new ArrayList<>();
		List<LotteryBoardEntity> lbEntityList = lotteryBoardRepository.findAll();
		for(LotteryBoardEntity lbEntity : lbEntityList) {
			RegionEntity regionEntity = regionRepository.findOneByRegionCode(lbEntity.getRegionCode());
			LotteryDTO dto = lotteryBoardConverter.toDTO(lbEntity);
			dto.setId(lbEntity.getId());
			dto.setNameProvince(regionEntity.getNameProvince());
			dto.setNameRegion(regionEntity.getNameRegion());
			LotteryDTOList.add(dto);
		}
		
		return LotteryDTOList;
	}



	/*
	 * Search with pagination
	 * */
	@Override
	public Page<LotteryBoardEntity> searchPaginated(LotteryDTO lotteryDTO, int pageNo, int pageSize) {
		RegionEntity regionEntityByCity = regionRepository.findOneByNameProvince(lotteryDTO.getNameProvince());
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.lotteryBoardRepository.findByRegionCode(regionEntityByCity.getRegionCode(), pageable);
	}
	@Override
	public List<LotteryDTO> searchList(LotteryDTO lotteryDTO, int pageNo, int pageSize) {
		List<LotteryDTO> LotteryDTOList = new ArrayList<>();
		List<LotteryBoardEntity> lbEntityList = new ArrayList<>();
		
		RegionEntity regionEntityByCity = regionRepository.findOneByNameProvince(lotteryDTO.getNameProvince());
		System.out.println(regionEntityByCity.getRegionCode());
		
		//List LotteryBoard with city
//		lbEntityList = lotteryBoardRepository.findByRegionCode(regionEntityByCity.getRegionCode());
		lbEntityList = searchPaginated(lotteryDTO, pageNo, pageSize).getContent();
		
		//Check condition with date in dto to choose with year
		for(LotteryBoardEntity lbEntity : lbEntityList) {
			String dmyLB= lbEntity.getDate();
			String[] output = dmyLB.split("-");
			String year = output[2];
			System.out.println("Year date: "+year);
			System.out.println("Year search: "+lotteryDTO.getYear());
			
			if(lotteryDTO.getYear().equals(year)) {
				RegionEntity regionEntity = regionRepository.findOneByRegionCode(lbEntity.getRegionCode());
				LotteryDTO dto = lotteryBoardConverter.toDTO(lbEntity);
				dto.setId(lbEntity.getId());
				dto.setNameProvince(regionEntity.getNameProvince());
				dto.setNameRegion(regionEntity.getNameRegion());
				LotteryDTOList.add(dto);
			} 

		}
		
		
		return LotteryDTOList;
	}


	@Override
	public int totalItem() {
		return (int) lotteryBoardRepository.count();
	}

	
	/*test query native*/
	@Override
	public List<LotteryDTO> findAllByRegion_id(String region_id) {
		List<LotteryDTO> LotteryDTOList = new ArrayList<>();
		List<LotteryBoardEntity> lbEntityList = new ArrayList<>();
		
		lbEntityList = lotteryBoardRepository.findByRegion_id(region_id);
		
		for(LotteryBoardEntity lbEntity : lbEntityList) {
			RegionEntity regionEntity = regionRepository.findById(Long.parseLong(region_id)).get();
			LotteryDTO dto = lotteryBoardConverter.toDTO(lbEntity);
			dto.setId(lbEntity.getId());
			dto.setNameProvince(regionEntity.getNameProvince());
			dto.setNameRegion(regionEntity.getNameRegion());
			LotteryDTOList.add(dto);
		}
		
		
		return LotteryDTOList;
	}
	

	
	/*
	 * Lottery Board with Pagination
	 * */
	@Override
	public Page<LotteryBoardEntity> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.lotteryBoardRepository.findAll(pageable);
	}

	@Override
	public List<LotteryDTO> findAllLotteryBoardPagination(int pageNo, int pageSize) {
		List<LotteryDTO> LotteryDTOList = new ArrayList<>();
		List<LotteryBoardEntity> lbEntityList = new ArrayList<>();
		lbEntityList = findPaginated(pageNo, pageSize).getContent();
		
		for(LotteryBoardEntity lbEntity : lbEntityList) {
			RegionEntity regionEntity = regionRepository.findOneByRegionCode(lbEntity.getRegionCode());
			LotteryDTO dto = lotteryBoardConverter.toDTO(lbEntity);
			dto.setId(lbEntity.getId());
			dto.setNameProvince(regionEntity.getNameProvince());
			dto.setNameRegion(regionEntity.getNameRegion());
			LotteryDTOList.add(dto);
		}
		
		return LotteryDTOList;
	}
	
	


	




	
	
	
}

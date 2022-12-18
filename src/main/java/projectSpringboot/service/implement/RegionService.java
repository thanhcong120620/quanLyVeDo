package projectSpringboot.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projectSpringboot.converter.RegionConverter;
import projectSpringboot.dto.LotteryDTO;
import projectSpringboot.entity.LotteryBoardEntity;
import projectSpringboot.entity.RegionEntity;
import projectSpringboot.repository.LotteryBoardRepository;
import projectSpringboot.repository.RegionRepository;
import projectSpringboot.service.IRegionService;

@Service
@Transactional
public class RegionService implements IRegionService{
	
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	LotteryBoardRepository lotteryBoardRepository;
	
	@Autowired
	private RegionConverter regionConverter;
	
	

	@Override
	public LotteryDTO save(LotteryDTO lotteryDTO) {
		RegionEntity regionEntity = new RegionEntity();
		
		RegionEntity oldEntity = regionRepository.findOneByRegionCode(lotteryDTO.getRegionCode());
		
		if(oldEntity != null) {
			RegionEntity oldEntity2 = new RegionEntity();
//			System.out.println("test region start");
			oldEntity2.setId(oldEntity.getId());
			oldEntity2.setNameProvince(oldEntity.getNameProvince());
			oldEntity2.setDrawTime(oldEntity.getDrawTime());
			oldEntity2.setRegionCode(oldEntity.getRegionCode());
//			System.out.println("oldEntity2: \n "+oldEntity2.toString());
			
			regionEntity = regionConverter.toEntity(lotteryDTO, oldEntity2);
		} else {
			regionEntity = regionConverter.toEntity(lotteryDTO);
		}
		
//		System.out.println(regionEntity.toString());
		regionEntity = regionRepository.save(regionEntity);
//		System.out.println("test region end");
		return regionConverter.toDTO(regionEntity);
		
	}
	



	@Override
	public List<LotteryDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int totalItem() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void delete(String[] regionCode) {
		for(String item : regionCode) {
			RegionEntity oldEntity = regionRepository.findOneByRegionCode(item);
			Long id = oldEntity.getId();
			regionRepository.deleteById(id);
		}	
		
	}


	@Override
	public LotteryDTO getRegionById(long id) {
		LotteryBoardEntity lbEntity = lotteryBoardRepository.findById(id).get();
		RegionEntity regionEntity = regionRepository.findOneByRegionCode(lbEntity.getRegionCode());
		LotteryDTO dto = regionConverter.toDTO(regionEntity);
		
		return dto;
	}



}

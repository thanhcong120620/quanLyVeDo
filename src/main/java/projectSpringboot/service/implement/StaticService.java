package projectSpringboot.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projectSpringboot.algorithm.GetCurrentDate;
import projectSpringboot.algorithm.SetUpDate;
import projectSpringboot.algorithm.Static;
import projectSpringboot.algorithm.drawLottery;
import projectSpringboot.converter.LotteryBoardConverter;
import projectSpringboot.dto.IndexDTO;
import projectSpringboot.dto.LotteryDTO;
import projectSpringboot.dto.StaticDTO;
import projectSpringboot.entity.CusHistoryEntity;
import projectSpringboot.entity.LotteryBoardEntity;
import projectSpringboot.entity.RegionEntity;
import projectSpringboot.model.numStatic.NumFrequency;
import projectSpringboot.model.numStatic.TransferNum;
import projectSpringboot.repository.CusHistoryRepository;
import projectSpringboot.repository.LotteryBoardRepository;
import projectSpringboot.repository.RegionRepository;
import projectSpringboot.repository.UserRepository;
import projectSpringboot.service.IStaticService;

@Service
@Transactional
public class StaticService implements IStaticService{
	
	@Autowired
	CusHistoryRepository chRepository;
	
	@Autowired
	UserRepository userRepository;
	
	//lottery
	@Autowired
	LotteryBoardRepository lotteryBoardRepository;
	
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	private LotteryBoardConverter lotteryBoardConverter;
	

	
	@Override
	public IndexDTO displayRegion(String regionCode) {
		IndexDTO dto = new IndexDTO();
		
		/*Set up north list*/
		List<LotteryBoardEntity> lbQuery = lotteryBoardRepository.findByRegionCode(regionCode);
		List<LotteryDTO> lbDtoList = new ArrayList<>();
		
		for(LotteryBoardEntity entity : lbQuery) {
			LotteryDTO lbDto = new LotteryDTO();
			LotteryBoardEntity lbItem = new LotteryBoardEntity();
			lbItem.setId(entity.getId());
			lbItem.setDate(entity.getDate());
			lbItem.setRegionCode(entity.getRegionCode());
			lbItem.setG0(entity.getG0());
			lbItem.setG1(entity.getG1());
			lbItem.setG2(entity.getG2());
			lbItem.setG31(entity.getG31());
			lbItem.setG32(entity.getG32());
			lbItem.setG41(entity.getG41());
			lbItem.setG42(entity.getG42());
			lbItem.setG43(entity.getG43());
			lbItem.setG44(entity.getG44());
			lbItem.setG45(entity.getG45());
			lbItem.setG46(entity.getG46());
			lbItem.setG47(entity.getG47());
			lbItem.setG5(entity.getG5());
			lbItem.setG61(entity.getG61());
			lbItem.setG62(entity.getG62());
			lbItem.setG63(entity.getG63());
			lbItem.setG7(entity.getG7());
			lbItem.setG8(entity.getG8());
			
			lbDto = lotteryBoardConverter.toDTO(lbItem);
			lbDtoList.add(lbDto);
		}
		dto.setLbList(lbDtoList);
		
		return dto;
	}
	

	@Override
	public IndexDTO draw(IndexDTO indexDTO) {
		System.out.println("indexDTO.getDayDraw(): "+indexDTO.getDayDraw());
		IndexDTO dto = new IndexDTO();
		List<LotteryBoardEntity> lbEntityList = new ArrayList<>();
		
		System.out.println("1");
		//Find lotteryBoard List----------
		RegionEntity regionEntityByCity = regionRepository.findOneByNameProvince(indexDTO.getProvince());
		lbEntityList = lotteryBoardRepository.findByRegionCode(regionEntityByCity.getRegionCode());
		
		//Check condition with date in dto to choose with date
		LotteryBoardEntity lotteryBoard = new LotteryBoardEntity();
		String dateCH= indexDTO.getDayDraw();
			for(LotteryBoardEntity lbEntity : lbEntityList) {
				String dateLB= lbEntity.getDate();
				System.out.println("dateCH: "+dateCH+", dateLB: "+dateLB);				
				if(dateCH.equals(dateLB)) {
					lotteryBoard = lbEntity;
					break;
				} 
			}		
		
		
		//set draw result
		drawLottery dl = new drawLottery();
		String status = dl.drawWithLottery(indexDTO.getCodeDraw(), lotteryBoard);
		System.out.println("status: "+status);
//		chEntity.setResult(status);
		dto.setStatus(status);		
		
		return dto;
	}


	@Override
	public StaticDTO staticLottery() {
		Static st = new Static();
		SetUpDate setUpDate = new SetUpDate();
		String currentDay = setUpDate.setCurrentDay();
		String day30Ago = setUpDate.set30DayAgo();
		
		//get list lottery 30 day ago
		List<LotteryBoardEntity> LotteryList = lotteryBoardRepository.findAll();
		List<NumFrequency> numList = new ArrayList<>();
		numList = st.numFrequency(LotteryList,day30Ago,currentDay); //have data

		//get list lottery order 30 day ago
		List<NumFrequency> numListOrdered = new ArrayList<>();
		numListOrdered = st.sortByFrequency(numList);//have data
//		for(NumFrequency item : numListOrdered) {
//			System.out.println(item.toString());
//		}
		
		//set up dto
		TransferNum transferNum = new TransferNum();
		StaticDTO dto = new StaticDTO();
		dto = transferNum.TransferNumDetail(numList);// no data
//		System.out.println(dto.toString());
		dto.setNumList(numList);
		dto.setNumListOrdered(numListOrdered);
		
		int n = numListOrdered.size();
		
		dto.setTop1(numListOrdered.get(n-1).getNum());
		dto.setFrequencyTop1(numListOrdered.get(n-1).getFrequency());
		dto.setPercentTop1(numListOrdered.get(n-1).getPercent());
		
		dto.setTop2(numListOrdered.get(n-2).getNum());
		dto.setFrequencyTop2(numListOrdered.get(n-2).getFrequency());
		dto.setPercentTop2(numListOrdered.get(n-2).getPercent());
		
		dto.setTop3(numListOrdered.get(n-3).getNum());
		dto.setFrequencyTop3(numListOrdered.get(n-3).getFrequency());
		dto.setPercentTop3(numListOrdered.get(n-3).getPercent());
		
		dto.setTop4(numListOrdered.get(n-4).getNum());
		dto.setFrequencyTop4(numListOrdered.get(n-4).getFrequency());
		dto.setPercentTop4(numListOrdered.get(n-4).getPercent());
		
		dto.setTop5(numListOrdered.get(n-5).getNum());
		dto.setFrequencyTop5(numListOrdered.get(n-5).getFrequency());
		dto.setPercentTop5(numListOrdered.get(n-5).getPercent());
		
		return dto;
	}


	@Override
	public IndexDTO displayYesterday(String day) {
		
		IndexDTO dto = new IndexDTO();
//		SetUpDate setUpDate = new SetUpDate();
		String setDay = day;
//		String setDay = setUpDate.setYesterday();
//		String setDay = "5-7-2022";
		
		/*Set up north list*/
		List<LotteryBoardEntity> lbNorthQuery = lotteryBoardRepository.findByDateAndRegion(setDay,"MB%");

		List<LotteryBoardEntity> lbNorth = new ArrayList<>();
		for(LotteryBoardEntity lbNorthQueryItem : lbNorthQuery) {
			LotteryBoardEntity lbNorthItem = new LotteryBoardEntity();
			lbNorthItem.setId(lbNorthQueryItem.getId());
			lbNorthItem.setDate(lbNorthQueryItem.getDate());
			lbNorthItem.setRegionCode(lbNorthQueryItem.getRegionCode());
			lbNorthItem.setG0(lbNorthQueryItem.getG0());
			lbNorthItem.setG1(lbNorthQueryItem.getG1());
			lbNorthItem.setG2(lbNorthQueryItem.getG2());
			lbNorthItem.setG31(lbNorthQueryItem.getG31());
			lbNorthItem.setG32(lbNorthQueryItem.getG32());
			lbNorthItem.setG41(lbNorthQueryItem.getG41());
			lbNorthItem.setG42(lbNorthQueryItem.getG42());
			lbNorthItem.setG43(lbNorthQueryItem.getG43());
			lbNorthItem.setG44(lbNorthQueryItem.getG44());
			lbNorthItem.setG45(lbNorthQueryItem.getG45());
			lbNorthItem.setG46(lbNorthQueryItem.getG46());
			lbNorthItem.setG47(lbNorthQueryItem.getG47());
			lbNorthItem.setG5(lbNorthQueryItem.getG5());
			lbNorthItem.setG61(lbNorthQueryItem.getG61());
			lbNorthItem.setG62(lbNorthQueryItem.getG62());
			lbNorthItem.setG63(lbNorthQueryItem.getG63());
			lbNorthItem.setG7(lbNorthQueryItem.getG7());
			lbNorthItem.setG8(lbNorthQueryItem.getG8());
			
			lbNorth.add(lbNorthItem);
		}
		
		/*If query null*/
		String checkNorth = ""+ lbNorth.size();
		if(checkNorth.endsWith("0")) {
			LotteryBoardEntity lbSouththItem = new LotteryBoardEntity();
			lbSouththItem.setDate("Trống");
			lbNorth.add(lbSouththItem);
		}

		List<LotteryDTO> lbNorthDTOList = new ArrayList<>();
		for(LotteryBoardEntity lbEntity : lbNorth) {
			if(lbNorth.get(0).getDate().equals("Trống")) {
				LotteryDTO lbNorthlDTO = new LotteryDTO();
				lbNorthlDTO.setNameProvince("Trống");
				lbNorthDTOList.add(lbNorthlDTO);
			}
			else {
				LotteryDTO lbNorthDTO = new LotteryDTO();
				RegionEntity regionEntityLB = regionRepository.findOneByRegionCode(lbEntity.getRegionCode());
				lbNorthDTO = lotteryBoardConverter.toDTO(lbEntity);
				lbNorthDTO.setNameProvince(regionEntityLB.getNameProvince());
				
				lbNorthDTOList.add(lbNorthDTO);
			}
		}

		

		
		dto.setNorthLbList(lbNorthDTOList);


		/*Set up central list*/
		List<LotteryBoardEntity> lbCentralQuery = lotteryBoardRepository.findByDateAndRegion(setDay,"MT%");
		List<LotteryBoardEntity> lbCentral = new ArrayList<>();
		for(LotteryBoardEntity lbCentralQueryItem : lbCentralQuery) {
			LotteryBoardEntity lbCentralItem = new LotteryBoardEntity();
			lbCentralItem.setId(lbCentralQueryItem.getId());
			lbCentralItem.setDate(lbCentralQueryItem.getDate());
			lbCentralItem.setRegionCode(lbCentralQueryItem.getRegionCode());
			lbCentralItem.setG0(lbCentralQueryItem.getG0());
			lbCentralItem.setG1(lbCentralQueryItem.getG1());
			lbCentralItem.setG2(lbCentralQueryItem.getG2());
			lbCentralItem.setG31(lbCentralQueryItem.getG31());
			lbCentralItem.setG32(lbCentralQueryItem.getG32());
			lbCentralItem.setG41(lbCentralQueryItem.getG41());
			lbCentralItem.setG42(lbCentralQueryItem.getG42());
			lbCentralItem.setG43(lbCentralQueryItem.getG43());
			lbCentralItem.setG44(lbCentralQueryItem.getG44());
			lbCentralItem.setG45(lbCentralQueryItem.getG45());
			lbCentralItem.setG46(lbCentralQueryItem.getG46());
			lbCentralItem.setG47(lbCentralQueryItem.getG47());
			lbCentralItem.setG5(lbCentralQueryItem.getG5());
			lbCentralItem.setG61(lbCentralQueryItem.getG61());
			lbCentralItem.setG62(lbCentralQueryItem.getG62());
			lbCentralItem.setG63(lbCentralQueryItem.getG63());
			lbCentralItem.setG7(lbCentralQueryItem.getG7());
			lbCentralItem.setG8(lbCentralQueryItem.getG8());
			
			lbCentral.add(lbCentralItem);
		}

		/*If query null*/
		String checkCentral = ""+ lbCentral.size();
		if(checkCentral.endsWith("0")) {
			LotteryBoardEntity lbSouththItem = new LotteryBoardEntity();
			lbSouththItem.setDate("Trống");
			lbCentral.add(lbSouththItem);
		}

		List<LotteryDTO> lbCentralDTOList = new ArrayList<>();
		for(LotteryBoardEntity lbEntity : lbCentral) {
			if(lbCentral.get(0).getDate().equals("Trống")) {
				LotteryDTO lbCentralDTO = new LotteryDTO();
				lbCentralDTO.setNameProvince("Trống");
				lbCentralDTOList.add(lbCentralDTO);
			}
			else {
				LotteryDTO lbCentralDTO = new LotteryDTO();
				RegionEntity regionEntityLB = regionRepository.findOneByRegionCode(lbEntity.getRegionCode());
				lbCentralDTO = lotteryBoardConverter.toDTO(lbEntity);
				lbCentralDTO.setNameProvince(regionEntityLB.getNameProvince());
				
				lbCentralDTOList.add(lbCentralDTO);
			}
		}
		dto.setCentralLbList(lbCentralDTOList);
		

		/*Set up south list*/
		List<LotteryBoardEntity> lbSouththQuery = lotteryBoardRepository.findByDateAndRegion(setDay,"MN%");
		List<LotteryBoardEntity> lbSouthth = new ArrayList<>();
		
		for(LotteryBoardEntity lbSouththQueryItem : lbSouththQuery) {
			LotteryBoardEntity lbSouththItem = new LotteryBoardEntity();
			lbSouththItem.setId(lbSouththQueryItem.getId());
			lbSouththItem.setDate(lbSouththQueryItem.getDate());
			lbSouththItem.setRegionCode(lbSouththQueryItem.getRegionCode());
			lbSouththItem.setG0(lbSouththQueryItem.getG0());
			lbSouththItem.setG1(lbSouththQueryItem.getG1());
			lbSouththItem.setG2(lbSouththQueryItem.getG2());
			lbSouththItem.setG31(lbSouththQueryItem.getG31());
			lbSouththItem.setG32(lbSouththQueryItem.getG32());
			lbSouththItem.setG41(lbSouththQueryItem.getG41());
			lbSouththItem.setG42(lbSouththQueryItem.getG42());
			lbSouththItem.setG43(lbSouththQueryItem.getG43());
			lbSouththItem.setG44(lbSouththQueryItem.getG44());
			lbSouththItem.setG45(lbSouththQueryItem.getG45());
			lbSouththItem.setG46(lbSouththQueryItem.getG46());
			lbSouththItem.setG47(lbSouththQueryItem.getG47());
			lbSouththItem.setG5(lbSouththQueryItem.getG5());
			lbSouththItem.setG61(lbSouththQueryItem.getG61());
			lbSouththItem.setG62(lbSouththQueryItem.getG62());
			lbSouththItem.setG63(lbSouththQueryItem.getG63());
			lbSouththItem.setG7(lbSouththQueryItem.getG7());
			lbSouththItem.setG8(lbSouththQueryItem.getG8());
			
			lbSouthth.add(lbSouththItem);
		}
		
		/*If query null*/
		String checkSouth = ""+ lbSouthth.size();
		if(checkSouth.endsWith("0")) {
			LotteryBoardEntity lbSouththItem = new LotteryBoardEntity();
			lbSouththItem.setDate("Trống");
			lbSouthth.add(lbSouththItem);
		}

		List<LotteryDTO> lbSouthDTOList = new ArrayList<>();
		for(LotteryBoardEntity lbEntity : lbSouthth) {
			if(lbSouthth.get(0).getDate().equals("Trống")) {
				LotteryDTO lbSouthDTO = new LotteryDTO();
				lbSouthDTO.setNameProvince("Trống");
				lbSouthDTOList.add(lbSouthDTO);
			}
			else {
			LotteryDTO lbSouthDTO = new LotteryDTO();
			RegionEntity regionEntityLB = regionRepository.findOneByRegionCode(lbEntity.getRegionCode());
			lbSouthDTO = lotteryBoardConverter.toDTO(lbEntity);
			lbSouthDTO.setNameProvince(regionEntityLB.getNameProvince());
			lbSouthDTOList.add(lbSouthDTO);
			}
		}
		dto.setSouthLbList(lbSouthDTOList);
		
		
		
		
		
		return dto;
	}









}

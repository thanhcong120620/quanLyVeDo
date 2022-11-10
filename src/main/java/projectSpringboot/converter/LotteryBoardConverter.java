package projectSpringboot.converter;

import org.springframework.stereotype.Component;

import projectSpringboot.dto.LotteryDTO;
import projectSpringboot.entity.LotteryBoardEntity;

@Component
public class LotteryBoardConverter {
	
	public LotteryBoardEntity toEntity(LotteryDTO dto) {
		
		LotteryBoardEntity LotteryBoard = new LotteryBoardEntity();
		
		LotteryBoard.setRegionCode(dto.getRegionCode());
		LotteryBoard.setDate(""+dto.getDay()+"-"+dto.getMonth()+"-"+dto.getYear());
		LotteryBoard.setG0(dto.getG0());
		LotteryBoard.setG1(dto.getG1());
		LotteryBoard.setG2(dto.getG2());
		LotteryBoard.setG31(dto.getG31());
		LotteryBoard.setG32(dto.getG32());
		LotteryBoard.setG41(dto.getG41());
		LotteryBoard.setG42(dto.getG42());
		LotteryBoard.setG43(dto.getG43());
		LotteryBoard.setG44(dto.getG44());
		LotteryBoard.setG45(dto.getG45());
		LotteryBoard.setG46(dto.getG46());
		LotteryBoard.setG47(dto.getG47());
		LotteryBoard.setG5(dto.getG5());
		LotteryBoard.setG61(dto.getG61());
		LotteryBoard.setG62(dto.getG62());
		LotteryBoard.setG63(dto.getG63());
		LotteryBoard.setG7(dto.getG7());
		LotteryBoard.setG8(dto.getG8());
		
		return LotteryBoard;
		
	}
	
	
	public LotteryBoardEntity toEntity(LotteryDTO dto, LotteryBoardEntity LotteryBoard) {
		
		//because to update a old data with new data from DTO, we use old Object to change data
		
		//set Region Code
		LotteryBoard.setRegionCode(dto.getRegionCode());
		//set Date		
		LotteryBoard.setDate(""+dto.getDay()+"-"+dto.getMonth()+"-"+dto.getYear());
		//set Lottery prize
		LotteryBoard.setG0(dto.getG0());
		LotteryBoard.setG1(dto.getG1());
		LotteryBoard.setG2(dto.getG2());
		LotteryBoard.setG31(dto.getG31());
		LotteryBoard.setG32(dto.getG32());
		LotteryBoard.setG41(dto.getG41());
		LotteryBoard.setG42(dto.getG42());
		LotteryBoard.setG43(dto.getG43());
		LotteryBoard.setG44(dto.getG44());
		LotteryBoard.setG45(dto.getG45());
		LotteryBoard.setG46(dto.getG46());
		LotteryBoard.setG47(dto.getG47());
		LotteryBoard.setG5(dto.getG5());
		LotteryBoard.setG61(dto.getG61());
		LotteryBoard.setG62(dto.getG62());
		LotteryBoard.setG63(dto.getG63());
		LotteryBoard.setG7(dto.getG7());
		LotteryBoard.setG8(dto.getG8());
		
		return LotteryBoard;
	}
	
	public LotteryDTO toDTO(LotteryBoardEntity LotteryBoard) {
		LotteryDTO dto = new LotteryDTO();
		
		//set Region Code
		dto.setRegionCode(LotteryBoard.getRegionCode());
		
		//set date
		String dmyRegion = LotteryBoard.getDate();
		String[] output = dmyRegion.split("-");
		dto.setDay(output[0]);
		dto.setMonth(output[1]);
		dto.setYear(output[2]);
		
		//set Lottery prize
		dto.setG0(LotteryBoard.getG0());
		dto.setG1(LotteryBoard.getG1());
		dto.setG2(LotteryBoard.getG2());
		dto.setG31(LotteryBoard.getG31());
		dto.setG32(LotteryBoard.getG32());
		dto.setG41(LotteryBoard.getG41());
		dto.setG42(LotteryBoard.getG42());
		dto.setG43(LotteryBoard.getG43());
		dto.setG44(LotteryBoard.getG44());
		dto.setG45(LotteryBoard.getG45());
		dto.setG46(LotteryBoard.getG46());
		dto.setG47(LotteryBoard.getG47());
		dto.setG5(LotteryBoard.getG5());
		dto.setG61(LotteryBoard.getG61());
		dto.setG62(LotteryBoard.getG62());
		dto.setG63(LotteryBoard.getG63());
		dto.setG7(LotteryBoard.getG7());
		dto.setG8(LotteryBoard.getG8());
		
		return dto;
	}
	
	

}

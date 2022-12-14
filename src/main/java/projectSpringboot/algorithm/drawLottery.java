package projectSpringboot.algorithm;

import projectSpringboot.entity.LotteryBoardEntity;

public class drawLottery {
	
	LotteryBoardEntity LotteryBoard = new LotteryBoardEntity();
	
	public drawLottery() {
	}
	
	public String drawWithLottery(String codeDraw, LotteryBoardEntity LotteryBoard) {

		if(codeDraw.equals(LotteryBoard.getG0())) {
			return"Giải đặc biệt";
		}
		else if(codeDraw.substring(1).equals(LotteryBoard.getG1())) {
			return"Giải nhất";
		}
		else if(codeDraw.substring(1).equals(LotteryBoard.getG2())) {
			return"Giải nhì";
		}
		else if(codeDraw.substring(1).equals(LotteryBoard.getG31())) {
			return"Giải ba";
		}
		else if(codeDraw.substring(1).equals(LotteryBoard.getG32())) {
			return"Giải ba";
		}
		else if(codeDraw.substring(1).equals(LotteryBoard.getG41())) {
			return"Giải tư";
		}
		else if(codeDraw.substring(1).equals(LotteryBoard.getG42())) {
			return"Giải tư";
		}
		else if(codeDraw.substring(1).equals(LotteryBoard.getG43())) {
			return"Giải tư";
		}
		else if(codeDraw.substring(1).equals(LotteryBoard.getG44())) {
			return"Giải tư";
		}
		else if(codeDraw.substring(1).equals(LotteryBoard.getG45())) {
			return"Giải tư";
		}
		else if(codeDraw.substring(1).equals(LotteryBoard.getG46())) {
			return"Giải tư";
		}
		else if(codeDraw.substring(1).equals(LotteryBoard.getG47())) {
			return"Giải tư";
		}
		else if(codeDraw.substring(2).equals(LotteryBoard.getG5())) {
			return"Giải năm";
		}
		else if(codeDraw.substring(2).equals(LotteryBoard.getG61())) {
			return"Giải sáu";
		}
		else if(codeDraw.substring(2).equals(LotteryBoard.getG62())) {
			return"Giải sáu";
		}
		else if(codeDraw.substring(2).equals(LotteryBoard.getG63())) {
			return"Giải sáu";
		}
		else if(codeDraw.substring(3).equals(LotteryBoard.getG7())) {
			return"Giải bảy";
		}
		else if(codeDraw.substring(4).equals(LotteryBoard.getG8())) {
			return"Giải tám";
		}
		else {
			return "Không trúng";
		}
		
	}
}

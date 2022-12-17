package projectSpringboot.algorithm;

import java.util.ArrayList;
import java.util.List;

import projectSpringboot.entity.LotteryBoardEntity;
import projectSpringboot.model.numStatic.NumFrequency;

public class Static {
	
	public List<NumFrequency> sortByNum(List<NumFrequency> numList) {
		int n = numList.size();
		for(int i=0; i < n; i++){   
        	for(int j=1; j < (n-i); j++){  
                	 int j0 = Integer.parseInt(numList.get(j-1).getNum());
                	 int j1 = Integer.parseInt(numList.get(j).getNum());
                         if(j0 > j1 ){  
                        	  NumFrequency fr0 = numList.get(j-1);
                        	  NumFrequency fr1 = numList.get(j);
                            numList.remove(j-1);
                            numList.add(j-1, fr1);
                            numList.remove(j);
                            numList.add(j, fr0);
                        	  
                         }  
                 }  
         }
         
         return numList;
	}
	
	public List<NumFrequency> sortByFrequency(List<NumFrequency> numList) {
		int n = numList.size();
        for(int i=0; i < n; i++){  
                 for(int j=1; j < (n-i); j++){  
                          if(numList.get(j-1).getFrequency() > numList.get(j).getFrequency()){  
                        	  NumFrequency fr0 = numList.get(j-1);
                        	  NumFrequency fr1 = numList.get(j);
                            numList.remove(j-1);
                            numList.add(j-1, fr1);
                            numList.remove(j);
                            numList.add(j, fr0);
                         }  
                 }  
         }
         
         return numList;
	}

	public List<NumFrequency> numFrequency(List<LotteryBoardEntity> lbEntity, String dateStart, String dateEnd) {
		List<NumFrequency> numList = new ArrayList<>();
		Static st = new Static();
		for(LotteryBoardEntity entity : lbEntity) {
			// check and filter by day
			String dmyLB = entity.getDate();
			String[] outputLB = dmyLB.split("-");
			int dayLB = Integer.parseInt(outputLB[0]);
			int monthLB = Integer.parseInt(outputLB[1]);
			int yearLB = Integer.parseInt(outputLB[2]);
			
			String dmySt = dateStart;
			String[] outputSt = dmySt.split("-");
			int daySt = Integer.parseInt(outputSt[0]);
			int monthSt = Integer.parseInt(outputSt[1]);
			int yearSt = Integer.parseInt(outputSt[2]);
			
			String dmyEn = dateEnd;
			String[] outputEn = dmyEn.split("-");
			int dayEn = Integer.parseInt(outputEn[0]);
			int monthEn = Integer.parseInt(outputEn[1]);
			int yearEn = Integer.parseInt(outputEn[2]);

			/*
			 * //If same year boolean sameYear = yearSt==yearEn &&
			 * ((monthLB==monthSt&&dayLB>=daySt)||(monthLB==monthEn&&dayLB<=dayEn));
			 * System.out.println("sameYear: "+sameYear); //If different year boolean
			 * difYear = yearSt!=yearEn &&
			 * ((monthLB==monthSt&&dayLB>=daySt)||(monthLB==monthEn&&dayLB<=dayEn));
			 * System.out.println("difYear: "+difYear);
			 */
			
			//set up numFrequency
			if((monthLB==monthSt&&dayLB>=daySt)||(monthLB==monthEn&&dayLB<=dayEn)) {
				st.setFrequency(numList, entity.getG0().substring(4));
				st.setFrequency(numList, entity.getG1().substring(3));
				st.setFrequency(numList, entity.getG2().substring(3));
				st.setFrequency(numList, entity.getG31().substring(3));
				st.setFrequency(numList, entity.getG32().substring(3));
				st.setFrequency(numList, entity.getG41().substring(3));
				st.setFrequency(numList, entity.getG42().substring(3));
				st.setFrequency(numList, entity.getG43().substring(3));
				st.setFrequency(numList, entity.getG44().substring(3));
				st.setFrequency(numList, entity.getG45().substring(3));
				st.setFrequency(numList, entity.getG46().substring(3));
				st.setFrequency(numList, entity.getG47().substring(3));
				st.setFrequency(numList, entity.getG5().substring(2));
				st.setFrequency(numList, entity.getG61().substring(2));
				st.setFrequency(numList, entity.getG62().substring(2));
				st.setFrequency(numList, entity.getG63().substring(2));
				st.setFrequency(numList, entity.getG7().substring(1));
				st.setFrequency(numList, entity.getG8());
			}
		}
		
		double frTotal = 0;
		for(NumFrequency num : numList) {
			frTotal = frTotal + num.getFrequency();
		}
//		System.out.println("frTotal: "+frTotal);
//		System.out.println("Tổng số vé: "+(frTotal/18));
		
		for(NumFrequency num : numList) {
			double getFrequency = (double)num.getFrequency();
//			System.out.println("getFrequency():"+ getFrequency + ", frTotal: "+frTotal);
			double rate = (double)(getFrequency/frTotal)*100;
//			System.out.println("rate:"+ rate);
			num.setPercent((double) Math.round(rate * 100) / 100);
		}
		
		return numList;
	}
	
	public List<NumFrequency> setFrequency(List<NumFrequency> numList, String code) {
		if(numList.isEmpty()) {
//			System.out.println("1");
			NumFrequency numNew = new NumFrequency();
			numNew.setFrequency(1);
			numNew.setNum(code);
			numList.add(numNew);
		}
		else if(!numList.isEmpty()) {
			boolean exist = true;
			for(int i=0;i<numList.size();i++) {
				int count = numList.get(i).getFrequency();		
				if(numList.get(i).getNum().equals(code)) {
//					System.out.println("2");
					count = count +1;
					numList.get(i).setFrequency(count);
					exist = false;
				}
			}
//			System.out.println("exist: "+exist);
			if(exist) {
				NumFrequency numNew = new NumFrequency();
				numNew.setFrequency(1);
				numNew.setNum(code);
				numList.add(numNew);
			}
		}
		return numList;
	}
	
	
}

package projectSpringboot.algorithm;

import java.util.Calendar;

public class GetCurrentDate {

	public static String getCurrentDate() {
		  Calendar instance = Calendar.getInstance();
	      int year = instance.get(Calendar.YEAR);
	      int month = instance.get(Calendar.MONTH)+1;
	      int day = instance.get(Calendar.DAY_OF_MONTH);
	      String date = ""+day+"-"+month+"-"+year;
		return date;
	}
	
//	public static void main(String[] args){
//		
//        System.out.println("current day: "+GetCurrentDate.getCurrentDate());
//}
}

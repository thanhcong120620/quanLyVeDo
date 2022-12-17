package projectSpringboot.algorithm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class SetUpDate {
	

	public String setCalendarIndex(String dayCalendar) {
		String[] output = dayCalendar.split("/");
		int day = Integer.parseInt(output[1]);
		int month = Integer.parseInt(output[0]);
		int year = Integer.parseInt(output[2]);
		
		String setDay = ""+day+"-"+month+"-"+year;
		return setDay;
	}
	
	
	
	public String setYesterday() {
		String Today = setCurrentDay();
		String[] output = Today.split("-");
		int dayCr = Integer.parseInt(output[0]);
		int monthCr = Integer.parseInt(output[1]);
		int yearCr = Integer.parseInt(output[2]);
		
		int day=0; 
		int month=0; 
		int year=0;
		
		if(dayCr==1&&monthCr==1) {
			day = 31; month =12; year = yearCr-1;
		} else if (dayCr == 1) {
			if(monthCr==1||monthCr==3||monthCr==5||monthCr==7||monthCr==8||monthCr==10||monthCr==12) {
				month = monthCr -1;
				day = 31;
				year = yearCr;
			} else if (monthCr==4||monthCr==6||monthCr==5||monthCr==9||monthCr==11) {
				month = monthCr -1;
				day = 30;
				year = yearCr;
			} else {
				month = monthCr;
				day = 28;
				year = yearCr;
			}
		} else {
			year = yearCr;
			month = monthCr;
			day = dayCr -1;
		}
		
		String yesterday = ""+day+"-"+month+"-"+year;
		
		return yesterday;
	}

	public String setCurrentDay() {
		//case1:
		/*
		 * Date date = new Date(); DateFormat dateFormat = null; dateFormat = new
		 * SimpleDateFormat("dd/MM/yyyy"); System.out.println(dateFormat.format(date));
		 */
		
		//case2:
		Calendar c = Calendar.getInstance();
//		c.setTime(new Date(yourmilliseconds));
		c.set(Calendar.MILLISECOND, 0);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss.SSS'Z'");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		String dmy = ""+ sdf.format(c.getTime());
		String[] output = dmy.split("-");
		int day = Integer.parseInt(output[2]);
		int month = Integer.parseInt(output[1]);
		int year = Integer.parseInt(output[0]);
		
		String currentDay =  ""+ day +"-"+ month +"-"+ year;
//		System.out.println(currentDay);
		
		return currentDay;
	}
	
	
	public String set30DayAgo() {
		SetUpDate setUpDate = new SetUpDate();
		String currentDay = setUpDate.setCurrentDay();
//		String currentDay = "15-06-2023";
		
		String dmy = currentDay;
		String[] output = dmy.split("-");
		int dayCr = Integer.parseInt(output[0]);
		int monthCr = Integer.parseInt(output[1]);
		int yearCr = Integer.parseInt(output[2]);

		/*
		 * System.out.println("dayCr: "+dayCr); System.out.println("monthCr: "+monthCr); System.out.println("yearCr: "+yearCr);
		 */
		
		//If month = 1 
		if(monthCr == 1) {
			int day = dayCr;
			int month = 12;
			int year = yearCr-1;
			String day30Ago = ""+ day +"-"+ month +"-"+ year;
			return day30Ago;
		}		
		int day = dayCr;
		int month = monthCr-1;
		int year = yearCr;
		String day30Ago = ""+ day +"-"+ month +"-"+ year;
		return day30Ago;
	}
	
}

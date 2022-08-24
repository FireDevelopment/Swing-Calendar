package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class HolidayUtils {

	private static HashMap<String, String> holidays = new HashMap<String, String>(); //list of holidays used in calendar
	public static HashMap<String, String> events = new HashMap<String, String>(); //list of events put in calendar
	
	public static int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	private static LocalDate now = LocalDate.now();
	
	public static int year = now.getYear();
	
	private static int daysNotice;
	
	public static void initHolidays() {
		
		if (LocalDate.now().isLeapYear()) { //adjust days in month list if it is leap year
			daysInMonth[2] = 29;
		}
		
		try {
			BufferedReader holidayFile = new BufferedReader(new FileReader("./events/holidays.calendar")); //to get holidays
			BufferedReader holidayCounter = new BufferedReader(new FileReader("./events/holidays.calendar")); //to get number of lines
			
			int holidayCount = 0; //count lines in file
			while((holidayCounter.readLine()) != null) {holidayCount++;} //read all lines to see how many holidays there are
			holidayCounter.close();
			
			holidayCount = (holidayCount/2); //events are two lines long
			
			for (int l=0; l<holidayCount; l++) { //read through every holiday
				
				String holidayName = holidayFile.readLine(); //get the holiday name
				String holidayID = holidayFile.readLine(); //get the holiday id
				
				holidays.put(holidayName, holidayID); //put it in the holiday list thing
			}
			
			holidayFile.close();
			
		} catch(Exception e) {
			System.err.println("Holidays file could not be found. Full Error:\n\n"+e);
		}
		
		//formating - type 1:
		// 1231 = 12 month of year and 31st day of the month
		
		//formatting - type 2:
		// 001144 = 00 indicates date changes based on year, 11 indicates event is in november
		// first 4 indicates the day of week with 1 being monday which is thursday
		// second 4 indicates the fourth thursday of november
		
		//formatting - type 3:
		// 0000051 = 0000 in dicates date changes every year, and is the last weekday of a month
		// 05 indicates it is in may
		// 1 indicates it is the last monday of may

	}
	
	private static void getDaysNotice() {
		//default setting
		daysNotice = 7;
		
		//read file to get last days notice thing
		try {
			BufferedReader fileSetting = new BufferedReader(new FileReader("./settings/notice.calendar"));
			daysNotice = Integer.parseInt(fileSetting.readLine());
			fileSetting.close();
		}
		catch(Exception e) {
			System.err.println("Could not theme settings file.\n\n" + e);
		}
	}
	
	public static String getHoliday(int checkForNext) {
		
		LocalDateTime date = LocalDateTime.now();
		int currentmonth = date.getMonthValue();
		int currentday = date.getDayOfMonth();
		int newMonth;
		int newerDay;
		
		for (int i=0; i<holidays.size(); i++) {
			  String holiday = (String) holidays.keySet().toArray()[i];
			  String value = holidays.get(holiday);
			  int month = Integer.parseInt(value.substring(0,2));
			  int day = Integer.parseInt(value.substring(2,4));
			  
			  if (month == 0) {
				  if (day == 0) {
					  
					  month = Integer.parseInt(value.substring(4,6));
					  int targetWeekDay = Integer.parseInt(value.substring(6,7));
					  
					  boolean dayFound = false;
					  int newDay = 0;
					  
					  if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
						  newDay = 31;}
					  if (month == 4 || month == 6 || month == 9 || month == 11) {
						  newDay = 30;}
					  if (month == 2) {
						  LocalDate leapYearTest = LocalDate.now();
						  if (leapYearTest.isLeapYear()) { newDay = 29;}
						  else {newDay = 28;}
					  }
					  
					  while(dayFound == false) {
						  LocalDate weekdayTest = LocalDate.of(date.getYear(), month, newDay);
						  int weekday = weekdayTest.getDayOfWeek().getValue();
						  if (weekday == targetWeekDay) {
							  dayFound = true;
						  } else {
							  newDay--;
						  }	  
						  day = newDay;
					  }
				  } else {
					  boolean dayFound = false;
					  int newDay = 1;
					  
					  int targetWeekDay = Integer.parseInt(value.substring(4,5));
					  int weekNumber = Integer.parseInt(value.substring(5,6));
					  
					  while(dayFound == false) {
						  LocalDateTime weekdayTest = LocalDateTime.of(date.getYear(), day, newDay, 1, 0);
						  int weekday = weekdayTest.getDayOfWeek().getValue();
						  if (weekday == targetWeekDay) {
							  newDay = newDay+((weekNumber-1)*7);
							  dayFound = true;
						  } else {
							  newDay++;
						  }
					  }
					  
					  month = day;
					  day = newDay;
				  }
			  }
			  if (checkForNext == 0) {
				  if (currentmonth == month && currentday == day) {
					  return holiday;
				  }
			  } else {
				  
				  getDaysNotice();
				  
				  for(int x = 1; x<daysNotice+1; x++) {
					  if (currentday+x>daysInMonth[currentmonth]) {
						  newerDay = currentday+x-daysInMonth[currentmonth];
						  newMonth = currentmonth+1;
					  } else {
						  newerDay = currentday+x;
						  newMonth = currentmonth;
					  }

					  
					  if (newMonth == month && newerDay == day) {
						  if (x == 1) {
							  return holiday+" - "+x+" day";
						  } else {
							  return holiday+" - "+x+" days";
						  }
					  }
				  }
			  }
				  
		  }
		  if (checkForNext == 0) {
			  return "No Holidays Today";
		  } else {
			  return "";
		  }
	}

}

package minuteurs;

import java.util.Calendar;
import java.util.Date;


public class DateMinuteur {
		
	public static Date addHours(Date date, int hours) {
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date);
		cal.add(Calendar.HOUR, hours);
		return cal.getTime();
	}
}
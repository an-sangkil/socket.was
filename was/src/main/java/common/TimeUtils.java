package common;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeUtils {

	public String getWorldTime(String city) {
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 aa hh시mm분ss초");
		
		TimeZone timeZone = TimeZone.getTimeZone(city);
		calendar.setTimeZone(timeZone);

		String str = dateFormat.format(calendar.getTime());
		
		return str;
		
	}

	public void getTimeZoneCity() {
		for (String name : TimeZone.getAvailableIDs()) {
			System.out.println(name);
		}
	}
}

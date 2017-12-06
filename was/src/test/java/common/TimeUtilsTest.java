package common;

import org.junit.Test;

public class TimeUtilsTest {
	
	@Test
	public void getWorldTimeTest() {
		TimeUtils time = new TimeUtils();
		time.getWorldTime("Asia/Seoul");
	}
	
	@Test
	public void getTimeZoneCity() {
		TimeUtils time = new TimeUtils();
		time.getTimeZoneCity();
	}
}

package core.Helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Timing {
	
public static String defaultTimeMask = "dd/MM/yyyy HH:mm:ss.SSSS";
	
	public static String nowTime() {
		return new SimpleDateFormat(defaultTimeMask).format(Calendar
				.getInstance().getTime());
	}

	public static String nowTime(String mask) {
		return new SimpleDateFormat(mask).format(Calendar.getInstance()
				.getTime());
	}
	
	
	public static String nowTime(Long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return new SimpleDateFormat(defaultTimeMask).format(c.getTime());
	}
	
	
	public static String nowTime(String mask, Long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return new SimpleDateFormat(mask).format(c.getTime());
	}

}

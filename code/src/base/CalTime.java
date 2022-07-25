package base;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class CalTime {
	public static String calculate(int num, int mode) {
		LocalDateTime now = LocalDateTime.now();
		String time = now.toString().replace('T', ' ');
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currdate = null;
		try {
			currdate = format.parse(time);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
//		System.out.println("初始的时间是：" + time);
		
		Calendar ca = Calendar.getInstance();
		ca.setTime(currdate);
		if(mode == 0)
			ca.add(Calendar.DATE, num);
		if(mode == 1)
			ca.add(Calendar.MONTH, num);
		if(mode == 2)
			ca.add(Calendar.YEAR, num);
		currdate = ca.getTime();
		
		String enddate = format.format(currdate);
//		System.out.println("增加天数以后的时间：" + enddate);
		
		return enddate;
	}
}

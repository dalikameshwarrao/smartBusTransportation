package com.smartbustransport.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
	private static final String DATE_FORMAT_WITHOUT_ZONE = "yyyy-MM-dd'T'HH:mm:ss";
	public static SimpleDateFormat sdfT = new SimpleDateFormat(DATE_FORMAT_WITHOUT_ZONE);
	public static final SimpleDateFormat sdfD = new SimpleDateFormat("yyyyMMdd");

	public enum Roles {
		ADMIN, OWNER
	};
	public enum METRIC_CALCULATION_TYPE {
		YEARLY, WEEKLY, HOURLY, MONTHLY,DAY
	}
	public static int dateToNumber(Date date) {
		int dayId = -1;
		try {
			dayId = Integer.parseInt(sdfD.format(date));
		} catch (Exception e) {
		}
		return dayId;
	}
	public static int dateToMonthStart(Date date) {
		int dayId = -1;
		try {
			dayId = Integer.parseInt(sdfD.format(date));
			dayId = ((dayId / 100) * 100) + 1;
		} catch (Exception e) {
		}
		return dayId;
	}
}

package com.my.common.mapper.filter;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Dates {
	
	public static final String CHINESE_DATE_FORMAT_LINE = "yyyy-MM-dd";
	public static final String CHINESE_DATETIME_FORMAT_LINE = "yyyy-MM-dd HH:mm:ss";
	public static final String CHINESE_DATE_FORMAT_SLASH = "yyyy/MM/dd";
	public static final String CHINESE_DATETIME_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";
	public static final String DATETIME_NOT_SEPARATOR = "yyyyMMddHHmmssSSS";
	// 中国周一是一周的第一天
	public static final int FIRST_DAY_OF_WEEK = Calendar.MONDAY;
	private static final String DEFAULT_DATE_FORMAT = CHINESE_DATETIME_FORMAT_LINE;
	private static SimpleDateFormat dLFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final List<String> patterns = Lists
		.newArrayList(new String[] {	CHINESE_DATETIME_FORMAT_LINE, CHINESE_DATETIME_FORMAT_SLASH,
										DATETIME_NOT_SEPARATOR, CHINESE_DATE_FORMAT_LINE, CHINESE_DATE_FORMAT_SLASH });

	/** 简单年日期格式 */
	public static final String DATE_YEAR = "yyyy";
	/** 简单周日期格式 */
	public static final String DATE_WEEK = "EE";

	/** 简单年月期格式 */
	public static final String DATE_SHORT_SIMPLE_MONTH_FORMAT = "yyyyMM";
	/** 简单年月期格式 */
	public static final String DATE_SHORT_LINED_MONTH_FORMAT = "yyyy-MM";

	/** 简单年月日日期格式 */
	public static final String DATE_SHORT_SIMPLE_FORMAT = "yyyyMMdd";
	/** 简单年月日 时格式 */
	public static final String DATE_SHORT_SIMPLE_FORMAT_WITHHOUR = "yyyyMMddHH";
	/** 简单年月日 时 分格式 */
	public static final String DATE_SHORT_SIMPLE_FORMAT_WITHMINUTE = "yyyyMMddHHmm";
	/** 年月日时分秒格式 */
	public static final String DATE_LONG_SIMPLE_FORMAT = "yyyyMMddHHmmss";
	/** 简单时分秒毫秒 */
	public static final String DATE_SHORT_TIME_FORMAT = "HHmmss.S";
	/** 简单时分钞 **/
	public static final String DATE_TIME_FORMAT = "HHmmss";
	/** 年月日日期格式 */
	public static final String DATE_SHORT_FORMAT = "yyyy-MM-dd";
	/** 中文年月日日期格式 */
	public static final String DATE_SHORT_CHN_FORMAT = "yyyy年MM月dd日";
	/** 年月日时日期格式 */
	public static final String DATE_WITHHOUR_FORMAT = "yyyy-MM-dd HH";
	/** 中文年月日时日期格式 */
	public static final String DATE_WITHHOUR_CHN_FORMAT = "yyyy年MM月dd日 HH";
	/** 年月日时分日期格式 */
	public static final String DATE_WITHMINUTE_FORMAT = "yyyy-MM-dd HH:mm";
	/** 中文年月日时分日期格式 */
	public static final String DATE_WITHMINUTE_CHN_FORMAT = "yyyy年MM月dd日 HH:mm";
	/** 年月日时分秒日期格式 */
	public static final String DATE_WITHSECOND_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** 中文年月日时分秒日期格式 */
	public static final String DATE_WITHSECOND_CHN_FORMAT = "yyyy年MM月dd日 HH:mm:ss";
	/** 年月日时分秒毫秒日期格式 */
	public static final String DATE_WITHMILLISECOND_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	/** 中文年月日时分秒毫秒日期格式 */
	public static final String DATE_WITHMILLISECOND_CHN_FORMAT = "yyyy年MM月dd日 HH:mm:ss.S";


	public static Date parse(String dateString, String[] patterns) {
		for (String pattern : patterns) {
			if (StringUtils.isBlank(pattern)) {
				continue;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(CHINESE_DATETIME_FORMAT_LINE);
			try {
				return sdf.parse(dateString);
			} catch (Exception e) {
				// ignore exception
			}
		}
		throw new UnsupportedOperationException(
			"Parse String[" + dateString + "] to Date faulure with patterns[" + Arrays.toString(patterns) + "]");
		
	}
	
	public static Date parse(String dateString) {
		return parse(dateString, patterns.toArray(new String[0]));
	}


	public static String format(Date date) {
		return format(date, DEFAULT_DATE_FORMAT);
	}
	public static String format(Date date, String pattern) {
		SimpleDateFormat sdf = getSimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static SimpleDateFormat getSimpleDateFormat(String defaultFormat) {
		if (defaultFormat!=null && defaultFormat!="") {
			defaultFormat = DEFAULT_DATE_FORMAT;
		}
		return new SimpleDateFormat(defaultFormat);
	}

}

package cn.exam.util;

import lombok.extern.slf4j.Slf4j;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//

@Slf4j
public class DateUtil {


	/**
	 * 时间格式
	 */
	public static final String YYYY = "yyyy";
	public static final String YM = "yyyyMM";
	public static final String Y_M = "yyyy-MM";
	public static final String YMD = "yyyyMMdd";
	public static final String Y_M_D = "yyyy-MM-dd";
	public static final String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
	public static final String YMDHMS = "yyyyMMddHHmmss";
	public static final String Y_M_D_ZW = "yyyy年MM月dd日";
	public static final String Y_M_D_ZW_1 = "yyyy年M月d日";
	public static final String Y_M_D_H_M_ZW = "yyyy年MM月dd日HH:mm";
	public static final String Y_M_D_H_M_ZW_2 = "yyyy年MM月dd日HH点mm分ss秒";
	/**
	 * 当前日期
	 */
	public static String currentDate;

	/***************************************************************
	 * 获取yyyy-MM-dd格式的时间串
	 *
	 * @return
	 **************************************************************/
	public static String getCurrentDate() {
		return getCurrentDate(Y_M);
	}

	/****************************************************************
	 * 获取自定义格式的时间串
	 *
	 * @param format
	 * @return
	 ***************************************************************/
	public static String getCurrentDate(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	/***************************************************************
	 * 获取yyyy-MM-dd HH:mm:ss格式的时间串
	 *
	 ***************************************************************/
	public static String getCurrentDateTime() {
		return getCurrentDate(Y_M_D_H_M_S);
	}

	/****************************************************************
	 * 获取某格式的时间
	 *
	 * @param date
	 * @param format
	 * @return
	 ***************************************************************/
	public static Date getDate(String date, String format) {

		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			log.error("获取某格式的时间异常", e);
		}
		return null;
	}

	/****************************************************************
	 * 获取字符串
	 *
	 * @param date   时间
	 * @param format 格式
	 * @return
	 ***************************************************************/
	public static String getDateToString(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/****************************************************************
	 * 获取其他月的当前时间
	 *
	 * @param num 正数为之后num个月，负数为之前num个月
	 * @return
	 ****************************************************************/
	public static Date getAnotherMonthDate(int num) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, num);
		return c.getTime();
	}

	/**
	 * 转化成 XMLGregorianCalendar格式
	 *
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar dateToXmlDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		DatatypeFactory dtf = null;
		try {
			dtf = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
		}
		XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
		dateType.setYear(cal.get(Calendar.YEAR));
		// 由于Calendar.MONTH取值范围为0~11,需要加1
		dateType.setMonth(cal.get(Calendar.MONTH) + 1);
		dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
		dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
		dateType.setMinute(cal.get(Calendar.MINUTE));
		dateType.setSecond(cal.get(Calendar.SECOND));
		return dateType;
	}

	/**
	 * 字符串日期转换成中文格式日期
	 *
	 * @param date 字符串日期 yyyy-MM-dd
	 * @return yyyy年MM月dd日
	 * @throws Exception
	 */
	public static String dateToCnDate(String date) {
		String result = "";
		String[] cnDate = new String[] { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String ten = "十";
		String[] dateStr = date.split("-");
		for (int i = 0; i < dateStr.length; i++) {
			for (int j = 0; j < dateStr[i].length(); j++) {
				String charStr = dateStr[i];
				String str = String.valueOf(charStr.charAt(j));
				if (charStr.length() == 2) {
					if ("10".equals(charStr)) {
						result += ten;
						break;
					} else {
						if (j == 0) {
							if (charStr.charAt(j) == '1') {
								result += ten;
							}

							else if (charStr.charAt(j) == '0') {
								result += "";
							}

							else {
								result += cnDate[Integer.parseInt(str)] + ten;
							}

						}
						if (j == 1) {
							if (charStr.charAt(j) == '0') {
								result += "";
							}

							else {
								result += cnDate[Integer.parseInt(str)];
							}

						}
					}
				} else {
					result += cnDate[Integer.parseInt(str)];
				}
			}
			if (i == 0) {
				result += "年";
				continue;
			} else if (i == 1) {
				result += "月";
				continue;
			} else if (i == 2) {
				result += "日";
				continue;
			}
		}
		return result;
	}

	/**
	 * 返回num天后的日期
	 *
	 * @param date   开始日期
	 * @param num    天数
	 * @param format 返回格式
	 * @return
	 */
	public static String getAnotherDate(String date, int num, String format) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new SimpleDateFormat(Y_M_D).parse(date));
			calendar.add(Calendar.DAY_OF_MONTH, num);
			return getDateToString(calendar.getTime(), format);
		} catch (ParseException e) {
			log.error("获取某格式的时间异常", e);
		}
		return null;
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param begin 较小的时间
	 * @param end   较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(String begin, String end) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(Y_M_D);
			Date bdate;
			bdate = sdf.parse(begin);
			Date edate = sdf.parse(end);
			Calendar cal = Calendar.getInstance();
			cal.setTime(bdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(edate);
			long time2 = cal.getTimeInMillis();
			long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(betweenDays));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取指定日期，月份的最后一天
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateString(String date, String format) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new SimpleDateFormat(Y_M_D).parse(date));
			return getDateToString(calendar.getTime(), format);
		} catch (ParseException e) {
			log.error("获取某格式的时间异常", e);
		}
		return null;
	}

	/**
	 * 日期转换成中文年月日
	 *
	 * @param date
	 * @return
	 */
	public static String convertDate(String date) {
		String newDate = "";
		String[] dates = new String[3];
		dates[0] = date.substring(0, 4);
		dates[1] = date.substring(5, 7);
		dates[2] = date.substring(8, date.length() - 1);
		for (int i = 0; i < dates.length; i++) {
			if (dates[i].startsWith("0")) {
				dates[i] = dates[i].substring(1, 2);
			}
			newDate += dates[i];
			if (i == 0) {
				newDate += "年";
			} else if (i == 1) {
				newDate += "月";
			} else {
				newDate += "日";
			}
		}
		return newDate;
	}

	/**
	 * 判断某一时间是否在一个区间内
	 *
	 * @param sourceTime 时间区间,半闭合,如[10:00-20:00)
	 * @param curTime    需要判断的时间 如10:00
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean isInTime(String sourceStartTime, String sourceEndTime, String curTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(Y_M_D);
		try {
			long now = sdf.parse(curTime).getTime();
			long start = sdf.parse(sourceStartTime).getTime();
			long end = sdf.parse(sourceEndTime).getTime();
			if (end < start) {
				if (now >= end && now <= start) {
					return false;
				} else {
					return true;
				}
			} else {
				if (now >= start && now <= end) {
					return true;
				} else {
					return false;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String replaceDateFormat(String date, String format) {
		try {
			date = date.replaceAll("[_./]", "-");
			date = date.replace("年", "-");
			date = date.replace("月", "-");
			date = date.replace("日", "");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new SimpleDateFormat(Y_M_D).parse(date));
			return getDateToString(calendar.getTime(), format);
		} catch (ParseException e) {
			log.error("获取某格式的时间异常", e);
		}
		return null;
	}

	/**
	 * 通过日历类的Calendar.add方法第二个参数-1达到前一天日期的效果
	 *
	 * @return
	 */
	public static String getYesterdayByCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date time = calendar.getTime();
		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(time);
		return yesterday;
	}

	/**
	 * 获取两个日期之间的所有日期
	 *
	 * @param startTime 开始日期
	 * @param endTime   结束日期
	 * @return
	 */
	public static List<String> getDays(String startTime, String endTime) {

		// 返回的日期集合
		List<String> days = new ArrayList<String>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date start = dateFormat.parse(startTime);
			Date end = dateFormat.parse(endTime);

			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start);

			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end);
			// 日期加1(包含结束)
			tempEnd.add(Calendar.DATE, +1);
			while (tempStart.before(tempEnd)) {
				days.add(dateFormat.format(tempStart.getTime()));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return days;
	}

	/**
	 * 获取两个日期之间的所有月份
	 *
	 * @param startTime 开始日期
	 * @param endTime   结束日期
	 * @return
	 */
	public static List<String> getMonths(String startTime, String endTime) {

		// 返回的日期集合
		List<String> days = new ArrayList<String>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		try {
			Date start = dateFormat.parse(startTime);
			Date end = dateFormat.parse(endTime);

			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start);

			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end);
			// 日期加1(包含结束)
			tempEnd.add(Calendar.MONTH, +1);
			while (tempStart.before(tempEnd)) {
				days.add(dateFormat.format(tempStart.getTime()));
				tempStart.add(Calendar.MONTH, 1);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return days;
	}

	/**
	 * 给出两个时间返回中间的月份
	 *
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	public static List<String> getMonthBetween(String minDate, String maxDate) {
		ArrayList<String> result = new ArrayList<String>();
		// 格式化为年月
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
		try {
			min.setTime(sdf.parse(minDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		try {
			max.setTime(sdf.parse(maxDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}
		return result;
	}

	/**
	 * 获取指定月份的上一个月
	 * @param month 指定月份
	 * @param format 格式
	 * @return
	 */
	public static String getLastMonth(String month,String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dft = new SimpleDateFormat(format);
		try {
			cal.setTime(dft.parse(month));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(Calendar.MONTH, -1);
		String lastMonth = dft.format(cal.getTime());
		return lastMonth;
	}

	/**
	 * 获取指定月份的下一个月
	 * @param month 指定月份
	 * @param format 格式
	 * @return
	 */
	public static String getPreMonth(String month,String format) {
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat dft = new SimpleDateFormat(format);
		try {
			cal.setTime(dft.parse(month));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(Calendar.MONTH, 1);
		String preMonth = dft.format(cal.getTime());
		return preMonth;
	}

	/**
	 * 时间戳转换成日期格式字符串
	 * @param seconds 精确到秒的字符串
	 * @param
	 * @return
	 */
	public static String timeStamp2Date(String seconds,String format) {
		if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
			return "";
		}
		if(format == null || format.isEmpty()){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds+"000")));
	}
	/**
	 * 日期格式字符串转换成时间戳
	 * @param
	 * @param format 如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2TimeStamp(String date_str){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat();
			return String.valueOf(sdf.parse(date_str).getTime()/1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 取得当前时间戳（精确到秒）
	 * @return
	 */
	public static String timeStamp(){
		long time = System.currentTimeMillis();
		String t = String.valueOf(time/1000);
		return t;
	}

	public static String timeStampmill(){
		long time = System.currentTimeMillis();
		String t = String.valueOf(time);
		return t;
	}
	/**
	 * 获取上月的最后一天
	 */
	public static  String getBeforeDate(){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		int month=calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return sf.format(calendar.getTime());
	}
	/**
	 * 获取本月月的最后一天
	 */
	public static  String getMaxDate(String format){
		SimpleDateFormat sf=new SimpleDateFormat(format);
		Calendar calendar=Calendar.getInstance();
		int month=calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return sf.format(calendar.getTime());
	}
    /**
	 * 日期格式字符串转换成时间戳
	 * @param date 字符串日期
	 * @param format 如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String dateToTimeStamp(String date_str,String format){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return String.valueOf(sdf.parse(date_str).getTime()/1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 *	 时间戳转换成日期格式字符串
	 * 	@param seconds 精确到毫秒的字符串
	 * 	@param formatStr
	 * 	@return
   */
	public static String timeStampToDate(String seconds,String format) {
		if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
			return "";
		}
		if(format == null || format.isEmpty()) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds)));
	}
	/**
          * 日期转时间戳
     */
    public static  Long getDayTime(String time)  {
	    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date date1 = null;
	    try {
		  date1 = format.parse(time);
	    } catch (ParseException e) {
		  e.printStackTrace();
	    }
	    //日期转时间戳（毫秒）
	    return date1.getTime();
    }

	/**
	 * 获取去年的最后一天
	 * @param date
	 * @return
	 */
	public static  String getBeforeYearDay(String date) {
		SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Calendar c=Calendar.getInstance(Locale.CHINA);
		try {
			c.setTime(f.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.add(Calendar.YEAR, -1);//拨回去年
		c.set(Calendar.DAY_OF_YEAR,c.getActualMaximum(Calendar.DAY_OF_YEAR));//最后一天
		return f.format(c.getTime());
	}



	public   static boolean isWorkDay() {
		Date today = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		int weekday = c.get(Calendar.DAY_OF_WEEK);
		return weekday != 1;
	}
}

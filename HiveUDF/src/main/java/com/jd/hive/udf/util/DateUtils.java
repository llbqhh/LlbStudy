package com.jd.hive.udf.util;

/**
 * Created by lilibiao on 2018/4/20.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
    public DateUtils() {
    }

    public static Date actualMaximumDate(Date date) {
        Calendar calendar = calendar(date);
        int actualMaximumDay = calendar.getActualMaximum(5);
        calendar.set(5, actualMaximumDay);
        return calendar.getTime();
    }

    public static Date actualMinimumDate(Date date) {
        Calendar calendar = calendar(date);
        int actualMininumDay = calendar.getActualMinimum(5);
        calendar.set(5, actualMininumDay);
        return calendar.getTime();
    }

    public static int monthBetween(Date startDate, Date endDate) {
        boolean months = false;
        Calendar startDay = calendar(startDate);
        Calendar endDay = calendar(endDate);
        int startYear = startDay.get(1);
        int startMonth = startDay.get(2);
        int endYear = endDay.get(1);
        int endMonth = endDay.get(2);
        int months1 = (endYear - startYear) * 12 + (endMonth - startMonth);
        return months1;
    }

    public static Calendar calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String getTimeByCustomPattern(Date date, String pattern) {
        return (new SimpleDateFormat(pattern)).format(date);
    }

    public static int getMonth(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(2) + 1;
    }

    public static int getYear(Date date) {
        Calendar calendar = getCalendar(date);
        return calendar.get(1);
    }

    public static Date parse(String str, String pattern) {
        try {
            return (new SimpleDateFormat(pattern)).parse(str);
        } catch (ParseException var3) {
            throw new RuntimeException("日期格式转换错误", var3);
        }
    }

    public static List getDate(Date begDate, Date endDate) {
        int betweenMonth = monthBetween(begDate, endDate);
        int begMonth = getMonth(begDate);
        int years = getYear(begDate);
        String dates = "";
        String months = "";
        boolean month = false;
        int year = 12;
        ArrayList result = new ArrayList();

        for(int i = begMonth; i <= begMonth + betweenMonth; ++i) {
            int var11 = i;
            if(i > 12) {
                var11 = i - year;
                if(var11 == 1) {
                    ++years;
                }

                if(var11 >= 12) {
                    year += 12;
                }
            }

            if(var11 < 10) {
                months = "-0" + var11;
            } else {
                months = "-" + var11;
            }

            dates = years + months + "-01";
            result.add(actualMaximumDate(parse(dates, "yyyy-MM-dd")));
        }

        return result;
    }

    public static ArrayList<String> dateToList(String beginDateStr, String endDateStr) throws ParseException {
        ArrayList dateList = new ArrayList();
        if(beginDateStr.compareTo(endDateStr) > 0) {
            return dateList;
        } else {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = dateFormat1.parse(beginDateStr);
            Date endDate = dateFormat1.parse(endDateStr);

            for(Date date = beginDate; !date.equals(endDate); date = c.getTime()) {
                dateList.add(dateFormat1.format(date));
                c.setTime(date);
                c.add(5, 1);
            }

            dateList.add(endDateStr);
            return dateList;
        }
    }

    public static void main(String[] args) throws ParseException {
        String beginDateStr = "2017-12-05";
        String endDateStr = "2017-12-09";
        System.out.println(dateToList(beginDateStr, endDateStr));
    }
}
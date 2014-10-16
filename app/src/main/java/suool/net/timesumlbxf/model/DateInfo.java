package suool.net.timesumlbxf.model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by SuooL on 2014/10/9.
 */
public class DateInfo {
    private static String mYear;
    private static String mMonth;
    private static String mDay;
    private static String mWay;
    private static int iWeekday;
    private static int iDay;
    private static int iMonth;
    private static int iYear;
    private static int iHourOfDay;
    private static int iMinuteOfHour;


    Calendar cal = Calendar.getInstance();

 //   cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

    /**
     * 构造方法
     */
    public DateInfo() {

        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if(1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        iWeekday = cal.get(Calendar.DAY_OF_WEEK);
        iYear = cal.get(Calendar.YEAR);
        iMonth = cal.get(Calendar.MONTH) + 1;
        iDay = cal.get(Calendar.DAY_OF_MONTH);
        iHourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        iMinuteOfHour = cal.get(Calendar.MINUTE);

        mYear = String.valueOf(iYear);     // 获取当前年份
        mMonth = String.valueOf(iMonth);   // 获取当前月份
        mDay = String.valueOf(iDay);       // 获取当前月份的日期号码
        mWay = String.valueOf(iWeekday);   // 当前周的第几日
        if ("7".equals(mWay)) {
            mWay = "天";
        } else if ("1".equals(mWay)) {
            mWay = "一";
        } else if ("2".equals(mWay)) {
            mWay = "二";
        } else if ("3".equals(mWay)) {
            mWay = "三";
        } else if ("4".equals(mWay)) {
            mWay = "四";
        } else if ("5".equals(mWay)) {
            mWay = "五";
        } else if ("6".equals(mWay)) {
            mWay = "六";
        }

        Log.d("MyTest", mYear+" "+ mDay+" " +mMonth);
    }

    // 格式化的日期
    public String getFormatDate(Date date){
        String time;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        cal.setTime(date);

        time = sdf.format(cal.getTime());

        return time;
    }

    /**
     * 根据日期计算所在周的的日期范围
     * @param time 指定的日期
     * @return 返回日期组成的字符串数组
     */
    public String[] convertWeekByDate(Date time) {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if(1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        Log.d("MyTest","YYYYY要计算日期为:" + sdf.format(cal.getTime())); //输出要计算日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day-1);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值

        String[] date = new String[day];

        for (int i = 0; i< day; i++ ) {
            cal.add(Calendar.DATE, 1);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            date[i] = sdf.format(cal.getTime());
            Log.d("MyTest","所在周周"+(i+1)+"的日期："+date[i]);
        }
        return date;
    }
    public String[] dateOfMonth(Date time) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        iDay = cal.get(Calendar.DAY_OF_MONTH);                   // 本月的第几日
        String[] date = new String[iDay];
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-iDay-1);  // 上月的最后一日
        for (int i =0; i < iDay; i++) {
            cal.add(Calendar.DATE, 1);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            date[i] = sdf.format(cal.getTime());
        }
        return date;
    }


    public int getmWeekday() {
        return iWeekday;
    }

    public int getiWeekday(){
        return iWeekday;
    }

    public int getiDay() {
        return iDay;
    }

    public int getiMonth() {
        return iMonth;
    }

    // 获取小时
    public static int getiMinuteOfHour() {
        return iMinuteOfHour;
    }

    // 获取分钟
    public static int getiHourOfDay() {
        return iHourOfDay;
    }

    // 获取日
    public String getmDay() {
        return mDay;
    }

    // 获取月
    public String getmMonth() {
        return mMonth;
    }

    // 获取年
    public String getmYear() {
        return mYear;
    }

    // 获取星期
    public String getmWay() {
        return mWay;
    }
}
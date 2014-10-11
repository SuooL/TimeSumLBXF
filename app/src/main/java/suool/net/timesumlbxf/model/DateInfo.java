package suool.net.timesumlbxf.model;

import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by SuooL on 2014/10/9.
 */
public class DateInfo {
    private static String mYear;
    private static String mMonth;
    private static String mDay;
    private static String mWay;

    /**
     * 构造方法
     */
    public DateInfo() {
        Calendar c = Calendar.getInstance();

        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }

        Log.d("MyTest", mYear+" "+ mDay+" " +mMonth);
    }



    // 获取星期
    public String getmWay() {
        return mWay;
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


}
package suool.net.timesumlbxf.model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SuooL on 2014/10/9.
 */
public class TimeRange {

    private String week_start;
    private String week_end;
    private String date_start;
    private String date_end;
    private String month;
    private String year;
    private int weekday;


    DateInfo dateInfo = new DateInfo();

    public String getWeek_start() {
        week_start = "一";
        return week_start;
    }

    public String getWeek_end() {
        week_end = dateInfo.getmWay();
        return week_end;
    }

    public int getDayOfWeek(){
        return dateInfo.getiWeekday();
    }

    public String getDate_start() {
        date_start = "1";
        return date_start;
    }

    public String getDate_end() {
        date_end = dateInfo.getmDay();
        return date_end;
    }

    public String getMonth() {
        month = dateInfo.getmMonth();
        return month;
    }

    public String getYear() {
        year = dateInfo.getmYear();
        return year;
    }

    public int getWeekday() {
        weekday = dateInfo.getmWeekday();
        return weekday;
    }
}

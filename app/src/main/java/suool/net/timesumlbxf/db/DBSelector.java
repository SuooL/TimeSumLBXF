package suool.net.timesumlbxf.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import suool.net.timesumlbxf.model.DateInfo;
import suool.net.timesumlbxf.model.TimeRange;

/**
 * Created by SuooL on 2014/10/9.
 */
public class DBSelector {

    DateInfo dateInfo = new DateInfo();

    Date now = new Date();

    public static final String TAG = "MyTest";
    private final Context mCtx;    //上下文实例

    public DBSelector(Context context){
        this.mCtx = context;
    }

    public double toadySum(){
        // 获取格式化日期
        Date now = new Date();
        String date = dateInfo.getFormatDate(now);
        double sum = 0.0;
        sum = dateSum(date);
        Log.d(TAG, "获取到的");
        return sum;
    }

    public double weekSum(){
        double sum = 0.0;
        String[] date = dateInfo.convertWeekByDate(now);
        for (int i = 0; i < date.length; i++) {
            sum += dateSum(date[i]);
        }
        return sum;
    }

    public double monthSum(){
        double sum = 0.0;
        String[] date = dateInfo.dateOfMonth(now);
        for (int i = 0; i < date.length; i++) {
            sum += dateSum(date[i]);
        }
        return sum;
    }


    public double dateSum(String date) {

        DBHelper dbHelper = DBHelper.getInstance(mCtx,"Mission.db", null, 2);
        SQLiteDatabase DB = dbHelper.getWritableDatabase();

        double sum = 0.0;

        Cursor cursor = DB.rawQuery("select SUM(AMOUNT) from TBL_EXPENDITURE as TOTAL where DATE = ?",
                new String[]{date});
        Log.d("MyTest", "SUM查询成功"+String.valueOf(cursor.getCount()));
        Log.d("MyTest",date);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            sum = cursor.getDouble(cursor
                    .getColumnIndex("SUM(AMOUNT)"));
        }

        BigDecimal time = new BigDecimal(String.valueOf(sum));
        sum = time.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return sum;
    }
}

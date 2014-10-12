package suool.net.timesumlbxf.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import suool.net.timesumlbxf.model.TimeRange;

/**
 * Created by SuooL on 2014/10/9.
 */
public class DBSelector {

    public static final String TAG = "MyTest";
    private final Context mCtx;    //上下文实例

    TimeRange timeRange = new TimeRange();

    public DBSelector(Context context){
        this.mCtx = context;
    }

    public double toadySum(){
        DBHelper dbHelper = DBHelper.getInstance(mCtx,"Mission.db", null, 2);
        SQLiteDatabase DB = dbHelper.getWritableDatabase();

        double sum = 0.0;
        Cursor cursor = DB.rawQuery("select SUM(AMOUNT) from TBL_EXPENDITURE as TOTAL where DATE = ?",
                                     new String[]{timeRange.getDate_end()});

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            sum = cursor.getInt(cursor
                    .getColumnIndex("sum(AMOUNT)"));
        }

        return sum;
    }
}

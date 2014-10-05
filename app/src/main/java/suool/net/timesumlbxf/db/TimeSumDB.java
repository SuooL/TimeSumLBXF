package suool.net.timesumlbxf.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import suool.net.timesumlbxf.model.Enter;
import suool.net.timesumlbxf.model.Happy;
import suool.net.timesumlbxf.model.Study;
import suool.net.timesumlbxf.model.Work;

/**
 * 各表数据存取和统计等APP功能操作封装
 * Created by SuooL on 2014/10/5.
 */
public class TimeSumDB {
    // 数据库名
    public static final String DB_NAME = "time_sum";
    // 数据库版本
    public static final int DB_VERSION = 1;
    // 实例
    public static TimeSumDB timeSumDB;
    private SQLiteDatabase db;

    /**
     * 构造方法私有化
     */
    private TimeSumDB(Context context) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, DB_VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取TimeSumDB实例
     */
    public synchronized static TimeSumDB getInstance (Context context) {
        if (timeSumDB == null) {
            timeSumDB = new TimeSumDB(context);
        }
        return timeSumDB;
    }

    /**
     * 将Study实例存储进入数据库
     * @param study study实例
     */
    public void saveStudy (Study study) {
        if (study != null) {
            ContentValues values = new ContentValues();
            values.put("study_name", study.getStudyName());
            values.put("study_time", study.getStudyTime());
            db.insert("Study", null, values);
        }
    }

    /**
     * 将Work实例存储进入数据库
     * @param work work实例
     */
    public void saveWork (Work work) {
        if (work != null) {
            ContentValues values = new ContentValues();
            values.put("work_name", work.getWorkName());
            values.put("work_time", work.getWorkTime());
            db.insert("Work", null, values);
        }
    }

    /**
     * 将Enter实例存储进入数据库
     * @param enter enter实例
     */
    public void saveEnter (Enter enter) {
        if (enter != null) {
            ContentValues values = new ContentValues();
            values.put("work_name", enter.getEnterName());
            values.put("work_time", enter.getEnterTime());
            db.insert("Work", null, values);
        }
    }

    /**
     * 将Happy实例存储进入数据库
     * @param happy work实例
     */
    public void saveHappy (Happy happy) {
        if (happy != null) {
            ContentValues values = new ContentValues();
            values.put("work_name", happy.getHappyName());
            values.put("work_time", happy.getHappyTime());
            db.insert("Work", null, values);
        }
    }

    // TODO 修改和删除数据库表内数据

    // 统计时间
    public double sumStudy (Study study) {
        double studyTIme = 0;

        Cursor cursor = db.query("Study", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                studyTIme += cursor.getDouble(cursor.getColumnIndex("study_time"));
            } while (cursor.moveToNext());
        }
        return studyTIme;
    }

    public double sumWork (Work work) {
        double workTIme = 0;

        Cursor cursor = db.query("Work", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                workTIme += cursor.getDouble(cursor.getColumnIndex("work_time"));
            } while (cursor.moveToNext());
        }
        return workTIme;
    }

    public double sumEnter (Enter enter) {
        double enterTIme = 0;

        Cursor cursor = db.query("Enter", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                enterTIme += cursor.getDouble(cursor.getColumnIndex("happy_time"));
            } while (cursor.moveToNext());
        }
        return enterTIme;
    }

    public double sumHappy (Happy happy) {
        double happyTIme = 0;

        Cursor cursor = db.query("Happy", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                happyTIme += cursor.getDouble(cursor.getColumnIndex("happy_time"));
            } while (cursor.moveToNext());
        }
        return happyTIme;
    }

    // TODO 上面的代码好多重复呀,还有model里面的代码也是,觉得是不是可以封装抽象一下呢?


}

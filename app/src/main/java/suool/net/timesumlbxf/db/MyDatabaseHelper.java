package suool.net.timesumlbxf.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库创建类
 *
 * Created by SuooL on 2014/10/5.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    /**
     * Study表创建语句
     * 预定的内容包括基础语言学习,脚本语言学习,Linux,Android.
     */
    public static final String CREATE_STUDY = "create table Study ( "
            + "id integer primary key autoincrement, "
            + "study_name text, "
            + "study_time double) ";

    /**
     * Entertainment表创建语句
     * 预定的内容有社交网络,游戏,Guitar
     */
    public static final String CREATE_ENTER = "create table Enter ( "
            + "id integer primary key autoincrement, "
            + "enter_name text,"
            + "enter_time double)";

    /**
     * Work表床架语句
     * 预定的内容有APP
     */
    public static final String CREATE_WORK = "create table Work ( "
            + "id integer primary key autoincrement, "
            + "work_name text,"
            + "work_time double)";

    /**
     * HappyTime表创建语句
     * 预定的内容有阅读,写作,锻炼,睡觉
     */
    public static final String CREATE_HAPPY = "create table Happy ( "
            + "id integer primary key autoincrement, "
            + "happy_name text,"
            + "happy_time double)";

    /**
     * 构造函数
     * @param context 上下文
     * @param name 数据库名
     * @param factory CursorFactory
     * @param version 版本
     */

    public MyDatabaseHelper (Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDY);
        db.execSQL(CREATE_ENTER);
        db.execSQL(CREATE_WORK);
        db.execSQL(CREATE_HAPPY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int Version) {

    }
}

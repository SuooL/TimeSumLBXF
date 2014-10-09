package suool.net.timesumlbxf.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SuooL on 2014/10/6.
 */
public class DBHelper {
    private DatabaseHelper mDbHelper;    //SQLiteOpenHelper实例对象
    private SQLiteDatabase mDb;    //数据库实例对象
    private static DBHelper openHelper = null;//数据库调用实例

    private static int version = 1;//数据库版本号

    private static String myDBName = "mydb";

    private static String TableNames[];     //表名
    private static String FieldNames[][];   //字段名
    private static String FieldTypes[][];   //字段类型

    private static String NO_CREATE_TABLES = "no tables";
    private static String message = "";

    private final Context mCtx;    //上下文实例

    // 构造方法
    private DBHelper(Context ctx) {
        this.mCtx = ctx;
    }

    // 获取DBHelper实例
    public static DBHelper getInstance(Context context){
        if(openHelper == null){
            openHelper = new DBHelper(context);
            TableNames = TimeSumDBInfo.getTableNames();
            FieldNames = TimeSumDBInfo.getFieldNames();
            FieldTypes = TimeSumDBInfo.getFieldTypes();
        }
        return openHelper;
    }
    //　数据库辅助内部类
    private static class DatabaseHelper extends SQLiteOpenHelper {    //数据库辅助类

        DatabaseHelper(Context context) {
            super(context, myDBName, null, version);
        }

        // 创建数据库动作
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            if (TableNames == null)
            {
                message = NO_CREATE_TABLES;
                return;
            }
            for (int i = 0; i < TableNames.length; i++)
            {
                String sql = "CREATE TABLE " + TableNames[i] + " (";
                for (int j = 0; j < FieldNames[i].length; j++)
                {
                    sql += FieldNames[i][j] + " " + FieldTypes[i][j] + ",";
                }
                sql = sql.substring(0, sql.length() - 1); // 最后一个字段
                sql += ")";
                db.execSQL(sql);  // 执行语句
            }
        }

        // 更新数据库
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            for (int i = 0; i < TableNames[i].length(); i++)
            {
                String sql = "DROP TABLE IF EXISTS " + TableNames[i];  // 存在则删除
                db.execSQL(sql);
            }
            onCreate(db);   // 执行创建数据库语句
        }
    }

    /**添加数据库相关信息*/
    public void insertTables(String[] tableNames,String[][] fieldNames,String[][] fieldTypes){
        TableNames = tableNames;
        FieldNames = fieldNames;
        FieldTypes = fieldTypes;
    }

    /**打开数据库*/
    public DBHelper open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    /**关闭数据库*/
    public void close() {
        mDbHelper.close();
    }

    public void execSQL(String sql) throws java.sql.SQLException
    {
        mDb.execSQL(sql);
    }

    /**sql语句查询数据*/
    public Cursor rawQuery(String sql,String[] selectionArgs)
    {
        Cursor cursor = mDb.rawQuery(sql, selectionArgs);
        return cursor;
    }

    /**查询数据*/
    public Cursor select(String table, String[] columns,
                         String selection, String[] selectionArgs, String groupBy,
                         String having, String orderBy)
    {
        Cursor cursor = mDb.query
                (
                        table, columns, selection, selectionArgs,
                        groupBy, having, orderBy
                );
        return cursor;
    }

    /**添加数据*/
    public long insert(String table, String fields[], String values[])
    {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < fields.length; i++)
        {
            cv.put(fields[i], values[i]);
        }
        return mDb.insert(table, null, cv);
    }

    /**删除数据*/
    public int delete(String table, String where, String[] whereValue)
    {
        return mDb.delete(table, where, whereValue);
    }

    /**更新数据*/
    public int update(String table, String updateFields[],
                      String updateValues[], String where, String[] whereValue)
    {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < updateFields.length; i++)
        {
            cv.put(updateFields[i], updateValues[i]);
        }
        return mDb.update(table, cv, where, whereValue);
    }

    /**错误信息： 不为null数据库未建立*/
    public String getMessage()
    {
        return message;
    }
}

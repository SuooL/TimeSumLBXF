package suool.net.timesumlbxf.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by SuooL on 2014/10/6.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String TAG = "MyTest";

    private DBHelper mDbHelper;    //SQLiteOpenHelper实例对象
    private SQLiteDatabase mDb;    //数据库实例对象
    private static DBHelper openHelper = null;//数据库调用实例

    private static String TableNames[];     //表名
    private static String FieldNames[][];   //字段名
    private static String FieldTypes[][];   //字段类型

    private static String NO_CREATE_TABLES = "no tables";
    private static String message = "";

    private final Context mCtx;    //上下文实例

    // 构造方法
    private DBHelper(Context context, String myDBName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, myDBName, factory, version);
        mCtx = context;
    }

    // 获取DBHelper实例
    public static DBHelper getInstance(Context context, String myDBName,
                                       SQLiteDatabase.CursorFactory factory, int version) {
        if (openHelper == null) {
            openHelper = new DBHelper(context, myDBName, factory, version);
            TableNames = TimeSumDBInfo.getTableNames();
            FieldNames = TimeSumDBInfo.getFieldNames();
            FieldTypes = TimeSumDBInfo.getFieldTypes();
        }

        Log.d(TAG, "" + TableNames.length);
        return openHelper;
    }

    // 创建数据库动作
    @Override
    public void onCreate(SQLiteDatabase db) {
        if (TableNames == null) {
            message = NO_CREATE_TABLES;
            Log.d(TAG, message);
            return;
        }
        for (int i = 0; i < TableNames.length; i++) {
            String sql2 = "CREATE TABLE " + TableNames[i] + " (";
            for (int j = 0; j < FieldNames[i].length; j++) {
                sql2 += FieldNames[i][j] + " " + FieldTypes[i][j] + ",";
            }
            sql2 = sql2.substring(0, sql2.length() - 1);
            sql2 += ")";
            Log.d(TAG, "自动组装的sql语句."+sql2);
            db.execSQL(sql2);  // 执行语句
        }
        Log.d(TAG,"数据库创建成功.");
    }

    // 更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = 0; i < TableNames[i].length(); i++) {
            String sql = "DROP TABLE IF EXISTS " + TableNames[i];  // 存在则删除
            db.execSQL(sql);
        }
        onCreate(db);   // 执行创建数据库语句
    }

    /**
     * 添加数据库相关信息
     */
    public void insertTables(String[] tableNames, String[][] fieldNames, String[][] fieldTypes) {
        TableNames = tableNames;
        FieldNames = fieldNames;
        FieldTypes = fieldTypes;
    }


    /**
     * 关闭数据库
     */
    public void close() {
        mDbHelper.close();
    }

    public void execSQL(String sql) throws java.sql.SQLException {
        mDb.execSQL(sql);
    }

    /**
     * sql语句查询数据
     */
    public Cursor rawQuery(String sql, String[] selectionArgs) {
        Cursor cursor = mDb.rawQuery(sql, selectionArgs);
        return cursor;
    }

    /**
     * 查询数据
     */
    public Cursor select(String table, String[] columns,
                         String selection, String[] selectionArgs, String groupBy,
                         String having, String orderBy) {
        Cursor cursor = mDb.query
                (
                        table, columns, selection, selectionArgs,
                        groupBy, having, orderBy
                );
        return cursor;
    }

    /**
     * 添加数据
     */
    public long insert(String table, String fields[], String values[]) {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < fields.length; i++) {
            cv.put(fields[i], values[i]);
        }
        return mDb.insert(table, null, cv);
    }

    /**
     * 删除数据
     */
    public int delete(String table, String where, String[] whereValue) {
        return mDb.delete(table, where, whereValue);
    }

    /**
     * 更新数据
     */
    public int update(String table, String updateFields[],
                      String updateValues[], String where, String[] whereValue) {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < updateFields.length; i++) {
            cv.put(updateFields[i], updateValues[i]);
        }
        return mDb.update(table, cv, where, whereValue);
    }

    /**
     * 错误信息： 不为null数据库未建立
     */
    public String getMessage() {
        return message;
    }
}

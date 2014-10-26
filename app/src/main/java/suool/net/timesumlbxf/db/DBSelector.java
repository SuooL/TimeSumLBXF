package suool.net.timesumlbxf.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import suool.net.timesumlbxf.Activity.MyApplication;
import suool.net.timesumlbxf.R;
import suool.net.timesumlbxf.model.DateInfo;
import suool.net.timesumlbxf.model.MissionItem;

/**
 * Created by SuooL on 2014/10/9.
 */
public class DBSelector {

    DateInfo dateInfo = new DateInfo();

    // 获取格式化日期
    Date now = new Date();
    String date = dateInfo.getFormatDate(now);
    public static final String TAG = "MyTest";
    private final Context mCtx = MyApplication.getmContext();    //上下文实例

    // 获取资源数组名称
    String[] parentCate = mCtx.getResources().getStringArray(R.array.TBL_EXPENDITURE_CATEGORY);
    String[] subCate1 = mCtx.getResources().getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_1);
    String[] subCate2 = mCtx.getResources().getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_2);
    String[] subCate3 = mCtx.getResources().getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_3);
    String[] subCate4 = mCtx.getResources().getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_4);
    String[] subCate5 = mCtx.getResources().getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_5);

   // String[] parentCate = .getResources().getStringArray(R.array.TBL_EXPENDITURE_CATEGORY);

    public double toadySum(){
        double sum = 0.0;
        sum = dateSum(date);
        Log.d(TAG, "获取到的"+sum);
        return sum;
    }

    public ArrayList toadySelect(){
        // 获取格式化日期
        Date now = new Date();
        String date = dateInfo.getFormatDate(now);
        ArrayList missions = dateSelect(date);
        Log.d(TAG, "获取到的"+missions);
        return missions;
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

    public ArrayList dateSelect(String date) {
        ArrayList missions = new ArrayList();

        DBHelper dbHelper = DBHelper.getInstance(mCtx,"Mission.db", null, 2);
        SQLiteDatabase DB = dbHelper.getWritableDatabase();

        Cursor cursor = DB.rawQuery("select EXPENDITURE_CATEGORY_ID,EXPENDITURE_SUB_CATEGORY_ID," +
                        "DATE,AMOUNT,MEMO from TBL_EXPENDITURE where DATE = ?",new String[]{date});

        Log.d("MyTest", "查询成功"+String.valueOf(cursor.getCount()));
        if (cursor.moveToFirst()) {
            do {
                MissionItem missionItem = new MissionItem();
                int parentId =cursor.getInt(cursor.getColumnIndex("EXPENDITURE_CATEGORY_ID"));
                // 父目录名字
                missionItem.setParentCategory(parentCate[parentId]);
                // 对应父目录的子目录
                int subID;
                subID = cursor.getInt(cursor.getColumnIndex("EXPENDITURE_SUB_CATEGORY_ID"));
                switch (parentId){
                    case 0:
                        missionItem.setSubCategory(subCate1[subID]);
                        break;
                    case 1:
                        missionItem.setSubCategory(subCate2[subID]);
                        break;
                    case 2:
                        missionItem.setSubCategory(subCate3[subID]);
                        break;
                    case 3:
                        missionItem.setSubCategory(subCate4[subID]);
                        break;
                    case 4:
                        missionItem.setSubCategory(subCate5[subID]);
                        break;
                }

                // 目录信息
                missionItem.setCategory(missionItem.getParentCategory()+missionItem.getSubCategory());
                // 日期信息
                missionItem.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                // 时间长度
                missionItem.setTimeLength(cursor.getDouble(cursor.getColumnIndex("AMOUNT")));
                // 备注
                missionItem.setMemo(cursor.getString(cursor.getColumnIndex("MEMO")));

                // 添加到数组
                missions.add(missionItem);

            } while (cursor.moveToNext());
        }
        return  missions;
    }


}

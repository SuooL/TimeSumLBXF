package suool.net.timesumlbxf.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import suool.net.timesumlbxf.R;
import suool.net.timesumlbxf.db.DBHelper;
import suool.net.timesumlbxf.model.CommonData;

public class InitDatabaseActivity extends Activity {
    public static final String TAG = "MyTest";
    public static DBHelper dbHelper ;
    public static SQLiteDatabase DB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_initdb);
        initialDBData();
   //     CommonData.getInstance().load(this);
        Button next = (Button) findViewById(R.id.click_to_main);
        next.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitDatabaseActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initialDBData() {
        // 建立数据库
        dbHelper = DBHelper.getInstance(this,"Mission.db", null, 2);
        DB = dbHelper.getWritableDatabase();

        Resources res = this.getResources();

        Log.d(TAG, "获取数据库成功.");

        Cursor cursor = DB.query("TBL_EXPENDITURE_CATEGORY", new String[]{
                "ID", "NAME"}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            cursor.close();
            return;
        }
        // 插入支出类别
        String[] arr = res.getStringArray(R.array.TBL_EXPENDITURE_CATEGORY);
        for (int i = 0; i < arr.length; i++) {
            Log.i("TEST", arr[i]);
            ContentValues values = new ContentValues();
            values.put("name", arr[i]);
            DB.insert("TBL_EXPENDITURE_CATEGORY", null, values);
            Log.d(TAG, "数据插入成功");
        }
        // 插入支出子类别
        arr = res.getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_1);
        for (int i = 0; i < arr.length; i++) {
            Log.i("TEST", arr[i]);
            ContentValues values = new ContentValues();
            values.put("name", arr[i]);
            values.put("PARENT_CATEGORY_ID", "1");
            DB.insert("TBL_EXPENDITURE_SUB_CATEGORY", null, values);

        }
        arr = res.getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_2);
        for (int i = 0; i < arr.length; i++) {
            Log.i("TEST", arr[i]);
            ContentValues values = new ContentValues();
            values.put("name", arr[i]);
            values.put("PARENT_CATEGORY_ID", "2");
            DB.insert("TBL_EXPENDITURE_SUB_CATEGORY", null, values);

        }
        arr = res.getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_3);
        for (int i = 0; i < arr.length; i++) {
            Log.i("TEST", arr[i]);
            ContentValues values = new ContentValues();
            values.put("name", arr[i]);
            values.put("PARENT_CATEGORY_ID", "3");
            DB.insert("TBL_EXPENDITURE_SUB_CATEGORY", null, values);

        }
        arr = res.getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_4);
        for (int i = 0; i < arr.length; i++) {
            Log.i("TEST", arr[i]);
            ContentValues values = new ContentValues();
            values.put("name", arr[i]);
            values.put("PARENT_CATEGORY_ID", "4");
            DB.insert("TBL_EXPENDITURE_SUB_CATEGORY", null, values);
        }
        arr = res.getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_5);
        for (int i = 0; i < arr.length; i++) {
            Log.i("TEST", arr[i]);
            ContentValues values = new ContentValues();
            values.put("name", arr[i]);
            values.put("PARENT_CATEGORY_ID", "5");
            DB.insert("TBL_EXPENDITURE_SUB_CATEGORY", null, values);
        }
    }
}

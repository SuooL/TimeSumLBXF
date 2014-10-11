package suool.net.timesumlbxf.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import suool.net.timesumlbxf.R;
import suool.net.timesumlbxf.db.DBHelper;
import suool.net.timesumlbxf.model.DateInfo;

public class AddActivity extends Activity implements View.OnClickListener{
    DateInfo dateInfo = new DateInfo();
    EditText et_add_time;
    EditText et_add_remark;
    Button add_button_ok;
    Button add_button_cancel;

    public static final String TAG = "MyTest";
    public static DBHelper dbHelper ;
    public static SQLiteDatabase DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        add_button_ok = (Button) findViewById(R.id.add_ok);
        add_button_cancel = (Button) findViewById(R.id.add_cancel);
        et_add_time = (EditText) findViewById(R.id.et_add_time);
        et_add_remark = (EditText) findViewById(R.id.et_add_remark);
        add_button_ok.setOnClickListener(this);
        add_button_cancel.setOnClickListener(this);

        TextView tv_add_time = (TextView) findViewById(R.id.tv_add_time);
        tv_add_time.setText("今日日期: "+dateInfo.getmMonth()+"月"+dateInfo.getmDay()+"日");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.add_ok:
                insertDB();
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            break;
            case R.id.add_cancel:
                et_add_remark.setText(null);
                et_add_time.setText(null);
                break;
            default:
                break;
        }
    }

    public void insertDB(){
        dbHelper = DBHelper.getInstance(this,"Mission.db", null, 2);
        DB = dbHelper.getWritableDatabase();

        Double time = Double.valueOf(et_add_time.getText().toString());
        String memo = et_add_remark.getText().toString();
        Log.d(TAG, "添加数据记录时获取数据库成功.");

        ContentValues values = new ContentValues();
        values.put("AMOUNT", time);
        values.put("EXPENDITURE_CATEGORY_ID", 1);
        values.put("EXPENDITURE_SUB_CATEGORY_ID", 1);
        values.put("DATE", dateInfo.getmMonth()+"月"+dateInfo.getmDay()+"日");
        values.put("MEMO", memo);
        DB.insert("TBL_EXPENDITURE", null, values);
        values.clear();
    }
}

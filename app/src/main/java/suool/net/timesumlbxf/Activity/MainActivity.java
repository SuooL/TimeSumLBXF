package suool.net.timesumlbxf.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Date;

import suool.net.timesumlbxf.R;
import suool.net.timesumlbxf.db.DBHelper;
import suool.net.timesumlbxf.db.DBSelector;
import suool.net.timesumlbxf.model.DateInfo;
import suool.net.timesumlbxf.model.Mission;
import suool.net.timesumlbxf.model.TimeRange;


public class MainActivity extends Activity implements View.OnClickListener{
    TimeRange timeRange = new TimeRange();
    DateInfo dateInfo = new DateInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        setText();
        Button button = (Button) findViewById(R.id.click_add);
        LinearLayout linearLayout_today = (LinearLayout) findViewById(R.id.info_today);
        LinearLayout linearLayout_week = (LinearLayout) findViewById(R.id.info_week);
        LinearLayout linearLayout_month = (LinearLayout) findViewById(R.id.info_month);
        LinearLayout linearLayout_mission = (LinearLayout) findViewById(R.id.info_mission);

        button.setOnClickListener(this);
        linearLayout_today.setOnClickListener(this);
        linearLayout_week.setOnClickListener(this);
        linearLayout_month.setOnClickListener(this);
        linearLayout_mission.setOnClickListener(this);
        Log.d("MyTest", "监听器添加成功!");
    }

    @Override
    public void onResume(){
        super.onResume();
        setText();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void onClick (View v){
        Intent intent;
        switch (v.getId()) {
            case R.id.click_add:
                intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                break;
            case R.id.info_today:
                intent = new Intent(MainActivity.this, TodaySumActivity.class);
                startActivity(intent);
                break;
            case R.id.info_week:
                intent = new Intent(MainActivity.this, WeekSumActivity.class);
                startActivity(intent);
                break;
            case R.id.info_month:
                intent = new Intent(MainActivity.this, MonthSumActivity.class);
                startActivity(intent);
                break;
            case R.id.info_mission:
                intent = new Intent(MainActivity.this, MissionActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void setText(){
        Log.d("MyTest", "时间获取");
        TextView tv_today = (TextView) findViewById(R.id.tv_today);
        TextView tv_week =(TextView) findViewById(R.id.tv_week);
        TextView tv_month = (TextView) findViewById(R.id.tv_month);
        TextView tv_today_time = (TextView) findViewById(R.id.tv_today_time);
        TextView tv_week_time = (TextView) findViewById(R.id.tv_week_time);
        TextView tv_month_time = (TextView) findViewById(R.id.tv_month_time);

        tv_today.setText("今日日期: "+dateInfo.getmMonth()+"月"+dateInfo.getmDay()+"日");
        tv_week.setText("本周:星期"+timeRange.getWeek_start()+"至星期"+timeRange.getWeek_end());
        tv_month.setText("本月: "+timeRange.getDate_start()+"日至"+timeRange.getDate_end()+"日");
        tv_today_time.setText("已记录:"+ String.valueOf(toadySum()) + "小时");
        tv_week_time.setText("已记录:" + String.valueOf(toadySum())+ "小时");
        tv_month_time.setText("已记录:" + String.valueOf(toadySum())+ "小时");

    }

    public double toadySum(){
        DBHelper dbHelper = DBHelper.getInstance(this,"Mission.db", null, 2);
        SQLiteDatabase DB = dbHelper.getWritableDatabase();

        String date = dateInfo.getmMonth()+"月"+dateInfo.getmDay()+"日";
        Log.d("MyTest",date);
        double sum = 0.0;
        Cursor cursor = DB.rawQuery("select SUM(AMOUNT) from TBL_EXPENDITURE as TOTAL where DATE = ?",
                new String[]{date});
//        Cursor cursor = DB.rawQuery("select SUM(AMOUNT) from TBL_EXPENDITURE as TOTAL",
//                null);
        Log.d("MyTest", "SUM查询成功"+String.valueOf(cursor.getCount()));

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            sum = cursor.getDouble(cursor
                    .getColumnIndex("SUM(AMOUNT)"));
         //   sum = 2.0;
        }

        BigDecimal time = new BigDecimal(String.valueOf(sum));
        time = time.setScale(2, BigDecimal.ROUND_HALF_UP);

        return time.doubleValue();
    }

    public double weekSum(){
        DBHelper dbHelper = DBHelper.getInstance(this,"Mission.db", null, 2);
        SQLiteDatabase DB = dbHelper.getWritableDatabase();

        String date = dateInfo.getmMonth()+"月"+dateInfo.getmDay()+"日";
        Log.d("MyTest",date);
        double sum = 0.0;
        Cursor cursor = DB.rawQuery("select SUM(AMOUNT) from TBL_EXPENDITURE as TOTAL where DATE = ?",
                new String[]{date});
//        Cursor cursor = DB.rawQuery("select SUM(AMOUNT) from TBL_EXPENDITURE as TOTAL",
//                null);
        Log.d("MyTest", "SUM查询成功"+String.valueOf(cursor.getCount()));

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            sum = cursor.getDouble(cursor
                    .getColumnIndex("SUM(AMOUNT)"));
            //   sum = 2.0;
        }
        return sum;
    }

    public double monthSum(){
        DBHelper dbHelper = DBHelper.getInstance(this,"Mission.db", null, 2);
        SQLiteDatabase DB = dbHelper.getWritableDatabase();

        String date = dateInfo.getmMonth()+"月"+dateInfo.getmDay()+"日";
        Log.d("MyTest",date);
        double sum = 0.0;
        Cursor cursor = DB.rawQuery("select SUM(AMOUNT) from TBL_EXPENDITURE as TOTAL where DATE = ?",
                new String[]{date});
//        Cursor cursor = DB.rawQuery("select SUM(AMOUNT) from TBL_EXPENDITURE as TOTAL",
//                null);
        Log.d("MyTest", "SUM查询成功"+String.valueOf(cursor.getCount()));

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            sum = cursor.getDouble(cursor
                    .getColumnIndex("SUM(AMOUNT)"));
            //   sum = 2.0;
        }
        return sum;
    }


}

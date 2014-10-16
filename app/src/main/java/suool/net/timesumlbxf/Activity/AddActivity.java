package suool.net.timesumlbxf.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import suool.net.timesumlbxf.R;
import suool.net.timesumlbxf.db.DBHelper;
import suool.net.timesumlbxf.model.DateInfo;

public class AddActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    DateInfo dateInfo = new DateInfo();
    EditText et_add_remark;
    Button add_button_ok;
    Button add_button_cancel;

    int hour;
    int min;
    int parent_id = 1;
    int sub_id = 1;

    String dateSelect ;
    String timeSelect;
    String db_date;


    // 下拉框实例
    private Spinner spinnerHour;
    private Spinner spinnerMinute;
    private Spinner spinnerCategory;
    private Spinner spinnerSubCategory;
    private TableRow tableRowTimePicker;

    public static final String TAG = "MyTest";
    public static DBHelper dbHelper ;
    public static SQLiteDatabase DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add);


        spinnerHour = (Spinner) findViewById(R.id.hour);
        spinnerMinute = (Spinner) findViewById(R.id.minute);
        spinnerCategory = (Spinner) findViewById(R.id.parent);
        spinnerSubCategory = (Spinner) findViewById(R.id.sub);
        tableRowTimePicker = (TableRow) findViewById(R.id.tb_time_picker);

        add_button_ok = (Button) findViewById(R.id.add_ok);
        add_button_cancel = (Button) findViewById(R.id.add_cancel);
        et_add_remark = (EditText) findViewById(R.id.et_add_remark);

        tableRowTimePicker.setOnClickListener(this);
        add_button_ok.setOnClickListener(this);
        add_button_cancel.setOnClickListener(this);


        TextView tv_add_time = (TextView) findViewById(R.id.tv_add_time);

        dateSelect = dateInfo.getmYear()+"年" +
                ""+dateInfo.getmMonth()+"月"+dateInfo.getmDay()+"日";
        timeSelect = dateInfo.getiHourOfDay()+"时"+dateInfo.getiMinuteOfHour()+"分";

        tv_add_time.setText(dateSelect+timeSelect);

        // 缺省的日期,当前日期
        Date now = new Date();
        db_date = dateInfo.getFormatDate(now);

        //  小时列表 将可选内容与ArrayAdapter的连接
        ArrayAdapter<CharSequence> adapter_hour =
                ArrayAdapter.createFromResource(this, R.array.Hour, android.R.layout.simple_spinner_item);
        //设置下拉列表的风格
        adapter_hour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 分钟列表 将可选内容与ArrayAdapter的连接(从资源数组文件中获取数据)
        ArrayAdapter<CharSequence> adapter_min =
                ArrayAdapter.createFromResource(this, R.array.Minute, android.R.layout.simple_spinner_item);
        //设置下拉列表的风格
        adapter_min.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 父目录列表 将可选内容与ArrayAdapter的连接(从资源数组文件中获取数据)
        ArrayAdapter<CharSequence> adapter_category =
                ArrayAdapter.createFromResource(this, R.array.TBL_EXPENDITURE_CATEGORY, android.R.layout.simple_spinner_item);
        //设置下拉列表的风格
        adapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 子目录列表 将可选内容与ArrayAdapter的连接(从资源数组文件中获取数据)
        ArrayAdapter<CharSequence> adapter_subCategory =
                ArrayAdapter.createFromResource(this, R.array.TBL_EXPENDITURE_SUB_CATEGORY_1, android.R.layout.simple_spinner_item);
        //设置下拉列表的风格
        adapter_subCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 设置适配器
        spinnerHour.setAdapter(adapter_hour);
        spinnerMinute.setAdapter(adapter_min);
        spinnerCategory.setAdapter(adapter_category);
        spinnerSubCategory.setAdapter(adapter_subCategory);
        // 添加监听
        spinnerHour.setOnItemSelectedListener(this);
        spinnerMinute.setOnItemSelectedListener(this);
        spinnerCategory.setOnItemSelectedListener(this);
        spinnerSubCategory.setOnItemSelectedListener(this);

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

    // 按钮的响应
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.add_ok:
                insertDB(db_date,timeSelect);
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            break;
            case R.id.add_cancel:
                et_add_remark.setText(null);
                Intent intent_can = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent_can);
                finish();
  //              et_add_time.setText(null);
                break;
            case R.id.tb_time_picker:
                Intent intent1 = new Intent(AddActivity.this, DpTpActivity.class);
                startActivityForResult(intent1, 1);
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 100) {
                     timeSelect = data.getStringExtra("time");
                     db_date = data.getStringExtra("year")+"-"+data.getStringExtra("month") +"-"+
                               data.getStringExtra("day");
                     dateSelect = data.getStringExtra("year")+"年"+data.getStringExtra("month") +"月" +
                             data.getStringExtra("day")+"日";
                    Log.d(TAG,"从时间选择器选择的时间是"+db_date);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRestart(){
        super.onRestart();
        TextView tv_add_time = (TextView) findViewById(R.id.tv_add_time);
        tv_add_time.setText(dateSelect+timeSelect);
    }

    // 返回键的响应事件重写
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    // 确定选择的下拉控件及对应的动作
    @Override
    public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {

            switch (parent.getId()){
                case R.id.hour:
                    hour = position;
                    Log.d("MyTest",String.valueOf(hour));
                    break;
                case R.id.minute:
                    min = (position)*10;
                    Log.d("MyTest", String.valueOf(min));
                    break;
                case R.id.parent:
                    Spinner spinnerParent = (Spinner)parent;
                    // 获取选取的父目录名称
                    String category = (String)spinnerParent.getItemAtPosition(position);
                    Log.d("MyTest", "选取的目录是:"+category);
                    parent_id = position+1;
                    subLoad(category);
                    break;
                case R.id.sub:
                    sub_id = position+1;
                    break;
                default:
                    break;
            }
      }

    // 第二级下拉菜单联动选项
    public void subLoad(String category){
        if (category.equals("学习")){
            //  学习列表 将可选内容与ArrayAdapter的连接
            ArrayAdapter<CharSequence> adapter_Sub =
                    ArrayAdapter.createFromResource(this, R.array.TBL_EXPENDITURE_SUB_CATEGORY_1, android.R.layout.simple_spinner_item);
            //设置下拉列表的风格
            adapter_Sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCategory.setAdapter(adapter_Sub);
        }
        else if (category.equals("工作")) {
            //  列表 将可选内容与ArrayAdapter的连接
            ArrayAdapter<CharSequence> adapter_Sub =
                    ArrayAdapter.createFromResource(this, R.array.TBL_EXPENDITURE_SUB_CATEGORY_2, android.R.layout.simple_spinner_item);
            //设置下拉列表的风格
            adapter_Sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCategory.setAdapter(adapter_Sub);
        }
        else if (category.equals("娱乐")) {
            //  列表 将可选内容与ArrayAdapter的连接
            ArrayAdapter<CharSequence> adapter_Sub =
                    ArrayAdapter.createFromResource(this, R.array.TBL_EXPENDITURE_SUB_CATEGORY_3, android.R.layout.simple_spinner_item);
            //设置下拉列表的风格
            adapter_Sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCategory.setAdapter(adapter_Sub);
        }
        else if (category.equals("欢乐时光")){
            ArrayAdapter<CharSequence> adapter_Sub =
                    ArrayAdapter.createFromResource(this, R.array.TBL_EXPENDITURE_SUB_CATEGORY_4, android.R.layout.simple_spinner_item);
            //设置下拉列表的风格
            adapter_Sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCategory.setAdapter(adapter_Sub);
        }
        else if (category.equals("日常MISSION")){
            ArrayAdapter<CharSequence> adapter_Sub =
                    ArrayAdapter.createFromResource(this, R.array.TBL_EXPENDITURE_SUB_CATEGORY_5, android.R.layout.simple_spinner_item);
            //设置下拉列表的风格
            adapter_Sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCategory.setAdapter(adapter_Sub);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void insertDB(String db_date, String currentTime){
        // 获取数据库
        dbHelper = DBHelper.getInstance(this,"Mission.db", null, 2);
        DB = dbHelper.getWritableDatabase();

//        // 获取格式化日期和时间
//        Date now = new Date();
//        String date = dateInfo.getFormatDate(now);
//        String currentTime = String.valueOf(dateInfo.getiHourOfDay()) + "时"+
//                      String.valueOf(dateInfo.getiMinuteOfHour())+ "分";
        // 获取记录备注信息
        String memo = et_add_remark.getText().toString();

        Log.d(TAG, "添加数据记录时获取数据库成功.");
        // 获取时间长度
        double time = hour+min/60.0;
        // 组装数据
        ContentValues values = new ContentValues();
        values.put("AMOUNT", time);
        values.put("EXPENDITURE_CATEGORY_ID", parent_id);
        values.put("EXPENDITURE_SUB_CATEGORY_ID", sub_id);
        values.put("DATE", db_date);
        values.put("TIME", currentTime);
        values.put("MEMO", memo);
        // 插入数据库
        DB.insert("TBL_EXPENDITURE", null, values);
        Log.d(TAG, "选取的时间是"+String.valueOf(time));
        Log.d(TAG, "选取的日期是"+db_date+" " + currentTime);
        Log.d(TAG, db_date);
        // 清空数据
        values.clear();
    }
}

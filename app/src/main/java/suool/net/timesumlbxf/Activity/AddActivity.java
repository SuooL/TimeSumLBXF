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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

    // 下拉框实例
    private Spinner spinnerHour;
    private Spinner spinnerMinute;
    private Spinner spinnerCategory;
    private Spinner spinnerSubCategpry;

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
        spinnerSubCategpry = (Spinner) findViewById(R.id.sub);

        add_button_ok = (Button) findViewById(R.id.add_ok);
        add_button_cancel = (Button) findViewById(R.id.add_cancel);
        et_add_remark = (EditText) findViewById(R.id.et_add_remark);

        add_button_ok.setOnClickListener(this);
        add_button_cancel.setOnClickListener(this);


        TextView tv_add_time = (TextView) findViewById(R.id.tv_add_time);
        tv_add_time.setText("今日日期: "+dateInfo.getmMonth()+"月"+dateInfo.getmDay()+"日");

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
        spinnerSubCategpry.setAdapter(adapter_subCategory);
        // 添加监听
        spinnerHour.setOnItemSelectedListener(this);
        spinnerMinute.setOnItemSelectedListener(this);
        spinnerCategory.setOnItemSelectedListener(this);
        spinnerSubCategpry.setOnItemSelectedListener(this);

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
  //              et_add_time.setText(null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {

            switch (parent.getId()){
                case R.id.hour:
                    hour = position+1;
                    Log.d("MyTest",String.valueOf(hour));
                    break;
                case R.id.minute:
                    min = (position+1)*10;
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

    public void subLoad(String category){
        if (category.equals("学习")){
            //  学习列表 将可选内容与ArrayAdapter的连接
            ArrayAdapter<CharSequence> adapter_Sub =
                    ArrayAdapter.createFromResource(this, R.array.TBL_EXPENDITURE_SUB_CATEGORY_1, android.R.layout.simple_spinner_item);
            //设置下拉列表的风格
            adapter_Sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCategpry.setAdapter(adapter_Sub);
        }
        else if (category.equals("工作")) {
            //  列表 将可选内容与ArrayAdapter的连接
            ArrayAdapter<CharSequence> adapter_Sub =
                    ArrayAdapter.createFromResource(this, R.array.TBL_EXPENDITURE_SUB_CATEGORY_2, android.R.layout.simple_spinner_item);
            //设置下拉列表的风格
            adapter_Sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCategpry.setAdapter(adapter_Sub);
        }
        else if (category.equals("娱乐")) {
            //  列表 将可选内容与ArrayAdapter的连接
            ArrayAdapter<CharSequence> adapter_Sub =
                    ArrayAdapter.createFromResource(this, R.array.TBL_EXPENDITURE_SUB_CATEGORY_3, android.R.layout.simple_spinner_item);
            //设置下拉列表的风格
            adapter_Sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCategpry.setAdapter(adapter_Sub);
        }
        else if (category.equals("欢乐时光")){
            ArrayAdapter<CharSequence> adapter_Sub =
                    ArrayAdapter.createFromResource(this, R.array.TBL_EXPENDITURE_SUB_CATEGORY_4, android.R.layout.simple_spinner_item);
            //设置下拉列表的风格
            adapter_Sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCategpry.setAdapter(adapter_Sub);
        }
        else if (category.equals("日常MISSION")){
            ArrayAdapter<CharSequence> adapter_Sub =
                    ArrayAdapter.createFromResource(this, R.array.TBL_EXPENDITURE_SUB_CATEGORY_5, android.R.layout.simple_spinner_item);
            //设置下拉列表的风格
            adapter_Sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSubCategpry.setAdapter(adapter_Sub);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void insertDB(){
        dbHelper = DBHelper.getInstance(this,"Mission.db", null, 2);
        DB = dbHelper.getWritableDatabase();

 //       Double time = Double.valueOf(et_add_time.getText().toString());
        String memo = et_add_remark.getText().toString();
        Log.d(TAG, "添加数据记录时获取数据库成功.");
        double time = hour+min/60.0;
        ContentValues values = new ContentValues();
        values.put("AMOUNT", time);
        values.put("EXPENDITURE_CATEGORY_ID", parent_id);
        values.put("EXPENDITURE_SUB_CATEGORY_ID", sub_id);
        values.put("DATE", dateInfo.getmMonth()+"月"+dateInfo.getmDay()+"日");
        values.put("MEMO", memo);
        DB.insert("TBL_EXPENDITURE", null, values);
        values.clear();
    }
}

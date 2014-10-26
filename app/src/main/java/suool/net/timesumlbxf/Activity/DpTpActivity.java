package suool.net.timesumlbxf.Activity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;

import suool.net.timesumlbxf.R;

public class DpTpActivity extends Activity implements View.OnClickListener{
    private Button button_ok;
    private Button button_cancel;

    private String time;

    private int iYear;
    private int iMonthOfYear;
    private int iDayOfMonth;

    private String year;
    private String monthOfYear;
    private String dayOfMonth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dp_tp);


        DatePicker datePicker=(DatePicker)findViewById(R.id.datePicker);
        TimePicker timePicker=(TimePicker)findViewById(R.id.timePicker);
        button_ok = (Button) findViewById(R.id.time_ok);
        button_cancel = (Button) findViewById(R.id.time_cancel);

        button_ok.setOnClickListener(this);
        button_cancel.setOnClickListener(this);

        timePicker.setIs24HourView(true);// 设置为24小时制

        Calendar calendar = Calendar.getInstance();
        iYear = calendar.get(Calendar.YEAR);
        iMonthOfYear = calendar.get(Calendar.MONTH);
        iDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        year = String.valueOf(iYear);
        if (iMonthOfYear <9)
        {
            monthOfYear = "0"+String.valueOf(iMonthOfYear+1);
        }
        else
            monthOfYear = String.valueOf(iMonthOfYear+1);
        if (iDayOfMonth < 9){
            dayOfMonth = "0"+String.valueOf(iDayOfMonth);
        }
        else
            dayOfMonth = String.valueOf(iDayOfMonth);
        time = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))+"时"+String.valueOf(calendar.get(Calendar.MINUTE))+"分";



        datePicker.init(iYear, iMonthOfYear, iDayOfMonth, new OnDateChangedListener(){

            public void onDateChanged(DatePicker view, int iYear,
                                      int iMonthOfYear, int iDayOfMonth) {
                // date = year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日";
                year = String.valueOf(iYear);
                if (iMonthOfYear <9)
                {
                    monthOfYear = "0"+String.valueOf(iMonthOfYear+1);
                }
                else
                    monthOfYear = String.valueOf(iMonthOfYear+1);
                if (iDayOfMonth < 9){
                    dayOfMonth = "0"+String.valueOf(iDayOfMonth);
                }
                else
                    dayOfMonth = String.valueOf(iDayOfMonth);

            }

        });

        timePicker.setOnTimeChangedListener(new OnTimeChangedListener(){

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time =  hourOfDay+"时"+minute+"分";
            }

        });
    }

    /**
     * 直接将年月日按照整数分别返回
     * 将具体的时间按照字符串返回
     * @param v
     */
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.time_ok:
                Intent intent_time = new Intent();
                //intent_time.putExtra("date", date);
                intent_time.putExtra("year", year);
                intent_time.putExtra("month", monthOfYear);
                intent_time.putExtra("day", dayOfMonth);
                intent_time.putExtra("time", time);
                setResult(100, intent_time);
                finish();
                break;
            case R.id.time_cancel:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 重写返回键相应事件
     */
    @Override
    public void onBackPressed(){
        Intent intent_time = new Intent();
        //intent_time.putExtra("date", date);
        intent_time.putExtra("year", year);
        intent_time.putExtra("month", monthOfYear);
        intent_time.putExtra("day", dayOfMonth);
        intent_time.putExtra("time", time);
        setResult(100, intent_time);
        finish();
    }
}


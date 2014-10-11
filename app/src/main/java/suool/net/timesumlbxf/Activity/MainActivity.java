package suool.net.timesumlbxf.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import suool.net.timesumlbxf.R;
import suool.net.timesumlbxf.model.Mission;


public class MainActivity extends Activity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
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
}

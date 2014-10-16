package suool.net.timesumlbxf.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import suool.net.timesumlbxf.R;
import suool.net.timesumlbxf.model.Mission;
import suool.net.timesumlbxf.model.MissionAdapter;

public class TodaySumActivity extends Activity {

    private List<Mission> missionList = new ArrayList<Mission>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_time_info);
     //   loadMissionToday();       // 导入今日任务信息数据
        MissionAdapter adapter = new MissionAdapter(TodaySumActivity.this, R.layout.mission_item, missionList);
        ListView listView = (ListView) findViewById(R.id.mission_list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.today_info, menu);
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
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }



}

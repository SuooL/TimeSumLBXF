package suool.net.timesumlbxf.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import suool.net.timesumlbxf.R;
import suool.net.timesumlbxf.db.DBHelper;
import suool.net.timesumlbxf.db.DBSelector;
import suool.net.timesumlbxf.model.DateInfo;
import suool.net.timesumlbxf.model.MissionItem;
import suool.net.timesumlbxf.ui.ListViewCompat;
import suool.net.timesumlbxf.ui.SlideView;

/**
 * Created by SuooL on 2014/10/25.
 */
public class ListViewShow extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener,
        SlideView.OnSlideListener {

    private static final String TAG = "LVShow";
    private int flag;
    public static DBSelector dbSelector;

    private suool.net.timesumlbxf.ui.ListViewCompat mListView;
    private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();
    private ArrayList missions = new ArrayList();


    private SlideView mLastSlideViewWithStatusOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_listviewcompat);
//        Intent intent = new Intent();
//        flag = intent.getIntExtra("date_flag",1);
        Log.d(TAG,"目标的界面是"+flag);

        initView(1);
    }

    private void initView(int flag) {
        mListView = (ListViewCompat) findViewById(R.id.list);


        if (flag == 1){
            // 查询当日

            missions = toadySelect();
            for (int i = 0; i < missions.size(); i++) {
                MessageItem item = new MessageItem();
                MissionItem missionItem = (MissionItem) missions.get(i);

                if (i % 3 == 0) {
                    item.iconRes = R.drawable.default_qq_avatar;
                    item.title = missionItem.getCategory();
                    item.msg = missionItem.getDate()+"时长："+missionItem.getTimeLength();
                    item.time = missionItem.getMemo();
                } else {
                    item.iconRes = R.drawable.wechat_icon;
                    item.title = missionItem.getCategory();
                    item.msg = missionItem.getDate()+"时长："+missionItem.getTimeLength();
                    item.time = missionItem.getMemo();
                }
                mMessageItems.add(item);
            }
            mListView.setAdapter(new SlideAdapter());
            mListView.setOnItemClickListener(this);
        }
    }

    private class SlideAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.list_item, null);

                slideView = new SlideView(ListViewShow.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(ListViewShow.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

            holder.icon.setImageResource(item.iconRes);
            holder.title.setText(item.title);
            holder.msg.setText(item.msg);
            holder.time.setText(item.time);
            holder.deleteHolder.setOnClickListener(ListViewShow.this);

            return slideView;
        }

    }

    public class MessageItem {
        public int iconRes;
        public String title;
        public String msg;
        public String time;
        public SlideView slideView;
    }

    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView msg;
        public TextView time;
        public ViewGroup deleteHolder;

        ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            msg = (TextView) view.findViewById(R.id.msg);
            time = (TextView) view.findViewById(R.id.time);
            deleteHolder = (ViewGroup)view.findViewById(R.id.holder);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Log.e(TAG, "onItemClick position=" + position);
    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.holder) {
            Log.e(TAG, "onClick v=" + v);
        }
    }



    private final Context mCtx = MyApplication.getmContext();    //上下文实例
    // 获取资源数组名称
    String[] parentCate = mCtx.getResources().getStringArray(R.array.TBL_EXPENDITURE_CATEGORY);
    String[] subCate1 = mCtx.getResources().getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_1);
    String[] subCate2 = mCtx.getResources().getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_2);
    String[] subCate3 = mCtx.getResources().getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_3);
    String[] subCate4 = mCtx.getResources().getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_4);
    String[] subCate5 = mCtx.getResources().getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_5);

    public ArrayList toadySelect(){
        // 获取格式化日期
        Date now = new Date();
        DateInfo dateInfo = new DateInfo();
        String date = dateInfo.getFormatDate(now);
        ArrayList missions = dateSelect(date);
        Log.d(TAG, "获取到的"+missions);
        return missions;
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
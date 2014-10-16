package suool.net.timesumlbxf.model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import suool.net.timesumlbxf.R;

/**
 * 记录适配器
 * 用于显示在列表中
 * Created by SuooL on 2014/10/8.
 */
public class MissionAdapter extends ArrayAdapter<Mission> {
    private int resourceId;

    /**
     * 构造方法
     * @param context 活动上下文
     * @param textViewResourceId 资源ID
     * @param objects 加载的对象数列
     */
    public MissionAdapter(Context context, int textViewResourceId, List<Mission> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Mission mission = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        return view;
    }
}





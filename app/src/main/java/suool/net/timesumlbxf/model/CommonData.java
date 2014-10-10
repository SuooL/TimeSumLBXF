package suool.net.timesumlbxf.model;

import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;

import suool.net.timesumlbxf.Activity.InitDatabaseActivity;
import suool.net.timesumlbxf.R;
import suool.net.timesumlbxf.db.DBHelper;
import suool.net.timesumlbxf.db.TimeSumDBInfo;

/**
 * Created by SuooL on 2014/10/10.
 */
public class CommonData {
    private static CommonData instance = null;
    private DBHelper dbHelper = null;
    private Context context = null;
    private int num1 = 0;
    private int num2 = 0;

    public class CategoryData {
        int		parent; // rootcategory not use this member;
        int		id;
        int 	icon;
        int 	type;
        String 	name;

        // transaction's root category
        public CategoryData(int id, int icon, String name, int type) {
            this.id = id;
            this.icon = icon;
            this.name = name;
            this.type = type;
        }

        // transaction's sub category
        public CategoryData(int id, int parent, int icon, String name, int type) {
            this.id = id;
            this.parent = parent;
            this.icon = icon;
            this.name = name;
            this.type = type;
        }
    }



    public HashMap<String, CategoryData> category = new HashMap<String, CategoryData>();
    public HashMap<String, CategoryData> subcategory = new HashMap<String, CategoryData>();


    public double budget_amount;
    public int[] budget_bar_bg;


    public static CommonData getInstance() {
        if (instance == null) {
            instance = new CommonData();
        }

        return instance;
    }

    public void load(Context context) {
        dbHelper = InitDatabaseActivity.dbHelper;
        loadCategory();
        this.context = context;
    }

    /**加载时间类别以及icon*/
    public void loadCategory()
    {
        category.clear();
        subcategory.clear();
        for(int i=0;i<2;i++){
            loatCategoryData(i);
        }
        num1 = 0;
        num2 = 0;
    }

    public void loatCategoryData(int id){
        Cursor cursor = dbHelper.select(TimeSumDBInfo.getTableNames()[id], TimeSumDBInfo.getFieldNames()[id], null, null, null, null, null);
        while (cursor.moveToNext()) {
            switch (id) {
                case 0:
                    category.put("out" + cursor.getInt(0), new CategoryData(num1, R.drawable.ic_launcher, cursor.getString(1),1));
                    num1 ++;
                    break;
                case 1:
                    subcategory.put("out" + cursor.getInt(0), new CategoryData(num2, 0, R.drawable.ic_launcher, cursor.getString(1),1));
                    num2 ++;
                    break;
                default:
                    break;
            }
        }
        cursor.close();
    }

}

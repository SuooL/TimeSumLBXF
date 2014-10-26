package suool.net.timesumlbxf.Activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by SuooL on 2014/10/26.
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        mContext = getApplicationContext();
    }
    public static Context getmContext() {
        return mContext;
    }
}

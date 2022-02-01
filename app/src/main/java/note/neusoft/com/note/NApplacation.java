package note.neusoft.com.note;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;

import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xxx on 2016/12/13 20:28.
 * Email：1124751755@qq.com
 * function：
 */

public class NApplacation extends Application {
    public static String user_number;
    public static String user_power;
    public static String ImageUrl;
    public static String nickname;



    private static NApplacation application;
    private static int mainTid;
    private static Handler handler;

    private List AllAcivity;





    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        AllAcivity = new ArrayList<Map<String, String>>();
        application=this;
        mainTid = android.os.Process.myTid();
        handler=new Handler();
        SpeechUtility.createUtility(NApplacation.this, "appid=" + getString(R.string.app_id));
    }



    public static Context getApplication() {
        return application;
    }
    public static int getMainTid() {
        return mainTid;
    }
    public static Handler getHandler() {
        return handler;
    }

    public   void addActivity(Activity activity) {
        try {
            AllAcivity.add(activity);
        } catch (Exception e) {
            
        }
    }

    public  void destoryAllActivity() {
        try {
            for (int i = 0; i < AllAcivity.size(); i++) {
                ((Activity) AllAcivity.get(i)).finish();
            }
        } catch (Exception e) {
            
        }
    }
}

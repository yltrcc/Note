package note.neusoft.com.note.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * author：xxx
 * Creation date： by 2016/12/27 on 11:25.
 * description：
 */

public class PrefUtils {

    public static final String PREF_NAME = "config";

    public static final String BGIMAGE="bgimage";

    public static final String ISLOCK="islock";

    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context ctx, String key, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void setString(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }


    /**
     * Set whether the application background is transparent, ok=true means transparent, ok=false means opaque
     * @param context
     * @param ok
     */
    public static void setAlphaBg(Context context,boolean ok){
        setBoolean(context,"AlphaBg",ok);
    }

    /**
     * Get whether the application background is transparent, true means transparent, false means opaque (default opaque)
     * @param context
     */
    public static boolean getAlphaBg(Context context){
        return getBoolean(context,"AlphaBg",false);
    }


    /**
     * Save the skin
     * @param context
     * @param imageid
     */
    public static void SaveBg(Context context,int imageid){
        SharedPreferences sp=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        sp.edit().putInt(BGIMAGE,imageid).commit();
    }

    /**
     * Get skin
     * @param context
     * @return
     */
    public static int GetBg(Context context){
        SharedPreferences sp=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sp.getInt(BGIMAGE,-1);
    }


    /**
     * Set the lock, true means the application has set the lock, flase means the application is not locked
     * @param context
     * @param islock
     */
    public static void SetLock(Context context,boolean islock){
        setBoolean(context,ISLOCK,islock);
    }

    /**
     * Get whether the application is locked, true means the application is locked, flase means the application is not locked
     * @param context
     * @return
     */
    public static boolean GetLock(Context context){
        return getBoolean(context,ISLOCK,false);
    }









}

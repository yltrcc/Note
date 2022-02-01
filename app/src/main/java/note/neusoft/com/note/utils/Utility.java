package note.neusoft.com.note.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Toast tools
 */
public class Utility {

    private static final String PREFERENCES_NAME = "config_pref";
    private static final String PATTERN_PASSWORD = "pattern_password";
    public static final String IS_LOGIN = "is_login";

    private static Toast sToast;

    @SuppressLint("ShowToast")
    public static void showToast(@NonNull Context context, @Nullable CharSequence text) {
        if (text == null || TextUtils.isEmpty(text)) {
            return;
        }
        int duration = Toast.LENGTH_SHORT;
        if (text.length() > 15) {
            duration = Toast.LENGTH_LONG;
        }
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), text, duration);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }

    /**
     * Initialize the Action Bar
     */
    public static void initActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);     
            actionBar.setHomeButtonEnabled(true);           
            actionBar.setDisplayShowHomeEnabled(true);      
            actionBar.setDisplayHomeAsUpEnabled(true);      
        }
    }

    public static String getPatternPassword(@NonNull Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getString(PATTERN_PASSWORD, null);
    }

    public static String getPatternByKey(@NonNull Context context, String key) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getString(key, null);
    }

    public static void savePatternPassword(@NonNull Context context, @Nullable String password) {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(PATTERN_PASSWORD, password)
                .apply();
    }

    public static void savePatternByKey(@NonNull Context context, String key, @Nullable String value) {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(key, value)
                .apply();
    }

}

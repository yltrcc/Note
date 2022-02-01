package note.neusoft.com.note.utils;

import android.content.Context;

import com.leo.gesturelibray.util.ConfigUtil;

/**
 * author：xxx
 * Creation date： by 2016/12/30 on 18:53.
 * description：
 */

public class PasswordUtil {
    /**
     * Get the set password
     */
    public static String getPin(Context context) {
        String password = ConfigUtil.getInstance(context).getString(Contants.PASS_KEY);
        return password;
    }
}

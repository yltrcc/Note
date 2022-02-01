package note.neusoft.com.note.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.leo.gesturelibray.enums.LockMode;
import com.leo.gesturelibray.util.StringUtils;

import note.neusoft.com.note.MainActivity;
import note.neusoft.com.note.R;
import note.neusoft.com.note.utils.Contants;
import note.neusoft.com.note.utils.PasswordUtil;
import note.neusoft.com.note.utils.PrefUtils;
import note.neusoft.com.note.utils.Utility;

public class SplashActivity extends Activity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context=this;
        Init();
    }

    private void Init(){
        handler.sendEmptyMessageDelayed(0,2000);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            boolean islock= PrefUtils.GetLock(context);
            if(islock){
                actionSecondActivity(LockMode.VERIFY_PASSWORD);
            }else{

                String isLogin = Utility.getPatternByKey(SplashActivity.this, Utility.IS_LOGIN);
                if (StringUtils.isNotEmpty(isLogin) && "1".equals(isLogin)) {
                    Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                    intent.putExtra("isFirst",false);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
                }else {
                    Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
                }

            }
        }
    };


    /**
     * 跳转到密码处理界面
     */
    private void actionSecondActivity(LockMode mode) {
        if (mode != LockMode.SETTING_PASSWORD) {
            if (StringUtils.isEmpty(PasswordUtil.getPin(this))) {
                Toast.makeText(getBaseContext(), "请先设置密码", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Intent intent = new Intent(this, LockActivity.class);
        intent.putExtra(Contants.INTENT_SECONDACTIVITY_KEY, mode);
        intent.putExtra("isFirst",true);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);

    }



}

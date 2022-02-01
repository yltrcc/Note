package note.neusoft.com.note.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.leo.gesturelibray.enums.LockMode;
import com.leo.gesturelibray.view.CustomLockView;

import butterknife.Bind;
import note.neusoft.com.note.MainActivity;
import note.neusoft.com.note.R;
import note.neusoft.com.note.utils.Contants;
import note.neusoft.com.note.utils.PasswordUtil;

/**
 * author：xxx
 * Creation date： by 2016/12/30 on 19:05.
 * description：
 * 
 * 1：actionSecondActivity(LockMode.CLEAR_PASSWORD);
 *
 * 2：actionSecondActivity(LockMode.EDIT_PASSWORD);
 *
 * 3:actionSecondActivity(LockMode.SETTING_PASSWORD);
 *
 * 4:actionSecondActivity(LockMode.VERIFY_PASSWORD);
 *
 */

public class LockActivity extends BaseLockActivity implements RippleView.OnRippleCompleteListener{

    @Bind(R.id.title)
    FrameLayout title;
    @Bind(R.id.rv_back)
    RippleView rvBack;
    @Bind(R.id.tv_text)
    TextView tvText;
    @Bind(R.id.lv_lock)
    CustomLockView lvLock;
    @Bind(R.id.tv_hint)
    TextView tvHint;
    private LockMode lockMode;
    private boolean isFirst;


    @Override
    public void beforeInitView() {

        setContentView(R.layout.activity_lock);
    }

    /**
     * Initialize View
     */
    @Override
    public void initView() {
        
        lvLock.setShow(true);
        
        lvLock.setErrorNumber(3);
        
        lvLock.setPasswordMinLength(4);
        
        lvLock.setSavePin(true);
        
        lvLock.setSaveLockKey(Contants.PASS_KEY);
    }

    /**
     * Set up listening callback
     */
    @Override
    public void initListener() {
        lvLock.setOnCompleteListener(onCompleteListener);
        rvBack.setOnRippleCompleteListener(this);
    }

    /**
     * Initialization data
     */
    @Override
    public void initData() {
        
        lockMode = (LockMode) getIntent().getSerializableExtra(Contants.INTENT_SECONDACTIVITY_KEY);
        isFirst = getIntent().getBooleanExtra("isFirst",false);
        if(isFirst){
            rvBack.setVisibility(View.GONE);
        }else{
            rvBack.setVisibility(View.VISIBLE);
        }
        setLockMode(lockMode);
    }


    /**
     * password input mode
     */
    private void setLockMode(LockMode mode, String password, String msg) {
        lvLock.setMode(mode);
        lvLock.setErrorNumber(3);
        lvLock.setClearPasssword(false);
        if (mode != LockMode.SETTING_PASSWORD) {
            tvHint.setText("Please enter the password that has been set");
            lvLock.setOldPassword(password);
        } else {
            tvHint.setText("please-enter-the-password-to-be-set");
        }
        tvText.setText(msg);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(lockMode==LockMode.VERIFY_PASSWORD){
                if(isFirst){
                    Intent intent=new Intent(LockActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
                }else{
                    Intent intent=new Intent(LockActivity.this,UpdateLockActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);

                }
            }else if(lockMode==LockMode.SETTING_PASSWORD){
                finish();
                overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
            }else if(lockMode==LockMode.EDIT_PASSWORD){
                finish();
                overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
            }
        }
    };


    /**
     * password input monitoring
     */
    CustomLockView.OnCompleteListener onCompleteListener = new CustomLockView.OnCompleteListener() {
        @Override
        public void onComplete(String password, int[] indexs) {
            tvHint.setText(getPassWordHint());
            handler.sendEmptyMessageDelayed(0,1000);

            

        }

        @Override
        public void onError(String errorTimes) {
            tvHint.setText("Password is wrong, you can still enter" + errorTimes + "number");
        }

        @Override
        public void onPasswordIsShort(int passwordMinLength) {
            tvHint.setText("password cannot be less than" + passwordMinLength + "points");
        }

        @Override
        public void onAginInputPassword(LockMode mode, String password, int[] indexs) {
            tvHint.setText("please enter the password again");
        }


        @Override
        public void onInputNewPassword() {
            tvHint.setText("Please enter a new password");
        }

        @Override
        public void onEnteredPasswordsDiffer() {
            tvHint.setText("The two passwords entered are inconsistent");
        }

        @Override
        public void onErrorNumberMany() {
            tvHint.setText("The number of incorrect passwords exceeds the limit and cannot be entered again");
        }

    };


    /**
     * Password related operation completed callback prompt
     */
    private String getPassWordHint() {
        String str = null;
        switch (lvLock.getMode()) {
            case SETTING_PASSWORD:
                str = "Password set successfully";
                break;
            case EDIT_PASSWORD:
                str = "Password reset complete";
                break;
            case VERIFY_PASSWORD:
                str = "Password is correct";
                break;
            case CLEAR_PASSWORD:
                str = "Password has been cleared";
                break;
        }
        return str;
    }

    /**
     * Set unlock mode
     */
    private void setLockMode(LockMode mode) {
        String str = "";
        switch (mode) {
            case CLEAR_PASSWORD:
                str = "Clear password";
                setLockMode(LockMode.CLEAR_PASSWORD, PasswordUtil.getPin(this), str);
                break;
            case EDIT_PASSWORD:
                str = "change Password";
                setLockMode(LockMode.EDIT_PASSWORD, PasswordUtil.getPin(this), str);
                break;
            case SETTING_PASSWORD:
                str = "set password";
                setLockMode(LockMode.SETTING_PASSWORD, null, str);
                break;
            case VERIFY_PASSWORD:
                str = "verify password";
                setLockMode(LockMode.VERIFY_PASSWORD, PasswordUtil.getPin(this), str);
                break;
        }
        tvText.setText(str);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onComplete(RippleView rippleView) {
        finish();
        overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.out_up_in,R.anim.out_down_out);
    }

}

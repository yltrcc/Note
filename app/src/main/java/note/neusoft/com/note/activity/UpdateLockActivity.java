package note.neusoft.com.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.leo.gesturelibray.enums.LockMode;
import com.leo.gesturelibray.util.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import note.neusoft.com.note.R;
import note.neusoft.com.note.utils.Contants;
import note.neusoft.com.note.utils.PasswordUtil;

public class UpdateLockActivity extends AppCompatActivity {

    @Bind(R.id.img_back)
     ImageButton img_back;
    @Bind(R.id.rv_edit)
     RippleView rv_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_lock);
        ButterKnife.bind(this);
        Init();
    }

    private void Init(){
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
            }
        });
        rv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSecondActivity(LockMode.EDIT_PASSWORD);
                finish();
                overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.out_up_in,R.anim.out_down_out);
    }

    /**
     * 跳转到密码处理界面
     */
    private void actionSecondActivity(LockMode mode) {
        if (mode != LockMode.SETTING_PASSWORD) {
            if (StringUtils.isEmpty(PasswordUtil.getPin(this))) {
                Toast.makeText(getBaseContext(), "Please set a password first", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Intent intent = new Intent(this, LockActivity.class);
        intent.putExtra(Contants.INTENT_SECONDACTIVITY_KEY, mode);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
    }
}

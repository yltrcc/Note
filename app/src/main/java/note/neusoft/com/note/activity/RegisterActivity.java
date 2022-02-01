package note.neusoft.com.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import note.neusoft.com.note.MainActivity;
import note.neusoft.com.note.R;
import note.neusoft.com.note.utils.Utility;

/**
 * Created by Administrator on 2020/12/10.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText sidnInUsernameEt;
    private EditText sidnInStudentIdEt;
    private Button signInBtn;
    private String usernameStr;
    private String studentidStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        initEvent();
    }
    private void initView(){
        sidnInUsernameEt = findViewById(R.id.sign_in_username_et);
        sidnInStudentIdEt = findViewById(R.id.sign_in_student_id_et);
        signInBtn = findViewById(R.id.sign_in_btn);
    }
    private void initData(){
        usernameStr = sidnInUsernameEt.getText().toString()+"";
        studentidStr = sidnInStudentIdEt.getText().toString()+"";
    }
    private void initEvent(){
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
                Toast.makeText(RegisterActivity.this, "registration success", Toast.LENGTH_SHORT).show();
                Utility.savePatternByKey(RegisterActivity.this, Utility.IS_LOGIN, "1");
                Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("isFirst",false);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
            }
        });
    }

}

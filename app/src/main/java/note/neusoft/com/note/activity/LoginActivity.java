package note.neusoft.com.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import note.neusoft.com.note.MainActivity;
import note.neusoft.com.note.R;
import note.neusoft.com.note.utils.Utility;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEd;
    private EditText studentidEd;
    private Button loginBtn;
    private TextView forgetStudentIdTv;
    private TextView signInTv;
    private String studentidStr;
    private String usernameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        initEvent();
    }
    private void initView(){
        usernameEd = findViewById(R.id.username_et);
        studentidEd = findViewById(R.id.student_id_et);
        loginBtn = findViewById(R.id.login_btn);
        forgetStudentIdTv = findViewById(R.id.forget_student_id_tv);
        signInTv = findViewById(R.id.sign_in_tv);
    }
    private void initData(){
        usernameStr = usernameEd.getText().toString()+"";
        studentidStr = studentidEd.getText().toString()+"";
    }
    private void initEvent(){
       loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               initData();
               Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
               Utility.savePatternByKey(LoginActivity.this, Utility.IS_LOGIN, "1");
               Intent intent=new Intent(LoginActivity.this, MainActivity.class);
               intent.putExtra("isFirst",false);
               startActivity(intent);
               finish();
               overridePendingTransition(R.anim.in_left_in,R.anim.in_right_out);
           }
       });
       forgetStudentIdTv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });
       signInTv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
               startActivity(intent);
           }
       });
    }

}

package note.neusoft.com.note.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import note.neusoft.com.note.R;

public class CompileActivity extends Activity implements OnDismissListener, OnItemClickListener {

    @ViewInject(R.id.tv_cacel)
    private TextView tv_cacel;
    @ViewInject(R.id.tv_finish)
    private TextView tv_finish;
    @ViewInject(R.id.ll_nickname)
    private LinearLayout ll_nickname;
    @ViewInject(R.id.et_nickname)
    private EditText et_nickname;
    @ViewInject(R.id.rl_sex)
    private RelativeLayout rl_sex;
    @ViewInject(R.id.tv_sex)
    private TextView tv_sex;
    @ViewInject(R.id.rl_date)
    private RelativeLayout rl_date;
    @ViewInject(R.id.tv_date)
    private TextView tv_date;
    @ViewInject(R.id.ll_personnumber)
    private LinearLayout ll_personnumber;
    @ViewInject(R.id.et_personnumber)
    private EditText et_personnumber;
    @ViewInject(R.id.ll_email)
    private LinearLayout ll_email;
    @ViewInject(R.id.et_email)
    private EditText et_email;
    @ViewInject(R.id.et_signature)
    private EditText et_signature;

    OptionsPickerView optionsPickerView;
    TimePickerView pvTime;
    private ArrayList<String> Sex;

    private AlertView mAlertViewExt_nickname,mAlertViewExt_personnumber,mAlertViewExt_Email;
    private EditText etName1,etName2,etName3;


    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compile);
        ViewUtils.inject(this);
        context=this;
        InitData();
        Init();


    }

    @Override
    public void onBackPressed() {

        if(optionsPickerView.isShowing()){
            optionsPickerView.dismiss();
            tv_sex.setTextColor(Color.parseColor("#000000"));
        }else if(mAlertViewExt_Email.isShowing()){
            mAlertViewExt_Email.dismiss();
        }else if(mAlertViewExt_nickname.isShowing()){
            mAlertViewExt_nickname.dismiss();
        }else if(mAlertViewExt_personnumber.isShowing()){
            mAlertViewExt_personnumber.dismiss();
        }else if(pvTime.isShowing()){
            pvTime.dismiss();
        }
        else{
            finish();
            overridePendingTransition(R.anim.out_up_in,R.anim.out_down_out);
        }

    }

    private void InitData(){
        Sex=new ArrayList<>();
        Sex.add("man");
        Sex.add("female");


        optionsPickerView=new OptionsPickerView(this);
        optionsPickerView.setPicker(Sex);

        optionsPickerView.setTitle("chooseGender");
        optionsPickerView.setCyclic(false);
        optionsPickerView.setSelectOptions(1);
        optionsPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String sex=Sex.get(options1);
                tv_sex.setText(sex);
                tv_sex.setTextColor(Color.parseColor("#000000"));
            }
        });


        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                tv_date.setText(getTime(date));
            }
        });



        mAlertViewExt_nickname = new AlertView("prompt", "please enter your nickname！", "cancel", null, new String[]{"complete"}, this, AlertView.Style.Alert, this);
        mAlertViewExt_personnumber=new AlertView("prompt", "please enter your personal account！", "cancel", null, new String[]{"complete"}, this, AlertView.Style.Alert, this);
        mAlertViewExt_Email=new AlertView("prompt", "please enter your e mail address！", "cancel", null, new String[]{"complete"}, this, AlertView.Style.Alert, this);

        ViewGroup extView1= (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertext_form,null);
        etName1= (EditText) extView1.findViewById(R.id.etName);
        mAlertViewExt_nickname.addExtView(extView1);

        ViewGroup extView2= (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertext_form,null);
        etName2= (EditText) extView2.findViewById(R.id.etName);
        etName2.setHint("please enter your personal account");
        mAlertViewExt_personnumber.addExtView(extView2);

        ViewGroup extView3= (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertext_form,null);
        etName3= (EditText) extView3.findViewById(R.id.etName);
        etName3.setHint("Please enter your E-Mail");
        mAlertViewExt_Email.addExtView(extView3);
    }

    private void Init(){

        tv_cacel.setOnClickListener(new View.OnClickListener() {//取消编辑
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.out_up_in,R.anim.out_down_out);
            }
        });
        tv_finish.setOnClickListener(new View.OnClickListener() {//保存编辑内容
            @Override
            public void onClick(View v) {

                //save first then exit
                /****************保存************************/


                /****************save************************/
                finish();
                overridePendingTransition(R.anim.out_up_in,R.anim.out_down_out);
            }
        });

        rl_sex.setOnClickListener(new View.OnClickListener() {//点击进行性别选择
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
               tv_sex.setTextColor(Color.parseColor("#ff00ddff"));//Convert the color
                optionsPickerView.show();
            }
        });





//        rl_nickname.setOnClickListener(new View.OnClickListener() {//Click on the nickname field, and a box for entering nickname will pop up
//            @Override
//            public void onClick(View v) {
//                mAlertViewExt_nickname.show();
//            }
//        });
//        rl_personnumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAlertViewExt_personnumber.show();
//            }
//        });
//        rl_email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAlertViewExt_Email.show();
//            }
//        });

        rl_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){//Monitor/block/block return key
            if(optionsPickerView.isShowing()){
                optionsPickerView.dismiss();
                tv_sex.setTextColor(Color.parseColor("#000000"));
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {

        if(o==mAlertViewExt_nickname&&position!=AlertView.CANCELPOSITION){
            et_nickname.setText(etName1.getText().toString());
            etName1.setText("");
        }
        if(o==mAlertViewExt_Email&&position!=AlertView.CANCELPOSITION){
            et_email.setText(etName3.getText().toString());
            etName3.setText("");
        }
        if(o==mAlertViewExt_personnumber&&position!=AlertView.CANCELPOSITION){
            et_personnumber.setText(etName2.getText().toString());
            etName2.setText("");
        }
    }

    //Get the corresponding time
    public String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        //算星座
        SimpleDateFormat Moth=new SimpleDateFormat("MM");
        int moth= Integer.parseInt(Moth.format(date));
        SimpleDateFormat Day=new SimpleDateFormat("dd");
        int day= Integer.parseInt(Day.format(date));
        String Constellation=getConstellation(moth,day);
        return format.format(date)+"("+Constellation+")";
    }

    private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23,
            23, 23, 24, 23, 22 };
    private final static String[] constellationArr = new String[] { "capricorn",
            "aquarius", "pisces", "aries", "taurus", "gemini", "cancer", "leo", "virgo", "libra",
            "scorpio", "sagittarius", "capricorn" };

    /**
     * Java calculates constellations by birthday
     *
     * @param month
     * @param day
     * @return
     */
    public static String getConstellation(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1]
                : constellationArr[month];
    }
    /**
     * Open soft keyboard
     */
    private void openKeyboard(View view){
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,0);
    }

    /**
     * Close soft keyboard
     * @param view
     */
    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(view.getWindowToken(),0);
    }
}

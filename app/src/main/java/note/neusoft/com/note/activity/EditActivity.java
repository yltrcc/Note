package note.neusoft.com.note.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import note.neusoft.com.note.R;
import note.neusoft.com.note.db.NoteDatabase;
import note.neusoft.com.note.domain.NoteInfo;
import note.neusoft.com.note.utils.AnimationsUtils;
import note.neusoft.com.note.utils.FucUtil;
import note.neusoft.com.note.utils.JsonParser;
import note.neusoft.com.note.widget.InputMethodLayout;
import note.neusoft.com.note.widget.NoteItemCircleView;

public class EditActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener, OnDismissListener {
    private static String TAG = EditActivity.class.getSimpleName();

    @ViewInject(R.id.note_detail_img_button)
    private ImageView note_detail_img_button;
    @ViewInject(R.id.note_detail_menu)
    private RelativeLayout note_detail_menu;
    @ViewInject(R.id.note_detail_img_green)
    private NoteItemCircleView note_detail_img_green;
    @ViewInject(R.id.note_detail_img_blue)
    private NoteItemCircleView note_detail_img_blue;
    @ViewInject(R.id.note_detail_img_purple)
    private NoteItemCircleView note_detail_img_purple;
    @ViewInject(R.id.note_detail_img_yellow)
    private NoteItemCircleView note_detail_img_yellow;
    @ViewInject(R.id.note_detail_img_red)
    private NoteItemCircleView note_detail_img_red;
    @ViewInject(R.id.note_detail_titlebar)
    private RelativeLayout note_detail_titlebar;
    @ViewInject(R.id.note_detail_edit)
    private EditText note_detail_edit;
    @ViewInject(R.id.iv_color)
    private ImageView iv_color;
    @ViewInject(R.id.note_detail_tv_date)
    private TextView note_detail_tv_date;
    @ViewInject(R.id.rl_edit)
    private InputMethodLayout rl_edit;

    /***********************************************************************/
    @ViewInject(R.id.menu_item_text_font)
    private FloatingActionButton menu_item_text_font;
    @ViewInject(R.id.menu_item_clock)
    private FloatingActionButton menu_item_clock;
    
    private FloatingActionButton menu_item_share;
    @ViewInject(R.id.menu_item_voiceToText)
    private FloatingActionButton menu_item_voiceToText;
    /*****************************点击红圆圈，弹出来的东西******************************************/
    @ViewInject(R.id.ll_font_small)
    private FrameLayout ll_font_small;
    @ViewInject(R.id.ll_font_normal)
    private FrameLayout ll_font_normal;
    @ViewInject(R.id.ll_font_large)
    private FrameLayout ll_font_large;
    @ViewInject(R.id.ll_font_super)
    private FrameLayout ll_font_super;
    @ViewInject(R.id.iv_small_select)
    private ImageView iv_small_select;
    @ViewInject(R.id.iv_medium_select)
    private ImageView iv_medium_select;
    @ViewInject(R.id.iv_large_select)
    private ImageView iv_large_select;
    @ViewInject(R.id.iv_super_select)
    private ImageView iv_super_select;

    @ViewInject(R.id.font_size_selector)
    private LinearLayout font_size_selector;


    @ViewInject(R.id.floating_action_menu)
    private FloatingActionMenu floating_action_menu;


    private int Color;
    private int TitleColor;

    private float textsize=14;


    private NoteInfo noteInfo;


    private boolean isFirst=true;




    private String ModfitytextContent;




    private final int[] editcolor = new int[]{0xffe5fce8,
            0xffccf2fd,
            0xfff7f5f6,
            0xfffffdd7,
            0xffffddde,
    };


    private final int[] titlecolor = new int[]{0xffcef3d4,
            0xffa9d5e2,
            0xffddd7d9,
            0xffebe5a9,
            0xffecc4c3,
    };
    private AlertView mAlertView;
    private String date;
    private String timeId;

    private Context context;
    private StringBuffer buffer = new StringBuffer();
    
    private SpeechRecognizer mIat;
    
    private RecognizerDialog mIatDialog;
    
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private String resultType = "json";
    private String language="zh_cn";
    private SharedPreferences mSharedPreferences;
    private Toast mToast;
    private String PREFER_NAME = "com.iflytek.setting";
    private static int flg=0;
    private boolean cyclic = false;
    Handler han = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x001) {
                executeStream();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ViewUtils.inject(this);
        context=this;
        Intent intent=getIntent();
        noteInfo= (NoteInfo) intent.getSerializableExtra("noteinfo");
        showSoftInputFromWindow(note_detail_edit);
        if(noteInfo!=null){
            isFirst=false;
            note_detail_edit.setText(noteInfo.getContent());



            if(noteInfo.getTextSize()==12){
                ChoseTextSize(0);
            }else if(noteInfo.getTextSize()==14){
                ChoseTextSize(1);
            }else if(noteInfo.getTextSize()==20){
                ChoseTextSize(2);
            }else if(noteInfo.getTextSize()==25){
                ChoseTextSize(3);
            }


            note_detail_edit.setSelection(noteInfo.getContent().length());
            ModfitytextContent=noteInfo.getContent();
            note_detail_tv_date.setText(noteInfo.getDate());
            if(noteInfo.getColor()==editcolor[0]){
               iv_color.setImageResource(R.drawable.green);
            }else if(noteInfo.getColor()==editcolor[1]){
                iv_color.setImageResource(R.drawable.blue);
            }else if(noteInfo.getColor()==editcolor[2]){
                iv_color.setImageResource(R.drawable.purple);
            }else if(noteInfo.getColor()==editcolor[3]){
                iv_color.setImageResource(R.drawable.yellow);
            }else{
                iv_color.setImageResource(R.drawable.red);
            }
            note_detail_edit.setBackgroundColor(noteInfo.getColor());
            note_detail_titlebar.setBackgroundColor(noteInfo.getTitleColor());
            rl_edit.setBackgroundColor(noteInfo.getColor());
            Color=noteInfo.getColor();
            TitleColor=noteInfo.getTitleColor();

            date=noteInfo.getDate();
            timeId=noteInfo.getTimeId();


            



        }else{
            ChoseTextSize(1);
        }

        
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
        Log.i(TAG, "初始化 " + mIat.toString());
        
        
        mIatDialog = new RecognizerDialog(this, mInitListener);

        mSharedPreferences = getSharedPreferences(PREFER_NAME,
                Activity.MODE_PRIVATE);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mToast.show();
        Init();
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.i(TAG, "初始化 SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
            }
        }
    };
    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    private void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onBackPressed() {

        if(TextUtils.isEmpty(note_detail_edit.getText().toString())){
            finish();
            overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
            return;
        }

        if(isFirst){
            if(mAlertView==null){

                mAlertView = new AlertView("save", "Do I need to save it？", "cancel", new String[]{"confirm"},
                        null, this, AlertView.Style.Alert, this).setCancelable(true).setOnDismissListener(this);
            }
            if(!mAlertView.isShowing()){
                mAlertView.show();
            }
        }else{
            if(mAlertView==null){
                if(ModfitytextContent.equals(note_detail_edit.getText().toString())){
                    finish();
                    overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
                    return;
                }else{
                    mAlertView = new AlertView("modification", "Whether to save the changes？", "cancel", new String[]{"confirm"},
                            null, this, AlertView.Style.Alert, this).setCancelable(true).setOnDismissListener(this);
                }

            }
            if(!mAlertView.isShowing()){
                mAlertView.show();
            }
        }
    }

    private void Init() {


        if(isFirst){
            
            note_detail_tv_date.setText(getCurrentDate());
            Color=editcolor[0];
            TitleColor=titlecolor[0];
        }
        note_detail_menu.setVisibility(View.GONE);
        note_detail_img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (note_detail_menu.getVisibility() == View.GONE) {
                    openMenu();
                } else {
                    closeMenu();
                }
            }
        });


        
        note_detail_img_green.setOnClickListener(this);
        note_detail_img_blue.setOnClickListener(this);
        note_detail_img_purple.setOnClickListener(this);
        note_detail_img_yellow.setOnClickListener(this);
        note_detail_img_red.setOnClickListener(this);


        
        rl_edit.setOnkeyboarddStateListener(new InputMethodLayout.onKeyboardsChangeListener() {
            @Override
            public void onKeyBoardStateChange(int state) {
                switch (state) {
                    case InputMethodLayout.KEYBOARD_STATE_SHOW:

                        floating_action_menu.setVisibility(View.GONE);
                        note_detail_edit.setCursorVisible(true);
                        break;
                    case InputMethodLayout.KEYBOARD_STATE_HIDE:

                        floating_action_menu.setVisibility(View.VISIBLE);
                        note_detail_edit.setCursorVisible(false);
                        break;
                }
            }
        });


        menu_item_text_font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(font_size_selector.getVisibility()==LinearLayout.VISIBLE){
                    font_size_selector.setVisibility(View.GONE);
                }else{
                    font_size_selector.setVisibility(View.VISIBLE);
                }
            }
        });

        menu_item_voiceToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                buffer.setLength(0);
                
                setParam();
                
                mIatDialog.setListener(mRecognizerDialogListener);
                mIatDialog.show();
                showTip(getString(R.string.text_begin));
                
            }
        });

        menu_item_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Calendar beginTime = Calendar.getInstance();
                beginTime.clear();
                beginTime.set(2014,0,1,12,0);
                Calendar endTime = Calendar.getInstance();
                endTime.clear();
                endTime.set(2014,1,1,13,30);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(Uri.parse("content://com.android.calendar/events"))
                        .putExtra("beginTime", beginTime.getTimeInMillis())
                        .putExtra("endTime", endTime.getTimeInMillis())
                        .putExtra("title", "标题")
                        .putExtra("description", "地点");
                startActivity(intent);
            }
        });

        
        ll_font_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoseTextSize(0);
            }
        });
        
        ll_font_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoseTextSize(1);
            }
        });
        
        ll_font_large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoseTextSize(2);
            }
        });
        
        ll_font_super.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoseTextSize(3);
            }
        });





    }

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            
            showTip("start talking");
        }

        @Override
        public void onError(SpeechError error) {
            
            

            showTip(error.getPlainDescription(true));

        }

        @Override
        public void onEndOfSpeech() {
            
            showTip("End to talk");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            System.out.println(flg++);
            if (resultType.equals("json")) {

                printResult(results);

            }else if(resultType.equals("plain")) {
                buffer.append(results.getResultString());
                Log.i(TAG, "result1： " + buffer.toString());
                StringBuilder s = new StringBuilder();
                s.append(note_detail_edit.getText().toString());
                s.append(buffer.toString());
                note_detail_edit.setText(s.toString());
                note_detail_edit.setSelection(s.length());
            }

            if (isLast & cyclic) {
                
                Message message = Message.obtain();
                message.what = 0x001;
                han.sendMessageDelayed(message,100);
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("Is currently talking, volume size：" + volume);
            Log.d(TAG, "Return audio data："+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            
            
            
            
            
            
        }
    };

    /**
     * Dictation UI listener
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * Identify callback errors.
         */
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }

    };

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        Log.i(TAG, "result2： " + resultBuffer.toString());
        StringBuilder s = new StringBuilder();
        s.append(note_detail_edit.getText().toString());
        s.append(resultBuffer.toString());
        note_detail_edit.setText(s.toString());
        note_detail_edit.setSelection(s.length());
    }
    /**
     * Select the font size identifier
     * @param ID
     */
    private void ChoseTextSize(int ID){
        switch (ID){
            case 0:
                note_detail_edit.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                textsize=12;
                iv_small_select.setVisibility(View.VISIBLE);
                iv_medium_select.setVisibility(View.GONE);
                iv_large_select.setVisibility(View.GONE);
                iv_super_select.setVisibility(View.GONE);
                break;
            case 1:
                note_detail_edit.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                textsize=14;
                iv_small_select.setVisibility(View.GONE);
                iv_medium_select.setVisibility(View.VISIBLE);
                iv_large_select.setVisibility(View.GONE);
                iv_super_select.setVisibility(View.GONE);
                break;
            case 2:
                note_detail_edit.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                textsize=20;
                iv_small_select.setVisibility(View.GONE);
                iv_medium_select.setVisibility(View.GONE);
                iv_large_select.setVisibility(View.VISIBLE);
                iv_super_select.setVisibility(View.GONE);
                break;
            case 3:
                note_detail_edit.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
                textsize=25;
                iv_small_select.setVisibility(View.GONE);
                iv_medium_select.setVisibility(View.GONE);
                iv_large_select.setVisibility(View.GONE);
                iv_super_select.setVisibility(View.VISIBLE);
                break;
        }
    }
    /**
     * parameter setting
     *
     * @return
     */
    public void setParam() {
        
        Log.i(TAG,"Initialize parameters");
        mIat.setParameter(SpeechConstant.PARAMS, null);
        
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        
        mIat.setParameter(SpeechConstant.RESULT_TYPE, resultType);


        if(language.equals("zh_cn")) {
            String lag = mSharedPreferences.getString("iat_language_preference",
                    "mandarin");
            Log.e(TAG,"language:"+language);
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }else {

            mIat.setParameter(SpeechConstant.LANGUAGE, language);
        }
        Log.e(TAG,"last language:"+mIat.getParameter(SpeechConstant.LANGUAGE));

        
        

        
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

        
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
        Log.i(TAG,"Initialization parameters were successful");
    }

    /**
     * Switch note color menu
     */
    private void openMenu() {
        AnimationsUtils.openAnimation(note_detail_menu, note_detail_img_button, 700);
    }

    /**
     * Switch note color menu
     */
    private void closeMenu() {
        AnimationsUtils.closeAnimation(note_detail_menu, note_detail_img_button, 500);
    }

    int ret = 0; 
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_detail_img_green:
                note_detail_titlebar.setBackgroundColor(titlecolor[0]);
                note_detail_edit.setBackgroundColor(editcolor[0]);
                rl_edit.setBackgroundColor(editcolor[0]);
                iv_color.setImageResource(R.drawable.green);
                Color=editcolor[0];
                TitleColor=titlecolor[0];
                break;
            case R.id.note_detail_img_blue:
                note_detail_titlebar.setBackgroundColor(titlecolor[1]);
                note_detail_edit.setBackgroundColor(editcolor[1]);
                rl_edit.setBackgroundColor(editcolor[1]);
                iv_color.setImageResource(R.drawable.blue);
                Color=editcolor[1];
                TitleColor=titlecolor[1];
                break;
            case R.id.note_detail_img_purple:
                note_detail_titlebar.setBackgroundColor(titlecolor[2]);
                note_detail_edit.setBackgroundColor(editcolor[2]);
                rl_edit.setBackgroundColor(editcolor[2]);
                iv_color.setImageResource(R.drawable.purple);
                Color=editcolor[2];
                TitleColor=titlecolor[2];
                break;
            case R.id.note_detail_img_yellow:
                note_detail_titlebar.setBackgroundColor(titlecolor[3]);
                note_detail_edit.setBackgroundColor(editcolor[3]);
                rl_edit.setBackgroundColor(editcolor[3]);
                iv_color.setImageResource(R.drawable.yellow);
                Color=editcolor[3];
                TitleColor=titlecolor[3];
                break;
            case R.id.note_detail_img_red:
                note_detail_titlebar.setBackgroundColor(titlecolor[4]);
                note_detail_edit.setBackgroundColor(editcolor[4]);
                rl_edit.setBackgroundColor(editcolor[4]);
                iv_color.setImageResource(R.drawable.red);
                Color=editcolor[4];
                TitleColor=titlecolor[4];
                break;
            default:
                break;
        }
    }


    /**
     * Get the current date
     */

    public String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }

    /**
     * Gets the current time, used to save the ID
     */
    private  String getTimeId(){
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddhhmmss");
        String format1 = format.format(date);
        return format1;
    }

    @Override
    public void onItemClick(Object o, int position) {
        if(position==AlertView.CANCELPOSITION){

            finish();
            overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
        }else{

            if(!TextUtils.isEmpty(note_detail_edit.getText().toString())){
                
                NoteDatabase noteDatabase=new NoteDatabase(EditActivity.this);
                if(isFirst){
                    date = getCurrentDate();
                    timeId = getTimeId();
                }
                String content = note_detail_edit.getText().toString();
                NoteInfo noteInfo= new NoteInfo();
                noteInfo.setDate(date);
                noteInfo.setTitleColor(TitleColor);
                noteInfo.setColor(Color);
                noteInfo.setTimeId(timeId);
                noteInfo.setContent(content);
                noteInfo.setTextSize(textsize);
                if(isFirst){
                    boolean insert = noteDatabase.insert(noteInfo);
                    if(insert){
                        Toast.makeText(EditActivity.this,"save successfully！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(EditActivity.this,"fail to save！",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    noteDatabase.update(timeId,noteInfo);
                    Toast.makeText(EditActivity.this,"modify successfully！",Toast.LENGTH_SHORT).show();
                }
            }
            finish();
            overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
        }
    }

    @Override
    public void onDismiss(Object o) {

    }

    
    private void executeStream() {
        buffer.setLength(0);
        mIatResults.clear();
        
        setParam();
        
        mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        
        
        
        ret = mIat.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            showTip("Identify failure, error code：" + ret+",Please click the websitehttps://www.xfyun.cn/document/error-code查询解决方案");
        } else {
            byte[] audioData = FucUtil.readAudioFile(this, "iattest.wav");

            if (null != audioData) {
                showTip(getString(R.string.text_begin_recognizer));
                
                
                
                
                ArrayList<byte[]> bytes = FucUtil.splitBuffer(audioData,audioData.length,audioData.length/3);
                for(int i=0;i<bytes.size();i++) {
                    mIat.writeAudio(bytes.get(i), 0, bytes.get(i).length );

                    try {
                        Thread.sleep(1000);
                    }catch(Exception e){

                    }
                }
                mIat.stopListening();
				/*mIat.writeAudio(audioData, 0, audioData.length );
				mIat.stopListening();*/
            } else {
                mIat.cancel();
                showTip("Failed to read audio stream");
            }
        }
    }

}

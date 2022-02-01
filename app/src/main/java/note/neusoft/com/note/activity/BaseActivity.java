package note.neusoft.com.note.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import note.neusoft.com.note.NApplacation;

/**
 * Created by xxx on 2016/12/13 21:47.
 * Email：1124751755@qq.com
 * function：
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((NApplacation)this.getApplication()).addActivity(this);
    }
}

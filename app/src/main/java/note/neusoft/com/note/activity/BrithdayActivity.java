package note.neusoft.com.note.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.Date;

import note.neusoft.com.note.R;

public class BrithdayActivity extends Activity {

    @ViewInject(R.id.tv_date)
    private TextView tv_date;
    @ViewInject(R.id.tv_age)
    private TextView tv_age;
    @ViewInject(R.id.tv_constellation)
    private TextView tv_constellation;

    TimePickerView pvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brithday);
        ViewUtils.inject(this);

        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);



        pvTime.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.out_right_in,R.anim.out_left_out);
    }
}

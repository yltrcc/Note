package note.neusoft.com.note.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.henleylee.lockpattern.Cell;
import com.henleylee.lockpattern.CellStatus;
import com.henleylee.lockpattern.OnPatternChangedListener;
import com.henleylee.lockpattern.PatternHelper;
import com.henleylee.lockpattern.PatternIndicatorView;
import com.henleylee.lockpattern.PatternLockerView;

import java.util.List;

import note.neusoft.com.note.R;
import note.neusoft.com.note.domain.NoteInfo;
import note.neusoft.com.note.utils.Utility;

/**
 * create password
 */
public class PatternCheckActivity extends AppCompatActivity {

    private static final int MAX_RETRY_COUNT = 4;

    private int retryCount = MAX_RETRY_COUNT;
    private String password;
    private TextView tvMessage;
    private PatternIndicatorView indicatorView;
    private PatternLockerView lockerView;
    private NoteInfo noteInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_locker);
        Utility.initActionBar(getSupportActionBar());
        Intent intent = getIntent();
        noteInfo = (NoteInfo) intent.getSerializableExtra("noteinfo");
        password = Utility.getPatternPassword(this);

        tvMessage = findViewById(R.id.pattern_message);
        indicatorView = findViewById(R.id.pattern_indicator_view);
        lockerView = findViewById(R.id.pattern_locker_view);
        lockerView.setOnPatternChangedListener(new OnPatternChangedListener() {
            @Override
            public void onPatternStart() {
                indicatorView.setSelectedCells(null);
            }

            @Override
            public void onPatternChange(PatternLockerView view, List<Cell> cells) {

            }

            @Override
            public void onPatternComplete(PatternLockerView view, List<Cell> cells) {
                handlePatternPassword(view.getSide(), cells);
            }

            @Override
            public void onPatternClear() {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handlePatternPassword(int side, List<Cell> cells) {
        if (!TextUtils.equals(password, PatternHelper.patternToString(side, cells))) {
            retryCount--;
            lockerView.setPatternStatus(CellStatus.ERROR);
            indicatorView.setSelectedCells(cells);
            tvMessage.setTextColor(Color.RED);
            if (retryCount > 0) {
                tvMessage.setText("Password error can be entered " + retryCount + " more times");
            } else {
                retryCount = MAX_RETRY_COUNT;
                tvMessage.setText("The password is wrong, please reenter it");
            }
            return;
        }
        indicatorView.setSelectedCells(cells);
        Utility.showToast(this, "Password is correct");
        Intent intent = new Intent(this, EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("noteinfo", noteInfo);
        intent.putExtras(bundle);
        this.startActivity(intent);
        finish();
    }

}

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
import com.henleylee.lockpattern.style.ArrowLockerLinkedLineStyle;
import com.henleylee.lockpattern.style.RippleLockerCellStyle;

import java.util.List;

import note.neusoft.com.note.R;
import note.neusoft.com.note.domain.NoteInfo;
import note.neusoft.com.note.utils.Utility;

/**
 * create password
 */
public class PatternCreateActivity extends AppCompatActivity {

    private static final int MIN_PASSWORD_LENGTH = 4;

    private String password;
    private TextView tvMessage;
    private PatternIndicatorView indicatorView;
    private PatternLockerView lockerView;
    private NoteInfo noteInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_locker);
        Intent intent = getIntent();
        noteInfo = (NoteInfo) intent.getSerializableExtra("noteinfo");
        Utility.initActionBar(getSupportActionBar());

        tvMessage = findViewById(R.id.pattern_message);
        indicatorView = findViewById(R.id.pattern_indicator_view);
        lockerView = findViewById(R.id.pattern_locker_view);
        lockerView.setCellStyle(new RippleLockerCellStyle(lockerView.getDecoratorStyle()));
        lockerView.setLinkedLineStyle(new ArrowLockerLinkedLineStyle(lockerView.getDecoratorStyle()));
        lockerView.setOnPatternChangedListener(new OnPatternChangedListener() {
            @Override
            public void onPatternStart() {
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
        lockerView.setPatternStatus(CellStatus.ERROR);
        if (TextUtils.isEmpty(password)) {
            if (cells == null || cells.size() < 4) {
                lockerView.setPatternStatus(CellStatus.ERROR);
                indicatorView.setSelectedCells(cells);
                tvMessage.setTextColor(Color.RED);
                tvMessage.setText("Least connection" + MIN_PASSWORD_LENGTH + "Points, please redraw");
                return;
            }
            indicatorView.setSelectedCells(cells);
            password = PatternHelper.patternToString(side, cells);
            tvMessage.setTextColor(Color.DKGRAY);
            tvMessage.setText("Draw the unlock pattern again");
        } else {
            if (!TextUtils.equals(password, PatternHelper.patternToString(side, cells))) {
                lockerView.setPatternStatus(CellStatus.ERROR);
                indicatorView.setSelectedCells(cells);
                tvMessage.setTextColor(Color.RED);
                tvMessage.setText("Inconsistent with the last drawing, please redraw");
                return;
            }
            indicatorView.setSelectedCells(cells);
            Utility.savePatternPassword(this, password);
            Utility.showToast(this, "Password set successfully");
            Intent intent = new Intent(this, EditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("noteinfo", noteInfo);
            intent.putExtras(bundle);
            this.startActivity(intent);
            finish();
        }
    }

}

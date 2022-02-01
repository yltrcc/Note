package note.neusoft.com.note.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrListener;

import butterknife.ButterKnife;
import note.neusoft.com.note.utils.SlidrUtil;

/**
 * author：xxx
 * creationDate： by 2016/12/30 on 19:03.
 * description：basicLockScreenInterface
 */

public abstract class BaseLockActivity extends AppCompatActivity implements SlidrListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInitView();
        ButterKnife.bind(this);//callAnnotationFramework
        initView();
        initListener();
        initData();
        Slidr.attach(this, SlidrUtil.getConfig(this, this));
    }

    public abstract void beforeInitView();

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();
    /**
     * The following 4 methods are for gestures to return to the previous page to monitor the callback. If the subclass needs to use it, just rewrite it
     */
    @Override
    public void onSlideStateChanged(int state) {

    }

    @Override
    public void onSlideChange(float percent) {

    }

    @Override
    public void onSlideOpened() {

    }

    @Override
    public void onSlideClosed() {

    }
}

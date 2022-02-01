package note.neusoft.com.note.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * author：xxx
 * Creation date： by 2016/12/29 on 19:13.
 * description：Monitor soft keyboard
 */

public  class  InputMethodLayout extends RelativeLayout {
    private static final String TAG = "InputMethodLayout";
    /** Initialization state **/
    public static final byte KEYBOARD_STATE_INIT = -1;
    /** Hidden state **/
    public static final byte KEYBOARD_STATE_HIDE = -2;
    /** Open state **/
    public static final byte KEYBOARD_STATE_SHOW = -3;
    private boolean isInit;
    private boolean hasKeybord;
    private int viewHeight;
    private onKeyboardsChangeListener keyboarddsChangeListener;



    public InputMethodLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
    }

    public InputMethodLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        
    }

    public InputMethodLayout(Context context) {
        super(context);
        
    }

    /**
     * Set soft keyboard status monitoring
     *
     * @param listener
     */
    public void setOnkeyboarddStateListener(onKeyboardsChangeListener listener) {
        keyboarddsChangeListener = listener;
    }

    /**
     *
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!isInit) {
            isInit = true;
            viewHeight = b;
            keyboardSateChange(KEYBOARD_STATE_INIT);
        } else {
            viewHeight = viewHeight < b ? b : viewHeight;
        }
        if (isInit && viewHeight > b) {
            hasKeybord = true;
            keyboardSateChange(KEYBOARD_STATE_SHOW);
        }
        if (isInit && hasKeybord && viewHeight == b) {
            hasKeybord = false;
            keyboardSateChange(KEYBOARD_STATE_HIDE);
        }
    }

    /**
     * Switch soft keyboard state
     *
     * @param state
     *            
     */
    public void keyboardSateChange(int state) {
        if (keyboarddsChangeListener != null) {
            keyboarddsChangeListener.onKeyBoardStateChange(state);
        }
    }

    /**
     * Soft keyboard status switch monitoring
     *
     * @author zihao
     *
     */
    public interface onKeyboardsChangeListener {
        public void onKeyBoardStateChange(int state);
    }

}

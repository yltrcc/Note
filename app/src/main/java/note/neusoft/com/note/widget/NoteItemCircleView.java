package note.neusoft.com.note.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xxx on 2016/12/14 10:09.
 * Email：1124751755@qq.com
 * function：
 */

public class NoteItemCircleView extends CircleImageView {
    public NoteItemCircleView(Context context) {
        super(context);
    }

    public NoteItemCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoteItemCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }
}

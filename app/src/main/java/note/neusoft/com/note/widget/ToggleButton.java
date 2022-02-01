package note.neusoft.com.note.widget;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import note.neusoft.com.note.R;

/**
 * author：xxx
 * Creation date： by 2016/12/27 on 10:39.
 * description：
 */

public class ToggleButton extends View {

    /** */
    private float radius;
    /**
     * Turn on color
     */
    private int onColor = Color.parseColor("#4ebb7f");
    /**
     * Turn off color
     */
    private int offBorderColor = Color.parseColor("#dadbda");
    /**
     * Gray with color
     */
    private int offColor = Color.parseColor("#ffffff");
    /**
     * Handle color
     */
    private int spotColor = Color.parseColor("#ffffff");
    /**
     * Border color
     */
    private int borderColor = offBorderColor;
    /**
     * brush
     */
    private Paint paint;
    /**
     * switch status
     */
    private boolean toggleOn = false;
    /**
     * Border size
     */
    private int borderWidth = 2;
    /**
     * Vertical center
     */
    private float centerY;
    /**
     * The start and end positions of the button
     */
    private float startX, endX;
    /**
     * The minimum and maximum values ​​of the X position of the handle
     */
    private float spotMinX, spotMaxX;
    /**
     * handleSize
     */
    private int spotSize;
    /**
     * Handle X position
     */
    private float spotX;
    /**
     * Height of inner gray band when closed
     */
    private float offLineWidth;
    /** */
    private RectF rect = new RectF();

    private OnToggleChanged listener;

    private ToggleButton(Context context) {
        super(context);
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public void setup(AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                toggle();
            }
        });

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ToggleButton);
        offBorderColor = typedArray.getColor(R.styleable.ToggleButton_offBorderColor, offBorderColor);
        onColor = typedArray.getColor(R.styleable.ToggleButton_onColor, onColor);
        spotColor = typedArray.getColor(R.styleable.ToggleButton_spotColor, spotColor);
        offColor = typedArray.getColor(R.styleable.ToggleButton_offColor, offColor);
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.ToggleButton_toggle_border_width, borderWidth);
        typedArray.recycle();
    }

    public void toggle() {
        toggleOn = !toggleOn;
        animateCheckedState(toggleOn);
        if (listener != null) {
            listener.onToggle(toggleOn);
        }
    }

    public void toggleOn() {
        setToggleOn();
        if (listener != null) {
            listener.onToggle(toggleOn);
        }
    }

    public void toggleOff() {
        setToggleOff();
        if (listener != null) {
            listener.onToggle(toggleOn);
        }
    }

    /**
     * Set the display to open style, will not trigger the toggle event
     */
    public void setToggleOn() {
        toggleOn = true;
        setAnimatorProperty(true);
    }

    /**
     * Set the display as a closed style, will not trigger the toggle event
     */
    public void setToggleOff() {
        toggleOn = false;
        setAnimatorProperty(false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        final int width = getWidth();
        final int height = getHeight();
        radius = Math.min(width, height) * 0.5f;
        centerY = radius;
        startX = radius;
        endX = width - radius;
        spotMinX = startX + borderWidth;
        spotMaxX = endX - borderWidth;
        spotSize = height - 4 * borderWidth;

        
        setAnimatorProperty(toggleOn);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        rect.set(0, 0, getWidth(), getHeight());
        paint.setColor(borderColor);
        canvas.drawRoundRect(rect, radius, radius, paint);

        if (offLineWidth > 0) {
            final float cy = offLineWidth * 0.5f;
            rect.set(spotX - cy, centerY - cy, endX + cy, centerY + cy);
            paint.setColor(offColor);
            canvas.drawRoundRect(rect, cy, cy, paint);
        }

        rect.set(spotX - 1 - radius, centerY - radius, spotX + 1.1f + radius, centerY + radius);
        paint.setColor(borderColor);
        canvas.drawRoundRect(rect, radius, radius, paint);

        final float spotR = spotSize * 0.5f;
        rect.set(spotX - spotR, centerY - spotR, spotX + spotR, centerY + spotR);
        paint.setColor(spotColor);
        canvas.drawRoundRect(rect, spotR, spotR, paint);
    }

    /**
     * =============================================================================================
     * The Animate
     * =============================================================================================
     */
    private static final Interpolator ANIMATION_INTERPOLATOR = new DecelerateInterpolator();
    private static final int ANIMATION_DURATION = 280;
    private ObjectAnimator mAnimator;

    private void animateCheckedState(boolean newCheckedState) {
        AnimatorProperty property = new AnimatorProperty();
        if (newCheckedState) {
            property.color = onColor;
            property.offLineWidth = 10;
            property.spotX = spotMaxX;
        } else {
            property.color = offBorderColor;
            property.offLineWidth = spotSize;
            property.spotX = spotMinX;
        }

        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofObject(this, ANIM_VALUE, new AnimatorEvaluator(mCurProperty), property);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                mAnimator.setAutoCancel(true);
            mAnimator.setDuration(ANIMATION_DURATION);
            mAnimator.setInterpolator(ANIMATION_INTERPOLATOR);
        } else {
            mAnimator.cancel();
            mAnimator.setObjectValues(property);
        }
        mAnimator.start();
    }

    /**
     * =============================================================================================
     * The custom properties
     * =============================================================================================
     */

    private AnimatorProperty mCurProperty = new AnimatorProperty();

    private void setAnimatorProperty(AnimatorProperty property) {
        this.spotX = property.spotX;
        this.borderColor = property.color;
        this.offLineWidth = property.offLineWidth;
        invalidate();
    }

    private void setAnimatorProperty(boolean isOn) {
        AnimatorProperty property = mCurProperty;
        if (isOn) {
            property.color = onColor;
            property.offLineWidth = 10;
            property.spotX = spotMaxX;
        } else {
            property.color = offBorderColor;
            property.offLineWidth = spotSize;
            property.spotX = spotMinX;
        }
        setAnimatorProperty(property);
    }

    private final static class AnimatorProperty {
        private int color;
        private float offLineWidth;
        private float spotX;
    }

    private final static class AnimatorEvaluator implements TypeEvaluator<AnimatorProperty> {
        private final AnimatorProperty mProperty;

        public AnimatorEvaluator(AnimatorProperty property) {
            mProperty = property;
        }

        @Override
        public AnimatorProperty evaluate(float fraction, AnimatorProperty startValue, AnimatorProperty endValue) {
            
            mProperty.spotX = (int) (startValue.spotX + (endValue.spotX - startValue.spotX) * fraction);

            mProperty.offLineWidth = (int) (startValue.offLineWidth + (endValue.offLineWidth - startValue.offLineWidth) * (1 - fraction));

            
            int startA = (startValue.color >> 24) & 0xff;
            int startR = (startValue.color >> 16) & 0xff;
            int startG = (startValue.color >> 8) & 0xff;
            int startB = startValue.color & 0xff;

            int endA = (endValue.color >> 24) & 0xff;
            int endR = (endValue.color >> 16) & 0xff;
            int endG = (endValue.color >> 8) & 0xff;
            int endB = endValue.color & 0xff;

            mProperty.color = (startA + (int) (fraction * (endA - startA))) << 24 |
                    (startR + (int) (fraction * (endR - startR))) << 16 |
                    (startG + (int) (fraction * (endG - startG))) << 8 |
                    (startB + (int) (fraction * (endB - startB)));

            return mProperty;
        }
    }

    private final static Property<ToggleButton, AnimatorProperty> ANIM_VALUE = new Property<ToggleButton, AnimatorProperty>(AnimatorProperty.class, "animValue") {
        @Override
        public AnimatorProperty get(ToggleButton object) {
            return object.mCurProperty;
        }

        @Override
        public void set(ToggleButton object, AnimatorProperty value) {
            object.setAnimatorProperty(value);
        }
    };


    /**
     * @author ThinkPad
     */
    public interface OnToggleChanged {
        /**
         * @param on
         */
        public void onToggle(boolean on);
    }


    public void setOnToggleChanged(OnToggleChanged onToggleChanged) {
        listener = onToggleChanged;
    }
}

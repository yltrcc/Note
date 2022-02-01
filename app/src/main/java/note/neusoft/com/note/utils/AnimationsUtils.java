package note.neusoft.com.note.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * author：xxx
 * Creation date： by 2016/12/15 on 19:47.
 * description：
 */

public class AnimationsUtils {

    /**
     * Rotation animation
     * @param fromDegrees
     * @param toDegrees
     * @param durationMillis
     * @return
     */
    public static Animation getRotateAnimation(float fromDegrees,float toDegrees,long durationMillis){
        RotateAnimation rotateAnimation=new RotateAnimation(fromDegrees,toDegrees,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(durationMillis);
        rotateAnimation.setFillAfter(true);
        return rotateAnimation;
    }

    /**
     * Transparency animation
     * @param fromAlpha
     * @param toAlpha
     * @param durationMillis
     * @return
     */
    public static Animation getAlphaAnimation(float fromAlpha,float toAlpha,long durationMillis){
        AlphaAnimation alphaAnimation=new AlphaAnimation(fromAlpha,toAlpha);
        alphaAnimation.setDuration(durationMillis);
        alphaAnimation.setFillAfter(true);
        return alphaAnimation;
    }

    /**
     * Zoom animation
     * @param scaleXY
     * @param durationMillis
     * @return
     */
    public static Animation getScaleAnimation(float scaleXY,long durationMillis){
        ScaleAnimation scaleAnimation=new ScaleAnimation(1.0f,scaleXY,1.0f,scaleXY,Animation.RELATIVE_TO_SELF
                ,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(durationMillis);
        return scaleAnimation;
    }

    /**
     * Displacement animation
     * @param fromXDelta
     * @param toXDelta
     * @param fromYDelte
     * @param toYDelta
     * @param durationMillis
     * @return
     */
    public static Animation getTranslateAnimation(float fromXDelta,float toXDelta,float fromYDelte,float toYDelta,long durationMillis){
        TranslateAnimation translateAnimation=new TranslateAnimation(fromXDelta,toXDelta,fromYDelte,toYDelta);
        translateAnimation.setDuration(durationMillis);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    public static Animation clickAnimation(float scaleXY,long durationMillis){
        AnimationSet animationSet=new AnimationSet(true);
        animationSet.addAnimation(getScaleAnimation(scaleXY,durationMillis));
        animationSet.setDuration(durationMillis);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    public static Animation shakeAnimation(int X){
        AnimationSet animationSet=new AnimationSet(true);

        Animation animation1=getTranslateAnimation(0,-200,0,0,100);
        animation1.setStartOffset(100);
        animationSet.addAnimation(animation1);

        Animation animation2=getTranslateAnimation(-200,400,0,0,200);
        animation2.setStartOffset(300);
        animationSet.addAnimation(animation2);

        Animation animation3=getTranslateAnimation(400,-200,0,0,200);
        animation3.setStartOffset(500);
        animationSet.addAnimation(animation3);

        Animation animation4=getTranslateAnimation(-200,0,0,0,100);
        animation4.setStartOffset(600);
        animationSet.addAnimation(animation4);

        animationSet.setDuration(640);
        animationSet.setFillAfter(true);
        return  animationSet;
    }

    /**
     * Open animation
     * @param relativeLayout Submenu container
     * @param menu   Submenu background
     * @param durationMillis  Animation time
     */
    public static void openAnimation(RelativeLayout relativeLayout, ImageView menu,long durationMillis){
        relativeLayout.setVisibility(View.VISIBLE);
        for(int i=1;i<relativeLayout.getChildCount();i++){
            ImageView imageView=null;
            if(relativeLayout.getChildAt(i) instanceof  ImageView){
                imageView= (ImageView) relativeLayout.getChildAt(i);
            }else{
                continue;
            }

            int top=imageView.getTop();
            int left=imageView.getLeft();
            if(top==0){
                top=(menu.getHeight()+50)*i;
            }
            if(left==0){
                left=menu.getLeft();
            }
            AnimationSet set=new AnimationSet(true);
            set.addAnimation(getRotateAnimation(-360,0,durationMillis));
            set.addAnimation(getAlphaAnimation(0.5f,1.0f,durationMillis));
            set.addAnimation(getTranslateAnimation(menu.getLeft()-left,0,menu.getTop()-top+30,0,durationMillis));
            set.setFillAfter(true);
            set.setDuration(durationMillis);
            set.setStartOffset((i*100)/(-1+relativeLayout.getChildCount()));
            set.setInterpolator(new OvershootInterpolator(1f));
            imageView.startAnimation(set);
        }
    }


    /**
     * Closed animation
     * @param relativeLayout Submenu container
     * @param menu  Menu button
     * @param durationMillis Animation time
     */
    public static void closeAnimation(final  RelativeLayout relativeLayout,final ImageView menu,long durationMillis){
        for(int i=1;i<relativeLayout.getChildCount();i++){
            ImageView imageView=null;
            if(relativeLayout.getChildAt(i) instanceof  ImageView){
                imageView= (ImageView) relativeLayout.getChildAt(i);
            }else{
                continue;
            }
            AnimationSet animationSet=new AnimationSet(true);
            animationSet.addAnimation(getRotateAnimation(0,-360,durationMillis));
            animationSet.addAnimation(getAlphaAnimation(1.0f,0.5f,durationMillis));
            animationSet.addAnimation(getTranslateAnimation(0,menu.getLeft()-imageView.getLeft()
                    ,0,menu.getTop()-imageView.getTop()+30,durationMillis));
            animationSet.setFillAfter(true);
            animationSet.setDuration(durationMillis);
            animationSet.setStartOffset(((relativeLayout.getChildCount()-i)*100)/(-1+relativeLayout.getChildCount()));
            animationSet.setInterpolator(new AnticipateInterpolator(1f));
            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    relativeLayout.setVisibility(View.GONE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }
            });
            imageView.startAnimation(animationSet);
        }
    }
}

package com.tou;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.Date;

/**
 * Created by Administrator on 2018-03-01.
 */

public class UnlockBar extends RelativeLayout {

    private OnUnlockListener onUnlockListener;


    //private int thumbWidth = 0;
    boolean sliding = false;
    private int sliderPosition = 0;
    int initialSliderPosition = 0;
    ImageView background;
    float initialSlidingX = 0;
    boolean start = false;
    float center = 0;
    float startPointX = 0;
    float startPointY = 0;

    public UnlockBar(Context context) {
        super(context);
        init(context, null);
    }

    public UnlockBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public UnlockBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setOnUnlockListenerLeft(OnUnlockListener listener) {
        this.onUnlockListener = listener;
    }

    public void setOnUnlockListenerRight(OnUnlockListener listener) {
        this.onUnlockListener = listener;
    }

//    public void reset(){
//        final LayoutParams params = (LayoutParams) img_thumb.getLayoutParams();
//        ValueAnimator animator = ValueAnimator.ofInt(params.leftMargin, (getMeasuredWidth()-thumbWidth)/2);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator)
//            {
//                params.leftMargin = (Integer) valueAnimator.getAnimatedValue();
//                img_thumb.requestLayout();
//            }
//        });
//        animator.setDuration(300);
//        animator.start();
//        text_label.setAlpha(1f);
//    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.unlock_button, this, true);

        background = findViewById(R.id.backgroud);
        Date date = new Date();
        Glide.with(context).load(R.drawable.giphy)

                .into(background);

        //비올때 아이콘 랜덤하게 배경 출력
        /*TypedArray imgs = getResources().obtainTypedArray(R.array.rainIcon);
        int randomNum = ThreadLocalRandom.current().nextInt(0, imgs.getIndexCount() + 1);
        boolean isRain= false;
        if (!isRain){
            Glide.with(context).load(imgs.getResourceId(randomNum,-1)).into(background);
        } else{
            Glide.with(context).load(R.drawable.aaaa)
                .into(background);
        }*/


        // Get padding
       //thumbWidth = dpToPx(120); // 60dp + 2*10dp

//        ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
//        if (viewTreeObserver.isAlive()) {
//            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    UnlockBar.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    thumbWidth = img_thumb.getWidth()+dpToPx(20);
//                    sliderPosition = (UnlockBar.this.getWidth()-thumbWidth)/2;
//                    LayoutParams params = (LayoutParams) img_thumb.getLayoutParams();
//                    params.setMargins((UnlockBar.this.getWidth()-thumbWidth)/2, 0, 0, 0);
//                    img_thumb.setLayoutParams(params);
//
//
//                }
//            });
//        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        center = getMeasuredWidth() / 2;
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (!start) {
            startPointX = event.getX();
            startPointY = event.getY();
            start = true;
        }



       /* if (startPoint<center){
            Log.d("event밖","event "+(startPoint-event.getX())+"event2"+event.getX());
            if (startPoint-event.getX()>0||event.getX()>center){
                Log.d("event왼쪽","event "+(event.getX()/getMeasuredWidth())*10.3f);
                background.setScaleX((event.getX()/getMeasuredWidth())*10.3f);
                background.setScaleY((event.getY()/getMeasuredWidth())*10.3f);
            }
        } else {
            if (startPoint-event.getX()<0&&event.getX()<center){
                Log.d("event오른쪽","event "+(event.getX()/getMeasuredWidth())*1.3f);
                background.setScaleX((event.getX()/getMeasuredWidth())*1.3f);
                background.setScaleY((event.getY()/getMeasuredWidth())*1.3f);
            }
        }*/
        Log.d("차이", 2 - (startPointY / event.getY()) + ",,");
        if (startPointX / event.getX() < 1 && startPointY / event.getY() < 1) {
            background.setScaleX(Math.abs(2 - (startPointX / event.getX())));
            background.setScaleY(Math.abs(2 - (startPointY / event.getY())));
        } else {
            if (startPointX/event.getX()<1){
                background.setScaleX(Math.abs(2-(startPointX / event.getX())));
            } else {
                background.setScaleX(Math.abs((startPointX / event.getX())));
            }
            if (startPointY/event.getY()<1){
                background.setScaleY(Math.abs((startPointY / event.getY())));
            } else {
                background.setScaleY(Math.abs(2-(startPointY / event.getY())));
            }
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getX() > sliderPosition && event.getX() < (sliderPosition)) {
                Log.d("event15555", "event " + event.getX());
                sliding = true;
                initialSlidingX = event.getX();
                initialSliderPosition = sliderPosition;
            }
            Log.d("event10000", sliderPosition + ",," + getMeasuredWidth());
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            Log.d("event11111", sliderPosition + ",," + getMeasuredWidth());

            if (sliderPosition >= getMeasuredWidth() / 3) {
                if (onUnlockListener != null) {
                    Log.d("event19999", sliderPosition + ",," + getMeasuredWidth());
                    onUnlockListener.onUnlock();
                }
            } else if (sliderPosition >= -(getMeasuredWidth() / 3)) {
                if (onUnlockListener != null) onUnlockListener.onUnlock();
            } else {
                sliding = false;
                sliderPosition = 0;
                background.setScaleX(1f);
                background.setScaleY(1f);
                Log.d("event12222", sliderPosition + ",," + getMeasuredWidth());
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE && sliding) {
            sliderPosition = (int) (initialSliderPosition + (event.getX() - initialSlidingX));
            if (sliderPosition <= 0) sliderPosition = 0;

            if (sliderPosition >= (getMeasuredWidth())) {
                sliderPosition = (int) (getMeasuredWidth());
            }
            Log.d("event16666", sliderPosition + ",,");

            // setMarginLeft(sliderPosition);
        }

        return true;
    }

//    private void setMarginLeft(int margin)
//    {
//        if (img_thumb == null) return;
//        LayoutParams params = (LayoutParams) img_thumb.getLayoutParams();
//        params.setMargins(margin, 0, 0, 0);
//        img_thumb.setLayoutParams(params);
//    }
//
//    private int dpToPx(int dp) {
//        float density = getResources().getDisplayMetrics().density;
//        return Math.round((float)dp*density);
//    }

    public static interface OnUnlockListener {
        void onUnlock();
    }
}

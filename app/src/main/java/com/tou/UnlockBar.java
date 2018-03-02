package com.tou;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

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
    UserData userData;
    SharedPreference sharedPreference = new SharedPreference();
    String user_name, user_birth;
    long startDate;
    Context context;

    public UnlockBar(Context context) {
        super(context);
        this.context = context;
        init(context, null);

    }

    public UnlockBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    public UnlockBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
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
        getData();




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

        Log.d("차이", 2 - (startPointY / event.getY()) + ",,");
        if (startPointX / event.getX() < 1 && startPointY / event.getY() < 1) {
            background.setScaleX(Math.abs(2 - (startPointX / event.getX())));
            background.setScaleY(Math.abs(2 - (startPointY / event.getY())));
        } else {
            if (startPointX / event.getX() < 1) {
                background.setScaleX(Math.abs(2 - (startPointX / event.getX())));
            } else {
                background.setScaleX(Math.abs((startPointX / event.getX())));
            }
            if (startPointY / event.getY() < 1) {
                background.setScaleY(Math.abs((startPointY / event.getY())));
            } else {
                background.setScaleY(Math.abs(2 - (startPointY / event.getY())));
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


    public static interface OnUnlockListener {
        void onUnlock();
    }

    private void getData() {
        String data = sharedPreference.getValue(context, "userData", "");
        Gson gson = new Gson();
        userData = gson.fromJson(data, UserData.class);
        if (userData != null) {
            user_name = userData.getName();
            user_birth = userData.getBirth();
            startDate = userData.getStartDate();
        }
    }

    public void setBackground(String skyCode) {

        Date start = new Date();
        start.setTime(startDate);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
        String startSt = formatter.format(start);
        SimpleDateFormat formatter2 = new SimpleDateFormat("MM월dd일", Locale.ENGLISH);

        try {
            Date  date = formatter2.parse(user_birth);
            String birth = formatter.format(date);
            String[] specialDay = {startSt, birth, "24 Sep", "07 Aug"};
            Date today = new Date();
            today.setTime(System.currentTimeMillis());
            String todaySt = formatter.format(today);
            if (todaySt.equals(specialDay[0])){
                setImage(R.drawable.firstday);
            } else if (todaySt.equals(specialDay[1])){
                setImage(R.drawable.birthday);
            } else if(todaySt.equals(specialDay[2])){
                setImage(R.drawable.sep_24th);
            } else if (todaySt.equals(specialDay[3])){
                setImage(R.drawable.aug_7th);
            } else {
                Log.d("test123",skyCode+",,");
                setWeatherBackground(skyCode);
            }
        } catch (ParseException e) {
            Log.d("test123",e.getLocalizedMessage()+",,");
        }


    }


    private void setWeatherBackground(String skycode) {
        //비올때 아이콘 랜덤하게 배경 출력
        TypedArray rainyImgs = getResources().obtainTypedArray(R.array.rainIcon);
        TypedArray ranImgs = getResources().obtainTypedArray(R.array.randomBack);
        int rainyNum = ThreadLocalRandom.current().nextInt(0, rainyImgs.getIndexCount() + 1);
        int randomNum = ThreadLocalRandom.current().nextInt(0, ranImgs.getIndexCount() + 1);

        if (skycode.equals("SKY_A04") || skycode.equals("SKY_A08")) {
            setImage(rainyImgs.getResourceId(rainyNum,-1));
        } else if (skycode.equals("SKY_A06") || skycode.equals("SKY_A10")) {
            //두개 띄울 수 있나?
            setImage(rainyImgs.getResourceId(rainyNum,-1));
        } else if (skycode.equals("SKY_A12")) {
            //thunder, rainy
            setImage(rainyImgs.getResourceId(rainyNum,-1));
        } else if (skycode.equals("SKY_A14")) {
            setImage(rainyImgs.getResourceId(rainyNum,-1));
            //vector rainy,snowy-5,thuder
        } else  {

            setImage(ranImgs.getResourceId(randomNum,-1));
        }
    }

    private void setImage(int resource) {
        Glide.with(this).load(resource).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Log.d("test1234",e.getMessage()+",");
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(background);
    }
}

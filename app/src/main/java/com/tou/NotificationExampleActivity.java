package com.tou;

import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tou.weahter.MinutelyWeather;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018-02-27.
 */

public class NotificationExampleActivity extends AppCompatActivity {


    //GPSTracker
    GPSTracker gps = null;

    public Handler mHandler;

    public static int RENEW_GPS = 1;
    public static int SEND_PRINT = 2;

    double latitude;
    double longitude;

    //
    TextView currentdate, celcious, content1, content2;
    TextView button_exit;
    Typeface typeface1,typeface2, typeface3;
    ImageView weatherIcon,icon;
    UnlockBar unlock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_notificationexample);

        showComponet();
        setTypeface();
        setIcon();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==RENEW_GPS){
                    makeNewGpsService();
                }
                if(msg.what==SEND_PRINT){
                    logPrint((String)msg.obj);
                }
            }
        };

        GPSEvent();

        //Toast.makeText(this, latitude+"+"+longitude, Toast.LENGTH_SHORT).show();
        getWeather(latitude, longitude);

        unlock = findViewById(R.id.unlock);

        unlock.setOnUnlockListenerRight(new UnlockBar.OnUnlockListener() {
            @Override
            public void onUnlock() {
                Toast.makeText(NotificationExampleActivity.this, "Right Action", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        unlock.setOnUnlockListenerLeft(new UnlockBar.OnUnlockListener() {
            @Override
            public void onUnlock() {
                Toast.makeText(NotificationExampleActivity.this, "left Action", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    //GPS code

    public void GPSEvent(){
        if(gps == null) {
            gps = new GPSTracker(NotificationExampleActivity.this,mHandler);
        }else{
            gps.Update();
        }

        // check if GPS enabled
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
    public void makeNewGpsService(){
        if(gps == null) {
            gps = new GPSTracker(NotificationExampleActivity.this,mHandler);
        }else{
            gps.Update();
        }

    }
    public void logPrint(String str){

        Date date2 = new Date();
        Toast.makeText(this, str + " 현재시간: "+date2 , Toast.LENGTH_SHORT).show();

    }

    //화면 초기화
    private void showComponet() {

        currentdate = findViewById(R.id.currentdate);
        celcious = findViewById(R.id.celcious);
        content1 = findViewById(R.id.content_upper);
        content2 = findViewById(R.id.content_below);
       // button_exit = findViewById(R.id.button_exit);
        weatherIcon  = findViewById(R.id.weatherimage);
        icon = findViewById(R.id.icon);

        //button_exit.setOnClickListener(this);
    }

    private void setIcon(){
        Glide.with(this).load(R.drawable.fillingicon)
                .into(weatherIcon);
        Glide.with(this).load(R.drawable.fillingicon)//날씨 정보에 맞는 날씨 이미지 넣어야 함.
                .into(icon);

    }
    public void setTypeface(){
        typeface1 = Typeface.createFromAsset(getAssets(), "fonts/smr.ttf");
        typeface2 = Typeface.createFromAsset(getAssets(),"fonts/jejug.ttf");
        typeface3 = Typeface.createFromAsset(getAssets(),"fonts/nanumg.ttf");

//        button_exit.setTypeface(typeface3);
        currentdate.setTypeface(typeface2);
        celcious.setTypeface(typeface2);
        content1.setTypeface(typeface1);
        content2.setTypeface(typeface1);

    }

    //화면종료 event
//    @Override
//    public void onClick(View view) {
//        if(view == button_exit){
//            Toast.makeText(this, "화면을 종료합니다.", Toast.LENGTH_SHORT).show();
//            System.exit(0);
//        }
//    }
    private void setWeatherIcon(String skycode){

        if(skycode.equals("SKY_A01")){
            setImage(R.drawable.day);
        }else if(skycode.equals("SKY_A02")){
            setImage(R.drawable.cloudy_day_3);
        }else if(skycode.equals("SKY_A03") || skycode.equals("SKY_A07")){
            setImage(R.drawable.cloudy);
        }else if(skycode.equals("SKY_A04") || skycode.equals("SKY_A08")){
            setImage(R.drawable.rainy_4);
        }else if(skycode.equals("SKY_A05") || skycode.equals("SKY_A09")){
            setImage(R.drawable.snowy_5);
        }else if(skycode.equals("SKY_A06") || skycode.equals("SKY_A10")){
            //두개 띄울 수 있나?
            setImage(R.drawable.rainy_4);
        }else if(skycode.equals("SKY_A11")){
            setImage(R.drawable.thunder);
        }else if(skycode.equals("SKY_A12")){
            //thunder, rainy
            setImage(R.drawable.rainy_4);
        }else if(skycode.equals("SKY_A13")){
            //thunder, snowy-5
            setImage(R.drawable.snowy_5);
        }else if(skycode.equals("SKY_A14")){
            setImage(R.drawable.thunder);
            //vector rainy,snowy-5,thuder
        }
    }

    private void setImage(int resource){
        Glide.with(this).load(resource).into(weatherIcon);
    }
    @Override
    public void onAttachedToWindow() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
//                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onAttachedToWindow();

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((LockApplication) getApplication()).lockScreenShow = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((LockApplication) getApplication()).lockScreenShow = false;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    //날씨 정보 받아오는 코드
    private void getWeather(double latitude, double longtitude){
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.BASEURL).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<MinutelyWeather> call = apiService.getMinutely(ApiService.APPKEY,1,latitude,longtitude);
        call.enqueue(new Callback<MinutelyWeather>() {
            @Override
            public void onResponse(Call<MinutelyWeather> call, Response<MinutelyWeather> response) {

                if(response.isSuccessful()){
                    //날씨데이터를 받아옴
                    MinutelyWeather object = response.body();
                    String skyCode = object.getWeather().getMinutely().get(0).getSky().getCode();
                    String temper = object.getWeather().getMinutely().get(0).getTemperature().getTc();
                    Log.d("test123123",response.body().toString());
                    if(object!=null){
                        //데이터가 null이 아니면 날씨 데이터 텍스트 뷰로 보여주기
                        content1.setText(skyCode);
                        setWeatherIcon(skyCode);
                        unlock.setBackground(skyCode);
                    }
                }


            }

            @Override
            public void onFailure(Call<MinutelyWeather> call, Throwable t) {
                Log.d("test123",t.getLocalizedMessage()+",");
            }
        });

    }

}
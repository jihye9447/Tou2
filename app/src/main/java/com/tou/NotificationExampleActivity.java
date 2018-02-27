package com.tou;

import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018-02-27.
 */

public class NotificationExampleActivity extends AppCompatActivity implements View.OnClickListener{

    //GPSTracker
    GPSTracker gps = null;

    public Handler mHandler;

    public static int RENEW_GPS = 1;
    public static int SEND_PRINT = 2;

    double latitude;
    double longitude;

    //
    TextView currentdate, celcious, content1, content2;
    Button button_exit;
    Typeface typeface1,typeface2, typeface3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationexample);

        showComponet();
        setTypeface();

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
        Toast.makeText(this, latitude+"+"+longitude, Toast.LENGTH_SHORT).show();
        getWeather(latitude, longitude);


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
        button_exit = findViewById(R.id.button_exit);

        button_exit.setOnClickListener(this);
    }

    public void setTypeface(){
        typeface1 = Typeface.createFromAsset(getAssets(), "fonts/smr.ttf");
        typeface2 = Typeface.createFromAsset(getAssets(),"fonts/jejug.ttf");
        typeface3 = Typeface.createFromAsset(getAssets(),"fonts/nanumg.ttf");

        button_exit.setTypeface(typeface3);
        currentdate.setTypeface(typeface2);
        celcious.setTypeface(typeface2);
        content1.setTypeface(typeface1);
        content2.setTypeface(typeface1);

    }

    //화면종료 event
    @Override
    public void onClick(View view) {
        if(view == button_exit){
            Toast.makeText(this, "화면을 종료합니다.", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }
    }

    //날씨 정보 받아오는 코드
    private void getWeather(double latitude, double longtitude){
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.BASEURL).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<JsonObject> call = apiService.getHourly(ApiService.APPKEY,1,latitude,longtitude);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.isSuccessful()){
                    //날씨데이터를 받아옴
                    JsonObject object = response.body();
                    if(object!=null){
                        //데이터가 null이 아니면 날씨 데이터 텍스트 뷰로 보여주기
                        content1.setText(object.toString());
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

}

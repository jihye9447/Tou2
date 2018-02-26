package com.tou;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2018-02-17.
 */

public class BirthdayActivity extends AppCompatActivity {

    //위젯 삽입
    TextView birthday;
    String user_name;
    Button next,reset,birth_edit;
    Typeface typeface1, typeface2;
    int Birthyear, BirthMonth,Days;
    SharedPreference sharedPreference=new SharedPreference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        user_name = getIntent().getStringExtra("username");

        initView();
        setTypeface();

    }
    public void initView(){
        birthday = findViewById(R.id.birthday);

        birth_edit = findViewById(R.id.birthday_edit);
        birth_edit.setOnClickListener(dateButtonClick);
        reset = findViewById(R.id.button_reset);
        reset.setOnClickListener(dateButtonClick);
        next = findViewById(R.id.button_next);
        next.setOnClickListener(dateButtonClick);

    }
    public void setTypeface(){
        typeface1 = Typeface.createFromAsset(getAssets(),"fonts/jejug.ttf");
        typeface2 = Typeface.createFromAsset(getAssets(),"fonts/smr.ttf");

        birthday.setTypeface(typeface2);
        birth_edit.setTypeface(typeface1);
        reset.setTypeface(typeface1);
        next.setTypeface(typeface1);

    }
    Button.OnClickListener dateButtonClick = new Button.OnClickListener(){

        public void onClick(View view){

            if(R.id.birthday_edit == view.getId()){
                Toast.makeText(getApplicationContext(),"birthday버튼을 눌렀습니다.",Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(getApplicationContext(),DatepickerActivity.class);
                startActivityForResult(intent1,100);

            }
            else if(R.id.button_next == view.getId()){
                Intent intent = new Intent(getApplicationContext(),LastActivity.class);
                saveUserData();
                Toast.makeText(getApplicationContext(),"next버튼을 눌렀습니다.",Toast.LENGTH_LONG).show();
                startActivity(intent);

            }else if(R.id.button_reset == view.getId()){
                birth_edit.setBackgroundResource(R.drawable.edittextshape);
                birth_edit.setText("생일을 입력하세요.|");
                birth_edit.setShadowLayer(0,0,0,0);
                birth_edit.setTextColor(Color.DKGRAY);
            }
        }

    };
    /**
     * Gson 을 이용한 UserData 를  json 형태의 string 으로 변환하여 DB 에저장
     * 로그인 완료처리 개념으로 isLogin 을 true 로 저장* -> 로딩액티비티에서 쓰임*/
    private void saveUserData(){
        UserData userData = new UserData(user_name,BirthMonth+"월"+Days+"일");
        Gson gson  =new Gson();
        String userString = gson.toJson(userData);
        sharedPreference.put(this,"userData",userString);
        //userString=sharedPreference.getValue(this,"userData","");
        //Toast.makeText(getApplicationContext(),userString,Toast.LENGTH_LONG).show();
        sharedPreference.put(this,"isLogin",true);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null&&resultCode==RESULT_OK){

            //생일 값 받아와서 출력하기
            Birthyear = data.getIntExtra("year",0);
            BirthMonth = data.getIntExtra("month",0);
            Days = data.getIntExtra("days",0);
            Toast.makeText(getBaseContext(),Birthyear + "년"+ BirthMonth + "월"+Days+"일",Toast.LENGTH_LONG).show();
            birth_edit.setBackground(null);
            birth_edit.setTextColor(Color.WHITE);
            birth_edit.setGravity(Gravity.CENTER);
            birth_edit.setShadowLayer(10,0,0,Color.WHITE);
            Typeface jeju = Typeface.createFromAsset(getAssets(),"fonts/jejug.ttf");
            birth_edit.setTypeface(jeju);
            birth_edit.setText(BirthMonth+"월 "+Days+"일");

        }
    }

}

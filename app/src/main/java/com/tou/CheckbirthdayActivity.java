package com.tou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018-02-17.
 */

public class CheckbirthdayActivity extends AppCompatActivity {

    int Birthyear, BirthMonth, Days;
    TextView birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbirthday);

        birth = findViewById(R.id.selected_birthday);

        //생일 값 받아와서 출력하기
        Intent intent = getIntent();
        Birthyear = intent.getExtras().getInt("year");
        BirthMonth = intent.getExtras().getInt("month");
        Days = intent.getExtras().getInt("days");
        Toast.makeText(getBaseContext(),Birthyear + "년"+ BirthMonth + "월"+Days+"일",Toast.LENGTH_LONG).show();
        birth.setText(BirthMonth+"월 "+Days+"일");


        findViewById(R.id.button_reset).setOnClickListener(onButtonClick);
        findViewById(R.id.button_next).setOnClickListener(onButtonClick);
    }
    Button.OnClickListener onButtonClick = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {

            if(R.id.button_next == view.getId()){
                Toast.makeText(getApplicationContext(),"next버튼을 눌렀습니다.",Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(getApplicationContext(),LastActivity.class);
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                intent1.putExtra("CurrentDateTime", currentDateTimeString);
                startActivity(intent1);

            }else if(R.id.button_reset == view.getId()){
                Toast.makeText(getApplicationContext(),"reset버튼을 눌렀습니다",Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(getApplicationContext(),BirthdayActivity.class);
                startActivity(intent2);

            }

        }
    };
}

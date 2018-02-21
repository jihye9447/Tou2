package com.tou;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018-02-19.
 */

public class DatepickerActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_save;
    Date birth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker);

        btn_save = (Button)findViewById(R.id.button_save);


        btn_save.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view == btn_save ){

            //date picker에서 선택한 생일 저장해서 다음 activity에 값 넘겨주기
            DatePicker saved_date = (DatePicker)findViewById(R.id.datepicker);
            saved_date.setMaxDate(System.currentTimeMillis()-1000);

            int birthYear = saved_date.getYear();
            int birthMonth = saved_date.getMonth()+1;
            int birthDay = saved_date.getDayOfMonth();

            Intent intent = new Intent (getApplicationContext(),CheckbirthdayActivity.class);
            intent.putExtra("year",birthYear);
            intent.putExtra("month",birthMonth);
            intent.putExtra("days", birthDay);
            Toast.makeText(getBaseContext(),birthYear+"+"+birthMonth+"+"+birthDay+"click함",Toast.LENGTH_LONG).show();
            startActivity(intent);

        }//else에러메세지 출력하기

    }

}



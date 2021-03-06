package com.tou;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
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
    DatePicker saved_date;
    Typeface typeface;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker);

       initView();
       setTypeface();

    }
    public void initView(){

        btn_save = findViewById(R.id.button_save);
        saved_date = findViewById(R.id.datepicker);
        saved_date.setMaxDate(System.currentTimeMillis()-1000);

        btn_save.setOnClickListener(this);
    }
    public void setTypeface(){
        typeface = Typeface.createFromAsset(getAssets(),"fonts/jejug.ttf");
        btn_save.setTypeface(typeface);
    }

    @Override
    public void onClick(View view) {

        if(view == btn_save ){

            //date picker에서 선택한 생일 저장해서 다음 activity에 값 넘겨주기


            int birthYear = saved_date.getYear();
            int birthMonth = saved_date.getMonth()+1;
            int birthDay = saved_date.getDayOfMonth();

            Intent intent = new Intent ();
            intent.putExtra("year",birthYear);
            intent.putExtra("month",birthMonth);
            intent.putExtra("days", birthDay);
            Toast.makeText(getBaseContext(),birthYear+"+"+birthMonth+"+"+birthDay+"click함",Toast.LENGTH_LONG).show();
            setResult(RESULT_OK,intent);
            finish();

        }//else에러메세지 출력하기

    }

}



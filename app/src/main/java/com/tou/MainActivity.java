package com.tou;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Scanner;

/**
 * Created by Administrator on 2018-02-15.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText input_name;
    TextView title,question;
    Button btn_r;
    Button btn_n;
    Typeface typeface1, typeface2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showComponet();
        setTypeface();

        //xml 이름 입력하는 창과 연결.
        //input_name = findViewById(R.id.input_name);
        //findViewById(R.id.input_name).setOnClickListener(onButtonClick);

    }

    private void showComponet() {

        question = findViewById(R.id.name1);
        input_name = findViewById(R.id.input_name);
        btn_n = findViewById(R.id.button_next);
        btn_r = findViewById(R.id.button_reset);
        title = findViewById(R.id.logo_design);

        btn_n.setOnClickListener(this);
        btn_r.setOnClickListener(this);
    }

    public void setTypeface(){
        typeface1 = Typeface.createFromAsset(getAssets(), "fonts/smr.ttf");
        typeface2 = Typeface.createFromAsset(getAssets(),"fonts/jejug.ttf");

        btn_n.setTypeface(typeface2);
        btn_r.setTypeface(typeface2);
        title.setTypeface(typeface2);
        question.setTypeface(typeface1);
    }

    @Override
    public void onClick(View view) {
        if(view==btn_n){

            String st = input_name.getText().toString();
            Toast.makeText(getBaseContext(),st,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),ChecknameActivity.class);
            intent.putExtra("username",st);
            startActivity(intent);

        }else if(view==btn_r){

            input_name.setText("");
            Toast.makeText(getBaseContext(),"reset button clicked",Toast.LENGTH_LONG).show();

        }

    }

    public void exitButton(View view) {
        if(R.id.button_exit == view.getId()){
            //서비스-> 알람메니저 / 렌덤함수로 알람시간 지정(24시간 주기) / notification 띄우기 /
            //날씨 데이터 업뎃 해서 / 생일 /생일 제외한 비오는날 / 비안오는날

            Toast.makeText(getApplicationContext(),"종료",Toast.LENGTH_LONG).show();
            //System.exit(0);
        }
    }
}


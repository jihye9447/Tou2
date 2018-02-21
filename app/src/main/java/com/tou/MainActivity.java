package com.tou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Scanner;

/**
 * Created by Administrator on 2018-02-15.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText input_name;
    Button btn_r;
    Button btn_n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showComponet();

        //xml 이름 입력하는 창과 연결.
        //input_name = findViewById(R.id.input_name);
        //findViewById(R.id.input_name).setOnClickListener(onButtonClick);

    }

    private void showComponet() {

        input_name = (EditText)findViewById(R.id.input_name);
        btn_n = (Button)findViewById(R.id.button_next);
        btn_r = (Button)findViewById(R.id.button_reset);

        btn_n.setOnClickListener(this);
        btn_r.setOnClickListener(this);

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
            //지우는거 삽입하기
            Toast.makeText(getBaseContext(),"reset button clicked",Toast.LENGTH_LONG).show();

        }

    }

    public void exitButton(View view) {
        if(R.id.button_exit == view.getId()){
            Toast.makeText(getApplicationContext(),"종료",Toast.LENGTH_LONG).show();
            System.exit(0);
        }
    }
}


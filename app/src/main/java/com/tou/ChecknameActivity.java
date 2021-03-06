package com.tou;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2018-02-15.
 */

public class ChecknameActivity extends AppCompatActivity{

    TextView check_name, question;
    String user_name;
    Typeface typeface1, typeface2;
    Button button_r, button_n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkname);
        LockApplication.activities.add(this);
        initView();
        setTypeface();

    }
    public void initView(){
        check_name = findViewById(R.id.check_name);
        question = findViewById(R.id.name1);

        Intent intent = getIntent();
        user_name = intent.getExtras().getString("username");
        Toast.makeText(getBaseContext(),user_name,Toast.LENGTH_LONG).show();
        check_name.setText(user_name);

        button_r = findViewById(R.id.button_reset);
        button_r.setOnClickListener(onButtonClick);
        button_n = findViewById(R.id.button_next);
        button_n.setOnClickListener(onButtonClick);
    }

    public void setTypeface(){
        typeface1 = Typeface.createFromAsset(getAssets(), "fonts/smr.ttf");
        typeface2 = Typeface.createFromAsset(getAssets(),"fonts/jejug.ttf");

        button_n.setTypeface(typeface2);
        button_r.setTypeface(typeface2);
        check_name.setTypeface(typeface2);
        question.setTypeface(typeface1);
    }
    Button.OnClickListener onButtonClick = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {

            if(R.id.button_next == view.getId()){
                Toast.makeText(getApplicationContext(),"next버튼을 눌렀습니다.",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),BirthdayActivity.class);
                intent.putExtra("username",user_name);
                startActivity(intent);

            }else if(R.id.button_reset == view.getId()){
                finish();
            }

        }
    };

}

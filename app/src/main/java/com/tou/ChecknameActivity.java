package com.tou;

import android.content.Intent;
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

    TextView check_name;
    String user_name;
    //Button button_r, button_n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkname);

        check_name = findViewById(R.id.check_name);
        Intent intent = getIntent();
        user_name = intent.getExtras().getString("username");
        Toast.makeText(getBaseContext(),user_name,Toast.LENGTH_LONG).show();
        check_name.setText(user_name);

        //input_name.setText(String.valueOf(msg)+"님");


        findViewById(R.id.button_reset).setOnClickListener(onButtonClick);
        findViewById(R.id.button_next).setOnClickListener(onButtonClick);

        //name1은 성동명조(smr)
        //check_name은 제주고딕 빛 번짐
        //btn_r, btn_n 제주고딕
        //1.RESET 클릭하면 메인화면으로 돌아감.
        //2.NEXT 버튼 클릭하면 생일입력화면으로 이동

    }
    Button.OnClickListener onButtonClick = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {

            if(R.id.button_next == view.getId()){
                Toast.makeText(getApplicationContext(),"next버튼을 눌렀습니다.",Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(getApplicationContext(),BirthdayActivity.class);
                startActivity(intent1);

            }else if(R.id.button_reset == view.getId()){
                Toast.makeText(getApplicationContext(),"reset버튼을 눌렀습니다",Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent2);

            }

        }
    };

}

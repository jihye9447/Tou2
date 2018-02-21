package com.tou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2018-02-17.
 */

public class BirthdayActivity extends AppCompatActivity {

    //위젯 삽입

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        findViewById(R.id.birthday_edit).setOnClickListener(dateButtonClick);
        findViewById(R.id.button_reset).setOnClickListener(onButtonClick);
        findViewById(R.id.button_next).setOnClickListener(onButtonClick);

        //name1은 성동명조(smr)
        //check_name은 제주고딕 빛 번짐
        //btn_r, btn_n 제주고딕
        //1.RESET 클릭하면 메인화면으로 돌아감.
        //2.NEXT 버튼 클릭하면 생일입력화면으로 이동

    }
    Button.OnClickListener dateButtonClick = new Button.OnClickListener(){

        public void onClick(View view){

            if(R.id.birthday_edit == view.getId()){
                Toast.makeText(getApplicationContext(),"birthday버튼을 눌렀습니다.",Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(getApplicationContext(),DatepickerActivity.class);
                startActivity(intent1);

            }
        }

    };
    Button.OnClickListener onButtonClick = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {

            if(R.id.button_next == view.getId()){
                Toast.makeText(getApplicationContext(),"next버튼을 눌렀습니다.",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),BirthdayActivity.class);
                startActivity(intent);

            }else if(R.id.button_reset == view.getId()){
                Toast.makeText(getApplicationContext(),"reset버튼을 눌렀습니다",Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent2);

            }

        }
    };
}

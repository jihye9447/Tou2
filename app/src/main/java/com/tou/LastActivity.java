package com.tou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2018-02-20.
 */

public class LastActivity extends AppCompatActivity {

    TextView textView;
    String str;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        textView = (TextView)findViewById(R.id.sample);
        Intent intent = getIntent();
        str = intent.getExtras().getString("CurrentDateTime");
        Toast.makeText(getBaseContext(),str,Toast.LENGTH_LONG).show();
        textView.setText(str);

    }

}

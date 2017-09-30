package com.kpu.kpuindoormap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class PopupActivity extends Activity implements View.OnClickListener {


    TextView tv_title;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    Button btn_5;
    Button btn_6;
    Button btn_7;
    Button btn_8;
    Button btn_9;
    Button btn_10;
    Button btn_11;
    Button btn_12;
    Button btn_13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        tv_title = (TextView) findViewById(R.id.tv_title);
        btn_1 = (Button)findViewById(R.id.btn_1);
        btn_2 = (Button)findViewById(R.id.btn_2);
        btn_3 = (Button)findViewById(R.id.btn_3);
        btn_4 = (Button)findViewById(R.id.btn_4);
        btn_5 = (Button)findViewById(R.id.btn_5);
        btn_6 = (Button)findViewById(R.id.btn_6);
        btn_7 = (Button)findViewById(R.id.btn_7);
        btn_8 = (Button)findViewById(R.id.btn_8);
        btn_9 = (Button)findViewById(R.id.btn_9);
        btn_10 = (Button)findViewById(R.id.btn_10);
        btn_11 = (Button)findViewById(R.id.btn_11);
        btn_12 = (Button)findViewById(R.id.btn_12);
        btn_13 = (Button)findViewById(R.id.btn_13);

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_10.setOnClickListener(this);
        btn_11.setOnClickListener(this);
        btn_12.setOnClickListener(this);
        btn_13.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(PopupActivity.this,InfoActivity.class);
        switch (view.getId()){

            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
                intent.putExtra("LIST_NAME", view.getId());
                startActivity(intent);
                break;

            default:
                break;

        }

    }

    public void PopupClicked (View v){

        finish();
    }
}

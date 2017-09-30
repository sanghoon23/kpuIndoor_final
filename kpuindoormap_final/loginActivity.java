package com.kpu.kpuindoormap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.AsyncTask;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class loginActivity extends Activity {

    private EditText logintext1;
    private EditText logintext2;

    private RadioButton loginr1;
    private RadioButton loginr2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("로그인창");

        logintext1 = (EditText) findViewById(R.id.logintext1); // 아이디
        logintext2 = (EditText) findViewById(R.id.logintext2); // 비밀번호

        loginr1 = (RadioButton) findViewById(R.id.loginr1); // 고객
        loginr2 = (RadioButton) findViewById(R.id.loginr2); // 관리자

        Button btn1 = (Button) findViewById(R.id.loginb1); // 로그인
        Button btn2 = (Button) findViewById(R.id.loginb2); // 회원가입

        btn2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) { //인텐트 화면넘어가기.
                Intent intent = new Intent(loginActivity.this, MemberActivity.class);
                startActivity(intent);
            }
        });

    }

        /*
        public void login(View v){


            String id = logintext1.getText().toString();
            String passward = logintext2.getText().toString();

         }
         */

    public void login(View v) {

        if (logintext1.getText().toString().equals("sanghoon23") && logintext2.getText().toString().equals("1234")) {

            Intent intent = new Intent(loginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "맞지않습니다.", Toast.LENGTH_LONG).show();
        }

    }
}

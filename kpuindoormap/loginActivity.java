package com.kpu.kpuindoormap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.AsyncTask;
import android.os.Handler;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;


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

        logintext1 = (EditText)findViewById(R.id.logintext1); // 아이디
        logintext2 = (EditText)findViewById(R.id.logintext2); // 비밀번호

        loginr1 = (RadioButton)findViewById(R.id.loginr1); // 고객
        loginr2 = (RadioButton)findViewById(R.id.loginr2); // 관리자

        Button btn1 = (Button)findViewById(R.id.loginb1); // 로그인
        Button btn2 = (Button)findViewById(R.id.loginb2); // 회원가입


        /*
        btn1.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){ //인텐트 화면넘어가기.
                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        */

        btn2.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){ //인텐트 화면넘어가기.
                Intent intent = new Intent(loginActivity.this, MemberActivity.class);
                startActivity(intent);
            }
        });

    }

    // 로그인 버튼 Onclick.
        public void login(View v){

            String id = logintext1.getText().toString();
            String passward = logintext2.getText().toString();

            loginToDatabase(id, passward); // 데이터베이스 입력.

            Intent intent = new Intent(loginActivity.this, MainActivity.class);
            startActivity(intent);

        }
    private void loginToDatabase(String id, String passward){

        //스레드로 데이터베이스에서 받아옴. AsyncTask.
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            // 로그인 버튼 누르고 잠시 기다릴 때 출력되는 다이얼 로그.

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(loginActivity.this, "Please Wait", null, true, true);
            }

            @Override
            // post 방식으로 웹서포로 주기.
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String id = (String)params[0]; // name
                    String passward = (String)params[1]; // id

                    String link="http://119.207.205.72/login.php";
                    String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(passward, "UTF-8");

                    // 로그인 시 필요한 아이디와 패스워드를 인코딩해서 한글로 가져온다.

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(id, passward);
    }


}

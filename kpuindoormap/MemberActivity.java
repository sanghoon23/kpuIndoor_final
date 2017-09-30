package com.kpu.kpuindoormap;

import android.graphics.Color;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.text.Editable;
import android.text.TextWatcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Member;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MemberActivity extends Activity{

   private EditText membertext1, membertext2, membertext3, membertext4;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        membertext1 = (EditText)findViewById(R.id.membertext1); // 이름
        membertext2 = (EditText)findViewById(R.id.membertext2); // 아이디
        membertext3 = (EditText)findViewById(R.id.membertext3); // 비밀번호
        membertext4 = (EditText)findViewById(R.id.membertext4); // 비밀번호 확인

        Button memberb1 = (Button)findViewById(R.id.memberb1); // 중복확인
        Button memberb2 = (Button)findViewById(R.id.memberb2); // 회원가입
        Button memberb3 = (Button)findViewById(R.id.memberb3); // 취소


        memberb3.setOnClickListener(new Button.OnClickListener(){  //취소
            public void onClick(View v){

                finish();
            }
        });

    }

    //insert = 회원가입 버튼.
    public void insert(View view){
        String name = membertext1.getText().toString();
        String id = membertext2.getText().toString();
        String passward = membertext3.getText().toString();
        String pass_chk = membertext4.getText().toString();

        // 이름 입력 확인 -- requestFocus 레아이웃에 삽입.
        if( name.length() == 0 ) {
            Toast.makeText(MemberActivity.this, "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
            membertext1.requestFocus();
            return;
        }

        // 아이디 입력 확인
        if(id.length() == 0 ) {
            Toast.makeText(MemberActivity.this, "아이디를 입력하세요!", Toast.LENGTH_SHORT).show();
            membertext2.requestFocus();
            return;
        }

        // 비밀번호 입력 확인
        if( passward.length() == 0 ) {
            Toast.makeText(MemberActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
            membertext3.requestFocus();
            return;
        }

        // 비밀번호 일치 확인
        if( !passward.equals(pass_chk) ) {
            Toast.makeText(MemberActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
            membertext3.setText("");
            membertext4.setText("");
            membertext3.requestFocus();
            return;
        }

        insertToDatabase(name,id, passward); // 데이터베이스 입력.

        finish(); // mysql에 db정보 집어넣고 로그인 화면 출력.

    }

    // 회원 가입시 정보를 집어넣는다.
    private void insertToDatabase(String name, String id, String passward){

        //스레드로 데이터베이스에서 받아옴. AsyncTask.
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            // 로그인 버튼 누르고 잠시 기다릴 때 출력되는 다이얼 로그.

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MemberActivity.this, "Please Wait", null, true, true);
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
                    String name = (String)params[0]; // name
                    String id = (String)params[1]; // id
                    String passward = (String)params[2]; // passward

                    String link="http://119.207.205.72/insert.php";
                    String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                    data += "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += "&" + URLEncoder.encode("passward", "UTF-8") + "=" + URLEncoder.encode(passward, "UTF-8");
                    // 로그인 시 필요한 아이디와 패스워드를 인코딩해서 한글로 가져온다.

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
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
        task.execute(name,id, passward);
    }

}


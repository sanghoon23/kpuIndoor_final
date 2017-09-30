package com.kpu.kpuindoormap;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class PopupActivity2 extends Activity {

    final Handler handler = new Handler();
    String imgUrl = "http://119.207.205.72/photo/number1.jpg"; // 서버 PATH
    ImageView image_2;
    Button close2;
    Bitmap bmImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup2);

        image_2 = (ImageView) findViewById(R.id.image_2);
        image_2.setImageResource(R.drawable.chikentolk);
        close2 = (Button) findViewById(R.id.close2);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    // 걍 외우는게 좋다 -_-;
                    URL url = new URL(imgUrl);
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {

                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            image_2.setImageBitmap(bm);
                        }
                    });

                    image_2.setImageBitmap(bm); //비트맵 객체로 보여주기
                } catch(Exception e){
                    Log.e("이미지 에러","e.Message");

               }

            }

            });
        t.start();
             // 스레드를 이용해서 서버를 통해 이미지를 보내는 방법.
        }


    public void Popup2Clicked (View v) {

        finish();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_popup2, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

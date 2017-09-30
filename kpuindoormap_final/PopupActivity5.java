package com.kpu.kpuindoormap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PopupActivity5 extends Activity {

    String imgUrl = "http://119.207.205.72/photo/login.png";
    ImageView image_5;
    Button close5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Handler handler = new Handler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup5);

        image_5 = (ImageView) findViewById(R.id.image_5);
        image_5.setImageResource(R.drawable.bakery);
        close5 = (Button) findViewById(R.id.close3);

        //image_3.setImageResource(R.drawable.dayday);  //데이데이 이미지 띄우기.

        /* Thread t = new Thread(new Runnable() {
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
                            image_3.setImageBitmap(bm);
                        }
                    });

                    image_3.setImageBitmap(bm); //비트맵 객체로 보여주기
                } catch(Exception e){
                    Log.e("이미지 에러","e.Message");

                }

            }

        });
        t.start();
        */ //스레드를 이용해서 서버를 통해 이미지를 보내는 방법.
    }

    public void Popup5Clicked(View v) {

        finish();

    }
}

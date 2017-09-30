package com.kpu.kpuindoormap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminActivity extends Activity {

    private static int PICK_IMAGE_REQUEST = 1;
    ImageView imgView;
    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void change(View v){
        Intent intent = new Intent(AdminActivity.this, ImageActivity.class);
        startActivity(intent);

    }

}

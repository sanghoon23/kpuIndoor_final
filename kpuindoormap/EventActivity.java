package com.kpu.kpuindoormap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends Activity implements
        ViewSwitcher.ViewFactory{
    List<Integer> galleryIda = new ArrayList<Integer>();

    private ImageSwitcher mSwitcher;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        for(int i=1; i<6; i++){
            galleryIda.add(getResources().getIdentifier("e"+i, "drawable",
                    this.getPackageName()));
        }

        Gallery g = (Gallery) findViewById(R.id.gallery1);
        g.setAdapter(new galleryAdapter(this));
        g.setOnItemSelectedListener(new OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                mSwitcher.setImageResource(galleryIda.get(arg2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        mSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1); //수정
        mSwitcher.setFactory(this);
        mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left));
        mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right));

        mSwitcher.setImageResource(galleryIda.get(0));

    }

    @Override
    public View makeView() {
        ImageView iv1 = new ImageView(this);
        iv1.setBackgroundColor(0xFF000000);
        iv1.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv1.setLayoutParams(new ImageSwitcher.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        return iv1;
    }

    public class galleryAdapter extends BaseAdapter {

        private final Context mContext;
        LayoutInflater inflater;

        public galleryAdapter(Context c) {
            mContext = c;
            inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount(){
            return galleryIda.size();
        }

        public Object getItem(int position){
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.gallery_item, parent,
                        false);
            }
            ImageView imageView = (ImageView) convertView
                    .findViewById(R.id.imageView1);
            imageView.setImageResource(galleryIda.get(position));
            return convertView;
        }
    }
}
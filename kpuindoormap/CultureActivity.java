package com.kpu.kpuindoormap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
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

import static com.kpu.kpuindoormap.R.drawable.event;



public class CultureActivity extends Activity implements
        ViewSwitcher.ViewFactory {

    List<Integer> galleryIda = new ArrayList<Integer>();

    private ImageSwitcher mSwitcher;

    Activity act = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture);

        findViewById(R.id.button1).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(act, "전화로 연결합니다",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri
                                .parse("tel:01055074647"));
                        act.startActivity(intent);
                    }
                });

        findViewById(R.id.button2).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(act, "문자로 연결합니다",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri
                                .parse("smsto:01055074647"));
                        intent.putExtra("sms_body", "The SMS text");
                        act.startActivity(intent);
                    }
                });
        findViewById(R.id.button3).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(act.getBaseContext(),
                                "인터넷으로 연결합니다.", Toast.LENGTH_LONG)
                                .show();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kpu.ac.kr"));
                        act.startActivity(intent);
                    }
                });

        findViewById(R.id.button4).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(act, "일정을 등록합니다.",
                                Toast.LENGTH_LONG).show();
                        Intent calendarintent = new Intent(Intent.ACTION_EDIT);
                        calendarintent.setType("vnd.android.cursor.item/event");


                        act.startActivity(calendarintent);
                    }
                });

        for (int i = 1; i < 5; i++) {
            galleryIda.add(getResources().getIdentifier("ct" + i, "drawable",
                    this.getPackageName()));
        }

        Gallery g = (Gallery) findViewById(R.id.gallery2);
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

        mSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher2); //수정
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

        public int getCount() {
            return galleryIda.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
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


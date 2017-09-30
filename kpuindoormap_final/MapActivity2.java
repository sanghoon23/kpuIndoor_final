package com.kpu.kpuindoormap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kpu.kpuindoormap.common.DialogHelper;

public class MapActivity2 extends Activity
{
    private MapView mMapView;
    private TextView mTvDest;
    private Button mBtnDest;

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        mIntent = getIntent();  // 인텐트 값을 받는다.

        mMapView = (MapView) findViewById(R.id.mv_map2);
        mMapView.setPanEnabled(true);
        mMapView.setZoomEnabled(true);
        mMapView.setImageAsset("map_b1.png"); //지도 화면 보여주기.

        mTvDest = (TextView) findViewById(R.id.tv_destName2);

        int poiId;
        String poiName;
        float x;
        float y;
        String poiDest;

        if(mIntent != null)  // 널이 아니라면 최적의 경로를 X,Y 값으로 안내해줌.
        {
            poiId = mIntent.getIntExtra("POI_ID2", 0);
            poiName = mIntent.getStringExtra("POI_NAME2");
            x = mIntent.getFloatExtra("X2", 0);
            y = mIntent.getFloatExtra("Y2", 0);
            poiDest = mIntent.getStringExtra("POI_DESC2");

            // X 좌표가 460일 때 다이얼로그 보여주기. 2층으로
			 if(x == 510){

                 DialogHelper.showDialog(this, "알림", "1층 계단으로 길안내 시작합니다.\n" +
                         "층 이동 후 1층을 선택하세요!");

			}


            mTvDest.setText(poiName);
            mMapView.setMarker(new PointF(x, y));
        }

        mBtnDest = (Button) findViewById(R.id.btn_setDest2);
        mBtnDest.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });

    }

}

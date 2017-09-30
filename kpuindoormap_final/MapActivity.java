package com.kpu.kpuindoormap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MapActivity extends Activity
{
	private MapView mMapView;
	private TextView mTvDest;
	private Button mBtnDest;
	
	private Intent mIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		mIntent = getIntent();  // 인텐트 값을 받는다.
		
		mMapView = (MapView) findViewById(R.id.mv_map);
		mMapView.setPanEnabled(true);
		mMapView.setZoomEnabled(true);
		mMapView.setImageAsset("map.png"); //지도 화면 보여주기.
		
		mTvDest = (TextView) findViewById(R.id.tv_destName);
		
		int poiId;
		String poiName;
		float x;
		float y;
		String poiDest;
		
		if(mIntent != null)  // 널이 아니라면 최적의 경로를 X,Y 값으로 안내해줌.
		{
			poiId = mIntent.getIntExtra("POI_ID", 0);
			poiName = mIntent.getStringExtra("POI_NAME");
			x = mIntent.getFloatExtra("X", 0);
			y = mIntent.getFloatExtra("Y", 0);
			poiDest = mIntent.getStringExtra("POI_DESC");
			
			mTvDest.setText(poiName);
			mMapView.setMarker(new PointF(x, y));
		}
		
		mBtnDest = (Button) findViewById(R.id.btn_setDest);
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

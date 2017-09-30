package com.kpu.kpuindoormap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kpu.kpuindoormap.common.DialogHelper;

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
		
		if(mIntent != null)  // PoiListAdapter 에서 받은 putExtra 값을 get 함.
		{
			poiId = mIntent.getIntExtra("POI_ID", 0);
			poiName = mIntent.getStringExtra("POI_NAME");
			x = mIntent.getFloatExtra("X", 0);
			y = mIntent.getFloatExtra("Y", 0);
			poiDest = mIntent.getStringExtra("POI_DESC");

			// X 좌표가 460일 때 다이얼로그 보여주기. 2층으로
			if(x == 460){

				DialogHelper.showDialog(this, "알림", "2층 길안내 시작합니다.");

			}
			
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

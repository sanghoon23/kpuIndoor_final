package com.kpu.kpuindoormap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kpu.kpuindoormap.common.DBHelper;
import com.kpu.kpuindoormap.common.DialogHelper;
import com.kpu.kpuindoormap.common.GlobalMethod;
import com.kpu.kpuindoormap.dbitems.BEACON_INFO;
import com.kpu.kpuindoormap.dbitems.LINK;
import com.kpu.kpuindoormap.dbitems.NODE;
import com.kpu.kpuindoormap.interfaces.OnBeaconCallBackListener;
import com.kpu.kpuindoormap.items.Beacon;
import com.kpu.kpuindoormap.items.PFResult;
import com.kpu.kpuindoormap.sdks.BeaconReceiver;
import com.kpu.kpuindoormap.sdks.MapMatcher;
import com.kpu.kpuindoormap.sdks.PathFinder;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

public class NaviActivity extends Activity implements OnBeaconCallBackListener
{
    private MapView mMapView;
    private BeaconReceiver mReceiver;
    private EditText mEtSearch;
    private PointF mCurrentLocation = null;
    private LinearLayout mLayBottom;
    private TextView mTvDest;
    private Button mBtnCancel;

    private boolean check1 = true;
    private boolean check2 = true;
    private boolean check3 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);

        SQLiteDatabase.loadLibs(this);
        GlobalMethod.copyFile(this, GlobalMethod.DB_PATH, GlobalMethod.DB_NAME);

        mMapView = (MapView) findViewById(R.id.mv_map);
        mMapView.setPanEnabled(true);
        mMapView.setZoomEnabled(true);
        mMapView.setImageAsset("map.png");

        mReceiver = new BeaconReceiver(this);

        mLayBottom = (LinearLayout) findViewById(R.id.lay_bottom);
        mLayBottom.setVisibility(View.INVISIBLE);

        mTvDest = (TextView) findViewById(R.id.tv_rComment);
        mBtnCancel = (Button) findViewById(R.id.btn_rCancel);
        mBtnCancel.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                cancelPath();
            }
        });

        mEtSearch = (EditText) findViewById(R.id.et_search);
        mEtSearch.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivityForResult(new Intent(NaviActivity.this, SearchActivity.class), 0x00);
                }

                return false;
            }
        });

        // 비콘 존 아닐 경우 대비하여 초기 좌표 하드코딩
        // mCurrentLocation = new PointF(200, 300);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (!mReceiver.isRunning())
            mReceiver.startScan();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mReceiver.stopScan();
    }

    @Override
    public void onBeaconCallBack(ArrayList<Beacon> beacons)
    // 비콘을 통해서 실시간으로 계속 현재 위치 값을 받아옴.
    {
        if (beacons.size() > 0) // 만약 비콘 값 (RSSI) 반경에 있을 때
        {
            // Log.d("FIRST_BEACON_DISTANCE", beacons.get(0).getDistance() +
            // "");
            if (beacons.size() > 1) // 만약 비콘 값 (RSSI) 반경에서 멀어질 때
            {
                BEACON_INFO selectedBeacon = DBHelper.getBeacon(beacons.get(0).getMajor(), beacons.get(0).getMinor());
                BEACON_INFO secondBeacon = DBHelper.getBeacon(beacons.get(1).getMajor(), beacons.get(1).getMinor());

                if (selectedBeacon != null && secondBeacon != null)
                {
                    float errRate1 = (1 / beacons.get(0).getDistance()) / (1 / beacons.get(0).getDistance() + 1 / beacons.get(1).getDistance());
                    float errRate2 = (1 / beacons.get(1).getDistance()) / (1 / beacons.get(0).getDistance() + 1 / beacons.get(1).getDistance());

                    PointF point = new PointF((int) (selectedBeacon.getX() * errRate1 + secondBeacon.getX() * errRate2), (int) (selectedBeacon.getY() * errRate1 + secondBeacon.getY() * errRate2));
                    mCurrentLocation = point;
                    mMapView.setMarker(point); // 마커 설정
                }

            }

            else if (beacons.size() > 0) // 만약 비콘 값 (RSSI) 반경에 있을 때
            {
                BEACON_INFO beaconInfo = DBHelper.getBeacon(beacons.get(0).getMajor(), beacons.get(0).getMinor());

                if (beaconInfo != null && beacons.get(0).getDistance() < 1.5f)
                {
                    PointF point = new PointF(beaconInfo.getX(), beaconInfo.getY());
                    mCurrentLocation = point;
                    mMapView.setMarker(point); // 마커 설정
                }
            }
        }

        if(beacons.size() > 0) // 만약 비콘 값 (RSSI) 반경에 있을 때
        {
            if(beacons.get(0) != null) {
                BEACON_INFO beaconInfo = DBHelper.getBeacon(beacons.get(0).getMajor(), beacons.get(0).getMinor());
                if(beaconInfo != null) {
                    //서점 비콘 반경에 위치할 때
                    if (beaconInfo.getMajor() == 19523 && beaconInfo.getMinor() == 16707 && check1 && beacons.get(0).getDistance() < 2f) {
                        check1 = false;
                        Intent intent = new Intent(NaviActivity.this, PopupActivity.class);
                        startActivity(intent);

                    }


                    //치킨톡
                    else if (beaconInfo.getMajor() == 19523 && beaconInfo.getMinor() == 16712 && check2 && beacons.get(0).getDistance() < 2f) {
                        check2 = false;
                        Intent intent = new Intent(NaviActivity.this, PopupActivity2.class);
                        startActivity(intent);
                    }
                    // 베이커리
					else if (beaconInfo.getMajor() == 19523 && beaconInfo.getMinor() == 16706 && check3 && beacons.get(0).getDistance() < 2f) {
						check3 = false;
						Intent intent = new Intent(NaviActivity.this, PopupActivity3.class);
						startActivity(intent);
					}
                }
            }
        }


    }

    // 현재 위치 값을 산출하여 출력.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x00)
        {
            if (resultCode == RESULT_OK)
            {
                if (data != null)
                {
                    int poiId = data.getIntExtra("POI_ID", 0);
                    String poiName = data.getStringExtra("POI_NAME");

                    float x = data.getFloatExtra("X", 0);
                    float y = data.getFloatExtra("Y", 0);
                    String poiDest = data.getStringExtra("POI_DESC");

                    if (mCurrentLocation == null)
                    {
                        DialogHelper.showDialog(this, "알림", "현위치를 수신하지 못하여 탐색이 불가능합니다.");
                    }

                    else if (MapMatcher.getDistance(mCurrentLocation.x, mCurrentLocation.y, x, y) > 20)
                    {
                        PFResult pfResult = PathFinder.find(mCurrentLocation.x, mCurrentLocation.y, x, y);
                        Log.d("PathFinder", pfResult.toArray().size() + "");

                        ArrayList<PointF> lineArr = new ArrayList<PointF>();

                        if (pfResult != null && pfResult.getDistance() > 0)
                        {
                            for (LINK linkItem : pfResult.toArray())
                            {
                                NODE nodeItem = DBHelper.getNode(linkItem.getStartNode());
                                if (nodeItem != null)
                                {
                                    lineArr.add(new PointF(nodeItem.getX(), nodeItem.getY()));
                                }
                            }

                            NODE lastNode = DBHelper.getNode(pfResult.toArray().get(pfResult.toArray().size() - 1).getEndNode());

                            if (lastNode != null)
                                lineArr.add(new PointF(lastNode.getX(), lastNode.getY()));

                            if (lineArr.size() > 0)
                            {
                                mMapView.setPath(lineArr);
                                mTvDest.setText("현위치 - > " + poiName);
                                mLayBottom.setVisibility(View.VISIBLE);
                            }
                        }

                        else
                        {
                            // RP 실패
                            DialogHelper.showDialog(this, "알림", "경로탐색에 실패하였습니다.");
                        }
                    }

                    else
                    {
                        // 메시지 팝업 common -> DialogHelper
                        DialogHelper.showDialog(this, "알림", "가까운 거리는 경로탐색이 불가능합니다.");
                    }

                }
            }

        }
    }

    private void cancelPath()
    {
        mMapView.removePath();
        mLayBottom.setVisibility(View.INVISIBLE);
    }

}

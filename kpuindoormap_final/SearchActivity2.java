package com.kpu.kpuindoormap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.kpu.kpuindoormap.adapter.PoiListAdapter2;
import com.kpu.kpuindoormap.common.DBHelper;
import com.kpu.kpuindoormap.dbitems.POI2;
import com.kpu.kpuindoormap.interfaces.OnPoiSelectedListener2;

import java.util.ArrayList;

public class SearchActivity2 extends Activity implements OnPoiSelectedListener2
{
    private EditText mEtSearch;
    private Button mBtnSearch;
    private ListView mLvSearch;
    private PoiListAdapter2 mAdapter;
    private ArrayList<POI2> mArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        mEtSearch = (EditText) findViewById(R.id.et_search2);
        mBtnSearch = (Button) findViewById(R.id.btn_search2);
        mLvSearch = (ListView) findViewById(R.id.lv_searchResult2);
        mArray = new ArrayList<POI2>();
        mAdapter = new PoiListAdapter2(this, R.layout.item_poi2, mArray);
        mLvSearch.setAdapter(mAdapter);
        mBtnSearch.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mEtSearch != null)
                {
                    updateList2(mEtSearch.getText().toString());
                }
            }
        });

        updateList2("");

    }

    private void updateList2(String filter)
    {
        ArrayList<POI2> items = new ArrayList<POI2>();

        if (!mEtSearch.getText().toString().equals(""))
        {
            for (POI2 item : DBHelper.getPoi2())
            {
                if (item.getPoiName2().contains(filter))
                    items.add(item);
            }
        }

        else
        {
            items = DBHelper.getPoi2();
        }

        mArray.clear();
        mArray.addAll(items);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPoiSelected2(ArrayList<POI2> items)
    {
        if (items.size() > 0)
        {
            Intent dataIntent = new Intent();
            dataIntent.putExtra("POI_ID2", items.get(0).getPoiId2());
            dataIntent.putExtra("POI_NAME2", items.get(0).getPoiName2());
            dataIntent.putExtra("X2", items.get(0).getX2());
            dataIntent.putExtra("Y2", items.get(0).getY2());
            dataIntent.putExtra("POI_DESC2", items.get(0).getPoiDesc2());
            setResult(RESULT_OK, dataIntent);
            finish();
        }
    }

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
                    setResult(RESULT_OK, data);
                    finish();
                }
            }

        }
    }

}

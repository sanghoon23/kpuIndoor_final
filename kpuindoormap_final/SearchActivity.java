package com.kpu.kpuindoormap;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.kpu.kpuindoormap.adapter.PoiListAdapter;
import com.kpu.kpuindoormap.common.DBHelper;
import com.kpu.kpuindoormap.dbitems.POI;
import com.kpu.kpuindoormap.interfaces.OnPoiSelectedListener;

// 목적지 설정의 목적지들을 나열해줌.

public class SearchActivity extends Activity implements OnPoiSelectedListener
{
	private EditText mEtSearch;
	private Button mBtnSearch;
	private ListView mLvSearch;
	private PoiListAdapter mAdapter;
	private ArrayList<POI> mArray;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		mEtSearch = (EditText) findViewById(R.id.et_search);
		mBtnSearch = (Button) findViewById(R.id.btn_search);
		mLvSearch = (ListView) findViewById(R.id.lv_searchResult);
		mArray = new ArrayList<POI>();
		mAdapter = new PoiListAdapter(this, R.layout.item_poi, mArray);
		mLvSearch.setAdapter(mAdapter); // 어댑터 정보 가져옴.
		mBtnSearch.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mEtSearch != null)
				{
					updateList(mEtSearch.getText().toString());
				}
			}
		});

		updateList("");

	}

	private void updateList(String filter)
	{
		ArrayList<POI> items = new ArrayList<POI>();

		if (!mEtSearch.getText().toString().equals(""))
		{
			for (POI item : DBHelper.getPoi())
			{
				if (item.getPoiName().contains(filter))
					items.add(item);
			}
		}

		else
		{
			items = DBHelper.getPoi();
		}

		mArray.clear();
		mArray.addAll(items);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onPoiSelected(ArrayList<POI> items)
	{
		if (items.size() > 0)
		{
			Intent dataIntent = new Intent();
			dataIntent.putExtra("POI_ID", items.get(0).getPoiId());
			dataIntent.putExtra("POI_NAME", items.get(0).getPoiName());
			dataIntent.putExtra("X", items.get(0).getX());
			dataIntent.putExtra("Y", items.get(0).getY());
			dataIntent.putExtra("POI_DESC", items.get(0).getPoiDesc());
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

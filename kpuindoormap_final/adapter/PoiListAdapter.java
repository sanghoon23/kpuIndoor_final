package com.kpu.kpuindoormap.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.kpu.kpuindoormap.MapActivity;
import com.kpu.kpuindoormap.R;
import com.kpu.kpuindoormap.dbitems.POI;
import com.kpu.kpuindoormap.interfaces.OnPoiSelectedListener;

public class PoiListAdapter extends ArrayAdapter<POI>
{
	private ArrayList<POI> items;
	private int poiItemLayout;
	private Context ctx;
	private Activity activity;
	private OnPoiSelectedListener listener;

	public PoiListAdapter(Activity activity, int textViewResourceId, ArrayList<POI> items)
	{
		super(activity.getApplicationContext(), textViewResourceId, items);
		this.items = items;
		this.poiItemLayout = textViewResourceId;
		ctx = activity.getApplicationContext();
		this.activity = activity;
		listener = (OnPoiSelectedListener) activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;

		if (v == null)
		{
			LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(poiItemLayout, null);
		}

		if (items.size() >= position)
		{
			final POI poiItem = items.get(position);
			if (poiItem != null)
			{
				((TextView) v.findViewById(R.id.tv_poiDesc)).setText(poiItem.getPoiDesc());
				((TextView) v.findViewById(R.id.tv_poiName)).setText(poiItem.getPoiName());

				((Button) v.findViewById(R.id.btn_viewToMap)).setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent dataIntent = new Intent(activity, MapActivity.class);
						dataIntent.putExtra("POI_ID", poiItem.getPoiId());
						dataIntent.putExtra("POI_NAME", poiItem.getPoiName());
						dataIntent.putExtra("X", poiItem.getX());
						dataIntent.putExtra("Y", poiItem.getY());
						dataIntent.putExtra("POI_DESC", poiItem.getPoiDesc());
						activity.startActivityForResult(dataIntent, 0x00);
					}
				});
				
				v.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						ArrayList<POI> arr = new ArrayList<POI>();
						arr.add(poiItem);
						listener.onPoiSelected(arr);
					}
				});
			}
		}
		return v;
	}

}

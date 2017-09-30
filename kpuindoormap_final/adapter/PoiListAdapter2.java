package com.kpu.kpuindoormap.adapter;

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

import com.kpu.kpuindoormap.MapActivity2;
import com.kpu.kpuindoormap.R;
import com.kpu.kpuindoormap.dbitems.POI2;
import com.kpu.kpuindoormap.interfaces.OnPoiSelectedListener2;

import java.util.ArrayList;

public class PoiListAdapter2 extends ArrayAdapter<POI2>
{
    private ArrayList<POI2> items;
    private int poiItemLayout;
    private Context ctx;
    private Activity activity;
    private OnPoiSelectedListener2 listener;

    public PoiListAdapter2(Activity activity, int textViewResourceId, ArrayList<POI2> items)
    {
        super(activity.getApplicationContext(), textViewResourceId, items);
        this.items = items;
        this.poiItemLayout = textViewResourceId;
        ctx = activity.getApplicationContext();
        this.activity = activity;
        listener = (OnPoiSelectedListener2) activity;
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
            final POI2 poiItem2 = items.get(position);
            if (poiItem2 != null)
            {
                ((TextView) v.findViewById(R.id.tv_poiDesc2)).setText(poiItem2.getPoiDesc2());
                ((TextView) v.findViewById(R.id.tv_poiName2)).setText(poiItem2.getPoiName2());

                ((Button) v.findViewById(R.id.btn_viewToMap2)).setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent dataIntent = new Intent(activity, MapActivity2.class);
                        dataIntent.putExtra("POI_ID2", poiItem2.getPoiId2());
                        dataIntent.putExtra("POI_NAME2", poiItem2.getPoiName2());
                        dataIntent.putExtra("X2", poiItem2.getX2());
                        dataIntent.putExtra("Y2", poiItem2.getY2());
                        dataIntent.putExtra("POI_DESC2", poiItem2.getPoiDesc2());
                        activity.startActivityForResult(dataIntent, 0x00);
                    }
                });

                v.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        ArrayList<POI2> arr = new ArrayList<POI2>();
                        arr.add(poiItem2);
                        listener.onPoiSelected2(arr);
                    }
                });
            }
        }
        return v;
    }

}

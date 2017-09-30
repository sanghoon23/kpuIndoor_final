package com.kpu.kpuindoormap.items;

import com.kpu.kpuindoormap.dbitems.LINK;

import java.util.ArrayList;

public class PFResult
	{
		private ArrayList<LINK> mResultArr;
		private float mDistance;

	public PFResult(ArrayList<LINK> resultArr)
	{
		mResultArr = resultArr;
		mDistance = resultArr.size();
	}

	public String toString()
	{
		return null;
	}

	public float getDistance()
	{
		return mDistance;
	}

	public ArrayList<LINK> toArray()
	{
		return mResultArr;
	}
}

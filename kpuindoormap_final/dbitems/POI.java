package com.kpu.kpuindoormap.dbitems;

public class POI
{
	private int mPoiId;
	private String mPoiName;
	private float mX;
	private float mY;
	private String mPoiDesc;
	
	public POI(int poiId, String poiName, float x, float y, String poiDesc)
	{
		mPoiId = poiId; // 목적지 id
		mPoiName = poiName; //  목적지 이름
		mX = x;
		mY = y;
		mPoiDesc = poiDesc; // 설명.
	}
	
	public int getPoiId()
	{
		return mPoiId;
	}
	
	public String getPoiName()
	{
		return mPoiName;
	}
	
	public float getX()
	{
		return mX;
	}
	
	public float getY()
	{
		return mY;
	}
	
	public String getPoiDesc()
	{
		return mPoiDesc;
	}
}

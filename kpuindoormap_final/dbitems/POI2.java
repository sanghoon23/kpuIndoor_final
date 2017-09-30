package com.kpu.kpuindoormap.dbitems;

public class POI2
{
    private int mPoiId2;
    private String mPoiName2;
    private float mX2;
    private float mY2;
    private String mPoiDesc2;

    public POI2(int poiId2, String poiName2, float x2, float y2, String poiDesc2)
    {
        mPoiId2 = poiId2; // 목적지 id
        mPoiName2 = poiName2; //  목적지 이름
        mX2 = x2;
        mY2 = y2;
        mPoiDesc2 = poiDesc2; // 설명.
    }

    public int getPoiId2()
    {
        return mPoiId2;
    }

    public String getPoiName2()
    {
        return mPoiName2;
    }

    public float getX2()
    {
        return mX2;
    }

    public float getY2()
    {
        return mY2;
    }

    public String getPoiDesc2()
    {
        return mPoiDesc2;
    }
}

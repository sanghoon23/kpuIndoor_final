package com.kpu.kpuindoormap.dbitems;

public class BEACON_INFO
{
	private int mMajor;
	private int mMinor;
	private int mFloor;
	private int mX;
	private int mY;
	private int mType;
	
	public BEACON_INFO(int major, int minor, int x, int y, int floor, int type)
	{
		mMajor = major;
		mMinor = minor;
		mFloor = floor;
		mX = x;
		mY = y;
		mType = type;
	}
	
	public int getMajor()
	{
		return mMajor;
	}
	
	public int getMinor()
	{
		return mMinor;
	}
	
	public int getFloor()
	{
		return mFloor;
	}
	
	public int getX()
	{
		return mX;
	}
	
	public int getY()
	{
		return mY;
	}
	
	public int getType()
	{
		return mType;
	}
	
	public void setMajor(int major)
	{
		mMajor = major;
	}
	
	public void setMinor(int minor)
	{
		mMinor = minor;
	}
	
	public void setFloor(int floor)
	{
		mFloor = floor;
	}
	
	public void setX(int x)
	{
		mX = x;
	}
	
	public void setY(int y)
	{
		mY = y;
	}
	
	public void setType(int type)
	{
		mType = type;
	}
}

package com.kpu.kpuindoormap.items;

// 비콘 자체의 DB 정보.
public class Beacon
{
	private float mDistance = 0.0f;
	String mUUID, mName, mAddress;
	int mMajor, mMinor, mMeasuredPower, mRssi;

	public Beacon(String proximityUUID, String name, String macAddress, int major, int minor, int measuredPower, int rssi, float dist)
	{
		mUUID = proximityUUID; // UUID
		mName = name; // 이름
		mAddress = macAddress; // 맥어드레스
		mMajor = major; // beacon의 major 값
		mMinor = minor; // beacon의 minor 값
		mMeasuredPower = measuredPower;
		mRssi = rssi;
		mDistance = dist; // 거리
	}

	public float getDistance()
	{
		return mDistance;
	}

	public String getMacAddress()
	{
		return mAddress;
	}

	public String getProximityUUID()
	{
		return mUUID;
	}

	public int getMajor()
	{
		return mMajor;
	}

	public int getMinor()
	{
		return mMinor;
	}

	public int getMeasuredPower()
	{
		return mMeasuredPower;
	}

	public int getRssi()
	{
		return mRssi;
	}

}

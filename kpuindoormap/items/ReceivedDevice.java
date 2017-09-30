package com.kpu.kpuindoormap.items;

import java.util.ArrayList;

import android.bluetooth.BluetoothDevice;

public class ReceivedDevice
{
	private BluetoothDevice mDevice;
	private String mSavedTime;
	private ArrayList<Integer> mRssi;
	private String mUUID;
	private int mMajor;
	private int mMinor;
	private float mDistance;
	private int mTxPower;

	public ReceivedDevice(BluetoothDevice device, int rssi, String savedTime, int major, int minor, String uuid, int txPower)
	{
		mDevice = device;
		mRssi = new ArrayList<Integer>();
		mSavedTime = savedTime;
		mMajor = major;
		mMinor = minor;
		mUUID = uuid;
		mDistance = 0.0f;
		mTxPower = txPower;
	}

	public Beacon getBeaconClass()
	{
		int rssi = getRssi();
		return new Beacon(mUUID, mDevice.getName(), mDevice.getAddress(), mMajor, mMinor, mTxPower, rssi, mDistance);
	}

	public BluetoothDevice getBtDevice()
	{
		return mDevice;
	}

	public String getSavedTime()
	{
		return mSavedTime;
	}

	public String getMacAddress()
	{
		return mDevice.getAddress();
	}

	public String getName()
	{
		return mDevice.getName();
	}

	public int getRssi()  //RSSI 값 ArrayList로 가져오기.
	{
		try
		{
			ArrayList<Integer> arrBuf = (ArrayList<Integer>) mRssi.clone();

			if (arrBuf.size() == 0)
			{
				mDistance = 0.0f;
				return 0;
			}

			int count = arrBuf.size();
			int divider = 0;
			int rssiSum = 0;
			int distSum = 0;

			for (int i = count - 1; i >= 0; i--)
			{
				int pointer = i + 1;
				divider += pointer;

				int curRssi = arrBuf.get(i);

				rssiSum += curRssi * pointer;
				distSum += getDistance(curRssi) * pointer;
			}

			rssiSum /= divider;
			distSum /= divider;

			mDistance = getDistance(rssiSum) * 0.5f + distSum * 0.5f;
			return (int) rssiSum;
		}

		catch (Exception e)
		{
			mDistance = 0.0f;
			return 0;
		}
	}

	public void setRssi(int rssi, String savedTime) //Rssi 저장
	{
		mRssi.add(rssi);

		int rssiSz = mRssi.size();
		mSavedTime = savedTime;

		if (rssiSz > 10)
		{
			for (int i = 0; i < rssiSz - 10; i++)
			{
				mRssi.remove(i);
			}
		}
	}

	private float getDistance(int rssi) //RSSI 활용 거리 구하기.
	{
		return (float) Math.pow(10d, ((double) mTxPower - rssi) / (10 * 2));
	}
	
}

package com.kpu.kpuindoormap.sdks;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.kpu.kpuindoormap.interfaces.OnBeaconCallBackListener;
import com.kpu.kpuindoormap.items.Beacon;
import com.kpu.kpuindoormap.items.ReceivedDevice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BeaconReceiver
{
	public final static int BEACON_FOUND = 0x01;

	public final static int BEACON_NOT_FOUND = 0x02;

	public final static int BEACON_COUNT = 0x03;

	private int BEACON_VALID_TIME = 2000;

	private int BEACON_CALC_TIME = 1000;

	private HashMap<String, ReceivedDevice> mBtArray;

	private BluetoothAdapter mBtAdapter;

	private BluetoothAdapter.LeScanCallback mScanCallBack;

	private boolean isLoop = false;

	private OnBeaconCallBackListener mListener;

	private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case BeaconReceiver.BEACON_FOUND:
				case BeaconReceiver.BEACON_NOT_FOUND:
					mListener.onBeaconCallBack((ArrayList<Beacon>) msg.obj);
					break;

				default:
					break;
			}
		}
	};

	public BeaconReceiver(OnBeaconCallBackListener listener)
	{
		mListener = listener;
		mBtArray = new HashMap<String, ReceivedDevice>();
		mScanCallBack = new BluetoothAdapter.LeScanCallback()
		{
			@Override
			public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord)
			{
				ReceivedDevice btWt;

				if (mBtArray.containsKey(device.getAddress()))
				{
					mBtArray.get(device.getAddress()).setRssi(rssi, getDate());
				}

				else
				{
					int startByte = 2;
					boolean patternFound = false;
					while (startByte <= 5)
					{
						if (((int) scanRecord[startByte + 2] & 0xff) == 0x02 && ((int) scanRecord[startByte + 3] & 0xff) == 0x15)
						{
							patternFound = true;
							break;
						}
						startByte++;
					}

					if (patternFound)
					{
						byte[] uuidBytes = new byte[16];
						System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
						String hexString = bytesToHex(uuidBytes);

						String uuid = hexString.substring(0, 8) + "-" + hexString.substring(8, 12) + "-" + hexString.substring(12, 16) + "-" + hexString.substring(16, 20) + "-"
								+ hexString.substring(20, 32);
						if (uuid.equals("24DDF411-8CF1-440C-87CD-E368DAF9C93E"))
						{
							int major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);

							int minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);

							int curRssi = rssi;

							btWt = new ReceivedDevice(device, curRssi, getDate(), major, minor, uuid, scanRecord[startByte + 24]);

							mBtArray.put(btWt.getMacAddress(), btWt);
						}
					}
				}
			}
		};

		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	public void startScan()
	{
		isLoop = true;
		mBtAdapter.startLeScan(mScanCallBack);
		new processThread().start();
	}

	public void stopScan()
	{
		isLoop = false;
		mBtAdapter.stopLeScan(mScanCallBack);
	}

	// 스레드를 이용해서 계속 받을 수 있게
	private class processThread extends Thread
	{
		@Override
		public void run()
		{
			super.run();

			while (isLoop)
			{
				arrange();

				HashMap<String, ReceivedDevice> buf = (HashMap) mBtArray.clone();
				ArrayList<Beacon> beacons = new ArrayList<Beacon>();
				if (buf.size() > 0)
				{
					for (String mac : buf.keySet())
					{
						beacons.add(buf.get(mac).getBeaconClass());
					}

					for(int i = beacons.size() - 1 ; i >= 0 ; i--)
					{
						if(beacons.get(i).getRssi() == 0)
							beacons.remove(i);
					}

					for (int i = 1; i < beacons.size(); i++)
					{
						for (int j = 0; j < beacons.size() - i; j++)
						{
							if (beacons.get(j).getDistance() > beacons.get(j + 1).getDistance())
							{
								Beacon temp = beacons.get(j + 1);
								beacons.set(j + 1, beacons.get(j));
								beacons.set(j, temp);
							}
						}
					}

					mHandler.sendMessage(mHandler.obtainMessage(BEACON_FOUND, beacons));

				} else
				{
					mHandler.sendMessage(mHandler.obtainMessage(BEACON_NOT_FOUND, beacons));
				}
				try
				{
					Thread.sleep(BEACON_CALC_TIME);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private void arrange()
	{
		String curTimeData[] = getDate().split(",");
		ArrayList<String> deleteArr = new ArrayList<String>();
		HashMap<String, ReceivedDevice> bufArr = (HashMap) mBtArray.clone();

		for (String macId : bufArr.keySet())
		{
			String timeData[] = bufArr.get(macId).getSavedTime().split(",");

			int convertedData[] = new int[4];

			convertedData[0] = Integer.parseInt(timeData[0]);
			convertedData[1] = Integer.parseInt(timeData[1]);
			convertedData[2] = Integer.parseInt(curTimeData[0]);
			convertedData[3] = Integer.parseInt(curTimeData[1]);

			if (convertedData[1] > convertedData[3] && convertedData[2] - convertedData[0] == 1)
			{
				if ((convertedData[3] + 60 - convertedData[1]) * 1000 > BEACON_VALID_TIME)
				{
					deleteArr.add(macId);
				}
			}

			else if (convertedData[1] < convertedData[3] && convertedData[2] == convertedData[0])
			{
				if ((convertedData[3] - convertedData[1]) * 1000 > BEACON_VALID_TIME)
				{
					deleteArr.add(macId);
				}
			}

			else if (convertedData[1] == convertedData[3] && convertedData[2] == convertedData[0])
			{

			}

			else
			{
				deleteArr.add(macId);
			}
		}

		for (String macStr : deleteArr)
		{
			mBtArray.remove(macStr);
		}
	}

	private String getDate()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm,ss", Locale.KOREA);
		Date currentTime = new Date();
		return simpleDateFormat.format(currentTime);
	}

	public void setBeaconValidTime(int ms)
	{
		BEACON_VALID_TIME = ms;
	}

	public void setBeaconUpdateTime(int ms)
	{
		BEACON_CALC_TIME = ms;
	}

	public boolean isRunning()
	{
		return isLoop;
	}

	static final char[] hexArray = "0123456789ABCDEF".toCharArray();

	private static String bytesToHex(byte[] bytes)
	{
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++)
		{
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}

		return new String(hexChars);
	}
}

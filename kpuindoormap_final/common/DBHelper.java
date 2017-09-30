package com.kpu.kpuindoormap.common;

import java.util.ArrayList;

import net.sqlcipher.Cursor;

import com.kpu.kpuindoormap.dbitems.BEACON_INFO;
import com.kpu.kpuindoormap.dbitems.LINK;
import com.kpu.kpuindoormap.dbitems.NODE;
import com.kpu.kpuindoormap.dbitems.POI;
import com.kpu.kpuindoormap.dbitems.POI2;
// SQLite 각 DB 정보들을 정의.
public class DBHelper
{
	private static ArrayList<BEACON_INFO> mBeacon;
	private static ArrayList<LINK> mLink;
	private static ArrayList<NODE> mNode;
	private static ArrayList<POI> mPoi;
	private static ArrayList<POI2> mPoi2;

	public static void loadFromDB()
	{
		if (mBeacon == null)
			mBeacon = new ArrayList<BEACON_INFO>();

		if (mLink == null)
			mLink = new ArrayList<LINK>();

		if (mNode == null)
			mNode = new ArrayList<NODE>();

		if (mPoi == null)
			mPoi = new ArrayList<POI>();

		if (mPoi2 == null)
			mPoi2 = new ArrayList<POI2>();

		if (mBeacon.size() == 0)
			loadBeacons();

		if (mLink.size() == 0)
			loadLinks();

		if (mNode.size() == 0)
			loadNodes();

		if (mPoi.size() == 0)
			loadPois();

		if (mPoi2.size() == 0)
			loadPois2();

	}
	// major, minor 값에 맞는 비콘 Get.
	public static BEACON_INFO getBeacon(int major, int minor)
	{
		if (mBeacon == null)
			mBeacon = new ArrayList<BEACON_INFO>();

		if (mBeacon.size() == 0)
		{
			loadBeacons();
		}

		for (BEACON_INFO item : mBeacon)
		{
			if (item.getMajor() == major && item.getMinor() == minor)
			{
				return item;
			}
		}

		return null;
	}

	// LINK Get.
	public static ArrayList<LINK> getLink()
	{
		if (mLink == null)
			mLink = new ArrayList<LINK>();

		if (mLink.size() == 0)
		{
			loadLinks();
		}

		return mLink;
	}

	// 목적지 노드 Get
	public static ArrayList<NODE> getNode()
	{
		if (mNode == null)
			mNode = new ArrayList<NODE>();

		if (mNode.size() == 0)
		{
			loadNodes();
		}

		return mNode;
	}

	// Node 정보를 Get
	public static NODE getNode(int nodeId)
	{
		if (mNode == null)
			mNode = new ArrayList<NODE>();

		if (mNode.size() == 0)
		{
			loadNodes();
		}

		for (NODE item : mNode)
		{
			if (item.getNodeId() == nodeId)
			{
				return item;
			}
		}

		return null;
	}

	// 스캔된 비콘 아이디 및 번호 Get
	public static ArrayList<POI> getPoi()
	{
		if (mPoi == null)
			mPoi = new ArrayList<POI>();

		if (mPoi.size() == 0)
		{
			loadPois();
		}

		return mPoi;
	}

	public static ArrayList<POI2> getPoi2() // 지하 1층 POI
	{
		if (mPoi2 == null)
			mPoi2 = new ArrayList<POI2>();

		if (mPoi2.size() == 0)
		{
			loadPois2();
		}

		return mPoi2;
	}

	//dbManager를 통해 쿼리문 수행하고 DB에 insert.
	//SQLite의 php 부분에 해당.
	private static void loadBeacons()
	{
		DBManager dbManager = new DBManager(GlobalMethod.DB_PATH, GlobalMethod.DB_NAME);

		Cursor csr = dbManager.selectQuery("SELECT MAJOR, MINOR, X, Y, FLOOR, TYPE FROM BEACON_INFO");

		while (csr.moveToNext())
		{
			mBeacon.add(new BEACON_INFO(csr.getInt(0), csr.getInt(1), csr.getInt(2), csr.getInt(3), csr.getInt(4), csr.getInt(5)));
		}

		csr.close();

		dbManager.closeDatabase();
	}

	private static void loadLinks()
	{
		DBManager dbManager = new DBManager(GlobalMethod.DB_PATH, GlobalMethod.DB_NAME);

		Cursor csr = dbManager.selectQuery("SELECT LINK_ID, ST_NODE_ID, ED_NODE_ID FROM LINK");

		while (csr.moveToNext())
		{
			mLink.add(new LINK(csr.getInt(0), csr.getInt(1), csr.getInt(2)));
		}

		csr.close();

		dbManager.closeDatabase();
	}

	private static void loadNodes()
	{
		DBManager dbManager = new DBManager(GlobalMethod.DB_PATH, GlobalMethod.DB_NAME);

		Cursor csr = dbManager.selectQuery("SELECT NODE_ID, X, Y FROM NODE");

		while (csr.moveToNext())
		{
			mNode.add(new NODE(csr.getInt(0), csr.getFloat(1), csr.getFloat(2)));
		}

		csr.close();

		dbManager.closeDatabase();
	}

	private static void loadPois()
	{
		DBManager dbManager = new DBManager(GlobalMethod.DB_PATH, GlobalMethod.DB_NAME);

		Cursor csr = dbManager.selectQuery("SELECT POI_ID, POI_NAME, X, Y, POI_DESC FROM POI");

		while (csr.moveToNext())
		{
			mPoi.add(new POI(csr.getInt(0), csr.getString(1), csr.getFloat(2), csr.getFloat(3), csr.getString(4)));
		}

		csr.close();

		dbManager.closeDatabase();
	}

	private static void loadPois2()
	{
		DBManager dbManager = new DBManager(GlobalMethod.DB_PATH, GlobalMethod.DB_NAME);

		Cursor csr = dbManager.selectQuery("SELECT POI_ID2, POI_NAME2, X2, Y2, POI_DESC2 FROM POI2");

		while (csr.moveToNext())
		{
			mPoi2.add(new POI2(csr.getInt(0), csr.getString(1), csr.getFloat(2), csr.getFloat(3), csr.getString(4)));
		}

		csr.close();

		dbManager.closeDatabase();
	}
}

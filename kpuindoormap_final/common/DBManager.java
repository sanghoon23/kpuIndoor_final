package com.kpu.kpuindoormap.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

// SQLite DB 의 경로, 생성, 삽입, 업데이트, 삭제
// sqlcipher 의 Cusor 활용.

public class DBManager
{
	private final String TAG = "DBManager";
	private String mDBPath;   // DB_PATH = "/data/data/com.kpu.kpuindoormap/databases/";
	private String mDBName;	// DB_NAME = "beaconitem.db";
	private SQLiteDatabase mDatabase = null;

	public DBManager(String dbPath, String dbName)
	{
		mDBPath = dbPath;
		mDBName = dbName;
		initDatabase();
	}

	public void initDatabase() //데이터 베이스에 값을 집어넣음.
	{
		if (mDatabase == null)
			makeDirectory(mDBPath);

		mDatabase = SQLiteDatabase.openOrCreateDatabase(mDBPath + mDBName, "", null);
	}

	public void closeDatabase()
	{
		if (mDatabase != null)
			if (mDatabase.isOpen())
			{
				mDatabase.close();
				mDatabase = null;
			}
	}

	public boolean deleteDatabase()
	{
		closeDatabase();

		if (isDBFileExist())
		{
			new File(mDBPath, mDBName).delete();
			return true;
		}

		return false;
	}

	public boolean isDBFileExist()
	{
		File dbFile = new File(mDBPath, mDBName);
		return dbFile.isFile();
	}

	/** 생성 QUERY */
	public boolean createQuery(String fullStatement)
	{
		return txQuery(fullStatement);
	}

	/** 삽입 QUERY */
	public boolean insertQuery(String fullStatement)
	{
		return txQuery(fullStatement);
	}

	/** 업데이트 QUERY */
	public boolean updateQuery(String fullStatement)
	{
		return txQuery(fullStatement);
	}

	/** 삭제 QUERY */
	public boolean deleteQuery(String fullStatement)
	{
		return txQuery(fullStatement);
	}

	public Cursor selectQuery(String fullStatement)
	{
		return trxQuery(fullStatement);
	}

	public ArrayList<HashMap> selectQuery(ArrayList<String> fieldArray, String tableName, String whereStatement)
	{

		ArrayList<HashMap> returnData = new ArrayList<HashMap>();

		try
		{
			String fullStatement = "SELECT ";

			for (int i = 0; i < fieldArray.size(); i++)
			{
				fullStatement = fullStatement + fieldArray.get(i) + ",";
			}

			fullStatement = fullStatement.substring(0, fullStatement.length() - 1);

			if (whereStatement.equals(""))
			{
				fullStatement = fullStatement + " FROM " + tableName;

			} else
			{
				fullStatement = fullStatement + " FROM " + tableName + " WHERE " + whereStatement;
			}

			// Util.writeLog(Util.CLEAR_TEXT, "query : " + query);

			Cursor cur = mDatabase.rawQuery(fullStatement, null);

			cur.moveToFirst();

			while (!cur.isAfterLast())
			{
				HashMap hm = new HashMap();
				for (int i = 0; i < cur.getColumnCount(); i++)
				{
					hm.put(fieldArray.get(i), String.valueOf(cur.getString(i)));
				}

				returnData.add(hm);
				cur.moveToNext();

			}
			cur.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return returnData;
	}

	private boolean txQuery(String fullStatement)
	{
		String statement = fullStatement.trim();
		boolean returnValue = true;
		try
		{
			mDatabase.execSQL(statement);
			returnValue = true;
		}

		catch (Exception e)
		{
			returnValue = false;
		}
		return returnValue;
	}

	private Cursor trxQuery(String fullStatement)
	{
		String statement = fullStatement.trim();
		Cursor returnCursor = null;
		try
		{
			returnCursor = mDatabase.rawQuery(statement, null);
		}

		catch (Exception e)
		{
			Log.d("ERROR", e.getMessage());
			e.printStackTrace();
			returnCursor = null;
		}
		return returnCursor;
	}

	private static void makeDirectory(String dir)
	{
		File file = new File(dir);
		if (!file.isDirectory())
			file.mkdir();
	}
}

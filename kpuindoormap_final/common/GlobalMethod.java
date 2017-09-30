package com.kpu.kpuindoormap.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;

public class GlobalMethod
{
	public final static String DB_PATH = "/data/data/com.kpu.kpuindoormap/databases/";
	public final static String DB_NAME = "beaconitem.db";

	// db 정보를 받을 파일을 안드로이드 내에 생성. ==>> SQLite
	public static void copyFile(Context context, String path, String fileName)
	{
		InputStream is = null;
		FileOutputStream fos = null;
		makeDirectory(path);

		try
		{
			is = context.getAssets().open(fileName);
			int size = is.available();
			byte[] buffer = new byte[size];
			File outfile = new File(path + "/" + fileName);
			fos = new FileOutputStream(outfile);

			for (int c = is.read(buffer); c != -1; c = is.read(buffer))
			{
				fos.write(buffer, 0, c);
			}

			is.close();
			fos.close();
		}

		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void makeDirectory(String dir)
	{
		File file = new File(dir);
		if (!file.isDirectory())
			file.mkdir();
	}
}

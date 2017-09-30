package com.kpu.kpuindoormap.common;

import android.app.AlertDialog;
import android.content.Context;

public class DialogHelper // 메시지 팝업.
{
	public static void showDialog(Context context, String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("확인", null);
		builder.show();
	}
	
}

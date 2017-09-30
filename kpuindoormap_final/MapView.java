package com.kpu.kpuindoormap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.kpu.kpuindoormap.R.drawable;
import com.kpu.kpuindoormap.sdks.SubsamplingScaleImageView;

import java.util.ArrayList;


// 이미지를 map 에 뿌려주는 메소드.
public class MapView extends SubsamplingScaleImageView //map 확대 기능 추가.
{
	private PointF sPin = null;
	private Bitmap pin = null;
	private Bitmap start = null;
	private Bitmap end = null;
	private ArrayList<PointF> mPathArr = null;
	private Paint mLinePaint = null;
	private Path mPath = null;

	public MapView(Context context)
	{
		super(context, null);
	}

	public MapView(Context context, AttributeSet attr)
	{
		super(context, attr);
		initialise();

	}

	public void setMarker(PointF sPin)
	{
		this.sPin = sPin;
		initialise();
		invalidate();
	}
	
	public void setPath(ArrayList<PointF> paths)
	{
		mPathArr = paths;
		invalidate();
	}
	
	
	public void removePath()
	{
		mPathArr = null;
		invalidate();
	}

	public PointF getMarkerPoint()
	{
		return sPin;
	}

	public void removeMarker()
	{
		sPin = null;
		invalidate();
	}

	private void initialise()
	{
		float density = getResources().getDisplayMetrics().densityDpi;
		
		pin = BitmapFactory.decodeResource(this.getResources(), drawable.marker);
		start = BitmapFactory.decodeResource(this.getResources(), drawable.start); 
		end = BitmapFactory.decodeResource(this.getResources(), drawable.end);
		
		float w = (density / 840f) * pin.getWidth();
		float h = (density / 840f) * pin.getHeight();
		
		pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
		start = Bitmap.createScaledBitmap(start, (int) w, (int) h, true); 
		end = Bitmap.createScaledBitmap(end, (int) w, (int) h, true); 
		
		mPathArr = new ArrayList<PointF>();
		mPath = new Path();
		mLinePaint = new Paint();
		mLinePaint.setColor(Color.RED);
		mLinePaint.setStyle(Style.STROKE);
		mLinePaint.setStrokeWidth(10);
		mLinePaint.setAlpha(80);
		mLinePaint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) // 마커의 위치를 설정하고 지도에 그려줌.
	{
		super.onDraw(canvas);

		if (!isImageReady())
		{
			return;
		}
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);

		if (sPin != null && pin != null)
		{
			PointF vPin = sourceToViewCoord(sPin);
			float vX = vPin.x - (pin.getWidth() / 2);
			float vY = vPin.y - pin.getHeight();
			canvas.drawBitmap(pin, vX, vY, paint);
		}
		
		if(mPathArr != null)
		{
			ArrayList<PointF> convArr = new ArrayList<PointF>();
			
			for(PointF point : mPathArr)
			{
				convArr.add(sourceToViewCoord(point));
			}
			
			mPath.reset();

			// 최단 경로 출력
			if(convArr != null && convArr.size() > 0)
			{
				for(int i = 0 ; i < convArr.size(); i++)
				{
					if(i == 0)
					{
						mPath.moveTo(convArr.get(i).x, convArr.get(i).y);
						float vX = convArr.get(i).x - (start.getWidth() / 2);
						float vY = convArr.get(i).y - start.getHeight();
						canvas.drawBitmap(start, vX, vY, paint);
					}
					
					else
						mPath.lineTo(convArr.get(i).x, convArr.get(i).y);
					
					if(i == convArr.size() - 1)
					{
						float vX = convArr.get(i).x - (end.getWidth() / 2);
						float vY = convArr.get(i).y - end.getHeight();
						canvas.drawBitmap(end, vX, vY, paint);
					}
				}
			}
			
			canvas.drawPath(mPath, mLinePaint);
		}
		
	}

}

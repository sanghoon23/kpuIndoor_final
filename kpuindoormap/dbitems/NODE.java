package com.kpu.kpuindoormap.dbitems;

//목적지 , 교차로를 담는 DB. LINK 와 함께 쓰임.
public class NODE
{
	private int mNodeId;
	private float mX, mY;
	
	public NODE(int nodeId, float x, float y)
	{
		mNodeId = nodeId; // 각각의 노드
		mX = x; // 지도 상의 x축값
		mY = y; // 지도 상의 y축값
	}
	
	public int getNodeId()
	{
		return mNodeId;
	}
	
	public float getX()
	{
		return mX;
	}
	
	public float getY()
	{
		return mY;
	}
}

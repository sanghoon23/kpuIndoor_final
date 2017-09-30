package com.kpu.kpuindoormap.dbitems;

// NODE를 연결해주는 LINK DB 정보.
public class LINK
{
	private int mLinkId;
	private int mStNodeId;
	private int mEdNodeId;
	
	public int DIST_PARAM = -1;
	public int ORD_PARAM = -1;
	public int PREV_LINK_ID = -1;
	
	public LINK(int linkId, int stNodeId, int edNodeId)
	{
		mLinkId = linkId; // LINK .
		mStNodeId = stNodeId; // 시작되는 노드.
		mEdNodeId = edNodeId; // 끝이 되는 노드
	}
	
	public int getLinkId()
	{
		return mLinkId;
	}
	
	public int getStartNode()
	{
		return mStNodeId;
	}
	
	public int getEndNode()
	{
		return mEdNodeId;
	}
}

package com.kpu.kpuindoormap.sdks;

import com.kpu.kpuindoormap.common.DBHelper;
import com.kpu.kpuindoormap.dbitems.LINK;
import com.kpu.kpuindoormap.dbitems.NODE;

import java.util.ArrayList;

public class MapMatcher
{
	public static ArrayList<LINK> match(float x, float y)
	{
		float matchingDistance = 10000;
		
		ArrayList<NODE> nodeArr = new ArrayList<NODE>();
		ArrayList<LINK> linkArr = new ArrayList<LINK>();

		NODE nearNode = null;

		for(NODE nodeItem : DBHelper.getNode())
		{
			float distance = getDistance(x, y, nodeItem.getX(), nodeItem.getY());
			
			if(distance < matchingDistance)
			{
				nearNode = nodeItem;
				matchingDistance = distance;
			}
		}
		
		if(nearNode != null)
		{
			for(LINK linkItem : DBHelper.getLink())
			{
				if(linkItem.getStartNode() == nearNode.getNodeId() || linkItem.getEndNode() == nearNode.getNodeId())
					linkArr.add(linkItem);
			}
		}
		
		return linkArr;
	}
	
	public static float getDistance(float x1, float y1, float x2, float y2)
	{
		return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
}

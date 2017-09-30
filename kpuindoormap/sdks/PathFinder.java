package com.kpu.kpuindoormap.sdks;

import java.util.ArrayList;

import android.util.Log;

import com.kpu.kpuindoormap.common.DBHelper;
import com.kpu.kpuindoormap.dbitems.LINK;
import com.kpu.kpuindoormap.items.PFResult;

public class PathFinder
{
	public final static String START_MATCHING_FAIL = "START_MM_FAIL";
	public final static String DEST_MATCHING_FAIL = "DEST_MM_FAIL";
	public final static String RP_FAIL = "RP_FAIL";

	public static PFResult find(float startX, float startY, float destX, float destY)
	{
		ArrayList<LINK> startMM = MapMatcher.match(startX, startY);
		ArrayList<LINK> destMM = MapMatcher.match(destX, destY);

		ArrayList<LINK> mLink = DBHelper.getLink();
		
		Log.d("PathFinder", "startMM's size = " + startMM.get(0).getStartNode());
		Log.d("PathFinder", "destMM's size = " + destMM.get(0).getEndNode());
		
		ArrayList<PFResult> resultArr = new ArrayList<PFResult>();

		if (startMM.size() > 0)
		{
			if (destMM.size() > 0)
			{
				for (LINK startLink : startMM)
				{
					for (LINK destLink : destMM)
					{
						ArrayList<LINK> targetLinkArr = new ArrayList<LINK>();

						for (LINK linkItem : mLink)
						{
							if (linkItem.getLinkId() == startLink.getLinkId())
							{
								linkItem.DIST_PARAM = 1;
								linkItem.ORD_PARAM = 0;
								linkItem.PREV_LINK_ID = -1;
								targetLinkArr.add(linkItem);
							}

							else
							{
								linkItem.DIST_PARAM = -1;
								linkItem.ORD_PARAM = -1;
								linkItem.PREV_LINK_ID = -1;
							}
						}

						while (true)
						{
							if (targetLinkArr.size() <= 0)
								break;

							ArrayList<LINK> nTargetLink = new ArrayList<LINK>();

							for (LINK targetLink : targetLinkArr)
							{
								for (LINK linkItem : mLink)
								{
									if (targetLink.getEndNode() == linkItem.getStartNode() && targetLink.getStartNode() != linkItem.getEndNode())
									{
										if (linkItem.DIST_PARAM == -1 || (linkItem.DIST_PARAM != -1 && targetLink.DIST_PARAM + 1 < linkItem.DIST_PARAM))
										{
											linkItem.DIST_PARAM = targetLink.DIST_PARAM + 1;
											linkItem.ORD_PARAM = targetLink.ORD_PARAM + 1;
											linkItem.PREV_LINK_ID = targetLink.getLinkId();
											nTargetLink.add(linkItem);
											
											if(targetLink.getLinkId() == destLink.getLinkId())
											{
												nTargetLink.clear();
												break;
											}
										}
									}
								}
							}

							targetLinkArr.clear();
							targetLinkArr.addAll(nTargetLink);
						}

						ArrayList<LINK> resultLinkArr = new ArrayList<LINK>();

						for (LINK linkItem : mLink)
						{
							if (linkItem.getLinkId() == destLink.getLinkId())
							{
								resultLinkArr.add(linkItem);
								break;
							}
						}

						if (resultLinkArr.size() > 0)
						{
							Log.d("Result Link Arr size", resultLinkArr.size() + "");
							int targetLinkId = resultLinkArr.get(0).PREV_LINK_ID;
							Log.d("Target Link Id", targetLinkId + "");

							while (true)
							{
								for (LINK linkItem : mLink)
								{
									if (linkItem.getLinkId() == targetLinkId)
									{
										resultLinkArr.add(linkItem);
										targetLinkId = linkItem.PREV_LINK_ID;
										Log.d("Target Link Id", targetLinkId + "");
										break;
									}
								}

								if (targetLinkId == -1)
									break;
							}
							Log.d("Result Arr size = ", resultLinkArr.size() + "");

							ArrayList<LINK> resultRvLinkArr = new ArrayList<LINK>();

							for (int i = resultLinkArr.size() - 1; i >= 0; i--)
							{
								resultRvLinkArr.add(resultLinkArr.get(i));
							}
							
							Log.d("Result R size = ", resultRvLinkArr.size() + "");

							if(resultRvLinkArr.size() > 1)
								resultArr.add(new PFResult(resultRvLinkArr));
						}
					}
				}
			}

			else
			{
				Log.d("PathFinder", DEST_MATCHING_FAIL);
				return new PFResult(new ArrayList<LINK>());
			}
		}

		else
		{
			Log.d("PathFinder", START_MATCHING_FAIL);
			return new PFResult(new ArrayList<LINK>());
		}

		if (resultArr.size() <= 0)
		{
			Log.d("PathFinder", RP_FAIL);
			return new PFResult(new ArrayList<LINK>());
		}

		else
		{
			PFResult pfResult = null;

			for (PFResult result : resultArr)
			{
				if (pfResult == null || (result.getDistance() > 0 && result.getDistance() < pfResult.getDistance()))
				{
					pfResult = result;
				}
			}

			return pfResult;
		}
	}

}

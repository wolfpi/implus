package com.baidu.im.frame.inapp;

public class SeqGenerator {
	
	private static int mStart = 0;
	private static int mEnd = 0 ;
	private static boolean  mInit = false;
	private static int mCurSeq = -1;
	public static void setSeqRange(int start, int end)
	{
		mStart = start;
		mEnd = end;
		mCurSeq = start;
		mInit = true;
	}

	public static int getSeq()
	{
		if(mInit)
		{
			if(++mCurSeq >= mEnd )
			{
				mCurSeq = mStart;
			}
			
			return mCurSeq;
		}
		return  -1;
	}
	
	public static void reset()
	{
		mInit = false;
	}
}

package com.baidu.im.frame.utils;
/*
 * 
 * 临时策略 因为代码中的Util的问题
 */
public class GlobalInstance {
	
	static private GlobalInstance instance = null;
	static public GlobalInstance Instance()
	{
		if(instance == null)
		{
			instance = new GlobalInstance();
		}
		return instance;
	}
	private PreferenceUtil mPreference = null;
	public PreferenceUtil preferenceInstace()
	{
		if(mPreference == null)
			mPreference = new PreferenceUtil();
		return mPreference;
	}

}

package com.baidu.im.frame;

import java.util.TimerTask;

public interface GlobalTimer 
{
	  public void addTask(String key, TimerTask task, int timeOut);
	  public void addTask(int key, TimerTask task, int timeOut);
	  public void removeTask(String key);
	  public void removeTask(int key);
	  public void removeAllTask();
	  public void pauseTimer();
}
package com.baidu.im.frame;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

public class GlobalTimerTasks implements GlobalTimer 
{
	  final static String TAG = "GlobalTimer";
	  private Object lock = new Object();
	  
	  private static GlobalTimerTasks uniqueInstance = null;
	  public static GlobalTimerTasks getInstance() {
	        if (uniqueInstance == null) {    
	            synchronized (GlobalTimerTasks.class) {    
	               if (uniqueInstance == null) {    
	            	   uniqueInstance = new GlobalTimerTasks();   
	               }    
	            }    
	        }    
	        return uniqueInstance; 
	  }
	  
	  private GlobalTimerTasks(){
		  
	  }
	  
	  @Override
	  public void addTask(String key, TimerTask task, int timeOut)
	    {
		  synchronized (lock){
	    	Timer sendTimer = this.getTimer();
	    	sendTimer.schedule(task, timeOut); 
	    	this.mSendingTasks.put(key, task);
		  }
	    }
	  
	  @Override
	  public void removeTask(String key)
	    {
	    	synchronized (lock) {
	    	if(this.mSendingTasks.containsKey(key))
	    	{
	    		TimerTask task = this.mSendingTasks.get(key);
	    		if(task!= null)
	    		{
	    			task.cancel();
	    			this.mSendingTasks.remove(key);
	    		}
	    	}
	    	}
	    }
	  
	  public void pauseTimer()
	  {
		  //this.getTimer().
	  }
	  
	  @Override
	  public void removeAllTask()
	    {
	    	synchronized (lock) {
	    		
	    		Iterator<Entry<String, TimerTask>> it = mSendingTasks.entrySet().iterator(); 
	    		while (it.hasNext()) { 
	    		Map.Entry<String, TimerTask> entry = (Entry<String, TimerTask>) it.next(); 
	    		TimerTask value = (TimerTask)entry.getValue(); 
	    	    value.cancel();
	    		}
	    		mSendingTasks.clear();
	    	}
	    }
	  
	    
	private Timer getTimer()
	{
		if(this.timer == null)
		{
			this.timer = new Timer(true);
		}
		return this.timer;
	}
	
	private Map< String, TimerTask> mSendingTasks = Collections
            						.synchronizedMap(new HashMap< String, TimerTask>()); 	
	private Timer timer = null;
	@Override
	public void addTask(int key, TimerTask task, int timeOut) {
		
		addTask(String.valueOf(key),task,timeOut);
	}

	@Override
	public void removeTask(int key) {
		
		removeTask(String.valueOf(key));
	}
}

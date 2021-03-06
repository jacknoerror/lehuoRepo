package com.lehuozu.entity;

import android.os.Bundle;

/**
 *used for {@link EventBus} 
 * @author taotao
 * @Date 2014-6-13
 */
public class MyEvent implements Cloneable{
	int eventId = -1;
	String eventMsg="";
	Bundle bundle;
	
	public int what ;
	public Object obj;
	
	public MyEvent(int eventId) {
		super();
		this.eventId = eventId;
	}
	
	public MyEvent(String eventMsg) {
		super();
		this.eventMsg = eventMsg;
	}

	public final Bundle getBundle() {
		return bundle;
	}
	public final void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}
	public final int getEventId() {
		return eventId;
	}

	public final String getEventMsg() {
		return eventMsg;
	}
	
	public final MyEvent msg(String eventMsg) {
		this.eventMsg = eventMsg;
		return this;
	}

	@Override
	public MyEvent clone()   {
		MyEvent ev=null;
		try
		{
			ev=(MyEvent)super.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return ev;
	}
	
	public boolean equalsMsg(String msg	){
		if(null==msg) return eventMsg==null;
		return msg.equals(this.eventMsg);
	}
	public interface MyEventImpl{
		public void onEventMainThread(MyEvent event) ;

		public void onEventBackgroundThread(MyEvent event) ;
	}
}

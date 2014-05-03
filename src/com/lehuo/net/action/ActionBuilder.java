package com.lehuo.net.action;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.net.HttpRequestTask;



public class ActionBuilder {
	
	private Map<String, ActionPhpRequestImpl> actingMap ;
	
	private static ActionBuilder ab;
	private ActionBuilder(){
		actingMap = new HashMap<String, ActionPhpRequestImpl>();
	}
	public static ActionBuilder getInstance(){
		if(null==ab){
			ab = new ActionBuilder();
		}
		return ab;
	}
	
	public void request(ActionPhpRequestImpl actReq, ActionPhpReceiverImpl actRcv ){
		//TODO map?
		new HttpRequestTask(actRcv).execute(actReq.toHttpBody());
	}
}

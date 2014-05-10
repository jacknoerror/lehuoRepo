package com.lehuo.net.action;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.Const;
import com.lehuo.net.HttpRequestTask;
import com.lehuo.util.TestDataTracker;



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
		if(!Const._SIM){
			new HttpRequestTask(actRcv).execute(actReq.toHttpBody());
		}else{
			//本地缓存接口数据
			TestDataTracker.simulateConnection(actRcv, actReq.getApiName());
		}
	}
}

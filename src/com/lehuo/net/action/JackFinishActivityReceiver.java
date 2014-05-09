package com.lehuo.net.action;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.net.NetStrategies;


import android.app.Activity;
import android.content.Context;

/**
 * �з���resultObj�����activity
 * @author taotao
 *
 */
public class JackFinishActivityReceiver implements ActionPhpReceiverImpl {

	Activity activity;
	ActionPhpReceiverImpl arv;
	
	
	public JackFinishActivityReceiver( Activity activity) {
		this.activity = activity;
	}
	


	public JackFinishActivityReceiver(Activity activity, ActionPhpReceiverImpl arv) {
		this.activity = activity;
		this.arv = arv;
	}



	@Override
	public boolean response(String result) throws JSONException {
//		NetStrategies.getResultObj(result);//0421
//		if(result.contains(RESULT_OBJ)&&null!=activity&&(null==arv||!arv.response(result)))activity.finish();
		if(NetStrategies.dataAvailable(new JSONObject(result))&&null!=activity&&(null==arv||!arv.response(result)))activity.finish();
		return false;//TODO
	}



	@Override
	public Context getReceiverContext() {
		return activity;
	}


}

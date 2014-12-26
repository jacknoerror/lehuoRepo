package com.lehuozu.net.action;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.lehuozu.util.JackUtils;

/**
 * @author tao
 *A receiver does nothing but offers the request-result, and shows a message
 */
public class BareReceiver implements ActionPhpReceiverImpl {
	
	Context context;
	
	public BareReceiver(Context context) {
		super();
		this.context = context;
	}
	
	protected JSONObject resultJob;//for children's use
	@Override
	public boolean response(String result) throws JSONException {//0504
		resultJob = new JSONObject(result);
		if(null!=resultJob){
			if(resultJob.has(RESULT_ERROR_MSG)&&null!=context){//not only errors, show toast anyway
				String msg = resultJob.optString(RESULT_ERROR_MSG);
				if(!msg.isEmpty())JackUtils.showToast(context, msg);
			}
			if(resultJob.has(RESULT_SIGN)&&resultJob.getBoolean(RESULT_SIGN)){
				
				return false;
			}
		}
		return true;
	}
	

	@Override
	public Context getReceiverContext() {
		return context;
	}


}

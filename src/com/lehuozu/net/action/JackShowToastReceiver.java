package com.lehuozu.net.action;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.util.JackUtils;

import android.content.Context;

public class JackShowToastReceiver implements ActionPhpReceiverImpl {
	Context context;
	
	
	
	public JackShowToastReceiver(Context context) {
		super();
		this.context = context;
	}

	@Override
	public boolean response(String result) throws JSONException {
		JSONObject job = new JSONObject(result);
		if(null!=job){
			if(job.has(RESULT_ERROR_MSG)&&null!=context){//not only errors, show toast anyway
				String msg = job.getString(RESULT_ERROR_MSG);
				JackUtils.showToast(context, msg);
			}
			if(job.has(RESULT_SIGN)&&job.getBoolean(RESULT_SIGN)){
				
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

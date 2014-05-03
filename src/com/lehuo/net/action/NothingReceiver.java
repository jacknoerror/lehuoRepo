package com.lehuo.net.action;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.lehuo.util.JackUtils;

public class NothingReceiver implements ActionPhpReceiverImpl {
	
	Context context;
	
	public NothingReceiver(Context context) {
		super();
		this.context = context;
	}

	@Override
	public boolean response(String result) throws JSONException {
		if(result.contains("errorMsg")){
			JackUtils.showToast(getReceiverContext(), new JSONObject(result).getString("errorMsg"));
			return false;
		}
		return true;
	}
	

	@Override
	public Context getReceiverContext() {
		return context;
	}


}

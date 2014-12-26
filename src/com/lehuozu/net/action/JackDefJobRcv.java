package com.lehuozu.net.action;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.net.NetStrategies;

import android.content.Context;

public abstract class JackDefJobRcv implements ActionPhpReceiverImpl{

	protected Context context;
	
	
	public JackDefJobRcv(Context context) {
		super();
		this.context = context;
	}

	@Override
	public boolean response(String result) throws JSONException {
		return respJob(NetStrategies.getResultObj(result));
	}

	@Override
	public Context getReceiverContext() {
		return context;
	}

	public abstract boolean respJob(JSONObject job)throws JSONException;
	
}

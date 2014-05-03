package com.lehuo.net.action;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.net.NetStrategies;
import com.lehuo.util.JackUtils;

import android.content.Context;

public abstract class JackDefJarRcv implements ActionPhpReceiverImpl{

	protected Context context;
	
	
	public JackDefJarRcv(Context context) {
		super();
		this.context = context;
	}

	@Override
	public boolean response(String result) throws JSONException {
		return respJar(NetStrategies.getResultArray(result));
	}

	@Override
	public Context getReceiverContext() {
		return context;
	}

	public abstract boolean respJar(JSONArray jar)throws JSONException;
	
}

package com.lehuo.net.action.order;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.lehuo.data.MyData;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.ui.TitleManager;

public class UpdateCartRcv implements ActionPhpReceiverImpl {

	Context context;
	TitleManager tm;
	

	public UpdateCartRcv(Context context, TitleManager tm) {
		super();
		this.context = context;
		this.tm = tm;
	}

	@Override
	public boolean response(String result) throws JSONException {
		JSONObject job = null;
		if(NetStrategies.dataAvailable(job = new JSONObject(result))){
			int count = job.optInt(RESULT_OBJ);
			MyData.data().setCartCount(count);
			if(null!=tm)tm.updateCart();
			return false;//即使tm为，依然正确
		}
		return true;
	}

	@Override
	public Context getReceiverContext() {
		return context;
	}

}

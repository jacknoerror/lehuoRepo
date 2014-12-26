package com.lehuozu.net.action.order;

import org.json.JSONException;

import android.content.Context;

import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpReceiverImpl;

public class DelCartRcv implements ActionPhpReceiverImpl {

	@Override
	public boolean response(String result) throws JSONException {
		return false;
	}

	@Override
	public Context getReceiverContext() {
		// TODO Auto-generated method stub
		return null;
	}

}

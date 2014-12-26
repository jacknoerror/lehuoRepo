package com.lehuozu.net.action;

import org.json.JSONException;

import android.content.Context;

import com.lehuozu.data.NetConst;


public interface ActionPhpReceiverImpl extends NetConst{
	/**
	 * @param result
	 * @return false if no error occurs
	 * @throws JSONException
	 */
	public boolean response(String result) throws JSONException;
	public Context getReceiverContext();
}

package com.lehuo.util;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.lehuo.MyApplication;
import com.lehuo.net.action.ActionPhpReceiverImpl;


public class TestDataTracker {
	static final String TAG = "TestDataTracker";
	
	public static String fetchDataString(String dataKey){
		String fetch = JackUtils.readFromFile(getContext()	, shortenFilename(dataKey));
		Log.i(TAG, fetch);
		return fetch;
	}
	
	private static String shortenFilename(String dataKey){
		/*try {
			return dataKey.substring(dataKey.charAt(dataKey.lastIndexOf(".")));
		} catch (Exception e) {
			return dataKey;
		}*/
		return dataKey;
	}
	private static Context getContext(){
		return MyApplication.app();
	}
	
	public static void simulateConnection(ActionPhpReceiverImpl receiver,String dataKey){
		try {
			receiver.response("{"+fetchDataString(dataKey));
		} catch (JSONException e) {
			Log.e(TAG, "simulateConnection json error");
			e.printStackTrace();
		}
	}
	
	public static  boolean settleDataString(String dataKey, String content){
		return JackUtils.writeToFile(getContext(), shortenFilename(dataKey), content);
	}
	
}

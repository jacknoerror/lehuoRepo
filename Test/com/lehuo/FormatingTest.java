package com.lehuo;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;
import android.util.Log;

import com.lehuo.net.NetStrategies;
import com.lehuo.util.HandleStrings;
import com.lehuo.util.JackUtils;

public class FormatingTest extends AndroidTestCase {
	private final String TAG  = "UNIT_TEST";

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	
	public void fromJsonstrToCode() throws JSONException{
		convertJsonToCode();
		String result = HandleStrings.readAndConvert3(mContext);
		assertNotNull(result);
		Log.i(TAG, result);
	}
	
	public void convertJsonToCode() throws JSONException{
		final String jsonStr = "{\"latitude\":\"30.283775\",\"longitude\":\"120.121765\",\"addressname\":\"\\u897f\\u6e56\\u533a\\u76ca\\u4e50\\u8def7\\u53f7\",\"time\":\"1399660010\",\"truename\":\"\\u90b5\\u6587\\u971e\",\"mobile_phone\":\"15906669497\",\"logo_small\":\"http:\\/\\/58.64.178.2\\/images\\/201404\\/1397106601090738560_100X150.jpg\"}";
		JSONObject job = new JSONObject(jsonStr);
		Iterator<String> it = job.keys();
		StringBuffer sb = new StringBuffer();
		while(it.hasNext()){
			String name = it.next();
			String line = String.format("private String %s;", name);
			sb.append(line);
//			write(name);
		}
//		Log.i(TAG, sb.toString());
		JackUtils.writeToSomeWhere(mContext, sb.toString());
	}

	/**
	 * @param name
	 */
	private void write(String name) {
		JackUtils.writeToFile(getContext(), "Format", name);
	}
	
	
}

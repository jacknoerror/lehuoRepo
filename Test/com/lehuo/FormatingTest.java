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
		final String jsonStr = "{\"goods_price\":1800,\"market_price\":2808,\"saving\":1008,\"save_rate\":\"36%\",\"goods_amount\":1800,\"integral\":0,\"real_goods_count\":1,\"virtual_goods_count\":0}";
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

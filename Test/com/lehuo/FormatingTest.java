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
		final String jsonStr = "{\"real_goods_count\":2,\"gift_amount\":0,\"goods_price\":1200,\"market_price\":1764,\"discount\":null,\"shipping_fee\":0,\"integral_money\":0,\"bonus\":0,\"cod_fee\":0,\"saving\":564,\"save_rate\":\"32%\",\"goods_price_formated\":\"￥1200.00元\",\"market_price_formated\":\"￥1764.00元\",\"saving_formated\":\"￥564.00元\",\"discount_formated\":\"￥0.00元\",\"bonus_formated\":\"￥0.00元\",\"shipping_fee_formated\":\"￥0.00元\",\"amount\":1200,\"integral\":0,\"integral_formated\":\"￥0.00元\",\"amount_formated\":\"￥1200.00元\",\"will_get_integral\":1200,\"will_get_bonus\":\"￥0.00元\",\"formated_goods_price\":\"￥1200.00元\",\"formated_market_price\":\"￥1764.00元\",\"formated_saving\":\"￥564.00元\"}";
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

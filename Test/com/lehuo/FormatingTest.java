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
		final String jsonStr = "{\"order_id\":\"37\",\"order_sn\":\"2014042139639\",\"order_time\":\"2014-04-21 02:30:12\",\"order_status_state\":\"��ȷ��,δ����,�ѷ���\",\"order_status\":\"��ȷ��\",\"shipping_status\":\"�ѷ���\",\"pay_status\":\"δ����\",\"pay_method\":\"��������\",\"district\":\"�ϳ���\",\"address\":\"���¹�Ԣ\",\"consignee\":\"�����˺�\",\"best_time\":\"\",\"mobile\":\"15811111111\",\"total_fee\":\"��310.00Ԫ\",\"courier_status\":\"1\",\"nums\":1,\"goods\":[{\"rec_id\":\"75\",\"order_id\":\"37\",\"goods_id\":\"2\",\"goods_name\":\"����ѥ\",\"goods_number\":\"1\",\"goods_attr\":\"\",\"goods_price\":\"300.00\",\"goods_thumb\":\"http://58.64.178.2/images/201404/thumb_img/2_thumb_G_1398134141589118734_105X105.jpg\"}]}";
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

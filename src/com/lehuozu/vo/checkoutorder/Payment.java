package com.lehuozu.vo.checkoutorder;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.entity.json.JsonImport;

public class Payment extends JsonImport {
	public static int PAYTYPE_ARRIVE=2;
	public static int PAYTYPE_ALIPAY=1;
	public static int PAYTYPE_ALIWEB=3;
	
	int pay_id;//2，货到付款；1，支付宝客户端；3，支付宝网页
	String pay_name;
	//isdefault?
	public int getPay_id() {
		return pay_id;
	}

	public String getPay_name() {
		return pay_name;
	}

	public void setPay_id(int pay_id) {
		this.pay_id = pay_id;
	}

	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}

	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Payment(JSONObject job) {
		super(job);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("pay_id")) pay_id = job.getInt("pay_id");
		if(job.has("pay_name")) pay_name = job.getString("pay_name");
	}

}

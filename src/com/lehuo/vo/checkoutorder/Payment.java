package com.lehuo.vo.checkoutorder;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;

public class Payment extends JsonImport {
	int pay_id;
	String pay_name;
	
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

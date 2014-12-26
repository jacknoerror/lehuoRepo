package com.lehuozu.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.entity.json.JsonImport;

public class IntPrice extends JsonImport {

	int shop_price;
	int integral_need;

	public final int getShop_price() {
		return shop_price;
	}

	public final void setShop_price(int shop_price) {
		this.shop_price = shop_price;
	}

	public final int getIntegral_need() {
		return integral_need;
	}

	public final void setIntegral_need(int integral_need) {
		this.integral_need = integral_need;
	}

	public IntPrice() {
		super();
	}

	public IntPrice(JSONObject job) {
		super(job);
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if (job.has("shop_price"))
			setShop_price(job.getInt("shop_price"));
		if (job.has("integral_need"))
			setIntegral_need(job.getInt("integral_need"));
	}

}

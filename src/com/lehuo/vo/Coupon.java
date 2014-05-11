/**
 * 
 */
package com.lehuo.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;

/**
 * @author tao
 *
 */
public class Coupon extends JsonImport {

	/**
	 * {"result":true,"message":"","data":{"available":[],"used":[{"type_id":"5","type_name":"\u6ee1300\u9001","type_money":"30.00","bonus_id":"22","used_time":"2014.04.19"}],"expired":[]}}
	 */
	public Coupon() {
		super();
	}

	/**
	 * @param job
	 */
	public Coupon(JSONObject job) {
		super(job);
	}

	
	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		// TODO Auto-generated method stub

	}

}

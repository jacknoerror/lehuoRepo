/**
 * 
 */
package com.lehuozu.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.entity.json.JsonImport;
import com.lehuozu.ui.custom.list.ListItemImpl;

/**
 * @author tao
 *每个里面 都有 bonus_id type_money use_start_date use_end_date type_name
		分别表示优惠券id 优惠券金额 有效期开始－结束 优惠券描述
 */
public class Coupon extends JsonImport implements ListItemImpl{

	/**
	 * {"result":true,"message":"","data":{"available":[],"used":[{"type_id":"5","type_name":"\u6ee1300\u9001","type_money":"30.00","bonus_id":"22","used_time":"2014.04.19"}],"expired":[]}}
	 */
	
//	private String used_time;
	private String type_name;
	private int bonus_id;
	private int type_money;
//	private String type_id;
	
	String use_end_date;
	String use_start_date;
	
	

	public final String getType_name() {
		return type_name;
	}

	public final void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public final int getBonus_id() {
		return bonus_id;
	}

	public final void setBonus_id(int bonus_id) {
		this.bonus_id = bonus_id;
	}

	public final int getType_money() {
		return type_money;
	}

	public final void setType_money(int type_money) {
		this.type_money = type_money;
	}

	public final String getUse_end_date() {
		return use_end_date;
	}

	public final void setUse_end_date(String use_end_date) {
		this.use_end_date = use_end_date;
	}

	public final String getUse_start_date() {
		return use_start_date;
	}

	public final void setUse_start_date(String use_start_date) {
		this.use_start_date = use_start_date;
	}

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
		if(job.has("use_end_date")) use_end_date = job.getString("use_end_date");
		if(job.has("type_name")) type_name = job.getString("type_name");
		if(job.has("bonus_id")) bonus_id = job.getInt("bonus_id");
		if(job.has("type_money")) type_money = job.getInt("type_money");
		if(job.has("use_start_date")) use_start_date = job.getString("use_start_date");

	}

}

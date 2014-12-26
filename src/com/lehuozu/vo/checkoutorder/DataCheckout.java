package com.lehuozu.vo.checkoutorder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.entity.json.JsonImport;

/**
 * @author tao
 *checkout result jsonObj
 */
public class DataCheckout extends JsonImport {
	JSONArray cart;//购物车产品信息
	Consignee consignee;//地址
	CheckTotal total;//价格等信息
	JSONArray user_bonus;//优惠券
	Payment payment;//付款方式
	
	public DataCheckout(JSONObject job) {
		super(job);
	}

	public JSONArray getCart() {
		return cart;
	}

	public Consignee getConsignee() {
		return consignee;
	}

	public CheckTotal getTotal() {
		return total;
	}

	public JSONArray getUser_bonus() {
		return user_bonus;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setCart(JSONArray cart) {
		this.cart = cart;
	}

	public void setConsignee(Consignee consignee) {
		this.consignee = consignee;
	}

	public void setTotal(CheckTotal total) {
		this.total = total;
	}

	public void setUser_bonus(JSONArray user_bonus) {
		this.user_bonus = user_bonus;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("cart")) cart = job.getJSONArray("cart");
		if(job.has("user_bonus")) user_bonus = job.getJSONArray("user_bonus");
		if(job.has("consignee")) consignee = new Consignee(job.getJSONObject("consignee"));
		if(job.has("total")) total = new CheckTotal(job.getJSONObject("total"));
		if(job.has("payment")) payment = new Payment(job.getJSONObject("payment"));
	}

}

package com.lehuo.vo.cart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;

public class DataCart extends JsonImport {
	// result=>{"result":true,"data":
	// {"cart":{"goods_list":[],
	// "total":{"goods_price":0,"market_price":0,"saving":0,"save_rate":0,"goods_amount":0,
	// "integral":0,"real_goods_count":0,"virtual_goods_count":0}}},"message":""}

	/*
	 * {"cart":{"goods_list":[
	 * {"rec_id":"101","user_id":"28","session_id":"","goods_id"
	 * :"2","goods_sn":"ECS000002",
	 * "product_id":"0","goods_name":"\u68c9\u68c9\u9774"
	 * ,"market_price":"468.00",
	 * "goods_price":"300.00","goods_number":"6","goods_attr":"","is_real":"1",
	 * "extension_code"
	 * :"","parent_id":"0","rec_type":"0","is_gift":"0","is_shipping":"0",
	 * "can_handsel"
	 * :"0","goods_attr_id":"","pid":"2","integral":"0","subtotal":1800,
	 * "goods_thumb":
	 * "http:\/\/58.64.178.2\/images\/201404\/thumb_img\/2_thumb_G_1398134141589118734_105X105.jpg"}],
	 * "total"
	 * :{"goods_price":1800,"market_price":2808,"saving":1008,"save_rate":"36%",
	 * "goods_amount"
	 * :1800,"integral":0,"real_goods_count":1,"virtual_goods_count":0}},
	 * "discount":0,"fittings":[],"favourable":[]}
	 */
	CartInfo cart;
	int discount;
	JSONArray fittings;
	JSONArray favourable;

	public CartInfo getCart() {
		return cart;
	}

	public int getDiscount() {
		return discount;
	}

	public JSONArray getFittings() {
		return fittings;
	}

	public JSONArray getFavourable() {
		return favourable;
	}

	public void setCart(CartInfo cart) {
		this.cart = cart;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public void setFittings(JSONArray fittings) {
		this.fittings = fittings;
	}

	public void setFavourable(JSONArray favourable) {
		this.favourable = favourable;
	}

	public DataCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataCart(JSONObject job) {
		super(job);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if (job.has("cart"))
			cart = new CartInfo(job.getJSONObject("cart"));
		if (job.has("discount"))
			discount = job.getInt("discount");
		if (job.has("fittings"))
			fittings = job.getJSONArray("fittings");
		if (job.has("favourable"))
			favourable = job.getJSONArray("favourable");

	}
}

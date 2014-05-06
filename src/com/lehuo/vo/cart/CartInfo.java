package com.lehuo.vo.cart;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;

public class CartInfo extends JsonImport {
	/*
	 * {"goods_list":[{"rec_id":"101","user_id":"28","session_id":"","goods_id":"2"
	 * ,
	 * "goods_sn":"ECS000002","product_id":"0","goods_name":"\u68c9\u68c9\u9774"
	 * ,"market_price":"468.00","goods_price":"300.00","goods_number":"6",
	 * "goods_attr"
	 * :"","is_real":"1","extension_code":"","parent_id":"0","rec_type"
	 * :"0","is_gift"
	 * :"0","is_shipping":"0","can_handsel":"0","goods_attr_id":"",
	 * "pid":"2","integral":"0","subtotal":1800,"goods_thumb":
	 * "http:\/\/58.64.178.2\/images\/201404\/thumb_img\/2_thumb_G_1398134141589118734_105X105.jpg"}],"total":{"goods_price":1800,"market_price":2808,"saving":1008,"save_rate":"36%","goods_amount":1800,"integral":0,"real_goods_count":1,"virtual_goods_count":0}
	 */
	List<InfoGoods> goods_list;
	InfoTotal total;

	
	
	public List<InfoGoods> getGoods_list() {
		return goods_list;
	}



	public InfoTotal getTotal() {
		return total;
	}



	public void setGoods_list(List<InfoGoods> goods_list) {
		this.goods_list = goods_list;
	}



	public void setTotal(InfoTotal total) {
		this.total = total;
	}



	public CartInfo() {
		super();
	}



	public CartInfo(JSONObject job) {
		super(job);
	}



	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if (job.has("goods_list")) {
			goods_list = new ArrayList<InfoGoods>();
			JSONArray jar = job.getJSONArray("goods_list");
			for (int i = 0; i < jar.length(); i++) {
				goods_list.add(new InfoGoods(jar.getJSONObject(i)));
			}
		}
		if (job.has("total"))
			total = new InfoTotal(job.getJSONObject("total"));

	}
}
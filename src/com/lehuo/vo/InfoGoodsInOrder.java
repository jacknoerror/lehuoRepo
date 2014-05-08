package com.lehuo.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;

public class InfoGoodsInOrder extends JsonImport {
	
	  private String goods_price;
	  private String goods_attr;
	 private int goods_number;
	 private int is_commented;
	 private int goods_id;
	  private String goods_name;
	  private String goods_thumb;
	 private int order_id;
	 private int integral;
	 private int rec_id;
	
	public InfoGoodsInOrder() {
		super();
	}

	public InfoGoodsInOrder(JSONObject job) {
		super(job);
	}

	public final String getGoods_price() {
		return goods_price;
	}

	public final void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

	public final String getGoods_attr() {
		return goods_attr;
	}

	public final void setGoods_attr(String goods_attr) {
		this.goods_attr = goods_attr;
	}

	public final int getGoods_number() {
		return goods_number;
	}

	public final void setGoods_number(int goods_number) {
		this.goods_number = goods_number;
	}

	public final int getIs_commented() {
		return is_commented;
	}

	public final void setIs_commented(int is_commented) {
		this.is_commented = is_commented;
	}

	public final int getGoods_id() {
		return goods_id;
	}

	public final void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public final String getGoods_name() {
		return goods_name;
	}

	public final void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public final String getGoods_thumb() {
		return goods_thumb;
	}

	public final void setGoods_thumb(String goods_thumb) {
		this.goods_thumb = goods_thumb;
	}

	public final int getOrder_id() {
		return order_id;
	}

	public final void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public final int getIntegral() {
		return integral;
	}

	public final void setIntegral(int integral) {
		this.integral = integral;
	}

	public final int getRec_id() {
		return rec_id;
	}

	public final void setRec_id(int rec_id) {
		this.rec_id = rec_id;
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("goods_price")) goods_price = job.getString("goods_price");
		if(job.has("goods_attr")) goods_attr = job.getString("goods_attr");
		if(job.has("goods_number")) goods_number = job.getInt("goods_number");
		if(job.has("is_commented")) is_commented = job.getInt("is_commented");
		if(job.has("goods_id")) goods_id = job.getInt("goods_id");
		if(job.has("goods_name")) goods_name = job.getString("goods_name");
		if(job.has("goods_thumb")) goods_thumb = job.getString("goods_thumb");
		if(job.has("order_id")) order_id = job.getInt("order_id");
		if(job.has("integral")) integral = job.getInt("integral");
		if(job.has("rec_id")) rec_id = job.getInt("rec_id");

	}

}

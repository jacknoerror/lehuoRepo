package com.lehuozu.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.entity.json.JsonImport;

public class TongData extends JsonImport {

	private String goods_name;
	private long time;
	private int t_id;
	private int sort;
	private String img_small;
	private int is_goods;
	private int goods_id;
	

	
	public final String getGoods_name() {
		return goods_name;
	}



	public final void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}



	public final long getTime() {
		return time;
	}



	public final void setTime(long time) {
		this.time = time;
	}



	public final int getT_id() {
		return t_id;
	}



	public final void setT_id(int t_id) {
		this.t_id = t_id;
	}



	public final int getSort() {
		return sort;
	}



	public final void setSort(int sort) {
		this.sort = sort;
	}



	public final String getImg_small() {
		return img_small;
	}



	public final void setImg_small(String img_small) {
		this.img_small = img_small;
	}



	public final int getIs_goods() {
		return is_goods;
	}
	
	public final boolean isGoods(){
		return is_goods==1;
	}


	public final void setIs_goods(int is_goods) {
		this.is_goods = is_goods;
	}



	public final int getGoods_id() {
		return goods_id;
	}



	public final void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}



	public TongData() {
		super();
	}



	public TongData(JSONObject job) {
		super(job);
	}



	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("goods_name")) goods_name = job.getString("goods_name");
		if(job.has("time")) time = job.getInt("time");
		if(job.has("t_id")) t_id = job.getInt("t_id");
		if(job.has("sort")) sort = job.getInt("sort");
		if(job.has("img_small")) img_small = job.getString("img_small");
		if(job.has("is_goods")) is_goods = job.getInt("is_goods");
		if(job.has("goods_id")) goods_id = job.getInt("goods_id");

	}

}

package com.lehuozu.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.entity.json.JsonImport;
import com.lehuozu.ui.adapter.MyGridViewAdapter.MyGridAbsJsonPic;

/**
 * @author taotao
 *
 */
public class LehuoPic extends MyGridAbsJsonPic{

	private String thumb_url;
	private String img_url;
	private String img_desc;
	private int img_id;
	private String img_original;
	private int goods_id;
	
	public LehuoPic() {
		super();
	}

	public LehuoPic(JSONObject job) {
		super(job);
	}

	public final String getThumb_url() {
		return thumb_url;
	}

	public final void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

	public final String getImg_url() {
		return img_url;
	}

	public final void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public final String getImg_desc() {
		return img_desc;
	}

	public final void setImg_desc(String img_desc) {
		this.img_desc = img_desc;
	}

	public final int getImg_id() {
		return img_id;
	}

	public final void setImg_id(int img_id) {
		this.img_id = img_id;
	}

	public final String getImg_original() {
		return img_original;
	}

	public final void setImg_original(String img_original) {
		this.img_original = img_original;
	}

	public final int getGoods_id() {
		return goods_id;
	}

	public final void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("thumb_url")) setThumb_url(job.getString("thumb_url"));
		if(job.has("img_url")) setImg_url(job.getString("img_url"));
		if(job.has("img_desc")) setImg_desc(job.getString("img_desc"));
		if(job.has("img_id")) setImg_id(job.getInt("img_id"));
		if(job.has("img_original")) setImg_original(job.getString("img_original"));
		if(job.has("goods_id")) setGoods_id(job.getInt("goods_id"));


	}

	@Override
	public String getPicUrl() {
		return img_url;
	}

}

package com.lehuozu.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.ui.adapter.MyGridViewAdapter.MyGridAbsJsonPic;

public class Category extends MyGridAbsJsonPic{

	private String cate_original_img;
	private String cate_thumb;
	private int cat_id;
	private String cate_img;
	private String cat_desc;
	private String measure_unit;
	private String cat_name;
	
	
	
	public Category() {
		super();
	}

	public Category(JSONObject job) {
		super(job);
	}

	public final String getCate_original_img() {
		return cate_original_img;
	}

	public final void setCate_original_img(String cate_original_img) {
		this.cate_original_img = cate_original_img;
	}

	public final String getCate_thumb() {
		return cate_thumb;
	}

	public final void setCate_thumb(String cate_thumb) {
		this.cate_thumb = cate_thumb;
	}


	public final int getCat_id() {
		return cat_id;
	}

	public final void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}

	public final String getCate_img() {
		return cate_img;
	}

	public final void setCate_img(String cate_img) {
		this.cate_img = cate_img;
	}

	public final String getCat_desc() {
		return cat_desc;
	}

	public final void setCat_desc(String cat_desc) {
		this.cat_desc = cat_desc;
	}

	public final String getMeasure_unit() {
		return measure_unit;
	}

	public final void setMeasure_unit(String measure_unit) {
		this.measure_unit = measure_unit;
	}

	public final String getCat_name() {
		return cat_name;
	}

	public final void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("cate_original_img")) setCate_original_img(job.getString("cate_original_img"));
		if(job.has("cate_thumb")) setCate_thumb(job.getString("cate_thumb"));
		if(job.has("cat_id")) setCat_id(job.getInt("cat_id"));
		if(job.has("cate_img")) setCate_img(job.getString("cate_img"));
		if(job.has("cat_desc")) setCat_desc(job.getString("cat_desc"));
		if(job.has("measure_unit")) setMeasure_unit(job.getString("measure_unit"));
		if(job.has("cat_name")) setCat_name(job.getString("cat_name"));
	}

	@Override
	public String getPicUrl() {
		return cate_img;
	}
	
}

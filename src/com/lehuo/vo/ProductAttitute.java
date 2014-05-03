package com.lehuo.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;

public class ProductAttitute extends JsonImport {
	private String attr_value;
	private String attr_name;
	
	
	
	public ProductAttitute() {
		super();
	}


	public ProductAttitute(JSONObject job) {
		super(job);
	}


	public final String getAttr_value() {
		return attr_value;
	}


	public final void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}


	public final String getAttr_name() {
		return attr_name;
	}


	public final void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}


	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("attr_value")) setAttr_value(job.getString("attr_value"));
		if(job.has("attr_name")) setAttr_name(job.getString("attr_name"));

	}

}

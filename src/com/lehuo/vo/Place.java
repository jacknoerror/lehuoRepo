/**
 * 
 */
package com.lehuo.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;

/**
 * @author tao
 *省份／城市／地区 接口的数据类型
 */
public class Place extends JsonImport {

	int region_id;
	String region_name;
	
	
	public Place() {
		super();
	}


	public Place(int region_id, String region_name) {
		super();
		this.region_id = region_id;
		this.region_name = region_name;
	}


	public Place(JSONObject job) {
		super(job);
	}


	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("region_id"))region_id = job.getInt("region_id");
		if(job.has("region_name"))region_name = job.getString("region_name");

	}


	public int getRegion_id() {
		return region_id;
	}


	public String getRegion_name() {
		return region_name;
	}


	public void setRegion_id(int region_id) {
		this.region_id = region_id;
	}


	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

}

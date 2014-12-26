package com.lehuozu.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.entity.json.JsonImport;

/**
 * @author tao
 *
 */
public class ProductComment extends JsonImport {
	private String content;
	private int id;
	private int rank;
	private String username;
	private String add_time;
	private int  is_real;//0
	

	public String getContent() {
		return content;
	}


	public int getId() {
		return id;
	}


	public int getRank() {
		return rank;
	}


	public String getUsername() {
		return username;
	}


	public String getAdd_time() {
		return add_time;
	}


	public int getIs_real() {
		return is_real;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setRank(int rank) {
		this.rank = rank;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}


	public void setIs_real(int is_real) {
		this.is_real = is_real;
	}


	public ProductComment() {
		super();
	}


	public ProductComment(JSONObject job) {
		super(job);
	}


	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("content")) content = job.getString("content");
		if(job.has("id")) id = job.getInt("id");
		if(job.has("rank")) rank = job.getInt("rank");
		if(job.has("username")) username = job.getString("username");
		if(job.has("add_time")) add_time = job.getString("add_time");
		if(job.has("is_real")) is_real = job.getInt("is_real");

	}

}

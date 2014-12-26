package com.lehuozu.vo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.entity.json.JsonImport;

public class Brand extends JsonImport {
	private String brand_desc;
	private String logo_thumb;
	private LehuoPic[] certs;
	private String logo_large;
	private String logo_medium;
	private String brand_name;

	public final String getBrand_desc() {
		return brand_desc;
	}

	public final void setBrand_desc(String brand_desc) {
		this.brand_desc = brand_desc;
	}

	public final String getLogo_thumb() {
		return logo_thumb;
	}

	public final void setLogo_thumb(String logo_thumb) {
		this.logo_thumb = logo_thumb;
	}

	public final String getLogo_large() {
		return logo_large;
	}

	public final void setLogo_large(String logo_large) {
		this.logo_large = logo_large;
	}

	public final String getLogo_medium() {
		return logo_medium;
	}

	public final void setLogo_medium(String logo_medium) {
		this.logo_medium = logo_medium;
	}

	public final LehuoPic[] getCerts() {
		return certs;
	}

	List<LehuoPic> certList;

	public final List<LehuoPic> getCertList() {
		return certList;
	}

	public final void setCerts(LehuoPic[] certs) {
		this.certs = certs;
	}

	public final String getBrand_name() {
		return brand_name;
	}

	public final void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public Brand() {
		super();
	}

	public Brand(JSONObject job) {
		super(job);
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if (job.has("brand_desc"))
			setBrand_desc(job.getString("brand_desc"));
		if (job.has("logo_thumb"))
			setLogo_thumb(job.getString("logo_thumb"));
		if (job.has("brand_name"))
			setBrand_name(job.getString("brand_name"));
		if (job.has("logo_large"))
			setLogo_large(job.getString("logo_large"));
		if (job.has("logo_medium"))
			setLogo_medium(job.getString("logo_medium"));
		// if(job.has("brand_name")) setCerts(job.getString("brand_name"));
		if (job.has("certs")) {
			JSONArray jar = job.getJSONArray("certs");
			certs = new LehuoPic[jar.length()];
			certList = new ArrayList<LehuoPic>();
			for (int i = 0; i < jar.length(); i++) {
				certs[i] = new LehuoPic(jar.getJSONObject(i));
				certList.add(certs[i]);
			}
		}
	}

}

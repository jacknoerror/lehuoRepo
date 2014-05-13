/**
 * 
 */
package com.lehuo.vo.deliver;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;

/**
 * @author tao
 *
 */
public class CourierInfo extends JsonImport {

	private String logo_small;
	private String mobile_phone;
	private String time;
	private double longitude;
	private double latitude;
	private String truename;
	private String addressname;
	
	
	public String getLogo_small() {
		return logo_small;
	}


	public String getMobile_phone() {
		return mobile_phone;
	}


	public String getTime() {
		return time;
	}


	public double getLongitude() {
		return longitude;
	}


	public double getLatitude() {
		return latitude;
	}


	public String getTruename() {
		return truename;
	}


	public String getAddressname() {
		return addressname;
	}


	public void setLogo_small(String logo_small) {
		this.logo_small = logo_small;
	}


	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public void setTruename(String truename) {
		this.truename = truename;
	}


	public void setAddressname(String addressname) {
		this.addressname = addressname;
	}


	public CourierInfo() {
		super();
	}


	public CourierInfo(JSONObject job) {
		super(job);
	}


	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("logo_small")) logo_small = job.getString("logo_small");
		if(job.has("mobile_phone")) mobile_phone = job.getString("mobile_phone");
		if(job.has("time")) time = job.getString("time");
		if(job.has("longitude")) longitude = job.getDouble("longitude");
		if(job.has("latitude")) latitude = job.getDouble("latitude");
		if(job.has("truename")) truename = job.getString("truename");
		if(job.has("addressname")) addressname = job.getString("addressname");


	}

}

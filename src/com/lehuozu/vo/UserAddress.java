package com.lehuozu.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.entity.json.JsonImport;

public class UserAddress extends JsonImport {

	 private String address;
	  private int address_id;
	  private String consignee;
	  private String zipcode;
	  private String province;
	  private int is_default;
	  private int user_id;
	  private String district;
	  private String mobile;
	  private String city;
	  private String country;
	
	public UserAddress() {
		super();
	}

	public UserAddress(JSONObject job) {
		super(job);
	}
	

	public final String getAddress() {
		return address;
	}

	public final void setAddress(String address) {
		this.address = address;
	}

	public final int getAddress_id() {
		return address_id;
	}

	public final void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public final String getConsignee() {
		return consignee;
	}

	public final void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public final String getZipcode() {
		return zipcode;
	}

	public final void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public final String getProvince() {
		return province;
	}

	public final void setProvince(String province) {
		this.province = province;
	}

	public final boolean isDefault() {
		return is_default==1;
	}

	public final void setDefault(boolean isDefualt){
		this.is_default = isDefualt?1:0;//0?
	}
	public final void setIs_default(int is_default) {
		this.is_default = is_default;
	}

	public final int getUser_id() {
		return user_id;
	}

	public final void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public final String getDistrict() {
		return district;
	}

	public final void setDistrict(String district) {
		this.district = district;
	}

	public final String getMobile() {
		return mobile;
	}

	public final void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public final String getCity() {
		return city;
	}

	public final void setCity(String city) {
		this.city = city;
	}

	public final String getCountry() {
		return country;
	}

	public final void setCountry(String country) {
		this.country = country;
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		 if(job.has("address")) address = job.getString("address");
		 if(job.has("address_id")) address_id = job.getInt("address_id");
		 if(job.has("consignee")) consignee = job.getString("consignee");
		 if(job.has("zipcode")) zipcode = job.getString("zipcode");
		 if(job.has("province")) province = job.getString("province");
		 if(job.has("is_default")) is_default = job.getInt("is_default");
		 if(job.has("user_id")) user_id = job.getInt("user_id");
		 if(job.has("district")) district = job.getString("district");
		 if(job.has("mobile")) mobile = job.getString("mobile");
		 if(job.has("city")) city = job.getString("city");
		 if(job.has("country")) country = job.getString("country");

	}

}

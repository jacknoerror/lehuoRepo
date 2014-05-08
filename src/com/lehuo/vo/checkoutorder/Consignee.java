/**
 * 
 */
package com.lehuo.vo.checkoutorder;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;

/**
 * @author tao
 *确认订单中de收件地址
 */
public class Consignee extends JsonImport {
	private String sign_building;
	 private String consignee;
	 private String tel;
	 private String zipcode;
	 private int is_default;
	 private String city;
	 private String country;
	 private String address;
	 private String email;
	 private int address_id;
	 private String address_name;
	 private String province;
	 private int user_id;
	 private String district;
	 private String best_time;
	 private String mobile;
	 
	public String getSign_building() {
		return sign_building;
	}

	public String getConsignee() {
		return consignee;
	}

	public String getTel() {
		return tel;
	}

	public String getZipcode() {
		return zipcode;
	}

	public int getIs_default() {
		return is_default;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public int getAddress_id() {
		return address_id;
	}

	public String getAddress_name() {
		return address_name;
	}

	public String getProvince() {
		return province;
	}

	public int getUser_id() {
		return user_id;
	}

	public String getDistrict() {
		return district;
	}

	public String getBest_time() {
		return best_time;
	}

	public String getMobile() {
		return mobile;
	}

	public void setSign_building(String sign_building) {
		this.sign_building = sign_building;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setBest_time(String best_time) {
		this.best_time = best_time;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Consignee() {
		super();
	}

	public Consignee(JSONObject job) {
		super(job);
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("sign_building")) sign_building = job.getString("sign_building");
		 if(job.has("consignee")) consignee = job.getString("consignee");
		 if(job.has("tel")) tel = job.getString("tel");
		 if(job.has("zipcode")) zipcode = job.getString("zipcode");
		 if(job.has("is_default")) is_default = job.getInt("is_default");
		 if(job.has("city")) city = job.getString("city");
		 if(job.has("country")) country = job.getString("country");
		 if(job.has("address")) address = job.getString("address");
		 if(job.has("email")) email = job.getString("email");
		 if(job.has("address_id")) address_id = job.getInt("address_id");
		 if(job.has("address_name")) address_name = job.getString("address_name");
		 if(job.has("province")) province = job.getString("province");
		 if(job.has("user_id")) user_id = job.getInt("user_id");
		 if(job.has("district")) district = job.getString("district");
		 if(job.has("best_time")) best_time = job.getString("best_time");
		 if(job.has("mobile")) mobile = job.getString("mobile");
	}

}

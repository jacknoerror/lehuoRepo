/**
 * 
 */
package com.lehuo.vo.deliver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;
import com.lehuo.ui.custom.list.MspJsonItem;

/**
 * @author tao
 *
 */
public class OrderInCourier extends MspJsonItem {
	private String consignee;
	private JSONArray goods;
	private String order_status_state;
	private int order_id;
	private String order_status;
	private String pay_status;//pay_status 已付款,则按钮不能点击
	private String pay_method;
	private String address;
	private String order_time;
	private int nums;
	private String total_fee;
	private String shipping_status;
	private String order_sn;
	private int courier_status;//0:未开始配送 1:正在配送中 2:配送已完成
	private String district;
	private String best_time;
	private String mobile;
	
	

	public String getConsignee() {
		return consignee;
	}

	public JSONArray getGoods() {
		return goods;
	}

	public String getOrder_status_state() {
		return order_status_state;
	}

	public int getOrder_id() {
		return order_id;
	}

	public String getOrder_status() {
		return order_status;
	}

	public String getPay_status() {
		return pay_status;
	}

	public String getPay_method() {
		return pay_method;
	}

	public String getAddress() {
		return address;
	}

	public String getOrder_time() {
		return order_time;
	}

	public int getNums() {
		return nums;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public String getShipping_status() {
		return shipping_status;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public int getCourier_status() {
		return courier_status;
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

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public void setGoods(JSONArray goods) {
		this.goods = goods;
	}

	public void setOrder_status_state(String order_status_state) {
		this.order_status_state = order_status_state;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public void setShipping_status(String shipping_status) {
		this.shipping_status = shipping_status;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public void setCourier_status(int courier_status) {
		this.courier_status = courier_status;
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

	/**
	 * 
	 */
	public OrderInCourier() {
	}

	/**
	 * @param job
	 */
	public OrderInCourier(JSONObject job) {
		super(job);
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("goods")) goods = job.getJSONArray("goods");
		if(job.has("consignee")) consignee = job.getString("consignee");
		if(job.has("order_status_state")) order_status_state = job.getString("order_status_state");
		if(job.has("order_id")) order_id = job.getInt("order_id");
		if(job.has("order_status")) order_status = job.getString("order_status");
		if(job.has("pay_status")) pay_status = job.getString("pay_status");
		if(job.has("pay_method")) pay_method = job.getString("pay_method");
		if(job.has("address")) address = job.getString("address");
		if(job.has("order_time")) order_time = job.getString("order_time");
		if(job.has("nums")) nums = job.getInt("nums");
		if(job.has("total_fee")) total_fee = job.getString("total_fee");
		if(job.has("shipping_status")) shipping_status = job.getString("shipping_status");
		if(job.has("order_sn")) order_sn = job.getString("order_sn");
		if(job.has("courier_status")) courier_status = job.getInt("courier_status");
		if(job.has("district")) district = job.getString("district");
		if(job.has("best_time")) best_time = job.getString("best_time");
		if(job.has("mobile")) mobile = job.getString("mobile");
		
		
	}

}

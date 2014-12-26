package com.lehuozu.vo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.ui.custom.list.MspJsonItem;

public class OrderInfo extends MspJsonItem {
	private JSONArray goods;
	private int courier_id;
	 private String order_status_state;
	private int order_id;
	private int order_status;
	private int pay_status;
	 private String courier_truename;
	 private String order_time;
	private int nums;
	 private String total_fee;
	private int shipping_status;
	 private String order_sn;
	private int need_integral;
	private int courier_status;

	public final JSONArray getGoods() {
		return goods;
	}

	public final void setGoods(JSONArray goods) {
		this.goods = goods;
	}

	public final int getCourier_id() {
		return courier_id;
	}

	public final void setCourier_id(int courier_id) {
		this.courier_id = courier_id;
	}


	public final String getOrder_status_state() {
		return order_status_state;
	}

	public final void setOrder_status_state(String order_status_state) {
		this.order_status_state = order_status_state;
	}

	public final int getOrder_id() {
		return order_id;
	}

	public final void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public final int getOrder_status() {
		return order_status;
	}

	public final void setOrder_status(int order_status) {
		this.order_status = order_status;
	}

	public final int getPay_status() {
		return pay_status;
	}

	public final void setPay_status(int pay_status) {
		this.pay_status = pay_status;
	}

	public final String getCourier_truename() {
		return courier_truename;
	}

	public final void setCourier_truename(String courier_truename) {
		this.courier_truename = courier_truename;
	}

	public final String getOrder_time() {
		return order_time;
	}

	public final void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public final int getNums() {
		return nums;
	}

	public final void setNums(int nums) {
		this.nums = nums;
	}

	public final String getTotal_fee() {
		return total_fee;
	}

	public final void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public final int getShipping_status() {
		return shipping_status;
	}

	public final void setShipping_status(int shipping_status) {
		this.shipping_status = shipping_status;
	}

	public final String getOrder_sn() {
		return order_sn;
	}

	public final void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public final int getNeed_integral() {
		return need_integral;
	}

	public final void setNeed_integral(int need_integral) {
		this.need_integral = need_integral;
	}

	public final int getCourier_status() {
		return courier_status;
	}

	public final void setCourier_status(int courier_status) {
		this.courier_status = courier_status;
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if (job.has("courier_id"))
			courier_id = job.getInt("courier_id");
		if (job.has("goods"))
			goods = job.getJSONArray("goods");
		if (job.has("order_status_state"))
			order_status_state = job.getString("order_status_state");
		if (job.has("order_id"))
			order_id = job.getInt("order_id");
		if (job.has("order_status"))
			order_status = job.getInt("order_status");
		if (job.has("pay_status"))
			pay_status = job.getInt("pay_status");
		if (job.has("courier_truename"))
			courier_truename = job.getString("courier_truename");
		if (job.has("order_time"))
			order_time = job.getString("order_time");
		if (job.has("nums"))
			nums = job.getInt("nums");
		if (job.has("total_fee"))
			total_fee = job.getString("total_fee");
		if (job.has("shipping_status"))
			shipping_status = job.getInt("shipping_status");
		if (job.has("order_sn"))
			order_sn = job.getString("order_sn");
		if (job.has("need_integral"))
			need_integral = job.getInt("need_integral");
		if (job.has("courier_status"))
			courier_status = job.getInt("courier_status");

	}

}

package com.lehuozu.vo.cart;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.entity.json.JsonImport;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.vo.IntPrice;
import com.lehuozu.vo.IntegralPriceImpl;

public class InfoTotal extends JsonImport implements IntegralPriceImpl{

	/*
	 * {"goods_price":1800,"market_price":2808,"saving":1008,"save_rate":"36%",
	 * "goods_amount"
	 * :1800,"integral":0,"real_goods_count":1,"virtual_goods_count":0}
	 */
	private int goods_price;
	private int virtual_goods_count;
	private double market_price;
	private int real_goods_count;
	private String save_rate;
	private int saving;
	private int integral;
	private int goods_amount;
	
	private IntPrice integral_price;
	

	public void setIntegral_price(IntPrice integral_price) {
		this.integral_price = integral_price;
	}

	public int getGoods_price() {
		return goods_price;
	}

	public int getVirtual_goods_count() {
		return virtual_goods_count;
	}

	public double getMarket_price() {
		return market_price;
	}

	public int getReal_goods_count() {
		return real_goods_count;
	}

	public String getSave_rate() {
		return save_rate;
	}

	public int getSaving() {
		return saving;
	}

	public int getIntegral() {
		return integral;
	}

	public int getGoods_amount() {
		return goods_amount;
	}

	public void setGoods_price(int goods_price) {
		this.goods_price = goods_price;
	}

	public void setVirtual_goods_count(int virtual_goods_count) {
		this.virtual_goods_count = virtual_goods_count;
	}

	public void setMarket_price(double market_price) {
		this.market_price = market_price;
	}

	public void setReal_goods_count(int real_goods_count) {
		this.real_goods_count = real_goods_count;
	}

	public void setSave_rate(String save_rate) {
		this.save_rate = save_rate;
	}

	public void setSaving(int saving) {
		this.saving = saving;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public void setGoods_amount(int goods_amount) {
		this.goods_amount = goods_amount;
	}

	public InfoTotal() {
		super();
	}

	public InfoTotal(JSONObject job) {
		super(job);
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if (job.has("goods_price"))
			goods_price = job.getInt("goods_price");
		if (job.has("virtual_goods_count"))
			virtual_goods_count = job.getInt("virtual_goods_count");
		if (job.has("market_price"))
			market_price = job.getDouble("market_price");
		if (job.has("real_goods_count"))
			real_goods_count = job.getInt("real_goods_count");
		if (job.has("save_rate"))
			save_rate = job.getString("save_rate");
		if (job.has("saving"))
			saving = job.getInt("saving");
		if (job.has("integral"))
			integral = job.getInt("integral");
		if (job.has("goods_amount"))
			goods_amount = job.getInt("goods_amount");
		if(job.has("integral_price")) setIntegral_price(new IntPrice(job.getJSONObject("integral_price"))) ;//TODO to be tested
	}

	
	public IntPrice getIntegral_price() {
		return integral_price;
	}

	@Override
	public String getShop_price() {
		return ""+goods_price;
	}

	@Override
	public String getRealPriceStr() {
		return NetStrategies.getRealPrice(this);
	}

}
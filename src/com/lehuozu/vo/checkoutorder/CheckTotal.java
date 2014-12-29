package com.lehuozu.vo.checkoutorder;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.entity.json.JsonImport;

/**
 * @author tao
 * 
 */
public class CheckTotal extends JsonImport {

	private int gift_amount;
	private String bonus_formated;
	private String saving_formated;
	private int real_goods_count;
	private String amount_formated;
	private String market_price_formated;
	private int integral;
	private int amount;
	private int goods_price;
	private String formated_goods_price;
	private String save_rate;
	private String goods_price_formated;
	private int integral_money;
	private String integral_formated;
	private String will_get_bonus;
	private int cod_fee;
	private String shipping_fee_formated;
	private int shipping_fee;
	private String discount;
	private String formated_saving;
	private int will_get_integral;
	private int market_price;
	private String discount_formated;
	private int saving;
	private int bonus;
	private String formated_market_price;

	public int getGift_amount() {
		return gift_amount;
	}

	public String getBonus_formated() {//”≈ª›»Ø
		return bonus_formated;
	}

	public String getSaving_formated() {
		return saving_formated;
	}

	public int getReal_goods_count() {
		return real_goods_count;
	}

	public String getAmount_formated() {
		return amount_formated;
	}

	public String getMarket_price_formated() {
		return market_price_formated;
	}

	public int getIntegral() {
		return integral;
	}

	public int getAmount() {
		return amount;
	}

	public int getGoods_price() {
		return goods_price;
	}

	public String getFormated_goods_price() {
		return formated_goods_price;
	}

	public String getSave_rate() {
		return save_rate;
	}

	public String getGoods_price_formated() {
		return goods_price_formated;
	}

	public int getIntegral_money() {
		return integral_money;
	}

	public String getIntegral_formated() {
		return integral_formated;
	}

	public String getWill_get_bonus() {
		return will_get_bonus;
	}

	public int getCod_fee() {
		return cod_fee;
	}

	public String getShipping_fee_formated() {
		return shipping_fee_formated;
	}

	public int getShipping_fee() {
		return shipping_fee;
	}

	public String getDiscount() {
		return discount;
	}

	public String getFormated_saving() {
		return formated_saving;
	}

	public int getWill_get_integral() {
		return will_get_integral;
	}

	public int getMarket_price() {
		return market_price;
	}

	public String getDiscount_formated() {
		return discount_formated;
	}

	public int getSaving() {
		return saving;
	}

	public int getBonus() {
		return bonus;
	}

	public String getFormated_market_price() {
		return formated_market_price;
	}

	public void setGift_amount(int gift_amount) {
		this.gift_amount = gift_amount;
	}

	public void setBonus_formated(String bonus_formated) {
		this.bonus_formated = bonus_formated;
	}

	public void setSaving_formated(String saving_formated) {
		this.saving_formated = saving_formated;
	}

	public void setReal_goods_count(int real_goods_count) {
		this.real_goods_count = real_goods_count;
	}

	public void setAmount_formated(String amount_formated) {
		this.amount_formated = amount_formated;
	}

	public void setMarket_price_formated(String market_price_formated) {
		this.market_price_formated = market_price_formated;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setGoods_price(int goods_price) {
		this.goods_price = goods_price;
	}

	public void setFormated_goods_price(String formated_goods_price) {
		this.formated_goods_price = formated_goods_price;
	}

	public void setSave_rate(String save_rate) {
		this.save_rate = save_rate;
	}

	public void setGoods_price_formated(String goods_price_formated) {
		this.goods_price_formated = goods_price_formated;
	}

	public void setIntegral_money(int integral_money) {
		this.integral_money = integral_money;
	}

	public void setIntegral_formated(String integral_formated) {
		this.integral_formated = integral_formated;
	}

	public void setWill_get_bonus(String will_get_bonus) {
		this.will_get_bonus = will_get_bonus;
	}

	public void setCod_fee(int cod_fee) {
		this.cod_fee = cod_fee;
	}

	public void setShipping_fee_formated(String shipping_fee_formated) {
		this.shipping_fee_formated = shipping_fee_formated;
	}

	public void setShipping_fee(int shipping_fee) {
		this.shipping_fee = shipping_fee;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public void setFormated_saving(String formated_saving) {
		this.formated_saving = formated_saving;
	}

	public void setWill_get_integral(int will_get_integral) {
		this.will_get_integral = will_get_integral;
	}

	public void setMarket_price(int market_price) {
		this.market_price = market_price;
	}

	public void setDiscount_formated(String discount_formated) {
		this.discount_formated = discount_formated;
	}

	public void setSaving(int saving) {
		this.saving = saving;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public void setFormated_market_price(String formated_market_price) {
		this.formated_market_price = formated_market_price;
	}

	public CheckTotal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckTotal(JSONObject job) {
		super(job);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if (job.has("gift_amount"))
			gift_amount = job.getInt("gift_amount");
		if (job.has("bonus_formated"))
			bonus_formated = job.getString("bonus_formated");
		if (job.has("saving_formated"))
			saving_formated = job.getString("saving_formated");
		if (job.has("real_goods_count"))
			real_goods_count = job.getInt("real_goods_count");
		if (job.has("amount_formated"))
			amount_formated = job.getString("amount_formated");
		if (job.has("market_price_formated"))
			market_price_formated = job.getString("market_price_formated");
		if (job.has("integral"))
			integral = job.getInt("integral");
		if (job.has("amount"))
			amount = job.getInt("amount");
		if (job.has("goods_price"))
			goods_price = job.getInt("goods_price");
		if (job.has("formated_goods_price"))
			formated_goods_price = job.getString("formated_goods_price");
		if (job.has("save_rate"))
			save_rate = job.getString("save_rate");
		if (job.has("goods_price_formated"))
			goods_price_formated = job.getString("goods_price_formated");
		if (job.has("integral_money"))
			integral_money = job.getInt("integral_money");
		if (job.has("integral_formated"))
			integral_formated = job.getString("integral_formated");
		if (job.has("will_get_bonus"))
			will_get_bonus = job.getString("will_get_bonus");
		if (job.has("cod_fee"))
			cod_fee = job.getInt("cod_fee");
		if (job.has("shipping_fee_formated"))
			shipping_fee_formated = job.getString("shipping_fee_formated");
		if (job.has("shipping_fee"))
			shipping_fee = job.getInt("shipping_fee");
		if (job.has("discount"))
			discount = job.getString("discount");
		if (job.has("formated_saving"))
			formated_saving = job.getString("formated_saving");
		if (job.has("will_get_integral"))
			will_get_integral = job.getInt("will_get_integral");
		if (job.has("market_price"))
			market_price = job.getInt("market_price");
		if (job.has("discount_formated"))
			discount_formated = job.getString("discount_formated");
		if (job.has("saving"))
			saving = job.getInt("saving");
		if (job.has("bonus"))
			bonus = job.getInt("bonus");
		if (job.has("formated_market_price"))
			formated_market_price = job.getString("formated_market_price");

	}

}

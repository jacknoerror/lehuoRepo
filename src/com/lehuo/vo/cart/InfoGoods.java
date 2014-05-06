package com.lehuo.vo.cart;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;

public class InfoGoods extends JsonImport {
	/*
	 * {"rec_id":"101","user_id":"28","session_id":"","goods_id":"2","goods_sn":
	 * "ECS000002"
	 * ,"product_id":"0","goods_name":"\u68c9\u68c9\u9774","market_price"
	 * :"468.00"
	 * ,"goods_price":"300.00","goods_number":"6","goods_attr":"","is_real"
	 * :"1","extension_code"
	 * :"","parent_id":"0","rec_type":"0","is_gift":"0","is_shipping"
	 * :"0","can_handsel"
	 * :"0","goods_attr_id":"","pid":"2","integral":"0","subtotal"
	 * :1800,"goods_thumb":
	 * "http:\/\/58.64.178.2\/images\/201404\/thumb_img\/2_thumb_G_1398134141589118734_105X105.jpg"}
	 */
	private int can_handsel;
	private String goods_sn;
	private String session_id;
	private int is_gift;
	private int product_id;
	private int is_real;
	private int goods_number;
	private String goods_name;
	private int pid;
	private int subtotal;
	private int integral;
	private int is_shipping;
	private String goods_attr;
	private String goods_price;
	private String goods_attr_id;
	private String market_price;
	private int rec_type;
	private int goods_id;
	private String extension_code;
	private int user_id;
	private String goods_thumb;
	private int parent_id;
	private int rec_id;

	
	
	public int getCan_handsel() {
		return can_handsel;
	}

	public String getGoods_sn() {
		return goods_sn;
	}

	public String getSession_id() {
		return session_id;
	}

	public int getIs_gift() {
		return is_gift;
	}

	public int getProduct_id() {
		return product_id;
	}

	public int getIs_real() {
		return is_real;
	}

	public int getGoods_number() {
		return goods_number;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public int getPid() {
		return pid;
	}

	public int getSubtotal() {
		return subtotal;
	}

	public int getIntegral() {
		return integral;
	}

	public int getIs_shipping() {
		return is_shipping;
	}

	public String getGoods_attr() {
		return goods_attr;
	}

	public String getGoods_price() {
		return goods_price;
	}

	public String getGoods_attr_id() {
		return goods_attr_id;
	}

	public String getMarket_price() {
		return market_price;
	}

	public int getRec_type() {
		return rec_type;
	}

	public int getGoods_id() {
		return goods_id;
	}

	public String getExtension_code() {
		return extension_code;
	}

	public int getUser_id() {
		return user_id;
	}

	public String getGoods_thumb() {
		return goods_thumb;
	}

	public int getParent_id() {
		return parent_id;
	}

	public int getRec_id() {
		return rec_id;
	}

	public void setCan_handsel(int can_handsel) {
		this.can_handsel = can_handsel;
	}

	public void setGoods_sn(String goods_sn) {
		this.goods_sn = goods_sn;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public void setIs_gift(int is_gift) {
		this.is_gift = is_gift;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public void setIs_real(int is_real) {
		this.is_real = is_real;
	}

	public void setGoods_number(int goods_number) {
		this.goods_number = goods_number;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public void setIs_shipping(int is_shipping) {
		this.is_shipping = is_shipping;
	}

	public void setGoods_attr(String goods_attr) {
		this.goods_attr = goods_attr;
	}

	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

	public void setGoods_attr_id(String goods_attr_id) {
		this.goods_attr_id = goods_attr_id;
	}

	public void setMarket_price(String market_price) {
		this.market_price = market_price;
	}

	public void setRec_type(int rec_type) {
		this.rec_type = rec_type;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public void setExtension_code(String extension_code) {
		this.extension_code = extension_code;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public void setGoods_thumb(String goods_thumb) {
		this.goods_thumb = goods_thumb;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public void setRec_id(int rec_id) {
		this.rec_id = rec_id;
	}

	public InfoGoods() {
		super();
	}

	public InfoGoods(JSONObject job) {
		super(job);
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("can_handsel")) can_handsel = job.getInt("can_handsel");
		if(job.has("goods_sn")) goods_sn = job.getString("goods_sn");
		if(job.has("session_id")) session_id = job.getString("session_id");
		if(job.has("is_gift")) is_gift = job.getInt("is_gift");
		if(job.has("product_id")) product_id = job.getInt("product_id");
		if(job.has("is_real")) is_real = job.getInt("is_real");
		if(job.has("goods_number")) goods_number = job.getInt("goods_number");
		if(job.has("goods_name")) goods_name = job.getString("goods_name");
		if(job.has("pid")) pid = job.getInt("pid");
		if(job.has("subtotal")) subtotal = job.getInt("subtotal");
		if(job.has("integral")) integral = job.getInt("integral");
		if(job.has("is_shipping")) is_shipping = job.getInt("is_shipping");
		if(job.has("goods_attr")) goods_attr = job.getString("goods_attr");
		if(job.has("goods_price")) goods_price = job.getString("goods_price");
		if(job.has("goods_attr_id")) goods_attr_id = job.getString("goods_attr_id");
		if(job.has("market_price")) market_price = job.getString("market_price");
		if(job.has("rec_type")) rec_type = job.getInt("rec_type");
		if(job.has("goods_id")) goods_id = job.getInt("goods_id");
		if(job.has("extension_code")) extension_code = job.getString("extension_code");
		if(job.has("user_id")) user_id = job.getInt("user_id");
		if(job.has("goods_thumb")) goods_thumb = job.getString("goods_thumb");
		if(job.has("parent_id")) parent_id = job.getInt("parent_id");
		if(job.has("rec_id")) rec_id = job.getInt("rec_id");



	}

}
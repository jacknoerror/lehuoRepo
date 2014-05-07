package com.lehuo.vo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;
import com.lehuo.net.NetStrategies;
/**
 * 产品goods。有简单和详细之分，都是这个类
 * @author taotao
 *
 */
public class Product extends JsonImport implements IntegralPriceImpl{

	private String promote_end_date;
	private int click_count;
	private String goods_sn;
	private String promote_start_date;
	private String goods_desc;
	private JSONArray gallery;
	private int goods_number;
	private int brand_id;
	private String goods_name;
	private String goods_img;
	private String promote_price;
	private int integral;
	private String goods_brief;
	private int cat_id;
	private String last_update;
	private String shop_price;
	private String market_price;
	private int goods_id;
	private String add_time;
	private String goods_thumb;
	private String goods_name_style;
	
	private String keywords;
	private IntPrice integral_price;
	
	
	
	public Product() {
		super();
	}

	public Product(JSONObject job) {
		super(job);
	}

	
	
	public final int getClick_count() {
		return click_count;
	}

	public final void setClick_count(int click_count) {
		this.click_count = click_count;
	}

	public final String getGoods_sn() {
		return goods_sn;
	}

	public final void setGoods_sn(String goods_sn) {
		this.goods_sn = goods_sn;
	}

	public final JSONArray getGallery() {
		return gallery!=null?gallery:new JSONArray();
	}

	public final void setGallery(JSONArray gallery) {
		this.gallery = gallery;
	}

	public final int getBrand_id() {
		return brand_id;
	}

	public final void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
	}

	public final int getIntegral() {
		return integral;
	}

	public final void setIntegral(int integral) {
		this.integral = integral;
	}

	public final int getCat_id() {
		return cat_id;
	}

	public final void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}

	public final String getAdd_time() {
		return add_time;
	}

	public final void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public final String getGoods_name_style() {
		return goods_name_style;
	}

	public final void setGoods_name_style(String goods_name_style) {
		this.goods_name_style = goods_name_style;
	}

	public final void setShop_price(String shop_price) {
		this.shop_price = shop_price;
	}

	public final String getPromote_end_date() {
		return promote_end_date;
	}

	public final void setPromote_end_date(String promote_end_date) {
		this.promote_end_date = promote_end_date;
	}


	public final String getPromote_start_date() {
		return promote_start_date;
	}

	public final void setPromote_start_date(String promote_start_date) {
		this.promote_start_date = promote_start_date;
	}

	public final String getGoods_desc() {
		return goods_desc;
	}

	public final void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}

	public final String getKeywords() {
		return keywords;
	}

	public final void setKeywords(String keywords) {
		this.keywords = keywords;
	}


	public final int getGoods_number() {
		return goods_number;
	}

	public final void setGoods_number(int goods_number) {
		this.goods_number = goods_number;
	}

	public final String getGoods_name() {
		return goods_name;
	}

	public final void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public final String getGoods_img() {
		return goods_img;
	}

	public final void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}


	public final String getGoods_brief() {
		return goods_brief;
	}

	public final void setGoods_brief(String goods_brief) {
		this.goods_brief = goods_brief;
	}

	public final String getLast_update() {
		return last_update;
	}

	public final void setLast_update(String last_update) {
		this.last_update = last_update;
	}



	public final String getShop_price() {
		return shop_price;
	}

	public final String getPromote_price() {
		return promote_price;
	}

	public final void setPromote_price(String promote_price) {
		this.promote_price = promote_price;
	}

	public final String getMarket_price() {
		return market_price;
	}

	public final void setMarket_price(String market_price) {
		this.market_price = market_price;
	}

	public final int getGoods_id() {
		return goods_id;
	}

	public final void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public final String getGoods_thumb() {
		return goods_thumb;
	}

	public final void setGoods_thumb(String goods_thumb) {
		this.goods_thumb = goods_thumb;
	}

	public final IntPrice getIntegral_price() {
		return integral_price;
	}

	public final void setIntegral_price(IntPrice integral_price) {
		this.integral_price = integral_price;
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("promote_end_date")) setPromote_end_date(job.getString("promote_end_date"));
		if(job.has("promote_price")) setPromote_price(job.getString("promote_price"));
		if(job.has("promote_start_date")) setPromote_start_date(job.getString("promote_start_date"));
		if(job.has("goods_desc")) setGoods_desc(job.getString("goods_desc"));
		if(job.has("keywords")) setKeywords(job.getString("keywords"));
		if(job.has("integral_price")) setIntegral_price(new IntPrice(job.getJSONObject("integral_price"))) ;
		if(job.has("goods_number")) setGoods_number(job.getInt("goods_number"));
		if(job.has("goods_name")) setGoods_name(job.getString("goods_name"));
		if(job.has("goods_img")) setGoods_img(job.getString("goods_img"));
		if(job.has("goods_brief")) setGoods_brief(job.getString("goods_brief"));
		if(job.has("last_update")) setLast_update(job.getString("last_update"));
		if(job.has("shop_price")) setShop_price(job.getString("shop_price"));
		if(job.has("market_price")) setMarket_price(job.getString("market_price"));
		if(job.has("goods_id")) setGoods_id(job.getInt("goods_id"));
		if(job.has("goods_thumb")) setGoods_thumb(job.getString("goods_thumb"));
		
		if(job.has("click_count")) setClick_count(job.getInt("click_count"));
		if(job.has("goods_sn")) setGoods_sn(job.getString("goods_sn"));
		if(job.has("gallery")) setGallery(job.getJSONArray("gallery"));
		if(job.has("brand_id")) setBrand_id(job.getInt("brand_id"));
		if(job.has("integral")) setIntegral(job.getInt("integral"));
		if(job.has("cat_id")) setCat_id(job.getInt("cat_id"));
		if(job.has("shop_price")) setShop_price(job.getString("shop_price"));
		if(job.has("add_time")) setAdd_time(job.getString("add_time"));
		if(job.has("goods_name_style")) setGoods_name_style(job.getString("goods_name_style"));
	}

	@Override
	public String getRealPriceStr() {
		return NetStrategies.getRealPrice(this);
	}
}

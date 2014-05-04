package com.lehuo.net.action.order;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.lehuo.data.NetConst;
import com.lehuo.entity.json.JackJson;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.vo.AddCartGoodsStandard;

/**
 * @author tao
 *添加购物车 接口
 * 只有goodsid和userid会变
 * 添加goods到cart时使用
 */
public class AddCartReq implements ActionPhpRequestImpl {

	JackJson goods ;/*参数为Json格式字符串
	{"getattr":1,"spec":[],"goods_id":1,"number":"1","parent":0}
	getattr 获取产品规格
	spec 为产品规格（如果是为获取产品规格，则为空）
	number 在获取产品规格是可以为空，或者不要
	parent 产品的父ID，如果没有就为0*/
	Integer user_id ;//用户ID号

	
	public AddCartReq(JackJson goods, Integer user_id) {
		super();
		this.goods = goods;
		this.user_id = user_id;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_ORDER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_ADDCART;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_GOODS,goods.toJsonObj().toString());//
		if(user_id!=null)halfway.put(PARAMS_USER_ID,user_id+"");

		return halfway;
	}
	
	
	public static class CartGoods extends JackJson{
		int getattr;
		JSONArray spec;
		int goods_id;
		String number;
		int parent ;
		
		public CartGoods(int goods_id){
			this.goods_id = goods_id;
			getattr = 0;
			spec = new JSONArray();
			number = "1";
			parent = 0;
		}
		
		
		@Override
		public JSONObject toJsonObj() {
			JSONObject job = new JSONObject();
			try {
				job.put("c", getattr);
				job.put("spec", spec);
				job.put("goods_id", goods_id);
				job.put("number", number);
				job.put("parent", parent);
			} catch (JSONException e) {
				Log.e("CartGoods", "json error:"+e.getMessage());
			}
			return job;
		}

		@Override
		public void initJackJson(JSONObject job) throws JSONException {
			if(job.has("getattr")) getattr = job.getInt("getattr");
			if(job.has("spec")) spec = job.getJSONArray("spec");
			if(job.has("goods_id")) goods_id = job.getInt("goods_id");
			if(job.has("number")) number = job.getString("number");
			if(job.has("parent")) parent = job.getInt("parent");
			
		}
		
	}
}

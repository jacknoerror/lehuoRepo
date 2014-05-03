package com.lehuo.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;

public class CartInfo extends JsonImport {
//result=>{"result":true,"data":
	//{"cart":{"goods_list":[],
	//	"total":{"goods_price":0,"market_price":0,"saving":0,"save_rate":0,"goods_amount":0,
			//"integral":0,"real_goods_count":0,"virtual_goods_count":0}}},"message":""}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		// TODO Auto-generated method stub

	}

	class CITotal extends JsonImport{

		@Override
		public void initJackJson(JSONObject job) throws JSONException {
			// TODO Auto-generated method stub
			
		}
		
	}
}

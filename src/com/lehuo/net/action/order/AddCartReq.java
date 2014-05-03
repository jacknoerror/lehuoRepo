package com.lehuo.net.action.order;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.lehuo.data.NetConst;
import com.lehuo.entity.json.JackJson;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.vo.AddCartGoodsStandard;

/**
 * @author tao
 *添加购物车（获取产品规格）接口
 *（此接口与添加产品到购物车接口参数都相似）
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

}

package com.lehuo.net.action.order;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

/**
 * @author tao
 *更新购物车接口（就只是产品数量）
 */
public class UpdateCart implements ActionPhpRequestImpl {

	int num ;//数量 如果为0 则会删除产品
	int rec_id ;//同上
	int user_id;

	
	public UpdateCart(int num, int rec_id, int user_id) {
		super();
		this.num = num;
		this.rec_id = rec_id;
		this.user_id = user_id;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_ORDER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_UPDATECART;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_NUM,num+"");
		halfway.put(PARAMS_REC_ID,rec_id+"");
		halfway.put(PARAMS_USER_ID,user_id+"");
		return halfway;
	}

}

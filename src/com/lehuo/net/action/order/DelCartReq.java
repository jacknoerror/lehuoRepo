package com.lehuo.net.action.order;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

/**
 * @author tao
 *删除购物车中商品接口
 */
public class DelCartReq implements ActionPhpRequestImpl {

	int rec_id ;//购物车ID
	int user_id;

	
	public DelCartReq(int rec_id, int user_id) {
		super();
		this.rec_id = rec_id;
		this.user_id = user_id;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_ORDER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_DELCART;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_REC_ID,rec_id+"");
		halfway.put(PARAMS_USER_ID,user_id+"");

		return halfway;
	}

}

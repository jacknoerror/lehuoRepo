package com.lehuo.net.action.order;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.SingleIdParamAbsReq;

/**
 * @author tao
 *获取购物车接口
 */
public class GetCartReq extends SingleIdParamAbsReq {

	public GetCartReq(int uid) {
		super(uid);
	}

	@Override
	public String getPhpName() {
		return PHPNAME_ORDER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_GETCART;
	}


	@Override
	public String getIdParamName() {
		return PARAMS_USER_ID;
	}

}

package com.lehuo.net.action.courier;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

public class ConfirmArriveForCourierReq implements ActionPhpRequestImpl {

	int user_id;
	int order_id;

	public ConfirmArriveForCourierReq(int user_id, int order_id) {
		super();
		this.user_id = user_id;
		this.order_id = order_id;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_COURIER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_PAY;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(),
				halfwayParamMap(new HashMap()));

	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_USER_ID, "" + user_id);
		halfway.put(PARAMS_ORDER_ID, "" + order_id);
		return halfway;
	}

}
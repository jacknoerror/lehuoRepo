package com.lehuozu.net.action.order;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;

public class ConfirmArriveReq implements ActionPhpRequestImpl {

	int user_id;
	int order_id;
	
	
	public ConfirmArriveReq(int user_id, int order_id) {
		super();
		this.user_id = user_id;
		this.order_id = order_id;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_ORDER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_CONFIRMRECEIVE;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap()));

	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_USER_ID,user_id+"");
		halfway.put(NetConst.PARAMS_ORDER_ID,order_id+"");
		return halfway;
	}

}

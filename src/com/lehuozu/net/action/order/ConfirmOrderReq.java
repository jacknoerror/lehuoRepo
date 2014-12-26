package com.lehuozu.net.action.order;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;

public class ConfirmOrderReq implements ActionPhpRequestImpl {

	int address_id;
	int bonus_id;//可为空
	int integral;//可为0
	int pay_id;//2，货到付款；1，支付宝客户端；3，支付宝网页
	int user_id;
	String timezone;//送货时段
	
	public ConfirmOrderReq(int address_id, int bonus_id, int integral,
			int pay_id, int user_id, String timezone) {
		super();
		this.address_id = address_id;
		this.bonus_id = bonus_id;
		this.integral = integral;
		this.pay_id = pay_id;
		this.user_id = user_id;
		this.timezone = timezone;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_ORDER;
	}

	@Override
	public String getApiName() {
		return "done";
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_ADDRESS_ID,address_id+"");
		halfway.put(NetConst.PARAMS_BONUS_ID,bonus_id+"");
		halfway.put(NetConst.PARAMS_INTEGRAL,integral+"");
		halfway.put(NetConst.PARAMS_PAY_ID,pay_id+"");
		halfway.put(PARAMS_USER_ID,user_id+"");
		if(null!=timezone)halfway.put(NetConst.PARAMS_TIMEZONE,timezone);
		return halfway;
	}

}

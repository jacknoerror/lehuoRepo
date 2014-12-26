package com.lehuozu.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;

public class SendCodeFpRequest implements ActionPhpRequestImpl {

	String phone ;
	
	public SendCodeFpRequest(String phone) {
		super();
		this.phone = phone;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_FORGETSENDCODE;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put("phone",phone);
		return halfway;
	}

}

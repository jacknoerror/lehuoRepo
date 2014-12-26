package com.lehuozu.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;

public class PwdResetRequest implements ActionPhpRequestImpl {

	private static final String ACTION_FORGETRESET = "forgetreset";
	String phone ;
	String code;
	String password;
	

	public PwdResetRequest(String phone, String code, String password) {
		super();
		this.phone = phone;
		this.code = code;
		this.password = password;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return ACTION_FORGETRESET;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put("phone",phone);
		halfway.put("code",code);
		halfway.put("password",password);
		return halfway;
	}

}

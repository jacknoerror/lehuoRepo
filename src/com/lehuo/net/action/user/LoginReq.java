package com.lehuo.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;


public class LoginReq implements ActionPhpRequestImpl {
	String username; //用户名
	String password; //密码 但必须是md5加密的32位字符串

	public LoginReq(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public String getPhpName() {
		return NetConst.PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_LOGIN;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_USERNAME,username);
		halfway.put(NetConst.PARAMS_PASSWORD,password);
		return halfway;
	}

}

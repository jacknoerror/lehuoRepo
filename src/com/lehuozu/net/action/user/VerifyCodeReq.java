package com.lehuozu.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;

/**
 * @author tao
 *验证手机验证码接口
 */
public class VerifyCodeReq implements ActionPhpRequestImpl {

	String phone ;//手机
	String code ;//验证码
	int user_id ;//用户ID（如果是注册时候则user_id为0）

	
	public VerifyCodeReq(String phone, String code, int user_id) {
		super();
		this.phone = phone;
		this.code = code;
		this.user_id = user_id;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_VERIFYCODE;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_PHONE,phone);
		halfway.put(NetConst.PARAMS_CODE,code);
		halfway.put(NetConst.PARAMS_USER_ID,user_id+"");
		return halfway;
	}

}

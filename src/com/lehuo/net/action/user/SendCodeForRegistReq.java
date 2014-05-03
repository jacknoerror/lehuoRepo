package com.lehuo.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

/**
 * @author tao
 *发送验证码（注册时）
 *验证码过期时间设置为60秒，60秒之类没有验证就需要重新发送，否则会提示验证失败
 */
public class SendCodeForRegistReq implements ActionPhpRequestImpl {

	String phone ;//用户手机号
	
	public SendCodeForRegistReq(String phone) {
		super();
		this.phone = phone;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_SENDCODE;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_PHONE,phone);

		return halfway;
	}

}

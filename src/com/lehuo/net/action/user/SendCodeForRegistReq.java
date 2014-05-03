package com.lehuo.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

/**
 * @author tao
 *������֤�루ע��ʱ��
 *��֤�����ʱ������Ϊ60�룬60��֮��û����֤����Ҫ���·��ͣ��������ʾ��֤ʧ��
 */
public class SendCodeForRegistReq implements ActionPhpRequestImpl {

	String phone ;//�û��ֻ���
	
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

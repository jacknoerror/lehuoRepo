package com.lehuo.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;


public class UserAddAddressReq implements ActionPhpRequestImpl {
	int user_id;// �û�ID
	String truename;// �ջ��ˣ������Ǻ���
	int province,city,district;// �������
	String address;// ��ϸ��ַ
	int mobile;// ��ϵ�绰
    

	public UserAddAddressReq(int user_id, String truename, int province,
			int city, int district, String address, int mobile) {
		super();
		this.user_id = user_id;
		this.truename = truename;
		this.province = province;
		this.city = city;
		this.district = district;
		this.address = address;
		this.mobile = mobile;
	}

	@Override
	public String getPhpName() {
		return NetConst.PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_ADDADDRESS_USER;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_USER_ID, user_id+"");
		halfway.put(PARAMS_TRUENAME, truename);
		halfway.put(NetConst.PARAMS_PROVINCE, province+"");
		halfway.put(NetConst.PARAMS_CITY, city+"");
		halfway.put(NetConst.PARAMS_DISTRICT, district+"");
		halfway.put(PARAMS_ADDRESS, address);
		halfway.put(NetConst.PARAMS_MOBILE, mobile+"");
		return halfway;
	}

}
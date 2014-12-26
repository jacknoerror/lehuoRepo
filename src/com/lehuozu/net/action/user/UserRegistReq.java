package com.lehuozu.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;

/**
 * @author tao
 *�û�ע��ӿ�
 */
public class UserRegistReq implements ActionPhpRequestImpl {

	String phone ;//�ֻ�
	String truename ;//��ʵ����
	String postcode ;//�ʱ�
	int province ;//ʡ��
	int city ;//����
	int district;// ����
	String address ;//��ϸ��ַ
	String password ;//����
	int sex;//1��2Ů

	

	public UserRegistReq(String phone, String truename, String postcode,
			int province, int city, int district, String address,
			String password, int sex) {
		super();
		this.phone = phone;
		this.truename = truename;
		this.postcode = postcode;
		this.province = province;
		this.city = city;
		this.district = district;
		this.address = address;
		this.password = password;
		this.sex = sex;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_REGISTER;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_PHONE,phone);
		halfway.put(NetConst.PARAMS_TRUENAME,truename);
		halfway.put(NetConst.PARAMS_POSTCODE,postcode);
		halfway.put(NetConst.PARAMS_PROVINCE,province+"");
		halfway.put(NetConst.PARAMS_CITY,city+"");
		halfway.put(NetConst.PARAMS_DISTRICT,district+"");
		halfway.put(NetConst.PARAMS_ADDRESS,address);
		halfway.put(NetConst.PARAMS_PASSWORD,password);
		halfway.put(NetConst.PARAMS_SEX,sex+"");
		
		return halfway;
	}

}

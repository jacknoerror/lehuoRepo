package com.lehuozu.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;


public class UserAddAddressReq implements ActionPhpRequestImpl {
	int user_id;// 用户ID
	String truename;// 收货人，必须是汉字
	int province,city,district;// 地区编号
	String address;// 详细地址
	String mobile;// 联系电话
	boolean is_default 
;


	public UserAddAddressReq(int user_id, String truename, int province,
			int city, int district, String address, String mobile,
			boolean is_default) {
		super();
		this.user_id = user_id;
		this.truename = truename;
		this.province = province;
		this.city = city;
		this.district = district;
		this.address = address;
		this.mobile = mobile;
		this.is_default = is_default;
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
		halfway.put(NetConst.PARAMS_IS_DEFAULT, ""+(is_default?1:0));
		return halfway;
	}

}

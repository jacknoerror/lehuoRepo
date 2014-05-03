package com.lehuo.net.action.user;
import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

public class UGetDistrictReq implements ActionPhpRequestImpl {
	int city_id;// ³ÇÊÐ±àºÅ
	
	
	public UGetDistrictReq(int city_id) {
		super();
		this.city_id = city_id;
	}

	@Override
	public String getPhpName() {
		return NetConst.PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_GETDISTRICT;
	}
	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}
	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_CITY_ID, city_id+"");
		return halfway;
	}
}
package com.lehuo.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;


public class UGetcityReq implements ActionPhpRequestImpl {
	int province_id;// Ê¡·ÝID
	
	
	
	public UGetcityReq(int province_id) {
		super();
		this.province_id = province_id;
	}
	@Override
	public String getPhpName() {
		return NetConst.PHPNAME_USER;
	}
	@Override
	public String getApiName() {
		return NetConst.ACTION_GETCITY;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_PROVINCE_ID, province_id+"");
		return halfway;
	}

}

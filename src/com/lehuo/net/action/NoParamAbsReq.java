package com.lehuo.net.action;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.net.NetStrategies;

public abstract class NoParamAbsReq implements ActionPhpRequestImpl {


	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		return halfway;
	}

}

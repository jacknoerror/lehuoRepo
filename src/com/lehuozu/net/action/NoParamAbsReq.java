package com.lehuozu.net.action;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.net.NetStrategies;

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

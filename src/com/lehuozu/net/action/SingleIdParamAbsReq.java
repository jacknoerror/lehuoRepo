package com.lehuozu.net.action;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.net.NetStrategies;

public abstract class SingleIdParamAbsReq implements ActionPhpRequestImpl {
	
	protected int id;

	public SingleIdParamAbsReq(int id) {
		super();
		this.id = id;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(getIdParamName(),id+"");
		return halfway;
	}

	public abstract String getIdParamName();
	
}

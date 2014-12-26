package com.lehuozu.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;


public class UserInformationReq implements ActionPhpRequestImpl {
	int user_id;// ÓÃ»§ID
	
	
	
	public UserInformationReq(int user_id) {
		super();
		this.user_id = user_id;
	}
	@Override
	public String getPhpName() {
		return NetConst.PHPNAME_USER;
	}
	@Override
	public String getApiName() {
		return NetConst.ACTION_GETUSERINFO;
	}
	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}
	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_USER_ID, user_id+"");
		return halfway;
	}

}

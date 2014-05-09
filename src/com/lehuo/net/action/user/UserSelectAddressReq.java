package com.lehuo.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

public class UserSelectAddressReq implements ActionPhpRequestImpl {
	int user_id;
	int address_id;
	boolean is_default;

	public UserSelectAddressReq(int user_id, int address_id, boolean is_default) {
		super();
		this.user_id = user_id;
		this.address_id = address_id;
		this.is_default = is_default;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_SETDEFAULT;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(),
				halfwayParamMap(new HashMap()));

	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_USER_ID, user_id + "");
		halfway.put(PARAMS_ADDRESS_ID, address_id + "");
		halfway.put(NetConst.PARAMS_IS_DEFAULT, (is_default ? 1 : 0) + "");
		return halfway;
	}

}

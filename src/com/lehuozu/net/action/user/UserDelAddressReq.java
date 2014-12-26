package com.lehuozu.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;

/**
 * 
 * @author taotao
 *
 */
public class UserDelAddressReq implements ActionPhpRequestImpl {
	int  address_id;// µÿ÷∑ID
	
	

	public UserDelAddressReq(int address_id) {
		super();
		this.address_id = address_id;
	}

	@Override
	public String getPhpName() {
		return NetConst.PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_DELADDRESS_ADDRESS;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));

	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		// TODO Auto-generated method stub
		halfway.put(NetConst.PARAMS_ADDRESS_ID , address_id+"");
		return halfway;
	}

}

package com.lehuo.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

/**
 * @author tao
 *快递员特供，每10秒发一次
 */
public class SendCourierLocReq implements ActionPhpRequestImpl {

	String addressname;
	String latitude;
	String longitude;
	int user_id;

	public SendCourierLocReq(String addressname, String latitude,
			String longitude, int user_id) {
		super();
		this.addressname = addressname;
		this.latitude = latitude;
		this.longitude = longitude;
		this.user_id = user_id;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_COURIERLOCATION;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(),
				halfwayParamMap(new HashMap()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.ACTION_ADDRESSNAME, addressname);
		halfway.put(NetConst.ACTION_LATITUDE, latitude);
		halfway.put(NetConst.ACTION_LONGITUDE, longitude);
		halfway.put(PARAMS_USER_ID, "" + user_id);
		return halfway;
	}

}

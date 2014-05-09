package com.lehuo.net.action.user;

import com.lehuo.data.NetConst;
import com.lehuo.net.action.SingleIdParamAbsReq;

/**
 * @author taotao
 *
 */
public class GetCourierReq extends SingleIdParamAbsReq {

	public GetCourierReq(int id) {
		super(id);
	}

	@Override
	public String getPhpName() {
		return PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_GETCOURIER;
	}

	@Override
	public String getIdParamName() {
		return "user_id";
	}

}

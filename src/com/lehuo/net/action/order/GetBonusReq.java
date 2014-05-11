package com.lehuo.net.action.order;

import com.lehuo.net.action.SingleIdParamAbsReq;

/**
 * @author tao
 *
 */
public class GetBonusReq extends SingleIdParamAbsReq {

	public GetBonusReq(int id) {
		super(id);
	}

	@Override
	public String getPhpName() {
		return PHPNAME_ORDER;
	}

	@Override
	public String getApiName() {
		return "userbonus";
	}

	@Override
	public String getIdParamName() {
		return PARAMS_USER_ID;
	}

}

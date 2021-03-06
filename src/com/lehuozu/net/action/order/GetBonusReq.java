package com.lehuozu.net.action.order;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.action.SingleIdParamAbsReq;

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
		return NetConst.ACTION_USERBONUS;
	}

	@Override
	public String getIdParamName() {
		return PARAMS_USER_ID;
	}

}

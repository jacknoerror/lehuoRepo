package com.lehuozu.net.action.courier;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.action.SingleIdParamAbsReq;

/**
 * @author tao
 *快递员特供，获得配送列表
 */
public class DeliverListReq extends SingleIdParamAbsReq {

	public DeliverListReq(int id) {
		super(id);
	}

	@Override
	public String getPhpName() {
		return PHPNAME_COURIER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_LIST;
	}

	@Override
	public String getIdParamName() {
		return PARAMS_USER_ID;
	}

}

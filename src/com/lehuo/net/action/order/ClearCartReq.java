package com.lehuo.net.action.order;

import com.lehuo.data.NetConst;
import com.lehuo.net.action.SingleIdParamAbsReq;

/**
 * @author tao
 *清空购物车接口
 */
public class ClearCartReq extends SingleIdParamAbsReq {

	public ClearCartReq(int id) {
		super(id);
	}

	@Override
	public String getPhpName() {
		return PHPNAME_ORDER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_CLEARCART;
	}

	@Override
	public String getIdParamName() {
		return PARAMS_USER_ID;
	}

}

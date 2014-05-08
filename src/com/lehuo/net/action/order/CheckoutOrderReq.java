package com.lehuo.net.action.order;

import com.lehuo.data.NetConst;
import com.lehuo.net.action.SingleIdParamAbsReq;

/**
 * @author tao
 *checkout订单到确认订单界面
 */
public class CheckoutOrderReq extends SingleIdParamAbsReq {

	public CheckoutOrderReq(int id) {
		super(id);
	}

	@Override
	public String getPhpName() {
		return PHPNAME_ORDER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_CHECKOUT;
	}

	@Override
	public String getIdParamName() {
		return PARAMS_USER_ID;
	}

}

/**
 * 
 */
package com.lehuo.net.action.courier;

import com.lehuo.data.NetConst;
import com.lehuo.net.action.SingleIdParamAbsReq;

/**
 * @author tao
 *快递员特供，开始配送
 */
public class StartDeliverReq extends SingleIdParamAbsReq {

	public StartDeliverReq(int id) {
		super(id);
	}

	@Override
	public String getPhpName() {
		return NetConst.PHPNAME_COURIER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_START;
	}

	@Override
	public String getIdParamName() {
		return PARAMS_USER_ID;
	}

}

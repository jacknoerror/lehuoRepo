package com.lehuo.net.action.courier;

import com.lehuo.data.NetConst;
import com.lehuo.net.action.SingleIdParamAbsReq;

/**
 * @author tao
 *���Ա�ع�����������б�
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

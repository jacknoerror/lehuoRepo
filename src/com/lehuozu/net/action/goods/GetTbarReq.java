package com.lehuozu.net.action.goods;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.action.NoParamAbsReq;

/**
 * @author tao
 *��ȡͨ���ӿ�
 */
public class GetTbarReq extends NoParamAbsReq {

	@Override
	public String getPhpName() {
		return PHPNAME_GOODS;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_TONGLAN;
	}

}

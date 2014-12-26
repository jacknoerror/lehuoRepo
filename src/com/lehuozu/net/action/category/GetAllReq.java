package com.lehuozu.net.action.category;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.action.NoParamAbsReq;

/**
 * @author tao
 *
 */
public class GetAllReq extends NoParamAbsReq {

	@Override
	public String getPhpName() {
		return NetConst.PHPNAME_CATEGORY;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_GETALL;
	}

}

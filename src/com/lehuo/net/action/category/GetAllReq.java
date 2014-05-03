package com.lehuo.net.action.category;

import com.lehuo.data.NetConst;
import com.lehuo.net.action.NoParamAbsReq;

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

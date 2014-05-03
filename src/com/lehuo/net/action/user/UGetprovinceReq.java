package com.lehuo.net.action.user;


import com.lehuo.data.NetConst;
import com.lehuo.net.action.NoParamAbsReq;


public class UGetprovinceReq extends NoParamAbsReq {

	@Override
	public String getPhpName() {
		return NetConst.PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_GETPROVINCE;
	}


}

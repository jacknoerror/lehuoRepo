package com.lehuozu.net.action.user;


import com.lehuozu.data.NetConst;
import com.lehuozu.net.action.NoParamAbsReq;


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

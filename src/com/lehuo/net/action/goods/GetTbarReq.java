package com.lehuo.net.action.goods;

import com.lehuo.data.NetConst;
import com.lehuo.net.action.NoParamAbsReq;

/**
 * @author tao
 *获取通栏接口
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

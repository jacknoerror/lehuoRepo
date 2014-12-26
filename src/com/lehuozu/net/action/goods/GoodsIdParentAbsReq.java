package com.lehuozu.net.action.goods;

import com.lehuozu.net.action.SingleIdParamAbsReq;

public abstract class GoodsIdParentAbsReq extends SingleIdParamAbsReq {

	public GoodsIdParentAbsReq(int id) {
		super(id);
	}


	@Override
	public String getPhpName() {
		return PHPNAME_GOODS;
	}

	@Override
	public String getIdParamName() {
		return PARAMS_GOODS_ID;
	}
	

}

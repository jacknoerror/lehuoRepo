package com.lehuo.net.action.goods;

import com.lehuo.net.action.SingleIdParamAbsReq;

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

package com.lehuozu.net.action.goods;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;

public class GetProductAttrReq extends GoodsIdParentAbsReq {


	public GetProductAttrReq(int goods_id2) {
		super(goods_id2);
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_GET_GOODS_ATTRIBUTE;
	}



}

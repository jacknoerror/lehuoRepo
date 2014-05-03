package com.lehuo.net.action.goods;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

public class GetProductAttrReq extends GoodsIdParentAbsReq {


	public GetProductAttrReq(int goods_id2) {
		super(goods_id2);
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_GET_GOODS_ATTRIBUTE;
	}



}

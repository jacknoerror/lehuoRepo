package com.lehuo.net.action.brand;

import java.util.Map;

import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.SingleIdParamAbsReq;

/**
 * @author taotao
 *
 */
public class GetBrandReq extends SingleIdParamAbsReq {

	//	getinfo
//	api/brand.php
//	brand_id
	int brand_id;
	
	public GetBrandReq(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getPhpName() {
		return "brand";
	}

	@Override
	public String getApiName() {
		return "getinfo";
	}

	@Override
	public String getIdParamName() {
		return "brand_id";
	}


}

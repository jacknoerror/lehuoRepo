package com.lehuozu.net.action.goods;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;

public class GetLongImgRequest implements ActionPhpRequestImpl {
	int goods_id;

	public GetLongImgRequest(int goods_id) {
	super();
	this.goods_id = goods_id;
}

	@Override
	public String getPhpName() {
		return PHPNAME_GOODS;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_GOOD_LONG_IMG;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put("goods_id",""+goods_id);
		return halfway;
	}

}

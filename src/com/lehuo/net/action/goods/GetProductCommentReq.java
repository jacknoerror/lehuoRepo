package com.lehuo.net.action.goods;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;


public class GetProductCommentReq implements ActionPhpRequestImpl {

	
	int goods_id ;//产品ID号
	int page ;//评论页数（默认为1）
	
	public GetProductCommentReq(int goods_id, int page) {
		super();
		this.goods_id = goods_id;
		this.page = page;
	}

	@Override
	public String getPhpName() {
		return NetConst.PHPNAME_GOODS;
	}

	@Override
	public String getApiName() {
		
		return NetConst.ACTION_GETCOMMENT;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> 	halfway) {
		halfway.put(NetConst.PARAMS_GOODS_ID,goods_id+"");
		halfway.put(NetConst.PARAMS_PAGE,page+"");
		return halfway;
	}

}

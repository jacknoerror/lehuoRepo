package com.lehuo.net.action.order;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

/**
 * @author taotao
 *获得订单列表
 */
public class GetOrderListReq implements ActionPhpRequestImpl {

	private static final String PARAMS_COMPLETE = "complete";
	private static final String PARAMS_SIZE = "size";
	int complete;//0,送货跟踪列表;1，已完成订单列表
	int page;
	int size;
	int user_id;
	
	public GetOrderListReq(int complete, int page, int user_id) {
		super();
		this.complete = complete;
		this.page = page;
		this.size = 5;
		this.user_id = user_id;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_ORDER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_ORDERLIST;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_COMPLETE,complete+"");
		halfway.put(PARAMS_PAGE,page+"");
		halfway.put(PARAMS_SIZE,size+"");
		halfway.put(PARAMS_USER_ID,user_id+"");
		return halfway;
	}

}

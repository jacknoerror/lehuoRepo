package com.lehuozu.net.action.order;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;

/**
 * @author taotao
 *获得订单列表
 */
public class GetOrderListReq implements ActionPhpRequestImpl {

	public static int COMPLETE_DELIVER = 0;
	public static int COMPLETE_DONE = 1;
	
	int complete;//0,送货跟踪列表;1，已完成订单列表
	int page;
	int size;
	int user_id;
	
	public GetOrderListReq(int complete, int page, int user_id) {
		super();
		this.complete = complete;
		this.page = page;
		this.size = 10;
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
		halfway.put(NetConst.PARAMS_COMPLETE,complete+"");
		halfway.put(PARAMS_PAGE,page+"");
		halfway.put(NetConst.PARAMS_SIZE,size+"");
		halfway.put(PARAMS_USER_ID,user_id+"");
		return halfway;
	}

}

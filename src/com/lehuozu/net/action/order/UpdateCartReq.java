package com.lehuozu.net.action.order;

import java.util.HashMap;
import java.util.Map;

import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;

/**
 * @author tao
 *更新购物车接口（就只是产品数量）
 *只有userid会变
 *添加购物车成功后发起
 */
public class UpdateCartReq implements ActionPhpRequestImpl {

	int num ;//数量 如果为0 则会删除产品
	String rec_id ;//同上
	int user_id;

	
	public UpdateCartReq( int user_id) {
		super();
		this.num = 1;
		this.rec_id = "";
		this.user_id = user_id;
	}

	
	
	public UpdateCartReq(int num, String rec_id, int user_id) {
		super();
		this.num = num;
		this.rec_id = rec_id;
		this.user_id = user_id;
	}



	@Override
	public String getPhpName() {
		return PHPNAME_ORDER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_UPDATECART;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_NUM,num+"");
		halfway.put(PARAMS_REC_ID,rec_id+"");
		halfway.put(PARAMS_USER_ID,user_id+"");
		return halfway;
	}

}

package com.lehuo.net.action.goods;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

/**
 * @author tao
 *
 */
public class AddCommentReq implements ActionPhpRequestImpl {

	int user_id;
	int goods_id;
	String content;
	
	int order_id;
	
	

	public AddCommentReq(int user_id, int goods_id, String content, int order_id) {
		super();
		this.user_id = user_id;
		this.goods_id = goods_id;
		this.content = content;
		this.order_id = order_id;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_GOODS;
	}

	@Override
	public String getApiName() {
		return "addcomment";
	}

	@Override
	public String toHttpBody() {

		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(PARAMS_USER_ID,""+user_id);
		halfway.put(PARAMS_GOODS_ID,""+goods_id);
		halfway.put(NetConst.PARAMS_CONTENT,content);
		halfway.put(NetConst.PARAMS_ORDER_ID,order_id+"");

		return halfway;
	}

}

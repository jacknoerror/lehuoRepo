package com.lehuo.net.action.order;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

/**
 * @author tao
 *��ӻ��Ʒ�ӿ�
 */
public class AddFavorable implements ActionPhpRequestImpl {

	int act_id ;//�ID��
	JSONObject gift ;//{��gift��:[1,2,3]}����json��ʽ �����ǲ�ƷID��

	
	public AddFavorable(int act_id, JSONObject gift) {
		super();
		this.act_id = act_id;
		this.gift = gift;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_ORDER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_ADDFAVOURABLE;
	}

	@Override
	public String toHttpBody() {
		 return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_ACT_ID,act_id+"");
		halfway.put(NetConst.PARAMS_GIFT,gift.toString());

		return halfway;
	}

}

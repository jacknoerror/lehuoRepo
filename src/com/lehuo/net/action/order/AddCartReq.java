package com.lehuo.net.action.order;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.lehuo.data.NetConst;
import com.lehuo.entity.json.JackJson;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.vo.AddCartGoodsStandard;

/**
 * @author tao
 *��ӹ��ﳵ����ȡ��Ʒ��񣩽ӿ�
 *���˽ӿ�����Ӳ�Ʒ�����ﳵ�ӿڲ��������ƣ�
 */
public class AddCartReq implements ActionPhpRequestImpl {

	JackJson goods ;/*����ΪJson��ʽ�ַ���
	{"getattr":1,"spec":[],"goods_id":1,"number":"1","parent":0}
	getattr ��ȡ��Ʒ���
	spec Ϊ��Ʒ��������Ϊ��ȡ��Ʒ�����Ϊ�գ�
	number �ڻ�ȡ��Ʒ����ǿ���Ϊ�գ����߲�Ҫ
	parent ��Ʒ�ĸ�ID�����û�о�Ϊ0*/
	Integer user_id ;//�û�ID��

	
	public AddCartReq(JackJson goods, Integer user_id) {
		super();
		this.goods = goods;
		this.user_id = user_id;
	}

	@Override
	public String getPhpName() {
		return PHPNAME_ORDER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_ADDCART;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_GOODS,goods.toJsonObj().toString());//
		if(user_id!=null)halfway.put(PARAMS_USER_ID,user_id+"");

		return halfway;
	}

}

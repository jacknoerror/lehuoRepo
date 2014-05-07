package com.lehuo.net.action.order;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

/**
 * @author tao
 *���¹��ﳵ�ӿڣ���ֻ�ǲ�Ʒ������
 *ֻ��userid���
 *���ӹ��ﳵ�ɹ�����
 */
public class UpdateCartReq implements ActionPhpRequestImpl {

	int num ;//���� ���Ϊ0 ���ɾ����Ʒ
	String rec_id ;//ͬ��
	int user_id;

	
	public UpdateCartReq( int user_id) {
		super();
		this.num = 1;
		this.rec_id = "";
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
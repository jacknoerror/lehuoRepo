package com.lehuo.net.action.user;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;

public class UserEdituserReq implements ActionPhpRequestImpl {
	int user_id;// �û�ID
	Integer sex;// �Ա� 1���� 2Ů��
	String birthday;// ��ʽΪ 1999-01-02
	String truename;// ��ʵ�����������Ǻ���

	public UserEdituserReq(int user_id, int sex, String birthday,
			String truename) {
		super();
		this.user_id = user_id;
		this.sex = sex;
		this.birthday = birthday;
		this.truename = truename;
	}

	@Override
	public String getPhpName() {
		return NetConst.PHPNAME_USER;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_EDITUSER;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(),
				halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_USER_ID, user_id + "");
		if (null != sex)
			halfway.put(NetConst.PARAMS_SEX, sex + "");
		if (null != birthday)
			halfway.put(NetConst.PARAMS_BIRTHDAY, birthday);
		if (null != truename)
			halfway.put(PARAMS_TRUENAME, truename);
		return halfway;
	}

}

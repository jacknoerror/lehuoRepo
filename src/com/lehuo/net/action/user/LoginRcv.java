package com.lehuo.net.action.user;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.lehuo.net.action.JackDefJobRcv;
import com.lehuo.ui.MyGate;
import com.lehuo.vo.User;

public class LoginRcv extends JackDefJobRcv{

	public LoginRcv(Context context) {
		super(context);
	}

	@Override
	public boolean respJob(JSONObject job) throws JSONException {
		if(null!=job){
			User user = new User(job);
			MyGate.login(context,user);
			return false;
		}
		return true;
	}

}

package com.lehuozu.net.action.user;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;

import com.lehuozu.data.MyData;
import com.lehuozu.net.action.JackDefJarRcv;
import com.lehuozu.vo.UserAddress;

public class GetUserAddrRcv extends JackDefJarRcv {

	List<UserAddress> addressList;
	public GetUserAddrRcv(Context context) {
		super(context);
		addressList = new ArrayList<UserAddress>();
	}

	@Override
	public boolean respJar(JSONArray jar) throws JSONException {
		if(null!=jar){
			for(int i=0;i<jar.length();i++){
				UserAddress addr = new UserAddress(jar.getJSONObject(i));
				addressList.add(addr);
			}
			MyData.data().setMyAddrs(addressList);
			return false;
		}
		return true;
	}

}

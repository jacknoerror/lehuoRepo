package com.lehuozu.net.action.order;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.lehuozu.data.MyData;
import com.lehuozu.net.action.JackDefJobRcv;
import com.lehuozu.ui.TitleManager;
import com.lehuozu.vo.cart.DataCart;

public class GetCartRcv extends JackDefJobRcv {

	

	public GetCartRcv(Context context) {
		super(context);
	}


	@Override
	public boolean respJob(JSONObject job) throws JSONException {
		if(null!=job){
			DataCart dc = new DataCart(job);
			MyData.data().setCurrentCart(dc);
//			if(null!=tm) tm.updateCart();
			return false; 
		}
		return true;
	}

}

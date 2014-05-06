package com.lehuo.net.action.order;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.lehuo.data.MyData;
import com.lehuo.net.action.JackDefJobRcv;
import com.lehuo.ui.TitleManager;
import com.lehuo.vo.cart.DataCart;

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

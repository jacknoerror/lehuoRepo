package com.lehuo.net.action.brand;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.GridView;

import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.ui.adapter.MyGridViewAdapter;
import com.lehuo.vo.Brand;

public class GetBrandRcv implements ActionPhpReceiverImpl {

	GridView gv;
	Context context;
	

	public GetBrandRcv(Context context, GridView gv) {
		this.context = context;
		this.gv = gv;
	}




	@Override
	public boolean response(String result) throws JSONException {
		JSONObject job = NetStrategies.getResultObj(result);
		if(null!=job){
			Brand b = new Brand(job);
			if(null!=gv) {
				gv.setAdapter(new MyGridViewAdapter(context, b.getCertList()));
				gv.setNumColumns(2);
				return false;
			}
		}
		return true;
	}


	@Override
	public Context getReceiverContext() {
		return context;
	}



}

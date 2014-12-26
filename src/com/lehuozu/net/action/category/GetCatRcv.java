package com.lehuozu.net.action.category;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.lehuozu.data.MyData;
import com.lehuozu.data.NetConst;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.vo.Category;

public class GetCatRcv implements ActionPhpReceiverImpl {

	Context context;
	
	
	public GetCatRcv(Context context) {
		super();
		this.context = context;
	}

	@Override
	public boolean response(String result) throws JSONException {
		JSONObject job = new JSONObject(result);
		if (null != job && !job.isNull(NetConst.RESULT_OBJ)) { // 这里resultsign竟然是false
			// fetch data
			JSONArray jar = job.getJSONArray(RESULT_OBJ);
			List<Category>categoryList = MyData.data().getAllCate();
			categoryList.clear();
			for (int i = 0; i < jar.length(); i++) {
				JSONObject catJob = jar.getJSONObject(i);
				Category cate = new Category(catJob);
				categoryList.add(cate);
			}
			
			return false;
		}
		return true;
	}

	@Override
	public Context getReceiverContext() {
		// TODO Auto-generated method stub
		return context;
	}

}

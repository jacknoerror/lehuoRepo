package com.lehuo.ui.custom.list;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;
import com.lehuo.vo.Product;

public class MspPage extends JsonImport {

	public int curPageNo;

	boolean resultSign;
	boolean hasNext;
	JSONArray infoArr;

	private MspFactoryImpl factory;


	public MspPage(JSONObject job,MspFactoryImpl factory) {
		super(job);
		this.factory = factory;
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if (job.has("result"))
			resultSign = job.getBoolean("result");
		if (job.has("next"))
			hasNext = job.getBoolean("next");
		if (job.has("data"))
			infoArr = job.getJSONArray("data");

	}
	
	List<MspJsonItem> LiiList;
	public  List<MspJsonItem> getDataList(){
		if(LiiList!=null) return LiiList;//如果有了，直接返回
		
		LiiList = new ArrayList<MspJsonItem>();//
		for(int i=0;i<(null!=infoArr?infoArr.length():0);i++){
			try {
				
				MspJsonItem pp = factory.getMjiInstance();
				pp.initJackJson(infoArr.getJSONObject(i));
				LiiList.add(pp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//
			
		}
		return LiiList;
	}
	
	
}

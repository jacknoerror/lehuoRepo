package com.lehuozu.ui.custom.list;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.entity.json.JsonImport;
import com.lehuozu.vo.Product;

public class MspPage extends JsonImport {

	public int curPageNo;

	boolean resultSign;
	public boolean hasNext;
	JSONArray infoArr;

	private MspFactoryImpl factory;

	public MspPage(MspFactoryImpl factory){
		super();
		this.factory = factory;
	}
	/*public MspPage(JSONObject job,MspFactoryImpl factory) {
		super(job);
		
	}*/

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if (job.has("result"))
			resultSign = job.getBoolean("result");
		if (job.has("next")){
			
			hasNext = job.getBoolean("next");
			if (job.has("data"))
				infoArr = job.getJSONArray("data");
		}else{
			if(job.has("data")){
				JSONObject jacob = job.getJSONObject("data");
				infoArr = jacob.optJSONArray(factory.getPageName());
				hasNext = jacob.optBoolean("next");
			}
		}

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
				e.printStackTrace();
			}//
			
		}
		return LiiList;
	}
	
	
}

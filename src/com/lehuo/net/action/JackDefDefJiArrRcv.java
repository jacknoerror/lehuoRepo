package com.lehuo.net.action;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.lehuo.entity.json.JsonImport;

public abstract class JackDefDefJiArrRcv extends JackDefJarRcv {

	protected List<JsonImport> jiList;
	
	public JackDefDefJiArrRcv(Context context) {
		super(context);
	}

	@Override
	public boolean respJar(JSONArray jar) throws JSONException {
		if(null!=jar){
			jiList = new ArrayList<JsonImport>();
			for(int i=0;i<jar.length();i++){
				jiList.add(initJi(jar.getJSONObject(i)));
				
			}
			return doSthMore();
		}
		return true;
	}
	
	public abstract JsonImport initJi(JSONObject jb);
	public abstract boolean doSthMore();
}

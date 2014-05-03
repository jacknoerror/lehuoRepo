package com.lehuo.entity.json;

import org.json.JSONObject;



/**
 * ����Ҫ�γ�JsonObj��JackJson ����Ĭ�Ϸ���
 * @author taotao
 *
 */
public abstract class JsonImport extends JackJson  {

	JSONObject jliJob;
	
	public JsonImport(){
	}
	public JsonImport(JSONObject job){
		super(job);
		jliJob=job;
	}
	
	@Override
	public JSONObject toJsonObj() {
		return null!=jliJob?jliJob:new JSONObject();
	}


}
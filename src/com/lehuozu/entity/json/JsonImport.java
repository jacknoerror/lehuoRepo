package com.lehuozu.entity.json;

import org.json.JSONObject;



/**
 * 不需要形成JsonObj的JackJson 给予默认方法
 * @author taotao
 *
 */
public abstract class JsonImport extends JackJson  {

	JSONObject jliJob;
	
	/**
	 * remember to init after this no-param construct
	 */
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

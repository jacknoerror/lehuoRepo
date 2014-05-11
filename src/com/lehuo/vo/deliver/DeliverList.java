/**
 * 
 */
package com.lehuo.vo.deliver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.entity.json.JsonImport;

/**
 * @author tao
 *
 */
public class DeliverList extends JsonImport {

	int need_ship;
	JSONArray orders;
	boolean next;
	
	/**
	 * 
	 */
	public DeliverList() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param job
	 */
	public DeliverList(JSONObject job) {
		super(job);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		// TODO Auto-generated method stub

	}

}

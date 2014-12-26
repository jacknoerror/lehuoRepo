package com.lehuozu.net.action.order;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.lehuozu.net.action.JackDefJobRcv;
import com.lehuozu.ui.custom.list.ListItemImpl;
import com.lehuozu.vo.Coupon;

/**
 * @author taotao
 *available;used;expired
 */
public class GetBonusRcv extends JackDefJobRcv {

	public List<ListItemImpl> availableBonusList,usedBonusList,expiredBonusList;
	
	public GetBonusRcv(Context context) {
		super(context);
	}

	@Override
	public boolean respJob(JSONObject job) throws JSONException {
		if(null!=job){
			
			availableBonusList = new ArrayList<ListItemImpl>();
			usedBonusList = new ArrayList<ListItemImpl>();
			expiredBonusList = new ArrayList<ListItemImpl>();
			initListFromJar(availableBonusList, job.getJSONArray("available"));
			initListFromJar(usedBonusList, job.getJSONArray("used"));
			initListFromJar(expiredBonusList, job.getJSONArray("expired"));
			return false;
		}
		return true;
	}

	/**
	 * @param couponList
	 * @param jar
	 * @throws JSONException
	 */
	public void initListFromJar(List<ListItemImpl> couponList, JSONArray jar)
			throws JSONException {
		for(int i=0;i<jar.length();i++){
			couponList.add(new Coupon(jar.getJSONObject(i)));
		}
	}

}

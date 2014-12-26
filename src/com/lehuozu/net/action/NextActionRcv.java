package com.lehuozu.net.action;


import org.json.JSONException;

import android.content.Context;

/**
 * @author tao
 *do next request if the last goes well
 *consider extends {@link BareReceiver}
 */
public class NextActionRcv implements ActionPhpReceiverImpl {

	ActionPhpReceiverImpl lastRcv;
	ActionPhpRequestImpl nextReq;
	ActionPhpReceiverImpl nextRcv;
	

	public NextActionRcv(ActionPhpReceiverImpl lastRcv,
			ActionPhpRequestImpl nReq, ActionPhpReceiverImpl nRcv) {
		super();
		this.lastRcv = lastRcv;
		this.nextReq = nReq;
		this.nextRcv = nRcv;
	}

	@Override
	public boolean response(String result) throws JSONException {
		if(null!=lastRcv&&!lastRcv.response(result)){
			ActionBuilder.getInstance().request(nextReq, nextRcv);
			return false;
		}
		return true;
	}

	@Override
	public Context getReceiverContext() {
		return null==nextRcv?null:nextRcv.getReceiverContext();
	}


}

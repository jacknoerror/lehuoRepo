package com.lehuo.custom;

import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;

public class ActionBuilderTest implements Runnable{
	ActionPhpRequestImpl req;
	ActionPhpReceiverImpl rcv;
	
	
	public ActionBuilderTest(ActionPhpRequestImpl req,
			ActionPhpReceiverImpl rcv) {
		super();
		this.req = req;
		this.rcv = rcv;
	}


	@Override
	public void run() {
		ActionBuilder.getInstance().request(req, rcv);
	}
	
}
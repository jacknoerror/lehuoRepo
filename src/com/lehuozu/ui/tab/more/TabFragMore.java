/**
 * 
 */
package com.lehuozu.ui.tab.more;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

import com.lehuozu.R;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.JackDefJobRcv;
import com.lehuozu.net.action.user.AboutRequest;
import com.lehuozu.ui.MyGate;
import com.lehuozu.ui.tab.ContentAbstractFragment;

/**
 * @author taotao
 * 
 */
public class TabFragMore extends ContentAbstractFragment implements OnClickListener {
	private String phoneString = "400-850-9811";
	private String htmlContent;

	/*
	 */
	@Override
	public int getLayoutRid() {
		return R.layout.fragment_more;
	}

	/*
	 */
	@Override
	public void initView() {
		initTitleManager();
		titleManager.setTitleName(getString(R.string.titlename_more));
		titleManager.initTitleMenu();
		titleManager.updateCart();
		
		mView.findViewById(R.id.tv_more2_about).setOnClickListener(this);
		mView.findViewById(R.id.tv_more2_focus).setOnClickListener(this);
		mView.findViewById(R.id.tv_more2_contact).setOnClickListener(this);
	}

	@Override
	public void onResume() {
		dorequest();
		super.onResume();
	}
	@Override
	public void onHiddenChanged(boolean hidden) {
		if(!hidden){
			dorequest();
		}
		super.onHiddenChanged(hidden);
	}
	
	private void dorequest() {
		ActionPhpRequestImpl actReq = new AboutRequest();
		ActionPhpReceiverImpl actRcv = new JackDefJobRcv(null) {
			

			@Override
			public boolean respJob(JSONObject job) throws JSONException {
				if(null!=job){//{"result":true,"data":{"id":"3","content":"<p>\u4f60\u597d\u4f60\u597d<\/p>","phone":"400-850-9811","status":"1"},"message":""}
					htmlContent = job.optString("content");
					phoneString = job.optString("phone");
					
				}
				return false;
			}
		};
		ActionBuilder.getInstance().request(actReq , actRcv );

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_more2_about:
			if(null!=htmlContent)MyGate.goAbout(getActivity(),htmlContent);
			break;
		case R.id.tv_more2_focus:
			MyGate.goFocus(getActivity());
			break;
		case R.id.tv_more2_contact:
			AlertDialog.Builder ad = new Builder(getActivity());
			ad.setCancelable(false);
			ad.setTitle("拨打客服热线");
			ad.setMessage(phoneString+"\n服务时间：09:00-20:00");
			DialogInterface.OnClickListener dListener = new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					boolean show = which == AlertDialog.BUTTON_POSITIVE;
					if(show){
						
					} 
				}
			};
			ad.setPositiveButton("确定", dListener);
			ad.setNegativeButton("取消", dListener);
			ad.create().show();	
			break;

		default:
			break;
		}
		
	}

}

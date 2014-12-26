package com.lehuozu.ui.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.R;
import com.lehuozu.data.MyData;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.user.SendCodeForRegistReq;
import com.lehuozu.net.action.user.VerifyCodeReq;
import com.lehuozu.ui.MyGate;
import com.lehuozu.ui.MyTitleActivity;
import com.lehuozu.util.JackUtils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistPhoneActivity extends MyTitleActivity implements ActionPhpReceiverImpl {

	EditText et_phone;
	Button btn_getcheck;
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_registphone;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_getcheck));
		titleManager.initTitleBack();
		
		et_phone = (EditText)this.findViewById(R.id.et_registphone_phone);
		btn_getcheck = (Button)this.findViewById(R.id.btn_registphone_getcheck);
		btn_getcheck.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getcheck();
				
			}

		});
		
	}
	private void getcheck() {
		if(getPhone().isEmpty()) {
			et_phone.setError("²»ÄÜÎª¿Õ");
			return;
		}
//		phone = et_phone.getText().toString();
		ActionPhpRequestImpl actReq = new SendCodeForRegistReq(getPhone());
		ActionBuilder.getInstance().request(actReq, this);
		
//		MyGate.GoRegistCode(this,getPhone());//test
	}

	@Override
	public boolean response(String result) throws JSONException {
		JSONObject job = new JSONObject(result);
		if(null!=job){
			if(job.has(RESULT_SIGN)&&job.getBoolean(RESULT_SIGN)){
				MyGate.GoRegistCode(this,getPhone());
			}else{
				if(job.has(RESULT_ERROR_MSG)){
					String msg = job.optString(RESULT_ERROR_MSG);
					JackUtils.showToast(this, msg);
				}
			}
		}
		return false;
	}

	@Override
	public Context getReceiverContext() {
		return this;
	}
	
	private String getPhone(){
		return et_phone.getText().toString();
	}
}

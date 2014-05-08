package com.lehuo.ui.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.R;
import com.lehuo.data.NetConst;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.user.VerifyCodeReq;
import com.lehuo.ui.MyGate;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.util.JackUtils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistCodeActivity extends MyTitleActivity implements ActionPhpReceiverImpl {

	String phone;
	
	TextView tv_mobile,tv_waiting;
	EditText et_input;
	Button btn_done;
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_commitcheck;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_commitcheck));
		titleManager.initTitleBack();
		
		phone = getIntent().getStringExtra(NetConst.EXTRAS_PHONE);
		if(null==phone) return;
		
		tv_mobile = (TextView)this.findViewById(R.id.tv_cmmtchck_mobile);
		tv_waiting = (TextView)this.findViewById(R.id.tv_cmmtchck_wait);
		et_input = (EditText)this.findViewById(R.id.et_cmmtchck_input);
		btn_done = (Button)this.findViewById(R.id.btn_cmmtchck_go);
		btn_done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				commit();
				
			}
		});
		
		tv_mobile.setText(phone);
	}
	private void commit(){
		String code = et_input.getText().toString();
		ActionPhpRequestImpl actReq = new VerifyCodeReq(phone, code, 0);
		ActionBuilder.getInstance().request(actReq, this);
		
		
	}

	@Override
	public boolean response(String result) throws JSONException {
		JSONObject job = new JSONObject(result);
		if(null!=job&&job.has(RESULT_SIGN)&&job.getBoolean(RESULT_SIGN)){
			MyGate.GoSetUser(this,phone);
		}else{
			if(null!=job)JackUtils.showToast(this, job.optString(RESULT_ERROR_MSG));
		}
		return false;
	}

	@Override
	public Context getReceiverContext() {
		return this;
	}
}

package com.lehuo.ui.login;

import com.lehuo.R;
import com.lehuo.R.layout;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.user.LoginRcv;
import com.lehuo.net.action.user.LoginReq;
import com.lehuo.ui.MyGate;
import com.lehuo.util.JackUtils;
import com.lehuo.util.TestDataTracker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements View.OnClickListener{
	 
	TextView tv_regist,tv_find;
	EditText et_mobile,et_pwd;
	Button btn_log;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		tv_regist = (TextView)this.findViewById(R.id.tv_goregist);
		tv_find = (TextView)this.findViewById(R.id.tv_findpwd);
		et_mobile = (EditText)this.findViewById(R.id.et_phone);
		et_pwd = (EditText)this.findViewById(R.id.et_password);
		btn_log = (Button)this.findViewById(R.id.btn_login);
		tv_regist.setOnClickListener(this);
		tv_find.setOnClickListener(this);
		btn_log.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_goregist:
			goRigist();
			break;
		case R.id.tv_findpwd:
			break;
		/*case R.id.et_phone:
			break;
		case R.id.et_password:
			break;*/
		case R.id.btn_login:
			goLogin();
			break;

		default:
			break;
		}
		
	}

	private void goRigist() {
		MyGate.GoRegist(this);
		
	}

	private void goLogin() {
		//TODO 检查合理输入
		String mobile = et_mobile.getText().toString();
		String pwd = et_pwd.getText().toString();
		pwd = JackUtils.getMD5(pwd);
		
		ActionPhpRequestImpl req = new LoginReq(mobile, pwd);
		ActionPhpReceiverImpl rcv= new LoginRcv(this);
		ActionBuilder.getInstance().request(req, rcv);
//		TestDataTracker.simulateConnection(rcv, req.getApiName());//test
		
		hideSoftKeyboard();//0608
	}
	private void hideSoftKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.SHOW_FORCED);
	}
	
}

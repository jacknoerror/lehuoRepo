package com.lehuozu.ui.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lehuozu.R;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.user.LoginRcv;
import com.lehuozu.net.action.user.LoginReq;
import com.lehuozu.ui.MyGate;
import com.lehuozu.ui.MyTitleActivity;
import com.lehuozu.ui.StartActivity;
import com.lehuozu.util.JackUtils;
import com.lehuozu.util.LoginKeeper;

public class LoginActivity extends MyTitleActivity implements View.OnClickListener{
	 
	TextView tv_regist,tv_find;
	EditText et_mobile,et_pwd;
	Button btn_log;
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_goregist:
			goRigist();
			break;
		case R.id.tv_findpwd:
			goFind();
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
	private void goStartPage() {
		Intent intent = new Intent();
		intent.setClass(this, StartActivity.class);
		this.startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	private void goFind() {
		MyGate.goFind(this);
		
	}

	private void goRigist() {
		MyGate.GoRegist(this);
		
	}

	private void goLogin() {
		//TODO ����������
		String mobile = et_mobile.getText().toString();
		String pwd = et_pwd.getText().toString();
		pwd = JackUtils.getMD5(pwd);
		
		ActionPhpRequestImpl req = new LoginReq(mobile, pwd);
		ActionPhpReceiverImpl rcv= new LoginRcv(this);
		ActionBuilder.getInstance().request(req, rcv);
//		TestDataTracker.simulateConnection(rcv, req.getApiName());//test
		LoginKeeper.putValue(this, "login", mobile);
		hideSoftKeyboard();//0608
	}
	private void hideSoftKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.SHOW_FORCED);
	}

	@Override
	public int getLayoutRid() {
		return R.layout.activity_login;
	}

	@Override
	public void initView() {
		titleManager.setTitleName("��¼");
		goStartPage();
		
		tv_regist = (TextView)this.findViewById(R.id.tv_goregist);
		tv_find = (TextView)this.findViewById(R.id.tv_findpwd);
		et_mobile = (EditText)this.findViewById(R.id.et_phone);
		et_pwd = (EditText)this.findViewById(R.id.et_password);
		btn_log = (Button)this.findViewById(R.id.btn_login);
		tv_regist.setOnClickListener(this);
		tv_find.setOnClickListener(this);
		btn_log.setOnClickListener(this);
		
		et_mobile.setText(LoginKeeper.getValue(this, "login"));
	}
	
}

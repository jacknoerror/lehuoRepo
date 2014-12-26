package com.lehuozu.ui.login;

import org.json.JSONException;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lehuozu.R;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.BareReceiver;
import com.lehuozu.net.action.user.PwdResetRequest;
import com.lehuozu.ui.MyTitleActivity;
import com.lehuozu.util.JackUtils;

public class FindPasswordDoActivity extends MyTitleActivity {

	private String phoneStr;
	private String codeStr;
	private EditText et1;
	private EditText et2;

	@Override
	public int getLayoutRid() {
		return R.layout.activity_findpwd2;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_findpwd));
		titleManager.initTitleBack();

		phoneStr = getIntent().getStringExtra("phone");
		codeStr = getIntent().getStringExtra("code");
		TextView tv_phone = (TextView) findViewById(R.id.tv_phone);
		TextView tv_code = (TextView) findViewById(R.id.tv_code);
		tv_phone.setText("手机号码："+phoneStr);
		tv_code.setText("验证码："+codeStr);
		
		et1 = (EditText) findViewById(R.id.et_password);
		et2 = (EditText) findViewById(R.id.et_password2);
		
		findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!checkValid(et1,et2)){return;
				}
				
				ActionPhpRequestImpl actReq = new PwdResetRequest(phoneStr, codeStr, JackUtils.getMD5(et2.getText().toString()));
				ActionPhpReceiverImpl actRcv = new BareReceiver(FindPasswordDoActivity.this){
					@Override
					public boolean response(String result) throws JSONException {
						boolean response = super.response(result);
						if(!response){
							JackUtils.showToast(FindPasswordDoActivity.this, "修改密码成功！");
							Intent intent = new Intent();
							intent.setClass(FindPasswordDoActivity.this, LoginActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							FindPasswordDoActivity.this.startActivity(intent);
						}
						return response;
					}
				};
				ActionBuilder.getInstance().request(actReq, actRcv);
				
			}

			private boolean checkValid(EditText et1, EditText et2) {
				String string = et1.getText().toString();
				String strin2 = et2.getText().toString();
				if(string.length()<6){
					et1.setError("密码太短");
					et1.requestFocus();
					return false;
				}
				if(!strin2.equals(string)){
					et2.setError("两次密码输入不同");
					et2.requestFocus();
					return false;
					
				}
				return true;
			}

		});
	}

}

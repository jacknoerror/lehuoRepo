package com.lehuozu.ui.login;

import org.json.JSONException;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.lehuozu.R;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.BareReceiver;
import com.lehuozu.net.action.user.SendCodeFpRequest;
import com.lehuozu.ui.MyTitleActivity;
import com.lehuozu.ui.common.JackCountDownHelper;
import com.lehuozu.util.JackUtils;

public class FindPasswordActivity extends MyTitleActivity implements OnClickListener {

	private EditText et_phone;
	private EditText et_code;
	private JackCountDownHelper jcdh;

	@Override
	public int getLayoutRid() {
		return R.layout.activity_findpwd;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_findpwd));
		titleManager.initTitleBack();

		et_phone = (EditText)this.findViewById(R.id.et_fp_phone);
		et_code = (EditText)this.findViewById(R.id.et_fp_code);
		Button btn = (Button) this.findViewById(R.id.btn_sendcode);
		btn.setOnClickListener(this);
		this.findViewById(R.id.btn_done).setOnClickListener(this);
		
		jcdh = new JackCountDownHelper(btn);
	}

	@Override
	public void onClick(View v) {
		String phonestr = et_phone.getText().toString();
		String codestr = et_code.getText().toString();
		switch (v.getId()) {
		case R.id.btn_sendcode:
			if(phonestr.length()<11){
				et_phone.setError("手机号位数不对");
				return;
			}
			ActionPhpRequestImpl actReq = new SendCodeFpRequest(phonestr);
			ActionPhpReceiverImpl actRcv = new BareReceiver(this){
				@Override
				public boolean response(String result) throws JSONException {
					boolean response = super.response(result);
					if(!response){
//						JackUtils.showToast(FindPasswordActivity.this, "验证码已经发送");
						jcdh.start(30);
					}
					return response;
				}
			};
			ActionBuilder.getInstance().request(actReq, actRcv);
			break;
		case R.id.btn_done:
			if(codestr.isEmpty()){
				et_code.setError("验证码不能为空");
				return;
			}
			Intent intent = new Intent();
			intent.setClass(FindPasswordActivity.this, FindPasswordDoActivity.class);
			intent.putExtra("phone", phonestr);
			intent.putExtra("code", codestr);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}

}

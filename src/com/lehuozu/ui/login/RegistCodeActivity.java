package com.lehuozu.ui.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.R;
import com.lehuozu.data.NetConst;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.user.SendCodeForRegistReq;
import com.lehuozu.net.action.user.VerifyCodeReq;
import com.lehuozu.ui.MyGate;
import com.lehuozu.ui.MyTitleActivity;
import com.lehuozu.ui.common.ZeroTextWather;
import com.lehuozu.util.JackUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistCodeActivity extends MyTitleActivity implements ActionPhpReceiverImpl {
	private int timerSec;

	String phone;
	
	TextView tv_mobile,tv_waiting;
	EditText et_input;
	Button btn_done;
	
	Handler mHandler = new Handler(){
		
		@Override
		public void handleMessage(android.os.Message msg) {
			
			if(null==tv_waiting) return;
			switch (msg.what) {
			case 0:
				tv_waiting.setText(String.format("%d秒后可重发验证码", timerSec--));
				break;
			case 1:
				tv_waiting.setVisibility(View.VISIBLE);
				tv_again.setVisibility(View.GONE);
				break;
			case 2:
				tv_waiting.setVisibility(View.GONE);
				tv_again.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}

			
		};
		
	};

	private TextView tv_again;
	
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
		tv_again = (TextView)this.findViewById(R.id.tv_cmmtchck_again);
		et_input = (EditText)this.findViewById(R.id.et_cmmtchck_input);
		btn_done = (Button)this.findViewById(R.id.btn_cmmtchck_go);
		btn_done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				commit();
				
			}
		});
		tv_again.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getcheck();
			}
			private void getcheck() {
//				phone = et_phone.getText().toString();
				ActionPhpRequestImpl actReq = new SendCodeForRegistReq(phone);
				ActionBuilder.getInstance().request(actReq, new ActionPhpReceiverImpl() {
					
					@Override
					public boolean response(String result) throws JSONException {
						JSONObject job = new JSONObject(result);
						if(null!=job){
							if(job.has(RESULT_SIGN)&&job.getBoolean(RESULT_SIGN)){
//								MyGate.GoRegistCode(this,getPhone());
								JackUtils.showToast(getReceiverContext(), "短信已重发");
								startCount();
							}else{
								if(job.has(RESULT_ERROR_MSG)){
									String msg = job.optString(RESULT_ERROR_MSG);
									JackUtils.showToast(getReceiverContext(), msg);
								}
							}
						}
						return false;
					}
					
					@Override
					public Context getReceiverContext() {
						return RegistCodeActivity.this;
					}
				});
				
//				MyGate.GoRegistCode(this,getPhone());//test
			}
		});
		
		tv_mobile.setText(phone);
		
		startCount();
		
		et_input.addTextChangedListener(new ZeroTextWather(this, btn_done));//0102
		
	}

	/**
	 * 
	 */
	public void startCount() {
		new Thread(){//

			@Override
			public void run() {
				if(null==mHandler) return;
				timerSec=60;
				mHandler.sendEmptyMessage(1);
				while (timerSec>=0) {
					mHandler.sendEmptyMessage(0);
					SystemClock.sleep(1000);
				}
				mHandler.sendEmptyMessage(2);
			};
		}.start();
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
			finish();
		}else{
			if(null!=job)JackUtils.showToast(this, job.optString(RESULT_ERROR_MSG));
		}
		return false;
	}

	@Override
	public Context getReceiverContext() {
		return this;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		timerSec = -1;
	}
}

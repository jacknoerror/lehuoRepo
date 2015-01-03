package com.lehuozu.ui.common;

import com.lehuozu.R;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

public class ZeroTextWather implements TextWatcher {
	
	private Button btnToChange;
	private Context mContext;

	
	
	public ZeroTextWather(Context mContext, Button btn_getcheck) {
		super();
		this.btnToChange = btn_getcheck;
		this.mContext = mContext;
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		if(arg0.length()>0){
			btnToChange.setBackgroundResource(R.drawable.btn_logout);
			btnToChange.setTextColor(mContext.getResources().getColor(android.R.color.white));
		}else{
			btnToChange.setBackgroundResource(android.R.drawable.btn_default);
			btnToChange.setTextColor(mContext.getResources().getColor(android.R.color.black));
			
		}
	}
	
	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
	}
	
	@Override
	public void afterTextChanged(Editable arg0) {
	}
}

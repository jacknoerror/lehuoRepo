package com.lehuo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lehuo.R;

public abstract class MyTitleActivity extends Activity {
	protected final String TAG = getClass().getSimpleName();

	protected TitleManager titleManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getLayoutRid()>0)setContentView(getLayoutRid());
		titleManager = new TitleManager(this);
		initView();
		
	}
	
	public abstract int getLayoutRid();
	public abstract void initView();
	
}

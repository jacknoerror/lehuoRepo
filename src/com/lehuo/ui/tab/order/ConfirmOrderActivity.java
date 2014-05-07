package com.lehuo.ui.tab.order;

import com.lehuo.R;
import com.lehuo.ui.MyTitleActivity;

public class ConfirmOrderActivity extends MyTitleActivity {

	@Override
	public int getLayoutRid() {
		return R.layout.activity_confirmorder;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getResources().getString(R.string.titlename_confirmorder));

	}

}

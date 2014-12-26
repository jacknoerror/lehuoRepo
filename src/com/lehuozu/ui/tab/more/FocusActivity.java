package com.lehuozu.ui.tab.more;

import com.lehuozu.R;
import com.lehuozu.ui.MyTitleActivity;

public class FocusActivity extends MyTitleActivity {

	@Override
	public int getLayoutRid() {
		return R.layout.activity_focus;
	}

	@Override
	public void initView() {
		titleManager.setTitleName("¹Ø×¢ÎÒÃÇ");
		titleManager.initTitleBack();

	}

}

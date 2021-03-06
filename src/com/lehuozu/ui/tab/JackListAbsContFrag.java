package com.lehuozu.ui.tab;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lehuozu.R;
import com.lehuozu.ui.custom.list.ListItemImpl.Type;
import com.lehuozu.ui.custom.list.MyScrollPageListView;
import com.lehuozu.util.JackUtils;

public abstract class JackListAbsContFrag extends ContentAbstractFragment {

	protected FrameLayout mFrame;
	protected MyScrollPageListView mListView;


	@Override
	public int getLayoutRid() {
		return R.layout.fragment_common_frame;
	}

	@Override
	public void initView() {
		initTitleManager();
		titleManager.setTitleName(getString(getType()==Type.ORDER_DONE?R.string.titlename_order:R.string.titlename_deliver));
		titleManager.initTitleMenu();
		titleManager.updateCart();
		
		mFrame = (FrameLayout)mView.findViewById(R.id.layout_belowtitle);
		mListView = new MyScrollPageListView(getActivity(), getType());
		mFrame.addView(mListView);
		mListView.setDivider(getResources().getDrawable(android.R.color.transparent));
		mListView.setDividerHeight(JackUtils.dip2px(getActivity(), 15));
		
	}
	public abstract Type getType();
	
	@Override
	public void onResume() {
		super.onResume();
		mListView.setup();
	}
}

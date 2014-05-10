package com.lehuo.ui.tab;

import android.widget.FrameLayout;

import com.lehuo.R;
import com.lehuo.ui.custom.list.MyScrollPageListView;
import com.lehuo.ui.custom.list.ListItemImpl.Type;

public abstract class JackListAbsContFrag extends ContentAbstractFragment {

	FrameLayout mFrame;
	private MyScrollPageListView mListView;


	@Override
	public int getLayoutRid() {
		return R.layout.fragment_common_frame;
	}

	@Override
	public void initView() {
		initTitleManager();
		titleManager.setTitleName(getString(R.string.titlename_order));
		
		mFrame = (FrameLayout)mView.findViewById(R.id.frame_order);
		mListView = new MyScrollPageListView(getActivity(), getType());
		mFrame.addView(mListView);
		mListView.setDivider(getResources().getDrawable(android.R.color.transparent));
		mListView.setDividerHeight(20);
		mListView.setup();
	}
	public abstract Type getType();
}

/**
 * 
 */
package com.lehuo.ui.tab.order;

import android.widget.FrameLayout;

import com.lehuo.R;
import com.lehuo.ui.custom.list.ListItemImpl.Type;
import com.lehuo.ui.custom.list.MyScrollPageListView;
import com.lehuo.ui.tab.ContentAbstractFragment;

/**
 * @author taotao
 *
 */
public class TabFragOrder extends ContentAbstractFragment {
	
	FrameLayout mFrame;
	private MyScrollPageListView mListView;
	
	@Override
	public int getLayoutRid() {
		return R.layout.fragment_order;
	}

	@Override
	public void initView() {
		initTitleManager();
		titleManager.setTitleName(getString(R.string.titlename_order));
		
		mFrame = (FrameLayout)mView.findViewById(R.id.frame_order);
		mListView = new MyScrollPageListView(getActivity(), Type.ORDER);
		mFrame.addView(mListView);
		mListView.setDividerHeight(20);
		mListView.setup();
	}

}

/**
 * 
 */
package com.lehuo.ui.tab.more;

import com.lehuo.R;
import com.lehuo.ui.tab.ContentAbstractFragment;

/**
 * @author taotao
 * 
 */
public class TabFragMore extends ContentAbstractFragment {

	/*
	 */
	@Override
	public int getLayoutRid() {
		return R.layout.fragment_more;
	}

	/*
	 */
	@Override
	public void initView() {
		initTitleManager();
		titleManager.initTitleMenu();
		titleManager.updateCart();
	}

}

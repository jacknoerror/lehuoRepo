/**
 * 
 */
package com.lehuo.ui.tab.my;

import com.lehuo.R;
import com.lehuo.ui.tab.ContentAbstractFragment;

/**
 * @author taotao
 *
 */
public class TabFragMy extends ContentAbstractFragment {

	/* (non-Javadoc)
	 * @see com.lehuo.ui.JackAbsFragment#getLayoutRid()
	 */
	@Override
	public int getLayoutRid() {
		return R.layout.fragment_account;
	}

	@Override
	public void initView() {
		initTitleManager();
		titleManager.setTitleName(getString(R.string.titlename_my));

	}

}

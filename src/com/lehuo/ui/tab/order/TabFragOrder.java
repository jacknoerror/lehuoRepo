/**
 * 
 */
package com.lehuo.ui.tab.order;

import com.lehuo.R;
import com.lehuo.ui.tab.ContentAbstractFragment;

/**
 * @author taotao
 *
 */
public class TabFragOrder extends ContentAbstractFragment {
	
	
	
	@Override
	public int getLayoutRid() {
		return R.layout.fragment_order;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		initTitleManager();
		titleManager.setTitleName(getString(R.string.titlename_order));
		
		
	}

}

/**
 * 
 */
package com.lehuo.ui.tab.order;

import android.view.View;

import com.lehuo.R;
import com.lehuo.ui.MyTitleActivity;

/**
 * @author tao
 *
 */
public class AddAddressActivity extends MyTitleActivity {

	@Override
	public int getLayoutRid() {
		return R.layout.activity_addaddr;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getResources().getString(R.string.titlename_addaddr));
		titleManager.initTitleBack();
		titleManager.setRightText("±£´æ", new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});

	}

}

/**
 * 
 */
package com.lehuo.ui.courier;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import com.baidumap.GeoCoderActivity;
import com.lehuo.R;
import com.lehuo.data.NetConst;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.ui.custom.list.ListItemImpl.Type;
import com.lehuo.ui.custom.list.MyScrollPageListView;
import com.lehuo.util.JackUtils;

/**
 * @author tao
 *
 */
public class DeliverListActivity extends MyTitleActivity {

	FrameLayout mFrame;
	private MyScrollPageListView mListView;
	
	@Override
	public int getLayoutRid() {
		return R.layout.fragment_common_frame;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_deliverlist));
		titleManager.setRightText("≈‰ÀÕ", new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
//				intent.putExtra(NetConst.EXTRAS_LOCATION, JackUtils.getLocation());
				intent.setClass(DeliverListActivity.this, GeoCoderActivity.class);
				startActivity(intent);
			}
		});
		
		mFrame = (FrameLayout)this.findViewById(R.id.frame_order);
		mListView = new MyScrollPageListView(this, getType());
		mFrame.addView(mListView);
		mListView.setDivider(getResources().getDrawable(android.R.color.transparent));
		mListView.setDividerHeight(JackUtils.dip2px(this, 36));
		mListView.setup();

	}
	
	protected Type getType(){
		return Type.ORDER_COURIER;
	}

}

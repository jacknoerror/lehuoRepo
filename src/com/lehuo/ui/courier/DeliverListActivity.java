/**
 * 
 */
package com.lehuo.ui.courier;

import org.json.JSONException;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import com.baidumap.GeoCoderActivity;
import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.data.NetConst;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.JackShowToastReceiver;
import com.lehuo.net.action.courier.StartDeliverReq;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.ui.custom.list.ListItemImpl.Type;
import com.lehuo.ui.custom.list.MyScrollPageListView;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.User;

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
		titleManager.initTitleBack();
		titleManager.setRightText("≈‰ÀÕ", new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				User me = MyData.data().getMe();
				if(null==me) return;
				ActionPhpRequestImpl actReq = new StartDeliverReq(me.getUser_id());
				ActionPhpReceiverImpl actRcv = new JackShowToastReceiver(DeliverListActivity.this){
					@Override
					public boolean response(String result) throws JSONException {
						boolean response = super.response(result);
						if(!response){
							Intent intent = new Intent();
//							intent.putExtra(address, JackUtils.getLocation());
							intent.setClass(DeliverListActivity.this, GeoCoderActivity.class);
							startActivity(intent);
						}
						return response;
					}
				};
				ActionBuilder.getInstance().request(actReq, actRcv);
				
				
			}
		});
		
		mFrame = (FrameLayout)this.findViewById(R.id.frame_order);
		mListView = new MyScrollPageListView(this, getType());
		mListView.setTag(this);//0513
		mFrame.addView(mListView);
		mListView.setDivider(getResources().getDrawable(android.R.color.transparent));
		mListView.setDividerHeight(JackUtils.dip2px(this, 36));
		mListView.setup();

	}
	
	protected Type getType(){
		return Type.ORDER_COURIER;
	}

}

/**
 * 
 */
package com.lehuozu.ui.courier;

import org.json.JSONException;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import com.baidumap.GeoCoderActivity;
import com.baidumap.LocationOverlayActivity;
import com.lehuozu.R;
import com.lehuozu.data.MyData;
import com.lehuozu.data.NetConst;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.JackShowToastReceiver;
import com.lehuozu.net.action.courier.StartDeliverReq;
import com.lehuozu.ui.MyTitleActivity;
import com.lehuozu.ui.custom.list.MyScrollPageListView;
import com.lehuozu.ui.custom.list.ListItemImpl.Type;
import com.lehuozu.util.JackUtils;
import com.lehuozu.vo.User;

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
		titleManager.setRightText("¿ªÊ¼ÅäËÍ", new View.OnClickListener() {
			
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
							intent.setClass(DeliverListActivity.this, LocationOverlayActivity.class);
//							intent.setClass(DeliverListActivity.this, GeoCoderActivity.class);
							startActivity(intent);
						}
						return response;
					}
				};
				ActionBuilder.getInstance().request(actReq, actRcv);
				
				
			}
		});
		
		mFrame = (FrameLayout)this.findViewById(R.id.layout_belowtitle);
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

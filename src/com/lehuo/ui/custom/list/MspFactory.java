package com.lehuo.ui.custom.list;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.lehuo.data.MyData;
import com.lehuo.entity.json.JsonImport;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.order.GetOrderListReq;
import com.lehuo.ui.adapter.msp.ListAdapterOrder;
import com.lehuo.ui.adapter.msp.ListAdapterOrder.OrderViewHolder;
import com.lehuo.ui.custom.list.MspAdapter.ViewHolderImpl;
import com.lehuo.ui.custom.list.MyScrollPageListView.OnGetPageListener;
import com.lehuo.util.TestDataTracker;
import com.lehuo.vo.OrderInfo;
import com.lehuo.vo.User;

public class MspFactory implements MspFactoryImpl {
	
	ListItemImpl.Type type;

	public MspFactory(ListItemImpl.Type type){
		this.type = type;
	}
	
	@Override
	public MspAdapter getNewAdapter() {
		MspAdapter adapter = null;
				switch (type) {
				case ORDER:
					adapter = new ListAdapterOrder();
					break;

				default:
					break;
				}
		return adapter;
	}

	@Override
	public MspPage getMspPage(JSONObject job) {
		if(job==null) return null;
		MspPage mp = new MspPage( this);
		try {
			mp.initJackJson(job);
		} catch (JSONException e) {
			Log.e("MspFactory", "msppage initjson error");
		}
		return mp;
	}


	@Override
	public MspJsonItem getMjiInstance() {
		MspJsonItem mji=null;
		switch (type) {
		case ORDER:
			mji = new OrderInfo();
			break;

		default:
			break;
		}
		return mji;
	}

	@Override
	public OnGetPageListener getDefaultOnPageChangeListener() {
		OnGetPageListener listener = null;
		switch (type) {
		case ORDER:
			listener = new OnGetPageListener() {
				
				@Override
				public void page(MyScrollPageListView qListView, int pageNo) {
					User me = MyData.data().getMe();
					if(null==me) return;
					ActionPhpRequestImpl req = new GetOrderListReq(1, pageNo, me.getUser_id());
//					ActionBuilder.getInstance().request(req, qListView);
					TestDataTracker.simulateConnection(qListView, req.getApiName());
					
				}
			};
			break;

		default:
			break;
		}
		return listener;
	}

	@Override
	public String getPageName() {
		String name="";
		switch (type) {
		case ORDER:
			name = "orders";
			break;

		default:
			break;
		}
		return name;
	}


}

package com.lehuozu.ui.custom.list;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.lehuozu.data.MyData;
import com.lehuozu.entity.json.JsonImport;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.courier.DeliverListReq;
import com.lehuozu.net.action.order.GetOrderListReq;
import com.lehuozu.ui.adapter.msp.ListAdapterCoupon;
import com.lehuozu.ui.adapter.msp.ListAdapterCourier;
import com.lehuozu.ui.adapter.msp.ListAdapterDeliver;
import com.lehuozu.ui.adapter.msp.ListAdapterGoods;
import com.lehuozu.ui.adapter.msp.ListAdapterOrder;
import com.lehuozu.ui.adapter.msp.ListAdapterOrder.OrderViewHolder;
import com.lehuozu.ui.custom.list.ListItemImpl.Type;
import com.lehuozu.ui.custom.list.MspAdapter.ViewHolderImpl;
import com.lehuozu.ui.custom.list.MyScrollPageListView.OnGetPageListener;
import com.lehuozu.util.TestDataTracker;
import com.lehuozu.vo.OrderInfo;
import com.lehuozu.vo.Product;
import com.lehuozu.vo.User;
import com.lehuozu.vo.deliver.OrderInCourier;

public class MspFactory implements MspFactoryImpl {

	ListItemImpl.Type type;

	public MspFactory(ListItemImpl.Type type) {
		this.type = type;
	}

	@Override
	public MspAdapter getNewAdapter() {
		MspAdapter adapter = null;
		switch (type) {
		case ORDER_DELIVER:
			adapter = new ListAdapterDeliver();
			break;
		case ORDER_DONE:
			adapter = new ListAdapterOrder();
			break;
		case ORDER_COURIER:
			adapter = new ListAdapterCourier();
			break;
		case COUPON:
			adapter = new ListAdapterCoupon();
			break;
		case CART:
//			adapter = new 
			break;
		case GOODS:
			adapter = new ListAdapterGoods();
			break;
		default:
			break;
		}
		return adapter;
	}

	@Override
	public MspPage getMspPage(JSONObject job) {
		if (job == null)
			return null;
		MspPage mp = new MspPage(this);
		try {
			mp.initJackJson(job);
		} catch (JSONException e) {
			Log.e("MspFactory", "msppage initjson error");
		}
		return mp;
	}

	/* set if have pages
	 * @see com.lehuo.ui.custom.list.MspFactoryImpl#getMjiInstance()
	 */
	@Override
	public MspJsonItem getMjiInstance() {
		MspJsonItem mji = null;
		switch (type) {
		case ORDER_DELIVER:
		case ORDER_DONE:
			mji = new OrderInfo();
			break;
		case ORDER_COURIER:
			mji = new OrderInCourier();
			break;
		case GOODS:
			mji = new Product();
			break;
		default:
			break;
		}
		return mji;
	}

	/* set if have pages
	 * @see com.lehuo.ui.custom.list.MspFactoryImpl#getDefaultOnPageChangeListener()
	 */
	@Override
	public OnGetPageListener getDefaultOnPageChangeListener() {
		OnGetPageListener listener = null;
		switch (type) {
		case ORDER_DELIVER:
		case ORDER_DONE:
			listener = new OnGetPageListener() {

				@Override
				public void page(MyScrollPageListView qListView, int pageNo) {
					User me = MyData.data().getMe();
					if (null == me)
						return;
					ActionPhpRequestImpl req = new GetOrderListReq(
							type == Type.ORDER_DELIVER ? GetOrderListReq.COMPLETE_DELIVER
									: GetOrderListReq.COMPLETE_DONE, pageNo,
							me.getUser_id());
					ActionBuilder.getInstance().request(req, qListView);
					// TestDataTracker.simulateConnection(qListView,
					// req.getApiName());

				}
			};
			break;
		case ORDER_COURIER:
			listener = new OnGetPageListener() {

				@Override
				public void page(MyScrollPageListView qListView, int pageNo) {
					User me = MyData.data().getMe();
					if (null == me)
						return;
					ActionPhpRequestImpl req = new DeliverListReq(
							me.getUser_id());
					 ActionBuilder.getInstance().request(req, qListView);
//					TestDataTracker.simulateConnection(qListView, req.getApiName());

				}
			};
			break;
		case GOODS:
			//TODO already add , test it
			break;
		default:
			break;
		}
		return listener;
	}

	/* set if have pages
	 * @see com.lehuo.ui.custom.list.MspFactoryImpl#getPageName()
	 */
	@Override
	public String getPageName() {
		String name = "";
		switch (type) {
		case ORDER_DELIVER:
		case ORDER_DONE:
		case ORDER_COURIER:
			name = "orders";
			break;

		default:
			break;
		}
		return name;
	}

}

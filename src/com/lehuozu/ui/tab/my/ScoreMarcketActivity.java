package com.lehuozu.ui.tab.my;

import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lehuozu.R;
import com.lehuozu.data.MyData;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.goods.GetProductListReq;
import com.lehuozu.net.action.order.UpdateCartRcv;
import com.lehuozu.net.action.order.UpdateCartReq;
import com.lehuozu.ui.MyGate;
import com.lehuozu.ui.MyTitleActivity;
import com.lehuozu.ui.custom.list.MyScrollPageListView;
import com.lehuozu.ui.custom.list.ListItemImpl.Type;
import com.lehuozu.ui.product.ProductListActivity;
import com.lehuozu.vo.Product;

/**
 * @author tao
 * 
 */
public class ScoreMarcketActivity extends MyTitleActivity implements
		View.OnClickListener {
	FrameLayout mFrame;
	private MyScrollPageListView mListView;

	@Override
	public int getLayoutRid() {
		return R.layout.activity_common_frame;
	}
	@Override
	public void onResume() {
		super.onResume();
		if (null != titleManager) {
			ActionBuilder.getInstance().request(
					new UpdateCartReq(MyData.data().getMe().getUser_id()),
					new UpdateCartRcv(this, titleManager));
		}
	}
	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_scoremarcket));
		titleManager.initTitleBack();
//		titleManager.updateCart();

		mListView = new MyScrollPageListView(this, Type.GOODS);
		TextView emptyText = (TextView) findViewById(R.id.empty_tv);
		emptyText.setText("暂无产品");
		mListView.setEmptyView(emptyText);
		mListView.setTag(this);// 把index设置进去在监听中用
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				Product p = (Product) (parent.getAdapter()).getItem(position);
				MyGate.GoProduct(ScoreMarcketActivity.this, p);

			}

		});
		mListView
				.setOnGetPageListener(new MyScrollPageListView.OnGetPageListener() {

					@Override
					public void page(MyScrollPageListView qListView, int pageNo) {
						ActionBuilder.getInstance().request(
								new GetProductListReq(pageNo), qListView);

					}
				});
		mListView.setup();// no onResume 'cause pullToRefresh.
		mFrame = (FrameLayout) this.findViewById(R.id.framelayout_cart);
		mFrame.addView(mListView);
	}

	@Override
	public void onClick(View v) {
		NetStrategies.addToCart(this, (Integer) v.getTag(), titleManager);
	}
}

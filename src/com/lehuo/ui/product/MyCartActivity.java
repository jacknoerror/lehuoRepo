/**
 * 
 */
package com.lehuo.ui.product;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.JackShowToastReceiver;
import com.lehuo.net.action.NextActionRcv;
import com.lehuo.net.action.order.AddCartReq;
import com.lehuo.net.action.order.DelCartRcv;
import com.lehuo.net.action.order.DelCartReq;
import com.lehuo.net.action.order.GetCartRcv;
import com.lehuo.net.action.order.GetCartReq;
import com.lehuo.net.action.order.UpdateCartRcv;
import com.lehuo.net.action.order.UpdateCartReq;
import com.lehuo.ui.MyGate;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.ui.adapter.MyGridViewAdapter;
import com.lehuo.ui.adapter.MyGridViewAdapter.MyGridAbsJsonPic;
import com.lehuo.ui.custom.JackEditWam;
import com.lehuo.ui.custom.list.ListItemImpl;
import com.lehuo.ui.custom.list.MspAdapter;
import com.lehuo.ui.custom.list.MyScrollPageListView;
import com.lehuo.ui.custom.list.ListItemImpl.Type;
import com.lehuo.ui.custom.list.MspAdapter.ViewHolderImpl;
import com.lehuo.util.JackButtonColorFilter;
import com.lehuo.util.JackImageLoader;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.User;
import com.lehuo.vo.cart.CartInfo;
import com.lehuo.vo.cart.DataCart;
import com.lehuo.vo.cart.InfoGoodsInCart;
import com.lehuo.vo.cart.InfoTotal;

/**
 * @author tao
 * 
 */
public class MyCartActivity extends MyTitleActivity implements MyScrollPageListView.OnGetPageListener{

	// ListView mList;
	MyScrollPageListView mList;

	ListAdapterCart myCartAdapter;
	FrameLayout frame;

	private User me;

	public ActionPhpReceiverImpl rcvGetcart;
	InfoTotal cart_total;

	@Override
	public int getLayoutRid() {
		return R.layout.activity_mycart;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_mycart));
		titleManager.initTitleBack();
		// titleManager.updateCart();
		me = MyData.data().getMe();
		if (null == me)
			return;
		frame = (FrameLayout) this.findViewById(R.id.framelayout_cart);
		// mList = (MyScrollPageListView)
		// this.findViewById(R.id.listview_common_activity);
		mList = new MyScrollPageListView(this, Type.GOODS);
//		TextView emptyView = (TextView)this.findViewById(R.id.empty_tv);
		frame.addView(mList);
		View emptyView =  this.findViewById(R.id.empty_img);
		mList.setEmptyView(emptyView);

		rcvGetcart = new JackShowToastReceiver(this) {
			@Override
			public boolean response(String result) throws JSONException {
				if (!super.response(result)) {
					mList.setup();
					return false;
				}
				return true;
			}
		};
		mList.setOnGetPageListener(this);
		mList.setup();
		mList.setAdapter(new ListAdapterCart());
		
	}

	@Override
	public void page(MyScrollPageListView qListView, int pageNo) {
		ActionPhpRequestImpl req = new GetCartReq(me.getUser_id());//
		ActionPhpReceiverImpl rcv = new GetCartRcv(this) {

			@Override
			public Context getReceiverContext() {
				return mList.isSetup()?null:super.getReceiverContext();
			}
			
			@Override
			public boolean respJob(JSONObject job) throws JSONException {
				DataCart cart = null;
				if (!super.respJob(job)
						&& null != (cart = MyData.data().getCurrentCart())) {
					CartInfo ci = cart.getCart();
					if (null != ci) {
//						initList(ci);
						mList.updateList((List)ci.getGoods_list());
						initTotal(ci);
						return false;
					}
				}
				return true;
			}

			private void initTotal(CartInfo ci) {
				cart_total = ci.getTotal();
				if (null != cart_total) {
					TextView count = (TextView) findViewById(R.id.tv_mycart_totalcount);
					TextView price = (TextView) findViewById(R.id.tv_mycart_totalprice);
					TextView old = (TextView) findViewById(R.id.tv_mycart_oldprice);
					Button confirm = (Button) findViewById(R.id.btn_mycart_confirm);
					JackButtonColorFilter.setButtonFocusChanged(confirm);
					count.setText(String.format("共%d件商品",
							cart_total.getReal_goods_count()));//
					price.setText(cart_total.getRealPriceStr());// TODO integral
					JackUtils.textpaint_bold(old);
					JackUtils.textpaint_bold(price);
					old.setText("市场价￥" + cart_total.getMarket_price());
					JackUtils.textpaint_deleteLine(old);
					confirm.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View arg0) {
							if (cart_total.getReal_goods_count() == 0) {
								JackUtils.showToast(MyCartActivity.this,
										"购物车中没有商品");
							} else {
								MyGate.goConfirmOrder(MyCartActivity.this, null);
							}

						}
					});
//					MyData.data().setCartCount(cart_total.getReal_goods_count());//not the right count
				}
			}

			private void initList(CartInfo ci) {
				/*
				 * myCartAdapter = new MyCartAdapter(ci .getGoods_list());
				 * mList.setAdapter(myCartAdapter);
				 */
				
				
			}
		};
		ActionBuilder.getInstance().request(req, rcv);
		try {
			qListView.response("");
		} catch (JSONException e) {
		}
	}

	public class ListAdapterCart extends MspAdapter {

		public ListAdapterCart() {
			super();
		}

		public ListAdapterCart(List contentList) {
			super(contentList);
		}

		@Override
		public ViewHolderImpl getHolderInstance() {
			return new ViewHolder();
		}

		class ViewHolder extends ViewHolderImpl {
			ImageView img;
			TextView tv_name, tv_price;
			// EditText et_count;
			JackEditWam jack_wam;
			ImageView btn_del;

			@Override
			public void init() {
				img = (ImageView) getHolderView().findViewById(
						R.id.img_itemcart_ic);
				tv_name = (TextView) getHolderView().findViewById(
						R.id.tv_itemcart_pname);
				tv_price = (TextView) getHolderView().findViewById(
						R.id.tv_itemcart_price1);
				// et_count = (EditText)getHolderView()
				// .findViewById(R.id.et_itemcart_count);
				jack_wam = (JackEditWam) getHolderView().findViewById(
						R.id.jack_itemcart_wam);
				btn_del = (ImageView) getHolderView().findViewById(
						R.id.btn_itemcart_delete);

			}

			@Override
			public void setup(int position) {
				final InfoGoodsInCart itm = (InfoGoodsInCart) getItem(position);
				if (null != itm) {
					JackImageLoader.justSetMeImage(itm.getGoods_thumb(), img);
					tv_name.setText(itm.getGoods_name());
					tv_price.setText(itm.getRealPriceStr());// TODO integral
					jack_wam.setNum(itm.getGoods_number());
					jack_wam.setOnAddListener(new JackEditWam.OnEditListener() {

						@Override
						public void edit(JackEditWam jackEW) {
							ActionPhpRequestImpl req = new UpdateCartReq(jackEW
									.getNum() + 1, itm.getRec_id() + "", me
									.getUser_id());
							ActionBuilder.getInstance()
									.request(req, rcvGetcart);

						}
					});
					jack_wam.setOnMinusListener(new JackEditWam.OnEditListener() {

						@Override
						public void edit(JackEditWam jackEW) {
							ActionPhpRequestImpl req = new UpdateCartReq(jackEW
									.getNum() - 1, itm.getRec_id() + "", me
									.getUser_id());

							ActionBuilder.getInstance()
									.request(req, rcvGetcart);

						}
					});
					View.OnClickListener onClickToASkIfDeleteItem = new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							JackUtils.showDialog(MyCartActivity.this, "您确定要删除这间商品吗？", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									doDelete(itm);
									dialog.dismiss();
								}
							});
							
						}
					};
					btn_del.setOnClickListener(onClickToASkIfDeleteItem);

				}
			}

			@Override
			public int getLayoutId() {
				return R.layout.item_cart;
			}

			/**
			 * @param itm
			 */
			private void doDelete(final InfoGoodsInCart itm) {
				// 删除
				int user_id = me.getUser_id();
				ActionPhpRequestImpl req = new DelCartReq(itm
						.getRec_id(), user_id);
				ActionBuilder.getInstance()
						.request(req, rcvGetcart);
			}
		}
	}


}

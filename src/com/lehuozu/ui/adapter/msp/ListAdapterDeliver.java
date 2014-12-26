package com.lehuozu.ui.adapter.msp;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidumap.GeoCoderActivity;
import com.lehuozu.MyApplication;
import com.lehuozu.R;
import com.lehuozu.data.NetConst;
import com.lehuozu.ui.MyGate;
import com.lehuozu.ui.custom.list.MspAdapter;
import com.lehuozu.util.JackImageLoader;
import com.lehuozu.util.JackUtils;
import com.lehuozu.vo.InfoGoodsInOrder;
import com.lehuozu.vo.OrderInfo;

public class ListAdapterDeliver extends MspAdapter {

	public ListAdapterDeliver() {
		super();
	}

	public class OrderViewHolder extends ViewHolderImpl {

		LinearLayout upLayout;
		TextView tv_sn, tv_price, tv_count;
		TextView tv_checkmap;

		@Override
		public void init() {
			tv_sn = (TextView) getHolderView().findViewById(
					R.id.tv_itemproduct_whole_ordernum);
			tv_price = (TextView) getHolderView().findViewById(
					R.id.tv_itemproduct_whole_price);
			tv_count = (TextView) getHolderView().findViewById(
					R.id.tv_itemproduct_whole_prodnum);
			upLayout = (LinearLayout) getHolderView().findViewById(
					R.id.layout_itemproduct_whole);
			tv_checkmap = (TextView) getHolderView().findViewById(
					R.id.tv_checkdeliver);
		}

		@Override
		public void setup(int position) {
			final OrderInfo oi = (OrderInfo) getItem(position);
			tv_sn.setText(oi.getOrder_sn());
//			tv_price.setText("总价：" + oi.getTotal_fee());//
			tv_price.setText(Html.fromHtml(String.format("%s<font color=\"#ff0000\">%s</font>", "总价：",oi.getTotal_fee())));
			tv_count.setText("数量：x" + oi.getNums());
			// deliver
			tv_checkmap.setVisibility(View.VISIBLE);
			tv_checkmap.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int shipping_status = oi.getShipping_status();
					String order_status_state = oi.getOrder_status_state();
					int courier_status = oi.getCourier_status();
					int order_status = oi.getOrder_status();
					int pay_status = oi.getPay_status();
					Log.i("ListAdapterDeliver", "_" + shipping_status + "+"
							+ courier_status + "+" + order_status_state + "+"
							+ order_status + "+" + pay_status);
					// if()
					// goGeo();TODO
					if (shipping_status == 0) {
						JackUtils.showToast(MyApplication.app(), "还没有发货");
					} else if (shipping_status == 1) {
//						if(pay_status==2){
//							JackUtils.showToast(MyApplication.app(), "已付款");
//						}else if(pay_status==0){
							goGeo(oi);
//						}
					}
				}
			});
			//
			if (upLayout.getChildCount() == 0) {
				JSONArray jar = oi.getGoods();
				for (int i = 0; i < jar.length(); i++) {
					try {
						InfoGoodsInOrder jgio = new InfoGoodsInOrder(
								jar.getJSONObject(i));
						addGoodsView(jgio);
					} catch (JSONException e) {
						Log.e("ListAdapterOrder", "infogoodsinorder jsonwrong:"
								+ e.getMessage());
						continue;
					}
				}
			}
		}

		private void addGoodsView(InfoGoodsInOrder jgio) {
			View view = LayoutInflater.from(getContextInAdapter()).inflate(
					R.layout.item_product_single, null);
			ImageView img = (ImageView) view.findViewById(R.id.img_itemprod);
			TextView name = (TextView) view.findViewById(R.id.tv_itemprodname);
			TextView price = (TextView) view
					.findViewById(R.id.tv_itemprodprice);
			TextView count = (TextView) view.findViewById(R.id.tv_itemprodnum);
			JackImageLoader.justSetMeImage(jgio.getGoods_thumb(), img);
			name.setText(jgio.getGoods_name());
			price.setText(jgio.getGoods_price());
			count.setText("x" + jgio.getGoods_number());
			// holder?clear?
			upLayout.addView(view);
		}

		@Override
		public int getLayoutId() {
			return R.layout.item_product_order;
		}

		private void goGeo(OrderInfo oi) {
			Intent intent = new Intent();
			intent.setClass(getContextInAdapter(), GeoCoderActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra(NetConst.EXTRAS_COURIER_ID, oi.getCourier_id());
			intent.putExtra(NetConst.EXTRAS_IS_ARRIVED, oi.getCourier_status()==2);//0:未开始配送 1:正在配送中 2:配送已完成
			intent.putExtra(NetConst.EXTRAS_ORDER_ID, oi.getOrder_id());//
			getContextInAdapter().startActivity(intent);
		}

	}

	@Override
	public ViewHolderImpl getHolderInstance() {
		return new OrderViewHolder();
	}

}

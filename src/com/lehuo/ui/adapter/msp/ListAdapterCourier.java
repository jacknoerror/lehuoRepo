package com.lehuo.ui.adapter.msp;

import org.json.JSONArray;
import org.json.JSONException;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.net.NetStrategies;
import com.lehuo.ui.custom.list.MspAdapter;
import com.lehuo.vo.InfoGoodsInOrder;
import com.lehuo.vo.deliver.OrderInCourier;

public class ListAdapterCourier extends MspAdapter {

	class ViewHolder extends ViewHolderImpl{
		TextView tv_name,tv_phone,tv_address,tv_time,tv_status2,tv_status1;
		TextView tv_sn,tv_payment,tv_price,tv_count;
		LinearLayout midLayout;
		
		
		@Override
		public void init() {
			tv_name = (TextView)getHolderView().findViewById(R.id.tv_item_courier_name);
			tv_phone = (TextView)getHolderView().findViewById(R.id.tv_item_courier_phone);
			tv_address = (TextView)getHolderView().findViewById(R.id.tv_item_courier_addr);
			tv_time = (TextView)getHolderView().findViewById(R.id.tv_item_courier_time);
			tv_status1 = (TextView)getHolderView().findViewById(R.id.tv_item_courier_status1);
			tv_sn = (TextView)getHolderView().findViewById(R.id.tv_item_courier_sn);
			tv_payment = (TextView)getHolderView().findViewById(R.id.tv_item_courier_payment);
			tv_price = (TextView)getHolderView().findViewById(R.id.tv_item_courier_price);
			tv_count = (TextView)getHolderView().findViewById(R.id.tv_item_courier_count);
			tv_status2 = (TextView)getHolderView().findViewById(R.id.tv_item_courier_status2);
			midLayout = (LinearLayout)getHolderView().findViewById(R.id.layout_item_courier_mid);
			
		}

		@Override
		public void setup(int position) {
			OrderInCourier itm = (OrderInCourier) getItem(position);
			tv_name.setText("收货人："+itm.getConsignee());
			tv_phone.setText(itm.getMobile());
			tv_address.setText("地址："+itm.getAddress());
			String best_time = itm.getBest_time();
			if(null==best_time||best_time.isEmpty()) best_time = "任何时段";
			tv_time.setText("最佳送货时间："+best_time);
			tv_status1.setText(itm.getPay_status());//TODO 付款状态
			tv_sn.setText(""+itm.getOrder_sn());
			tv_payment.setText(itm.getPay_method());
			tv_price.setText(itm.getTotal_fee());
			tv_count.setText("x"+itm.getNums());
			tv_status2.setText(itm.getShipping_status());//TODO 配送状态 
			JSONArray jar = itm.getGoods();
			for(int i=0;i<jar.length();i++){
				InfoGoodsInOrder g;
				try {
					g = new InfoGoodsInOrder(jar.getJSONObject(i));
				} catch (JSONException e) {
					e.printStackTrace();
					continue;
				}
				addProdView(midLayout, g);
				
			}
			
		}

		@Override
		public int getLayoutId() {
			return R.layout.item_courier;
		}
		
		private void addProdView(LinearLayout layout,InfoGoodsInOrder g){
			if(null==layout) return;
			View view = LayoutInflater.from(context).inflate(R.layout.item_singleproduct_courier, null);
			TextView name = (TextView)view.findViewById(R.id.tv_spc_pname);
			TextView price = (TextView)view.findViewById(R.id.tv_spc_price);
			TextView count = (TextView)view.findViewById(R.id.tv_spc_count);
			name.setText(g.getGoods_name());
			price.setText(g.getGoods_price());
			count.setText("x"+g.getGoods_number());
			layout.addView(view);
		}
		
	}
	
	@Override
	public ViewHolderImpl getHolderInstance() {
		return new ViewHolder();
	}

	
	
}

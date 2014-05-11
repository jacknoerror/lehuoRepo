package com.lehuo.ui.adapter.msp;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.ui.custom.list.MspAdapter;
import com.lehuo.util.JackImageLoader;
import com.lehuo.vo.InfoGoodsInOrder;
import com.lehuo.vo.OrderInfo;

public class ListAdapterOrder extends MspAdapter {

	public ListAdapterOrder() {
		super();
	}

	public class OrderViewHolder extends ViewHolderImpl{

		LinearLayout upLayout;
		TextView tv_sn,tv_price,tv_count;
		
		@Override
		public void init() {
			tv_sn = (TextView)getHolderView().findViewById(R.id.tv_itemproduct_whole_ordernum);
			tv_price = (TextView)getHolderView().findViewById(R.id.tv_itemproduct_whole_price);
			tv_count = (TextView)getHolderView().findViewById(R.id.tv_itemproduct_whole_prodnum);
			upLayout = (LinearLayout)getHolderView().findViewById(R.id.layout_itemproduct_whole);
			
		}

		@Override
		public void setup(int position) {
			OrderInfo oi = (OrderInfo)getItem(position);
			tv_sn.setText(oi.getOrder_sn());
			tv_price.setText(oi.getTotal_fee());//
			tv_count.setText(""+oi.getNums());
			//TODO deliver
			//
			if(upLayout.getChildCount()==0){
				JSONArray jar = oi.getGoods();
				for(int i=0;i<jar.length();i++){
					try {
						InfoGoodsInOrder jgio = new InfoGoodsInOrder(jar.getJSONObject(i));
						View view = LayoutInflater.from(context).inflate(R.layout.item_product_single, null);
						ImageView img = (ImageView)view.findViewById(R.id.img_itemprod);
						TextView name = (TextView)view.findViewById(R.id.tv_itemprodname);
						TextView price = (TextView)view.findViewById(R.id.tv_itemprodprice);
						TextView count = (TextView)view.findViewById(R.id.tv_itemprodnum);
						JackImageLoader.justSetMeImage(jgio.getGoods_thumb(), img);
						name.setText(jgio.getGoods_name());
						price.setText(jgio.getGoods_price());
						count.setText("x"+jgio.getGoods_number());
						//TODO comment
						//holder?clear?
						upLayout.addView(view);
					} catch (JSONException e) {
						Log.e("ListAdapterOrder", "infogoodsinorder jsonwrong:"+e.getMessage());
						continue;
					}
				}
			}
		}

		@Override
		public int getLayoutId() {
			return R.layout.item_product_order;
		}
		
	}

	@Override
	public ViewHolderImpl getHolderInstance() {
		return new OrderViewHolder();
	}
	
}

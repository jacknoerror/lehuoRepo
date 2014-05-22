package com.lehuo.ui.adapter.msp;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.net.NetStrategies;
import com.lehuo.ui.TitleManager;
import com.lehuo.ui.custom.list.ListItemImpl;
import com.lehuo.ui.custom.list.MspAdapter;
import com.lehuo.util.JackImageLoader;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.Product;

public class ListAdapterGoods extends MspAdapter {

		
		class ViewHolder extends ViewHolderImpl{
			ImageView icon;
			TextView tv_goodsname,tv_goodsdesc,tv_priceold,tv_pricenew;
			Button btn;
			@Override
			public void init() {
				icon = (ImageView)getHolderView().findViewById(R.id.img_sprod_icon);
				tv_goodsname = (TextView)getHolderView().findViewById(R.id.tv_item_pname);
				tv_goodsdesc = (TextView)getHolderView().findViewById(R.id.tv_item_pdesc);
				tv_priceold = (TextView)getHolderView().findViewById(R.id.tv_item_sprod_priceold);
				tv_pricenew = (TextView)getHolderView().findViewById(R.id.tv_item_sprod_pricenew);
				btn = (Button)getHolderView().findViewById(R.id.btn_pl_buynow);
			}
			@Override
			public void setup(int position) {
				Product p = (Product) getItem(position);
				JackImageLoader.justSetMeImage(p.getGoods_img(), icon);
				tv_goodsname.setText(p.getGoods_name());
				tv_goodsdesc.setText(p.getGoods_desc());
				tv_priceold.setText("Ô­¼Û£º£¤"+p.getMarket_price()); 
				JackUtils.textpaint_deleteLine(tv_priceold);
				tv_pricenew.setText(p.getRealPriceStr());
				btn.setTag(p.getGoods_id());
				btn.setOnClickListener((OnClickListener) myScrollPageListView.getTag());//
			}
			@Override
			public int getLayoutId() {
				return R.layout.item_seriesproduct;
			}
		}



		@Override
		public ViewHolderImpl getHolderInstance() {
			return new ViewHolder();
		}
	}
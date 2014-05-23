package com.lehuo.ui.adapter.msp;

import org.json.JSONArray;
import org.json.JSONException;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.ui.MyGate;
import com.lehuo.ui.custom.list.MspAdapter;
import com.lehuo.util.JackImageLoader;
import com.lehuo.vo.InfoGoodsInOrder;
import com.lehuo.vo.OrderInfo;
import com.lehuo.vo.Product;

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
//			tv_price.setText("总价："+oi.getTotal_fee());//
			tv_price.setText(Html.fromHtml(String.format("%s<font color=\"#ff0000\">%s</font>", "总价：",oi.getTotal_fee())));
			tv_count.setText("数量："+oi.getNums());
			//
			if(upLayout.getChildCount()==0){
				JSONArray jar = oi.getGoods();
				for(int i=0;i<jar.length();i++){
					try {
						InfoGoodsInOrder jgio = new InfoGoodsInOrder(jar.getJSONObject(i));
						addGoodsView(jgio);
					} catch (JSONException e) {
						Log.e("ListAdapterOrder", "infogoodsinorder jsonwrong:"+e.getMessage());
						continue;
					}
				}
			}
		}

		private void addGoodsView(final InfoGoodsInOrder jgio) {
			View view = LayoutInflater.from(getContextInAdapter()).inflate(R.layout.item_product_single, null);
			ImageView img = (ImageView)view.findViewById(R.id.img_itemprod);
			TextView name = (TextView)view.findViewById(R.id.tv_itemprodname);
			TextView price = (TextView)view.findViewById(R.id.tv_itemprodprice);
			TextView count = (TextView)view.findViewById(R.id.tv_itemprodnum);
			JackImageLoader.justSetMeImage(jgio.getGoods_thumb(), img);
			name.setText(jgio.getGoods_name());
			price.setText(jgio.getGoods_price());
			count.setText("x"+jgio.getGoods_number());
			// comment
			TextView goComment = (TextView)view.findViewById(R.id.tv_itemprodcomment);
			goComment.setVisibility(View.VISIBLE);
			if(!jgio.isCommented()){
				goComment.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						MyGate.goComment(getContextInAdapter(),jgio.getGoods_id(),jgio.getOrder_id());
					}
				});
			}else{
				goComment.setText("已评论");
			}
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Product product = new Product();
					product.setGoods_id(jgio.getGoods_id());
					MyGate.GoProduct(getContextInAdapter(), product);
					
				}
			});
			//holder?clear?
			upLayout.addView(view);
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

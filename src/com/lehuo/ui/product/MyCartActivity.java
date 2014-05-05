/**
 * 
 */
package com.lehuo.ui.product;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.order.DelCartRcv;
import com.lehuo.net.action.order.DelCartReq;
import com.lehuo.net.action.order.GetCartRcv;
import com.lehuo.net.action.order.GetCartReq;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.ui.adapter.MyGridViewAdapter;
import com.lehuo.ui.adapter.MyGridViewAdapter.MyGridAbsJsonPic;
import com.lehuo.util.JackImageLoader;
import com.lehuo.vo.User;
import com.lehuo.vo.cart.DataCart;
import com.lehuo.vo.cart.InfoGoods;

/**
 * @author tao
 *
 */
public class MyCartActivity extends MyTitleActivity {

	ListView mList;
	
	MyCartAdapter myCartAdapter;

	private User me;
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_common_listview;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_mycart));
		titleManager.updateCart();
		me = MyData.data().getMe();
		if(null==me) return;
		
		mList = (ListView)this.findViewById(R.id.listview_common_activity);
		
		ActionPhpRequestImpl req = new GetCartReq(me.getUser_id());//
		ActionPhpReceiverImpl rcv= new GetCartRcv(this){
			@Override
			public boolean respJob(JSONObject job) throws JSONException {
				if(!super.respJob(job)){
					DataCart cart = 
							MyData.data().getCurrentCart();
					if(null!=cart){
						myCartAdapter = new MyCartAdapter(cart.getCart().getGoods_list());
						mList.setAdapter(myCartAdapter);
						return false;
					}
				}
				return true;
			}
		};
		ActionBuilder.getInstance().request(req, rcv);
	}

	class MyCartAdapter extends BaseAdapter{

		List<InfoGoods> contentList;
		SparseArray<View> viewMap;
		
		public MyCartAdapter(List<InfoGoods> contentList) {
			super();
			this.contentList = contentList;
		}

		@Override
		public int getCount() {
			return contentList.size();
		}

		@Override
		public Object getItem(int i) {
			return contentList.get(i);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			View view = viewMap.get(position);
			ViewHolder holder;

			if (view == null) {
				view = LayoutInflater.from(MyCartActivity.this).inflate(
						R.layout.item_cart, null);// 

				holder = new ViewHolder();
				holder.img = (ImageView) view
						.findViewById(R.id.img_itemcart_ic);
				holder.tv_name = (TextView)view.findViewById(R.id.tv_itemcart_pname);
				holder.tv_price = (TextView)view.findViewById(R.id.tv_itemcart_price1);
				holder.et_count = (EditText)view.findViewById(R.id.et_itemcart_count);
				holder.btn_del = (Button)view.findViewById(R.id.btn_itemcart_delete);

				viewMap.put(position, view);

				view.setTag(holder);
			} else {
				view = viewMap.get(position);
				holder = (ViewHolder) view.getTag();
			}

			// 哟啊不要换地方？
			final InfoGoods itm = contentList.get(position);
			if(null!=itm){
				JackImageLoader.justSetMeImage(itm.getGoods_thumb(), holder.img);
				holder.tv_name .setText(itm.getGoods_name());
				holder.tv_price .setText(itm.getGoods_price());//TODO integral
				holder.et_count.setText(itm.getGoods_number());
				holder.btn_del.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// 删除
						ActionPhpRequestImpl req = new DelCartReq(itm.getRec_id(), me.getUser_id());
						ActionPhpReceiverImpl rcv= new DelCartRcv();//
						ActionBuilder.getInstance().request(req, rcv);
					}
				});
			}
			return view;
		}
		
		
	}
	static class ViewHolder{
		ImageView img;
		TextView tv_name,tv_price;
		EditText et_count;
		Button btn_del;
	}

}

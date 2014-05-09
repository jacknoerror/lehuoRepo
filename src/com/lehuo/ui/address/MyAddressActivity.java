/**
 * 
 */
package com.lehuo.ui.address;

import java.util.List;

import org.json.JSONException;

import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.data.NetConst;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.JackFinishActivityReceiver;
import com.lehuo.net.action.JackShowToastReceiver;
import com.lehuo.net.action.user.GetUserAddrRcv;
import com.lehuo.net.action.user.GetUserAddrReq;
import com.lehuo.net.action.user.UserDelAddressReq;
import com.lehuo.net.action.user.UserSelectAddressReq;
import com.lehuo.ui.MyGate;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.User;
import com.lehuo.vo.UserAddress;

/**
 * @author tao
 * 
 */
public class MyAddressActivity extends MyTitleActivity {

	ListView mListView;
	MyAddressAdapter mAdapter;

	int from;// 0:CO;1:MY
	private User user;
	private View.OnClickListener goaaListener;

	@Override
	protected void onResume() {
		super.onResume();
		requestAddrs();
	}
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_common_listview;
	}

	@Override
	public void initView() {
		from = getIntent().getIntExtra(NetConst.EXTRAS_FROM, 0);//
		goaaListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				goAddAddr();
			}
		};
		titleManager.setTitleName(getResources().getString(
				R.string.titlename_myaddr));
		titleManager.setRightText("＋", goaaListener);

		user = MyData.data().getMe();
		if (null == user)
			return;
		

		initList(goaaListener);

	}

	/**
	 * @param l
	 */
	private void initList(View.OnClickListener l) {
		//list初始化
		mListView = (ListView) this.findViewById(R.id.listview_common_activity);
		View footerView = LayoutInflater.from(this).inflate(
				R.layout.footer_addaddr, null);
		footerView.setOnClickListener(l);
		mListView.addFooterView(footerView);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				ActionPhpRequestImpl req = new UserSelectAddressReq(user.getUser_id(), (int) arg3, true);
				ActionPhpReceiverImpl rcv = new JackFinishActivityReceiver(MyAddressActivity.this);
						
				ActionBuilder.getInstance().request(req, rcv);
			}
		});
		mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, final long id) {

				
				JackUtils.showDialog(MyAddressActivity.this, "您是否要删除这条地址信息", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ActionPhpRequestImpl req = new UserDelAddressReq((int) id);
						ActionPhpReceiverImpl rcv = new JackShowToastReceiver(MyAddressActivity.this){
							
							@Override
							public boolean response(String result)
									throws JSONException {
								boolean response = super.response(result);
								if(!response){
									mAdapter.contentList.remove(position);
									mAdapter.notifyDataSetChanged();
								}
								return response;
							}
							
						};
								
						ActionBuilder.getInstance().request(req, rcv);
						
						
						
					}
				});
			
				return false;
			}
			
			
		});
	}

	/**
	 * 
	 */
	private void requestAddrs() {
		//获取地址请求
		ActionPhpRequestImpl req = new GetUserAddrReq(user.getUser_id());
		ActionPhpReceiverImpl rcv = new GetUserAddrRcv(this) {
			@Override
			public boolean response(String result) throws JSONException {
				boolean response = super.response(result);
				if (!response) {
					updateUI();
				}
				return response;
			}
		};
		ActionBuilder.getInstance().request(req, rcv);
	}

	/**
	 * 
	 */
	private void updateUI() {
		mAdapter = new MyAddressAdapter();
		mListView.setAdapter(mAdapter);
	}

	protected void goAddAddr() {
		MyGate.goAddAddr(this);

	}

	class MyAddressAdapter extends BaseAdapter {
		List<UserAddress> contentList;
		SparseArray<View> viewMap;

		public MyAddressAdapter() {
			super();
			contentList = MyData.data().getMyAddrs();
			viewMap = new SparseArray<View>();
		}

		@Override
		public int getCount() {
			return contentList.size();
		}

		@Override
		public UserAddress getItem(int arg0) {
			return contentList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return contentList.get(arg0).getAddress_id();
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			View view = viewMap.get(position);
			ViewHolder holder;

			if (view == null) {
				view = LayoutInflater.from(MyAddressActivity.this).inflate(
						R.layout.item_address, null);//

				holder = new ViewHolder();
				holder.check = (ImageView) view
						.findViewById(R.id.img_myaddr_check);
				holder.tv_name = (TextView) view
						.findViewById(R.id.tv_myaddr_name);
				holder.tv_detail = (TextView) view
						.findViewById(R.id.tv_myaddr_detail);
				holder.tv_phone = (TextView) view
						.findViewById(R.id.tv_myaddr_phone);

				viewMap.put(position, view);

				view.setTag(holder);
			} else {
				view = viewMap.get(position);
				holder = (ViewHolder) view.getTag();
			}

			// 哟啊不要换地方？
			final UserAddress itm = contentList.get(position);
			if (null != itm) {
				holder.check.setSelected(recordPos(position));
				holder.tv_name.setText(itm.getConsignee());
				holder.tv_detail.setText(itm.getAddress());
				holder.tv_phone.setText(itm.getMobile());
			}
			return view;
		}

		private boolean recordPos(int position) {
			boolean default1 = contentList.get(position).isDefault();
			if (default1) {
				defpos = position;
			}
			return default1;
		}

		int defpos;

		public void setDefault(int pos) {
			getItem(defpos).setDefault(false);
			viewMap.get(defpos).setSelected(false);
			getItem(pos).setDefault(true);
			viewMap.get(pos).setSelected(true);
		}
	}

	static class ViewHolder {
		public TextView tv_name, tv_detail, tv_phone;
		public ImageView check;
	}
}

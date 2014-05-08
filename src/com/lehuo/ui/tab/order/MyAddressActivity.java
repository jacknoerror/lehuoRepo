/**
 * 
 */
package com.lehuo.ui.tab.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.ui.MyGate;
import com.lehuo.ui.MyTitleActivity;

/**
 * @author tao
 *
 */
public class MyAddressActivity extends MyTitleActivity {

	ListView mListView;
	MyAddressAdapter mAdapter;
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_common_listview;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getResources().getString(R.string.titlename_myaddr));
		titleManager.setRightText("гл", new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				goAddAddr();
			}
		});

		mListView = (ListView)this.findViewById(R.id.listview_common_activity);
		View footerView = LayoutInflater.from(this).inflate(R.layout.footer_addaddr, null);
		mListView.addFooterView(footerView);
		mAdapter = new MyAddressAdapter();
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//request
				//check
				//finish
			}
		});
		
	}

	protected void goAddAddr() {
		MyGate.go
		
	}
	
	class MyAddressAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}

/**
 * 
 */
package com.lehuo.ui.product;

import org.json.JSONException;

import android.content.Context;
import android.widget.ListView;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.data.NetConst;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.order.GetCartReq;
import com.lehuo.ui.MyTitleActivity;

/**
 * @author tao
 *
 */
public class MyCartActivity extends MyTitleActivity implements ActionPhpReceiverImpl{

	ListView mList;
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_common_listview;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_mycart));
		
		mList = (ListView)this.findViewById(R.id.listview_common_activity);
		
		ActionPhpRequestImpl req = new GetCartReq(MyData.data().getCurrentUser().getUser_id());//
		ActionPhpReceiverImpl rcv= this;
		ActionBuilder.getInstance().request(req, rcv);
	}

	@Override
	public boolean response(String result) throws JSONException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Context getReceiverContext() {
		return this;
	}

}

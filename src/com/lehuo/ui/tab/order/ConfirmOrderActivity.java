package com.lehuo.ui.tab.order;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.user.GetUserAddrReq;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.vo.User;

public class ConfirmOrderActivity extends MyTitleActivity {

	private User user;

	@Override
	public int getLayoutRid() {
		return R.layout.activity_confirmorder;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getResources().getString(R.string.titlename_confirmorder));
		
		user = MyData.data().getMe();
		if(null==user) return;
		ActionPhpRequestImpl req = new GetUserAddrReq(user.getUser_id());
//		ActionPhpReceiverImpl rcv= new 
//		ActionBuilder.getInstance().request(req, rcv);
		
	}

}

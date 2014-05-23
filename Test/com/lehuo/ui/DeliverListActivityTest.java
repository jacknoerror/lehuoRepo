package com.lehuo.ui;


import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;

import com.lehuo.data.MyData;
import com.lehuo.ui.courier.DeliverListActivity;
import com.lehuo.ui.product.ProductListActivity;
import com.lehuo.ui.tab.order.ConfirmOrderActivity;
import com.lehuo.vo.Category;
import com.lehuo.vo.User;

public class DeliverListActivityTest extends ActivityInstrumentationTestCase2<DeliverListActivity> {

	public DeliverListActivityTest(){
		super(DeliverListActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityIntent(new Intent());
	}
	@Override
	public void setActivityIntent(Intent i) {
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		i.putExtra(NetConst.EXTRA_PHONE, "15888889999");
		User user = new User();
		user.setUser_id(1);
		user.setIs_courier(true);
		MyData.data().setCurrentUser(user);
		super.setActivityIntent(i);
	}
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPCTest() {
		assertNotNull(getActivity());
		SystemClock.sleep(1000*30);
	}
	
}

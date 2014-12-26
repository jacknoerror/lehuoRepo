package com.lehuo.ui;


import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;

import com.lehuozu.data.MyData;
import com.lehuozu.ui.tab.order.ConfirmOrderActivity;
import com.lehuozu.vo.User;

public class ConfirmOrderActivityTest extends ActivityInstrumentationTestCase2<ConfirmOrderActivity> {

	public ConfirmOrderActivityTest(){
		super(ConfirmOrderActivity.class);
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
		user.setUser_id(28);
		MyData.data().setCurrentUser(user);
		super.setActivityIntent(i);
	}
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPCTest() {
//		fail("Not yet implemented");
//		assertEquals(2, );
		assertNotNull(getActivity());
		SystemClock.sleep(15000);
	}
	
}

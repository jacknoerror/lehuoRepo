package com.lehuo.ui;


import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;

import com.lehuozu.data.MyData;
import com.lehuozu.ui.courier.DeliverListActivity;
import com.lehuozu.ui.product.ProductListActivity;
import com.lehuozu.ui.tab.my.MyCouponActivity;
import com.lehuozu.ui.tab.my.ScoreMarcketActivity;
import com.lehuozu.ui.tab.order.ConfirmOrderActivity;
import com.lehuozu.vo.Category;
import com.lehuozu.vo.User;

public class ScoreMarketActivityTest extends ActivityInstrumentationTestCase2<ScoreMarcketActivity> {

	public ScoreMarketActivityTest(){
		super(ScoreMarcketActivity.class);
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
		user.setUser_id(18);
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
		SystemClock.sleep(1000*120);
	}
	
}

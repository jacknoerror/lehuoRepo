package com.lehuo.ui;


import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;

import com.baidumap.GeoCoderActivity;
import com.lehuo.MyApplication;
import com.lehuo.data.MyData;
import com.lehuo.data.NetConst;
import com.lehuo.ui.courier.DeliverListActivity;
import com.lehuo.ui.product.ProductListActivity;
import com.lehuo.ui.tab.order.ConfirmOrderActivity;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.Category;
import com.lehuo.vo.User;

public class GeoActivityTest extends ActivityInstrumentationTestCase2<GeoCoderActivity> {

	public GeoActivityTest(){
		super(GeoCoderActivity.class);
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
		MyData.data().setCurrentUser(user);
//		i.putExtra(NetConst.EXTRAS_LOCATION, JackUtils.getLocation(MyApplication.app()));
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
		SystemClock.sleep(25000);
		SystemClock.sleep(15000);
	}
	
	public void testGetLocation(){
		JackUtils.getLocation();
	}
	
}

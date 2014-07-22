package com.lehuo.ui;


import com.baidumap.LocationOverlayActivity;
import com.lehuo.custom.ActionBuilderTest;
import com.lehuo.data.MyData;
import com.lehuo.data.NetConst;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.user.UGetPlaceRcv;
import com.lehuo.net.action.user.UGetprovinceReq;
import com.lehuo.ui.login.PersonInfoCreateActivity;
import com.lehuo.ui.product.MyCartActivity;
import com.lehuo.ui.tab.HubActivity;
import com.lehuo.vo.Place;
import com.lehuo.vo.User;

import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.widget.Spinner;

public class LocationActivityTest extends ActivityInstrumentationTestCase2<LocationOverlayActivity> {

	public LocationActivityTest(){
		super(LocationOverlayActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityIntent(new Intent());
	}
	@Override
	public void setActivityIntent(Intent i) {
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		User user = new User();
		user.setUser_id(1);
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
		SystemClock.sleep(60*1000);
	}
	
}

package com.lehuo.ui;


import com.baidumap.LocationOverlayActivity;
import com.lehuo.custom.ActionBuilderTest;
import com.lehuozu.data.MyData;
import com.lehuozu.data.NetConst;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.user.UGetPlaceRcv;
import com.lehuozu.net.action.user.UGetprovinceReq;
import com.lehuozu.ui.login.PersonInfoCreateActivity;
import com.lehuozu.ui.product.MyCartActivity;
import com.lehuozu.ui.tab.HubActivity;
import com.lehuozu.vo.Place;
import com.lehuozu.vo.User;

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

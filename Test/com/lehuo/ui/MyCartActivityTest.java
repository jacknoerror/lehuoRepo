package com.lehuo.ui;


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

public class MyCartActivityTest extends ActivityInstrumentationTestCase2<MyCartActivity> {

	public MyCartActivityTest(){
		super(MyCartActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityIntent(new Intent());
////		intent.putExtra(NetConst.EXTRA_PHONE, "15858889999");
//		//System.out.println("---------------------------------------------------");
//		intent.setClassName("com.lehuo", PersonInfoCreateActivity.class.getName());
//		System.out.println("99999999999999999");
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		//获取被测对象context
//		app = (PersonInfoCreateActivity) getInstrumentation().startActivitySync(intent);
	}
	@Override
	public void setActivityIntent(Intent i) {
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		User user = new User();
		user.setUser_id(28);
		MyData.data().setCurrentUser(user);
//		i.putExtra(NetConst.EXTRA_PHONE, "15888889999");
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

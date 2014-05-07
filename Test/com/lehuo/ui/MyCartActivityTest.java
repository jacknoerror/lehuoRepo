package com.lehuo.ui;


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

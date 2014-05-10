package com.lehuo.ui;


import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;

import com.lehuo.data.MyData;
import com.lehuo.ui.product.ProductListActivity;
import com.lehuo.ui.tab.order.ConfirmOrderActivity;
import com.lehuo.vo.Category;
import com.lehuo.vo.User;

public class ProductListActivityTest extends ActivityInstrumentationTestCase2<ProductListActivity> {

	public ProductListActivityTest(){
		super(ProductListActivity.class);
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
		Category cat = new Category();
		cat.setCat_id(2);
		cat.setCat_name("≤‚ ‘");
		MyData.data().setCurrentUser(user);
		MyData.data().storeCategory(cat);
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
	}
	
}

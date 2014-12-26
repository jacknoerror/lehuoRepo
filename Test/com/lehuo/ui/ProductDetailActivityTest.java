package com.lehuo.ui;


import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;

import com.lehuozu.data.MyData;
import com.lehuozu.ui.product.ProductDetailActivity;
import com.lehuozu.vo.Category;
import com.lehuozu.vo.Product;
import com.lehuozu.vo.User;

public class ProductDetailActivityTest extends ActivityInstrumentationTestCase2<ProductDetailActivity> {

	public ProductDetailActivityTest(){
		super(ProductDetailActivity.class);
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
		Product product = new Product();
		product.setGoods_id(11);
		MyData.data().setCurrentUser(user);
		MyData.data().storeProduct(product);
		super.setActivityIntent(i);
	}
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPCTest() {
//		assertEquals(2, );
		assertNotNull(getActivity());
		SystemClock.sleep(1000*60);
	}
	
}

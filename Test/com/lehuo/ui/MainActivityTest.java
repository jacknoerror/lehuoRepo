package com.lehuo.ui;


import com.lehuo.data.MyData;
import com.lehuo.ui.tab.HubActivity;
import com.lehuo.vo.User;

import android.content.Intent;
import android.os.SystemClock;
import android.test.InstrumentationTestCase;

public class MainActivityTest extends InstrumentationTestCase {
	HubActivity app;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent();
		//System.out.println("---------------------------------------------------");
		intent.setClassName("com.lehuo", HubActivity.class.getName());
		System.out.println("99999999999999999");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//��ȡ�������context
		app = (HubActivity) getInstrumentation().startActivitySync(intent);
	}
	
	
	@Override
	protected void tearDown() throws Exception {
		app.finish();
		super.tearDown();
	}
	
	public void testMainTest() {
//		fail("Not yet implemented");
//		assertEquals(2, );
		assertNotNull(app.getLocalClassName());
	}
	
	/*public void shareTest(){
		app.share();
	}*/
	/*
	 * ����ܲ���
	 */
	public void testActivity() throws Exception {
//		Log.v("testActivity", "test the Activity");
		SystemClock.sleep(1500);
		//getInstrumentation().runOnMainSync(new PerformClick(button));		
		getInstrumentation().runOnMainSync(new PerformClick( ));
		SystemClock.sleep(3000);
//		assertEquals("Hello Android", text.getText().toString());
	}
	
	/*
	 *ģ�ⰴť����Ľӿ�
	 */
	private class PerformClick implements Runnable {
//		Button btn;

		public PerformClick( ) {
//			btn = button;
		}
		public void run() {
			
//			btn.performClick();
		}
	}
}

package com.lehuo.ui;


import com.lehuo.custom.ActionBuilderTest;
import com.lehuo.data.MyData;
import com.lehuo.data.NetConst;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.user.UGetPlaceRcv;
import com.lehuo.net.action.user.UGetprovinceReq;
import com.lehuo.ui.login.PersonInfoCreateActivity;
import com.lehuo.ui.tab.HubActivity;
import com.lehuo.vo.Place;

import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.widget.Spinner;

public class PersonCreateActivityTest extends ActivityInstrumentationTestCase2<PersonInfoCreateActivity> {

	public PersonCreateActivityTest(){
		super(PersonInfoCreateActivity.class);
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
		i.putExtra(NetConst.EXTRAS_PHONE, "15888889999");
		super.setActivityIntent(i);
	}
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPCTest() {
//		fail("Not yet implemented");
//		assertEquals(2, );
		assertNotNull(getActivity().spinnerPrv);
	}
	
	/*public void shareTest(){
		app.share();
	}*/
	/*
	 * 活动功能测试
	 */
	public void testActivity() throws Exception {
//		Log.v("testActivity", "test the Activity");
		SystemClock.sleep(1500);
		//getInstrumentation().runOnMainSync(new PerformClick(button));	
		
		getInstrumentation().runOnMainSync(new PerformClick( ));
		SystemClock.sleep(3000);
//		assertEquals("Hello Android", text.getText().toString());
	}
	
	public void testSpinner(){
		SystemClock.sleep(1500);
		
		ActionPhpRequestImpl req = new UGetprovinceReq();
		ActionPhpReceiverImpl rcv= new UGetPlaceRcv(getActivity(), getActivity().spinnerPrv);
//		ActionBuilder.getInstance().request(req, rcv);
		getInstrumentation().runOnMainSync(new ActionBuilderTest(req,rcv ));
		SystemClock.sleep(3500);
		getInstrumentation().runOnMainSync(new SpinnerSelectTest(getActivity().spinnerPrv,2 ));
		SystemClock.sleep(1200);
		getInstrumentation().runOnMainSync(new SpinnerSelectTest(getActivity().spinnerCt,1 ));
		SystemClock.sleep(1200);
		getInstrumentation().runOnMainSync(new SpinnerSelectTest(getActivity().spinnerDist,1 ));
		SystemClock.sleep(2500);
//		String name = ((Place)getActivity().spinnerPrv.getItemAtPosition(2)).getRegion_name();
		String name = ((Place)getActivity().spinnerPrv.getSelectedItem()).getRegion_name();
		assertNotNull(name);
		assertEquals(name, "安徽");
		SystemClock.sleep(3500);
		
//		getActivity().spinnerPrv.setse
	}
	
	public void testJava(){
		Integer a = null; Integer b = 1;
		assertNotNull(a&b);
	}
	
	class SpinnerSelectTest implements Runnable{
		
		Spinner spinner;
		int selection;
		
		
		public SpinnerSelectTest(Spinner spinner, int selection) {
			super();
			this.spinner = spinner;
			this.selection = selection;
		}


		@Override
		public void run() {
			if(null!=spinner){
				spinner.setSelection(selection);
			}
		}
		
	}
	
	/*
	 *模拟按钮点击的接口
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

package com.lehuozu.ui.tab.order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lehuozu.R;
import com.lehuozu.data.MyData;
import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.JackShowToastReceiver;
import com.lehuozu.net.action.order.CheckoutOrderReq;
import com.lehuozu.net.action.order.ConfirmOrderReq;
import com.lehuozu.ui.MyTitleActivity;
import com.lehuozu.ui.address.MyAddressActivity;
import com.lehuozu.ui.tab.HubActivity;
import com.lehuozu.util.JackUtils;
import com.lehuozu.vo.User;
import com.lehuozu.vo.checkoutorder.CheckTotal;
import com.lehuozu.vo.checkoutorder.Consignee;
import com.lehuozu.vo.checkoutorder.DataCheckout;
import com.lehuozu.vo.checkoutorder.Payment;

public class ConfirmOrderActivity extends MyTitleActivity implements
		ActionPhpReceiverImpl, View.OnClickListener {
	public static Integer PAYID;
	
	private User user;

	View addressLayout, arrivePayLayout, aliPayLayout, couponLayout
			;
	TextView tv_a_name, tv_a_detail, tv_a_phone, tv_co_nocouponhint,tv_timezone;
	Button btn_commit;
	ImageView arrivepaycheck;

	// private DataCheckout dCheckout;

	private JSONArray dc_cart;

	private Consignee dc_consignee;

	private Payment dc_payment;

	private CheckTotal dc_total;

	private JSONArray dc_user_bonus;

	private int mSingleChoiceID=-1;

	private String[] timezoneArrays;

	private String timezoneStr="";

	private void updateUI() {
		// address
		if (null != dc_consignee) {
			tv_a_name.setText(dc_consignee.getConsignee());
			tv_a_detail.setText(dc_consignee.getAddress());
			tv_a_phone.setText(dc_consignee.getMobile());
		}
		// pay
		if (null != dc_payment) {
			// check sth
			int pay_id = null!=PAYID?PAYID:dc_payment.getPay_id();//
			arrivepaycheck.setSelected(pay_id == Payment.PAYTYPE_ARRIVE);
			;
		}
		// userbonus
		if (null != dc_user_bonus && dc_user_bonus.length() > 0) {
			tv_co_nocouponhint.setVisibility(View.INVISIBLE);
			// TODO storeData?

		} else {
			tv_co_nocouponhint.setVisibility(View.VISIBLE);
		}
		// timezone

	}

	@Override
	protected void onResume() {
		super.onResume();
		requestCO();
	}

	@Override
	public int getLayoutRid() {
		return R.layout.activity_confirmorder;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getResources().getString(
				R.string.titlename_confirmorder));
		titleManager.initTitleBack();
		titleManager.setRightText("提交订单", this);
		user = MyData.data().getMe();
		if (null == user)
			return;

		addressLayout = this.findViewById(R.id.checoutclickview_address);
		arrivePayLayout = this.findViewById(R.id.checoutclickview_arrivepay);
		aliPayLayout = this.findViewById(R.id.checoutclickview_alipay);
		couponLayout = this.findViewById(R.id.checoutclickview_coupon);
		tv_timezone = (TextView)this.findViewById(R.id.checoutclickview_timezone);
		addressLayout.setOnClickListener(this);
		arrivePayLayout.setOnClickListener(this);
		aliPayLayout.setOnClickListener(this);
		couponLayout.setOnClickListener(this);
		tv_timezone.setOnClickListener(this);
		tv_a_name = (TextView) this.findViewById(R.id.tv_co_a_name);
		JackUtils.textpaint_bold(tv_a_name);
		tv_a_detail = (TextView) this.findViewById(R.id.tv_co_a_detail);
		tv_a_phone = (TextView) this.findViewById(R.id.tv_co_a_phone);
		tv_co_nocouponhint = (TextView) this
				.findViewById(R.id.tv_co_nocouponhint);
		btn_commit = (Button) this.findViewById(R.id.btn_co_commit);
		btn_commit.setOnClickListener(this);
		arrivepaycheck = (ImageView) this
				.findViewById(R.id.img_co_arrivepaycheck);
	}

	/**
	 * 
	 */
	private void requestCO() {
		ActionPhpRequestImpl req = new CheckoutOrderReq(user.getUser_id());
		ActionBuilder.getInstance().request(req, this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.checoutclickview_address:// addresslist
			intent = new Intent();
			intent.setClass(this, MyAddressActivity.class);
			intent.putExtra(NetConst.EXTRAS_FROM, 0);//
			startActivity(intent);
			break;
		case R.id.checoutclickview_arrivepay:
			arrivepaycheck.setSelected(true);
			dc_payment.setPay_id(Payment.PAYTYPE_ARRIVE);// name?
			break;
		case R.id.checoutclickview_alipay:
			break;
		case R.id.checoutclickview_coupon:
			break;
		case R.id.checoutclickview_timezone:
			showSelectTimeDialog();
			break;
		case R.id.btn_co_commit:
			commit();
			break;
		default:
			commit();
			break;
		}
	}

	/**
	 * 
	 */
	private void commit() {
		ActionPhpRequestImpl req = new ConfirmOrderReq(
				dc_consignee.getAddress_id(), 0, dc_total.getIntegral(),
				// dc_payment.getPay_id(),
				2,// 写死
				user.getUser_id(), timezoneStr);// TODO
											// bonus
											// 0,timezone
											// null
		ActionPhpReceiverImpl rcv = new JackShowToastReceiver(this) {
			public boolean response(String result) throws JSONException {
				boolean response;
				if (!(response = super.response(result))) {
					// TODO
					Intent intent = new Intent();
					intent.setClass(ConfirmOrderActivity.this,
							HubActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra(NetConst.EXTRAS_HUB, 2);//
					startActivity(intent);
				}
				return response;
			};
		};
		ActionBuilder.getInstance().request(req, rcv);
	}

	private void showSelectTimeDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);   
		 
//		mSingleChoiceID = -1; 
		 builder.setTitle("单项选择");  
		    timezoneArrays = getResources().getStringArray(R.array.timezone);
			builder.setSingleChoiceItems(timezoneArrays , 0, new DialogInterface.OnClickListener() {  
		        public void onClick(DialogInterface dialog, int whichButton) {  
		                mSingleChoiceID = whichButton;  
//		                showDialog("你选择的id为" + whichButton + " , " + mItems[whichButton]);  
//		                JackUtils.showToast(ConfirmOrderActivity.this, ""+mSingleChoiceID);
		        }  
		    });  
		    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
		        public void onClick(DialogInterface dialog, int whichButton) {  
		        	String string = "送货时段：\t\t\t";
		            if(mSingleChoiceID > 0) {  
//		            showDialog("你选择的是" + mSingleChoiceID);  
		            	timezoneStr = timezoneArrays[mSingleChoiceID];
		            }  else{
		            	timezoneStr = "";
		            }
		            tv_timezone.setText(string+timezoneStr);
		        }  
		    });  
		    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  
		        public void onClick(DialogInterface dialog, int whichButton) {  
		 
		        }  
		    });  
		   builder.create().show(); 
		
	}

	@Override
	public boolean response(String result) throws JSONException {
		JSONObject job = NetStrategies.getResultObj(result);
		if (null != job) {
			DataCheckout dCheckout = new DataCheckout(job);
			dc_cart = dCheckout.getCart();
			dc_consignee = dCheckout.getConsignee();
			dc_payment = dCheckout.getPayment();
			dc_total = dCheckout.getTotal();
			dc_user_bonus = dCheckout.getUser_bonus();
			// update UI
			updateUI();
			return false;
		}
		finish();
		return true;
	}

	@Override
	public Context getReceiverContext() {
		return this;
	}

}

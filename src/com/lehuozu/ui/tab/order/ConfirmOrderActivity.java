package com.lehuozu.ui.tab.order;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.Result;
import com.alipay.sdk.pay.SignUtils;
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
import com.lehuozu.ui.tab.my.MyCouponActivity;
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
	private JSONObject dc_user_points;
	private JSONArray dc_user_bonus;

	private int mSingleChoiceID=-1;

	private String[] timezoneArrays;

	private String timezoneStr="";

	private ImageView alipaycheck;

	private String bonus_name;

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
			//  storeData?
			if(!tv_co_nocouponhint.getText().toString().contains("无")) tv_co_nocouponhint.setVisibility(View.VISIBLE);

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
		arrivepaycheck = (ImageView) this.findViewById(R.id.img_co_arrivepaycheck);
		alipaycheck = (ImageView) this.findViewById(R.id.img_co_alipaycheck);
		
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
			alipaycheck.setSelected(false);
			dc_payment.setPay_id(Payment.PAYTYPE_ARRIVE);// name?
			break;
		case R.id.checoutclickview_alipay:
			alipaycheck.setSelected(true);
			arrivepaycheck.setSelected(false);
			dc_payment.setPay_id(Payment.PAYTYPE_ALIPAY);
			break;
		case R.id.checoutclickview_coupon:
			if(null != dc_user_bonus && dc_user_bonus.length() > 0){
//				MyGate.goCoupon(this);
				Intent couponIntent = new Intent();
				couponIntent.setClass(this, MyCouponActivity.class);
				couponIntent.putExtra(NetConst.EXTRAS_NEEDBACKUP, true);
				startActivityForResult(couponIntent, 0x998);
			}
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK&&requestCode == 0x998){
			bonus_id = data.getIntExtra("bonus_id", 0);
			bonus_name = data.getStringExtra("bonus_name");
			if(bonus_id>0){
				tv_co_nocouponhint.setText(bonus_name);
				tv_co_nocouponhint.setVisibility(View.VISIBLE);
			}
		}
	}
	
	private long commitOrderId;
	private int bonus_id;


	

	/**
	 * 
	 */
	private void commit() {
		ActionPhpRequestImpl req = new ConfirmOrderReq(
				dc_consignee.getAddress_id(), 
				bonus_id, //1229
//				dc_total.getIntegral(),
				null!=dc_user_points?dc_user_points.optInt("available"):0,//1229
				 dc_payment.getPay_id(),
				user.getUser_id(), timezoneStr);// 
											// bonus
											// 0,timezone
											// null
		ActionPhpReceiverImpl rcv = new JackShowToastReceiver(this) {

			public boolean response(String result) throws JSONException {
				boolean response;
				//{"result":true,"data":{"shipping_id":0,"pay_id":1,"pack_id":0,"card_id":0,"card_message":"","surplus":0,"integral":0,"bonus_id":0,"need_inv":0,"inv_type":"","inv_payee":"","inv_content":"","postscript":"","how_oos":"\u7b49\u5f85\u6240\u6709\u5546\u54c1\u5907\u9f50\u540e\u518d\u53d1","need_insure":0,"user_id":49,"add_time":1419815414,"order_status":0,"shipping_status":0,"pay_status":0,"agency_id":0,"extension_code":"","extension_id":0,"address_id":"44","address_name":"","consignee":"\u5f88\u4e45\u4e86","email":"","country":"1","province":"31","city":"383","district":"3233","address":"\u6c5f\u5357\u5927\u9053","zipcode":"","tel":"","mobile":"15858596499","sign_building":"","best_time":"","is_default":"1","bonus":0,"goods_amount":99,"discount":null,"tax":0,"shipping_name":"\u81ea\u6709\u5feb\u9012","shipping_fee":10,"insure_fee":0,"pay_name":"\u652f\u4ed8\u5b9d","pay_fee":null,"cod_fee":0,"pack_fee":0,"card_fee":0,"order_amount":"109.00","integral_money":0,"from_ad":"0","referer":"","parent_id":0,"order_sn":"2014122945044","order_id":281,"log_id":276},"message":"\u8ba2\u5355\u751f\u4ea7\u6210\u529f"}
				if (!(response = super.response(result))) {
					if(dc_payment.getPay_id()==Payment.PAYTYPE_ALIPAY){
						JSONObject job = new JSONObject(result).optJSONObject("data");
						commitOrderId = job.optLong("order_sn");
						pay(null);
					}else{
						gobacktohub(2);
					}
				}
				return response;
			}

		};
		ActionBuilder.getInstance().request(req, rcv);
	}

	/**
	 * 
	 */
	public void gobacktohub(int tab) {
		Intent intent = new Intent();
		intent.setClass(ConfirmOrderActivity.this,
				HubActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(NetConst.EXTRAS_HUB, tab);//
		startActivity(intent);
	};
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
			dc_user_points = dCheckout.getUser_points();
//			Log.i(TAG, dc_total.getAmount()+":总价");
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

	
	/**
	 * alipay
	 */
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;
	
	
	/*private static final int RQF_PAY = 1;
	private static final int RQF_LOGIN = 2;*/
	/*void onPay(){
		try {
			String info = getNewOrderInfo();
			String sign = Rsa.sign(info, Keys.PRIVATE);
			sign = URLEncoder.encode(sign);
			info += "&sign=\"" + sign + "\"&" + getSignType();
			Log.i("ConfirmOrderActivity", "start pay");
			// start the pay.
			Log.i(TAG, "info = " + info);

			final String orderInfo = info;
			new Thread() {
				public void run() {
					AliPay alipay = new AliPay(ConfirmOrderActivity.this, mHandler);
					
					//设置为沙箱模式，不设置默认为线上环境
					//alipay.setSandBox(true);

					String result = alipay.pay(orderInfo);

					Log.i(TAG, "result = " + result);
					Message msg = new Message();
					msg.what = RQF_PAY;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			}.start();

		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(ConfirmOrderActivity.this, "尝试付款时出错",
					Toast.LENGTH_SHORT).show();
		}
	}*/
	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {
		String orderInfo = getOrderInfo("乐活族", dc_payment.getPay_name(), "0.01");//FIXME dc_total.getAmount();
		String sign =sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(ConfirmOrderActivity.this);
				// 调用支付接口
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
	/*private String getNewOrderInfo() {
		if(commitOrderId<=0) throw new IllegalStateException("订单号不正确"); 
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Keys.DEFAULT_PARTNER);
		sb.append("\"&out_trade_no=\"");
		sb.append(commitOrderId);//taotao
		sb.append("\"&subject=\"");
		sb.append(dc_payment.getPay_id());
		sb.append("\"&body=\"");
		sb.append(dc_payment.getPay_name());
		sb.append("\"&total_fee=\"");
		sb.append("0.01");// dc_total.getAmount();
		sb.append("\"&notify_url=\"");
		// 网址需要做URL编码
		sb.append(URLEncoder.encode("http://ilehuozu.com:8084/zbcbaba/notify_url.php"));
//		sb.append(URLEncoder.encode("http://notify.java.jpxx.org/index.jsp"));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
//		sb.append(URLEncoder.encode("http://ilehuozu.com:8084/zbcbaba/notify_url.php"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(Keys.DEFAULT_SELLER);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"&it_b_pay=\"1m");
		sb.append("\"");

		return new String(sb);
	}*/
	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {
		// 合作者身份ID
		String orderInfo = "partner=" + "\"" + DEFAULT_PARTNER + "\"";

		// 卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + DEFAULT_SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + commitOrderId + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";//

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://ilehuozu.com:8084/zbcbaba/notify_url.php"
//				orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// 接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}
	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, PRIVATE);
	}
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				Result resultObj = new Result((String) msg.obj);
				String resultStatus = resultObj.resultStatus;

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(ConfirmOrderActivity.this, "支付成功",
							Toast.LENGTH_SHORT).show();
					gobacktohub(2);//
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000” 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(ConfirmOrderActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(ConfirmOrderActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();
						gobacktohub(0);//
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {//not going here
				Toast.makeText(ConfirmOrderActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};
	/*Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Result result = new Result((String) msg.obj);
			if(null==result) return;
			switch (msg.what) {
			case RQF_PAY:
				gobacktohub(result.getResult().contains("取消")?0:2);//
			case RQF_LOGIN: 
				Toast.makeText(ConfirmOrderActivity.this, result.getResult(),
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};*/
	//合作身份者id，以2088开头的16位纯数字
		public static final String DEFAULT_PARTNER = "2088311474028851";

		//收款支付宝账号
		public static final String DEFAULT_SELLER = "ilehuozu@163.com";

		//商户私钥，自助生成
		public static final String PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALVRpT4eSKRqGDuNkcKkqbD0Krtilo5v8gTWIOwpJK63YIvxrNEsVztqRx+aR/XEdcHyNBNbAGEIA2X7Gu1L7cmu2b7cn2MmDJTwfPGQMCybrBB8lXdBgkIBLrxMM/Jo+PjWETJPBUUk/GXqZ/AZX3icXZ8THrISBkwXe25vjTGzAgMBAAECgYBK5uRtKdN2YAGMsGnTT3RuDh+M8yggxSvkRZSqGkD2D/jJNtfePQP4HmotKu2pIDRJH0XV7RTWAJpuyXGRL3mV0iwgUIy0ggcP7dhjoJx5jiMJxwFoG1o79zDa9mgdbqSOb3F5oQWNvRpMfBnE67O/psTfbVkr5d7GVIAh1VBjuQJBANkb0VHV+++Wr8P0s0y8daqgdnqX/3YwjL9QFKupEJa7/9p8P9ZYupX5GWNxXI549q4rJrBxw3IzRY5l33RNJwcCQQDVzJR4cStjP3Big1TEwFC+54mcXZrs2yf9yfHkMufigKEBQZezCL+GRAwoMY/oNH8gaZ2ESR9jWO3JaI80RGj1AkEApg/w+3eBTLEln+z7eCZumiRCe2Lns69O+MZ4CRU36xPBj4yaB4m2rh/qm3WKJi+//1hiL3PU2vT8rv6c/IhG4QJBAKsDt4cXzwLWTckfEAFJa80oW5St8yyeqMCCdnB4n684AJGGrBdTWg/GAotsCZZN15pPoOWdr/PBwIKollPSnLkCQAkzsa4hE75YwP5XI+KXGs4c+ekM8VdghmWDI7TFGPqBd3rSfc/AhtFpZ0FSZ77di0SN4FX01uRNZxil8jdBec8=";

		public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}

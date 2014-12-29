package com.lehuozu.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.lehuozu.data.MyData;
import com.lehuozu.data.NetConst;
import com.lehuozu.ui.address.AddAddressActivity;
import com.lehuozu.ui.common.ViewpagerActivity;
import com.lehuozu.ui.courier.DeliverListActivity;
import com.lehuozu.ui.login.FindPasswordActivity;
import com.lehuozu.ui.login.PersonInfoCreateActivity;
import com.lehuozu.ui.login.RegistCodeActivity;
import com.lehuozu.ui.login.RegistPhoneActivity;
import com.lehuozu.ui.product.CommentActivity;
import com.lehuozu.ui.product.ProductDetailActivity;
import com.lehuozu.ui.product.ProductListActivity;
import com.lehuozu.ui.tab.HubActivity;
import com.lehuozu.ui.tab.more.AboutUsActivity;
import com.lehuozu.ui.tab.more.FocusActivity;
import com.lehuozu.ui.tab.my.MyCouponActivity;
import com.lehuozu.ui.tab.order.ConfirmOrderActivity;
import com.lehuozu.util.JackUtils;
import com.lehuozu.vo.Category;
import com.lehuozu.vo.Product;
import com.lehuozu.vo.User;

public class MyGate implements NetConst {
	@SuppressWarnings("rawtypes")
	private static void justGo(Context context, Class clazz) {
		Intent intent = new Intent();
		intent.setClass(context, clazz);
		context.startActivity(intent);
	}

	public static void goGeo() {
		// TODO
		// intent.setClass(ShActivityInfo.this, GeoCoderActivity.class);
		// intent.putExtra("lat", shHelper.dfCompany.getCompLatitude());
		// intent.putExtra("lon", shHelper.dfCompany.getCompLongitude());
		// intent.putExtra("searchkey", shHelper.dfCompany.getCompAddress());
		// startActivity(intent);
	}

	public static void goAddAddr(Context context) {
		justGo(context, AddAddressActivity.class);
	}

	public static void goConfirmOrder(Context context, Object obj) {
		// if(null==obj)return;
		justGo(context, ConfirmOrderActivity.class);
	}

	public static void goCategory(Context context, Category category) {
		if (null == category)
			return;
		MyData.data().storeCategory(category);
		justGo(context, ProductListActivity.class);
	}

	public static void GoProduct(Context context, Product product) {
		if (null == product)
			return;
		MyData.data().storeProduct(product);
		Intent intent = new Intent();
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, ProductDetailActivity.class);
		context.startActivity(intent);
	}

	public static void GoRegist(Context context) {
		justGo(context, RegistPhoneActivity.class);
	}

	public static void GoRegistCode(Context context, String phone) {
		// justGo(context, RegistCodeActivity.class);
		Intent intent = new Intent();
		intent.putExtra(EXTRAS_PHONE, phone);
		intent.setClass(context, RegistCodeActivity.class);
		context.startActivity(intent);
	}

	public static void GoSetUser(Context context, String phone) {
		Intent intent = new Intent();
		intent.putExtra(EXTRAS_PHONE, phone);
		intent.setClass(context, PersonInfoCreateActivity.class);
		context.startActivity(intent);
	}

	public static void login(Context context, User user) {
		if (null == user || user.getUser_id() == 0) {
			JackUtils.showToast(context, "获取用户数据出错");
			return;
		}
		MyData.data().setCurrentUser(user);
//		user.setIs_courier(true);// test
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		if (user.isCourier()) {
			intent.setClass(context, DeliverListActivity.class);
		} else {
			intent.setClass(context, HubActivity.class);
		}
		context.startActivity(intent);
	}

	public static void goComment(Context context, int goods_id, int order_id) {
		if(null==context) return;
		Intent intent = new Intent();
		intent.setClass(context, CommentActivity.class);
		intent.putExtra(NetConst.EXTRAS_GOODS_ID, goods_id);
		intent.putExtra(NetConst.EXTRAS_ORDER_ID, order_id);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static void goCoupon(Context context) {
		justGo(context, MyCouponActivity.class);
	}

	public static void goViewPager(Context context, String[] ppPaths,int position) {
		if(null==context) return;
//		int picIndex = mViewPager.getCurrentItem();
    	Intent intent = new Intent();
    	intent.setClass(context, ViewpagerActivity.class);
    	intent.putExtra(NetConst.EXTRAS_SHOWPIC_PAGE, position);//
    	intent.putExtra(NetConst.EXTRAS_SHOWPIC_PATHS, ppPaths);//1022
    	context.startActivity(intent);
	}

	public static void goFocus(Activity activity) {
		justGo(activity, FocusActivity.class);
		
	}

	public static void goFind(Activity loginActivity) {
		justGo(loginActivity, FindPasswordActivity.class);
		
	}

	public static void goAbout(Activity activity,String htmlContent) {
		Intent intent = new Intent();
		intent.setClass(activity, AboutUsActivity.class);
		intent.putExtra("htmlcontent", htmlContent);
		activity.startActivity(intent);
	}

}

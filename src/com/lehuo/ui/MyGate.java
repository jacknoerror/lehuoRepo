package com.lehuo.ui;

import android.content.Context;
import android.content.Intent;

import com.lehuo.data.MyData;
import com.lehuo.data.NetConst;
import com.lehuo.ui.login.PersonInfoCreateActivity;
import com.lehuo.ui.login.RegistCodeActivity;
import com.lehuo.ui.login.RegistPhoneActivity;
import com.lehuo.ui.product.ProductDetailActivity;
import com.lehuo.ui.product.ProductListActivity;
import com.lehuo.ui.tab.HubActivity;
import com.lehuo.ui.tab.order.ConfirmOrderActivity;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.Category;
import com.lehuo.vo.Product;
import com.lehuo.vo.User;

public class MyGate implements NetConst{
	@SuppressWarnings("rawtypes")
	private static void justGo(Context context , Class clazz){
		Intent intent = new Intent();
		intent.setClass(context, clazz);
		context.startActivity(intent);
	}
	public static void goAddAddr(Context context){
		justGo(context, clazz)
	}
	public static void goConfirmOrder(Context context,Object obj){
//		if(null==obj)return;
		justGo(context, ConfirmOrderActivity.class);
	}
	public static void goCategory(Context context,Category category){
		if(null==category)return;
		MyData.data().storeCategory(category);
		justGo(context,ProductListActivity.class );
	}
	
	public static void GoProduct(Context context,Product product){
		if(null==product)return;
		MyData.data().storeProduct(product);
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, ProductDetailActivity.class);
		context.startActivity(intent);
	}
	
	public static void GoRegist(Context context){
		justGo(context, RegistPhoneActivity.class);
	}
	public static void GoRegistCode(Context context,String phone){
//		justGo(context, RegistCodeActivity.class);
		Intent intent = new Intent();
		intent.putExtra(EXTRAS_PHONE, phone);
		intent.setClass(context, RegistCodeActivity.class);
		context.startActivity(intent);
	}
	public static void GoSetUser(Context context,String phone){
		Intent intent = new Intent();
		intent.putExtra(EXTRAS_PHONE, phone);
		intent.setClass(context, PersonInfoCreateActivity.class);
		context.startActivity(intent);
	}

	public static void login(Context context, User user) {
		if(null==user||user.getUser_id()==0){
			JackUtils.showToast(context, "获取用户数据出错");
			return;
		}
		MyData.data().setCurrentUser(user);
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		intent.setClass(context, HubActivity.class);
		context.startActivity(intent);
	}
}

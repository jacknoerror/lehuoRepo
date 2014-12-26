package com.lehuozu.util;

import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LocationGetter  {

	LocationManager lm;
	String providerName;
	LocationListener listener;

	double[] locationArr = new double[2];
	private Context context2;

	public LocationGetter(Context context,LocationListener ll) {
		super();
		listener = ll;
		context2 = context;
		lm = (LocationManager) context2
				.getSystemService(Context.LOCATION_SERVICE);
		// 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前已停用的。
		List<String> lp = lm.getAllProviders();
		for (String item : lp) {
			Log.i("8023", "可用位置服务：" + item);
		}

		Criteria criteria = new Criteria();
		criteria.setCostAllowed(false);
		// 设置位置服务免费
		criteria.setAccuracy(Criteria.ACCURACY_COARSE); // 设置水平位置精度
		// getBestProvider 只有允许访问调用活动的位置供应商将被返回
		providerName = lm.getBestProvider(criteria, true);
		getLoc();
	}



	/**
	 * 
	 */
	public double[] getLoc() {
		Log.i("8023", "------位置服务：" + providerName);
		if (providerName != null) {
			Location location = lm.getLastKnownLocation(providerName);
			// 因为返回的是上次定位信息 所以很有可能返回为null
			if (location == null) {
				lm.requestLocationUpdates(providerName, 0,0, listener);
				return null;
			}
			Log.i("8023", "-------" + location);

			// 获取维度信息
			double latitude = location.getLatitude();
			// 获取经度信息
			double longitude = location.getLongitude();
			// textView.setText("定位方式： "+providerName+"  维度："+latitude+"  经度："+longitude);
			locationArr[0] = latitude;
			locationArr[1] = longitude;
			return locationArr;
		} else {
			Toast.makeText(context2, "1.请检查网络连接 \n2.请打开我的位置", Toast.LENGTH_SHORT)
					.show();
			return null;
		}
	}



	/**
	 * 记得调用
	 */
	public void onResume() {
		lm.requestLocationUpdates(providerName, 0,0, listener);
	}

	/**
	 * 记得调用
	 */
	public void onPause() {
		lm.removeUpdates(listener);
	}

}

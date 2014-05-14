package com.lehuo.net.location;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.baidumap.GeoCoderActivity;
import com.lehuo.MyApplication;
import com.lehuo.data.MyData;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.JackShowToastReceiver;
import com.lehuo.net.action.user.SendCourierLocReq;
import com.lehuo.net.location.LocHandlerHelper.GeoGetter;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.User;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

/**
 * @author taotao get locations per 10 sec
 */
public class LocationService extends Service implements LocationListener,
		GeoGetter {

	private static final String TAG = "LocationService";
	private IBinder binder = new LocationService.LocalBinder();

	public LocationManager lm;
	String providerName;
	public LocationListener listener;

	private boolean RUNNING = true;

	@Override
	public IBinder onBind(Intent intent) {

		return binder;
	}

	public void onActivityResume() {
		if (null != lm)
			lm.requestLocationUpdates(providerName, 0, 0, listener);
	}

	public void onActivityPause() {
		if (null != lm)
			lm.removeUpdates(listener);
	}

	MediaPlayer mediaPlayer = null;
	private double[] locArr;

	Handler aHandler = null;;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "onCreate");
		// 这里可以启动媒体播放器
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		getProvider();
		listener = this;
		locArr = new double[2];

		
//		initSearch();
		
		new Thread() {
			@Override
			public void run() {
				while (RUNNING) {
					Log.i(TAG, "--running:" + locArr[0] + "+" + locArr[1]);
					// 定时重新获得提供器
					if (count % 8 == 7) {
						count -= 7;
						getProvider();
					}
					// 获取位置
						// tryLocation();
					tryHandler.sendEmptyMessage(0);
					// 发请求
					if (locArr[0] != 0) {
//						searchByLatlon(locArr[0], locArr[1]);
						User me = MyData.data().getMe();
						if (null != me) {
							ActionPhpRequestImpl actReq = new SendCourierLocReq(
									GeoCoderActivity.addressname, locArr[0] + "", locArr[1] + "",
									me.getUser_id());
//							ActionPhpReceiverImpl actRcv = new JackShowToastReceiver(
//									null);
//							ActionBuilder.getInstance().request(actReq, actRcv);
							String result;
							try {
								result = NetStrategies.doHttpRequest(actReq.toHttpBody());
								Log.d(TAG, "service result:"+result);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					// 通知修改位置
					if (null != aHandler) {
						aHandler.sendEmptyMessage(0);
					}

					SystemClock.sleep(1000 * 10);
					count++;
				}
			};

		}.start();

	}

	
	

	Handler tryHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			tryLocation();
		};

	};

	/**
	 * // 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前已停用的。
	 */
	private void getProvider() {
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
	}

	int count;

	public void setHandler(Handler handler) {
		aHandler = handler;
	}

	/**
	 * 
	 */
	private void tryLocation() {
		Log.i("8023", "------位置服务：" + providerName);
		if (providerName != null) {
			Location location = lm.getLastKnownLocation(providerName);
			// 因为返回的是上次定位信息 所以很有可能返回为null
			if (location == null) {
				Log.e(TAG, "failed to get last know loc");
				lm.requestLocationUpdates(providerName, 0, 0, listener);
				return;
			}
			Log.i("8023", "-------" + location);

			// 获取维度信息
			double latitude = location.getLatitude();
			// 获取经度信息
			double longitude = location.getLongitude();
			// textView.setText("定位方式： "+providerName+"  维度："+latitude+"  经度："+longitude);
			locArr[0] = latitude;
			locArr[1] = longitude;
			// JackUtils.showToast(MyApplication.app(),
			// "tryLocation():"+latitude+"+"+longitude);
			// return locArr;
		} else {
			Toast.makeText(MyApplication.app(), "1.请检查网络连接 \n2.请打开我的位置",
					Toast.LENGTH_SHORT).show();
			// return null;
		}
	}

	@Override
	public double[] getLoc() {
		return locArr;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
		RUNNING = false;
	}

	// 定义内容类继承Binder
	public class LocalBinder extends Binder {
		// 返回本地服务
		public LocationService getService() {
			return LocationService.this;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		locArr[0] = location.getLatitude();
		locArr[1] = location.getLongitude();
		Log.i(TAG, "onLocationChanged");
		JackUtils.showToast(MyApplication.app(), "onLocationChanged:"
				+ locArr[0] + "+" + locArr[1]);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.i(TAG, "onStatusChanged");

	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

}

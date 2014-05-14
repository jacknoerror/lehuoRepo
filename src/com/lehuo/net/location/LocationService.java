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
		// �����������ý�岥����
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
					// ��ʱ���»���ṩ��
					if (count % 8 == 7) {
						count -= 7;
						getProvider();
					}
					// ��ȡλ��
						// tryLocation();
					tryHandler.sendEmptyMessage(0);
					// ������
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
					// ֪ͨ�޸�λ��
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
	 * // ����������֪��λ���ṩ�ߵ������б�����δ��׼���ʻ���ûĿǰ��ͣ�õġ�
	 */
	private void getProvider() {
		List<String> lp = lm.getAllProviders();
		for (String item : lp) {
			Log.i("8023", "����λ�÷���" + item);
		}

		Criteria criteria = new Criteria();
		criteria.setCostAllowed(false);
		// ����λ�÷������
		criteria.setAccuracy(Criteria.ACCURACY_COARSE); // ����ˮƽλ�þ���
		// getBestProvider ֻ��������ʵ��û��λ�ù�Ӧ�̽�������
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
		Log.i("8023", "------λ�÷���" + providerName);
		if (providerName != null) {
			Location location = lm.getLastKnownLocation(providerName);
			// ��Ϊ���ص����ϴζ�λ��Ϣ ���Ժ��п��ܷ���Ϊnull
			if (location == null) {
				Log.e(TAG, "failed to get last know loc");
				lm.requestLocationUpdates(providerName, 0, 0, listener);
				return;
			}
			Log.i("8023", "-------" + location);

			// ��ȡά����Ϣ
			double latitude = location.getLatitude();
			// ��ȡ������Ϣ
			double longitude = location.getLongitude();
			// textView.setText("��λ��ʽ�� "+providerName+"  ά�ȣ�"+latitude+"  ���ȣ�"+longitude);
			locArr[0] = latitude;
			locArr[1] = longitude;
			// JackUtils.showToast(MyApplication.app(),
			// "tryLocation():"+latitude+"+"+longitude);
			// return locArr;
		} else {
			Toast.makeText(MyApplication.app(), "1.������������ \n2.����ҵ�λ��",
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

	// ����������̳�Binder
	public class LocalBinder extends Binder {
		// ���ر��ط���
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

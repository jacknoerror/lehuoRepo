package com.lehuo.net.service;

import java.util.List;

import com.lehuo.MyApplication;
import com.lehuo.util.JackUtils;

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
public class LocationService extends Service implements LocationListener {

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

	public void onActivityResume(){
		if(null!=lm)lm.requestLocationUpdates(providerName, 0,0, listener);
	}
	public void onActivityPause(){
		if(null!=lm)lm.removeUpdates(listener);
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
		// ����������֪��λ���ṩ�ߵ������б�����δ��׼���ʻ���ûĿǰ��ͣ�õġ�
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
		listener = this;
		locArr = new double[2];
		tryLocation();

		
		new Thread(){
@Override
			public void run() {
				while (RUNNING) {
					SystemClock.sleep(1000*10);
					Log.i(TAG, "--running:"+locArr[0]+"+"+locArr[1]);
					//inform activity to update location
					if(null!=aHandler){
						aHandler.sendEmptyMessage(0);
					}
				}
			};
			
		}.start();
		
	}

	public void setHandler(Handler handler){
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
				return  ;
			}
			Log.i("8023", "-------" + location);

			// ��ȡά����Ϣ
			double latitude = location.getLatitude();
			// ��ȡ������Ϣ
			double longitude = location.getLongitude();
			// textView.setText("��λ��ʽ�� "+providerName+"  ά�ȣ�"+latitude+"  ���ȣ�"+longitude);
			locArr[0] = latitude;
			locArr[1] = longitude;
			JackUtils.showToast(MyApplication.app(), "tryLocation():"+latitude+"+"+longitude);
//			return locArr;
		} else {
			Toast.makeText(MyApplication.app(), "1.������������ \n2.����ҵ�λ��",
					Toast.LENGTH_SHORT).show();
//			return null;
		}
	}
	
	public double[] getLoc(){
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
		JackUtils.showToast(MyApplication.app(), "onLocationChanged:"+locArr[0]+"+"+locArr[1]);
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

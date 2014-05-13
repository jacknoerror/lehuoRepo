package com.baidumap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.lehuo.MyApplication;
import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.user.GetCourierReq;
import com.lehuo.net.service.LocationService;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.util.JackImageLoader;
import com.lehuo.util.JackUtils;
import com.lehuo.util.LocationGetter;
import com.lehuo.vo.deliver.CourierInfo;

/**
 * ��demo����չʾ��ν��е�������������õ�ַ�������꣩����������������������������ַ��
 * ͬʱչʾ�����ʹ��ItemizedOverlay�ڵ�ͼ�ϱ�ע����� [network 30.279750,120.110628 acc=88
 * et=+4d11h39m19s805ms]
 */
public class GeoCoderActivity extends MyTitleActivity implements
		LocationListener {
	/*
	 * //UI��� Button mBtnReverseGeoCode = null; // �����귴����Ϊ��ַ Button mBtnGeoCode
	 * = null; // ����ַ����Ϊ����
	 */
	TextView tv_title;
	// ��ͼ���
	MapView mMapView = null; // ��ͼView
	// �������
	// MKSearch mSearch = null; // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��

	GeoPoint ptCenter;
	private double[] mLocations;

	LocationGetter lg;
	protected LocationService localService;

	protected CourierInfo curCourier;

	private int courierId;
	protected boolean CHASING_COURIER = true;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				if (null != localService) {
					mLocations = localService.getLoc();
					updatePtCenterWithLocations();
				}
				break;
			case 1:
				if (null != curCourier) {
					mLocations[0] = curCourier.getLatitude();
					mLocations[1] = curCourier.getLongitude();
					updatePtCenterWithLocations();
				}
				break;
			default:
				break;
			}
		};

	};
	private boolean inited;

	@Override
	public int getLayoutRid() {
		return R.layout.map_geocoder;
	}

	@Override
	public void initView() {
		getExtras();// 1213
		titleManager.setTitleName("�ͻ�׷��");
		titleManager.initTitleBack();
		// ��ͼ��ʼ��
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.getController().enableClick(true);
		mMapView.getController().setZoom(18);

		mLocations = new double[2];

		// lg = new LocationGetter(this,this);
		if (MyData.data().getMe().isCourier()) {

			whenIsCourier();
		} else {
			whenNotCourier();
		}

	}

	private void updateUIwhenNotCourier() {
		if (inited || null == curCourier)
			return;
		View view1, view2;
		view1 = this.findViewById(R.id.layout_map_pic);
		view2 = this.findViewById(R.id.layout_map_bar);
		// view1.setVisibility(View.VISIBLE);
		view2.setVisibility(View.VISIBLE);
		TextView tv1, tv2;
		tv1 = (TextView) this.findViewById(R.id.tv_map_namestr);
		tv2 = (TextView) this.findViewById(R.id.tv_map_phonestr);
		tv1.setText(String.format("���Ա%s�ܸ���Ϊ������", curCourier.getTruename()));
		tv2.setText(String.format("���Ա�绰��%s", curCourier.getMobile_phone()));
		tv2.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				goDial(curCourier.getMobile_phone());
			}
		});
		ImageView img1, img2, img0;
		img0 = (ImageView) this.findViewById(R.id.img_map_courier);
		img1 = (ImageView) this.findViewById(R.id.img_map_info);
		img2 = (ImageView) this.findViewById(R.id.img_map_call);
		JackImageLoader.justSetMeImage(curCourier.getLogo_small(), img0);
		img0.setLayoutParams(new LinearLayout.LayoutParams(JackUtils.dip2px(
				this, 220), JackUtils.dip2px(this, 240)));
		img1.setTag(view1);
		img1.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				View vv = (View) v.getTag();
				if (null != vv) {
					vv.setVisibility(vv.getVisibility() == View.VISIBLE ? View.GONE
							: View.VISIBLE);
				}
			}
		});
		img2.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				goDial(curCourier.getMobile_phone());
			}
		});
		inited = true;
	}

	private void whenNotCourier() {
		new Thread() {
			@Override
			public void run() {
				while (CHASING_COURIER) {
					ActionPhpRequestImpl actReq = new GetCourierReq(courierId);
					ActionPhpReceiverImpl actRcv = new ActionPhpReceiverImpl() {

						@Override
						public boolean response(String result)
								throws JSONException {
							JSONObject job = NetStrategies.getResultObj(result);
							if (null != job) {
								curCourier = new CourierInfo(job);
								mHandler.sendEmptyMessage(1);// not nece
								updateUIwhenNotCourier();
								return false;
							}
							return true;
						}

						@Override
						public Context getReceiverContext() {
							return null;
						}
					};
					ActionBuilder.getInstance().request(actReq, actRcv);

					SystemClock.sleep(1000 * 10);
				}
			};
		}.start();

		// updateUIwhenNotCourier();

	}

	protected void goDial(String phoneNumber) {
		Intent phoneIntent = new Intent("android.intent.action.CALL",
				Uri.parse("tel:" + phoneNumber));

		startActivity(phoneIntent);
	}

	private void whenIsCourier() {
		updatePtCenterWithLocations();
		bindService(new Intent(MyApplication.app(), LocationService.class),
				new ServiceConnection() {
					@Override
					public void onServiceConnected(ComponentName componentName,
							IBinder binder) {
						// ����bindService������������ʱ�����������Ҫ��activity������
						// ��ͨ��onBind��������IBinder�����ص�ǰ���ط���
						localService = ((LocationService.LocalBinder) binder)
								.getService();
						localService.setHandler(mHandler);
						// ���������ʾ�û�,���ߵ��÷����ĳЩ����
					}

					@Override
					public void onServiceDisconnected(
							ComponentName componentName) {
						localService = null;
						// ���������ʾ�û�
					}
				}, Context.BIND_AUTO_CREATE);
	}

	/**
	 * 
	 * @param res
	 */
	private void handleOverlay(GeoPoint geoPt) {
		// ����ItemizedOverlayͼ��������ע�����
		ItemizedOverlay<OverlayItem> itemOverlay = new ItemizedOverlay<OverlayItem>(
				null, mMapView);
		// ����Item
		OverlayItem item = new OverlayItem(geoPt, "", null);
		// �õ���Ҫ���ڵ�ͼ�ϵ���Դ
		Drawable marker = getResources().getDrawable(R.drawable.map_bluecar); //
		// Ϊmaker����λ�úͱ߽�
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		// ��item����marker
		item.setMarker(marker);
		// ��ͼ�������item
		itemOverlay.addItem(item);

		// �����ͼ����ͼ��
		mMapView.getOverlays().clear();
		// ���һ����עItemizedOverlayͼ��
		mMapView.getOverlays().add(itemOverlay);
		// ִ��ˢ��ʹ��Ч
		mMapView.refresh();
	}

	/**
	 * ��������
	 * 
	 * @param v
	 */
	/*
	 * void SearchButtonProcess(View v) { if (mBtnReverseGeoCode.equals(v)) {
	 * EditText lat = (EditText)findViewById(R.id.lat); EditText lon =
	 * (EditText)findViewById(R.id.lon);
	 * searchByLatlon(lat.getText().toString(), lon.getText().toString()); }
	 * else if (mBtnGeoCode.equals(v)) { EditText editCity =
	 * (EditText)findViewById(R.id.city); EditText editGeoCodeKey =
	 * (EditText)findViewById(R.id.geocodekey); //Geo����
	 * mSearch.geocode(editGeoCodeKey.getText().toString(),
	 * editCity.getText().toString()); } }
	 */

	/*
	 * private void searchByLatlon(String latStr, String lonStr) { if (null ==
	 * latStr) latStr = Const.LOC_DEFAULT_LAT; if (null == lonStr) lonStr =
	 * Const.LOC_DEFAULT_LON; GeoPoint ptCenter = new GeoPoint((int)
	 * (Float.valueOf(latStr) * 1e6), (int) (Float.valueOf(lonStr) * 1e6)); //
	 * ��Geo���� mSearch.reverseGeocode(ptCenter); }
	 */

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
		// if(null!=lg)lg.onPause();
		if (null != localService)
			localService.onActivityPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
		// if(null!=lg)lg.onResume();
		if (null != localService)
			localService.onActivityResume();
	}

	private void getExtras() {
		Intent intent = getIntent();
		courierId = intent.getIntExtra(NetConst.EXTRAS_COURIER_ID, 0);
	}

	private void updatePtCenterWithLocations() {
		// mLocations = JackUtils.getLocation();
		// if(null==lg) return;
		// mLocations = lg.getLocArr();//0512 ע���� ����д��activity����
		Log.i("GEOCODERACTIVITY", "updatePtCenterWithLocations");
		if (null == mLocations || mLocations.length != 2)
			return;
		ptCenter = new GeoPoint((int) (mLocations[0] * 1e6),
				(int) (mLocations[1] * 1e6));

		if (ptCenter != null) {
			mMapView.getController().setCenter(ptCenter);
			handleOverlay(ptCenter);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.destroy();
		CHASING_COURIER = false;
		// mSearch.destory();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onLocationChanged(Location location) {
		// ��ȡά����Ϣ
		double latitude = location.getLatitude();
		// ��ȡ������Ϣ
		double longitude = location.getLongitude();
		// textView.setText("��λ��ʽ�� "+providerName+"  ά�ȣ�"+latitude+"  ���ȣ�"+longitude);
		mLocations[0] = latitude;
		mLocations[1] = longitude;
		updatePtCenterWithLocations();
		Log.i("GEO", "onLocationChanged");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.i("GEO", "onStatusChanged");
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.i("GEO", "onProviderEnabled");

	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.i("GEO", "onProviderDisabled");

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			return MyData.data().getMe().isCourier();
		}

		return super.onKeyDown(keyCode, event);
	}

}

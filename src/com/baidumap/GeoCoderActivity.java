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
 * 此demo用来展示如何进行地理编码搜索（用地址检索坐标）、反地理编码搜索（用坐标检索地址）
 * 同时展示了如何使用ItemizedOverlay在地图上标注结果点 [network 30.279750,120.110628 acc=88
 * et=+4d11h39m19s805ms]
 */
public class GeoCoderActivity extends MyTitleActivity implements
		LocationListener {
	/*
	 * //UI相关 Button mBtnReverseGeoCode = null; // 将坐标反编码为地址 Button mBtnGeoCode
	 * = null; // 将地址编码为坐标
	 */
	TextView tv_title;
	// 地图相关
	MapView mMapView = null; // 地图View
	// 搜索相关
	// MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用

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
		titleManager.setTitleName("送货追踪");
		titleManager.initTitleBack();
		// 地图初始化
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
		tv1.setText(String.format("快递员%s很高兴为您服务", curCourier.getTruename()));
		tv2.setText(String.format("快递员电话：%s", curCourier.getMobile_phone()));
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
						// 调用bindService方法启动服务时候，如果服务需要与activity交互，
						// 则通过onBind方法返回IBinder并返回当前本地服务
						localService = ((LocationService.LocalBinder) binder)
								.getService();
						localService.setHandler(mHandler);
						// 这里可以提示用户,或者调用服务的某些方法
					}

					@Override
					public void onServiceDisconnected(
							ComponentName componentName) {
						localService = null;
						// 这里可以提示用户
					}
				}, Context.BIND_AUTO_CREATE);
	}

	/**
	 * 
	 * @param res
	 */
	private void handleOverlay(GeoPoint geoPt) {
		// 生成ItemizedOverlay图层用来标注结果点
		ItemizedOverlay<OverlayItem> itemOverlay = new ItemizedOverlay<OverlayItem>(
				null, mMapView);
		// 生成Item
		OverlayItem item = new OverlayItem(geoPt, "", null);
		// 得到需要标在地图上的资源
		Drawable marker = getResources().getDrawable(R.drawable.map_bluecar); //
		// 为maker定义位置和边界
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		// 给item设置marker
		item.setMarker(marker);
		// 在图层上添加item
		itemOverlay.addItem(item);

		// 清除地图其他图层
		mMapView.getOverlays().clear();
		// 添加一个标注ItemizedOverlay图层
		mMapView.getOverlays().add(itemOverlay);
		// 执行刷新使生效
		mMapView.refresh();
	}

	/**
	 * 发起搜索
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
	 * (EditText)findViewById(R.id.geocodekey); //Geo搜索
	 * mSearch.geocode(editGeoCodeKey.getText().toString(),
	 * editCity.getText().toString()); } }
	 */

	/*
	 * private void searchByLatlon(String latStr, String lonStr) { if (null ==
	 * latStr) latStr = Const.LOC_DEFAULT_LAT; if (null == lonStr) lonStr =
	 * Const.LOC_DEFAULT_LON; GeoPoint ptCenter = new GeoPoint((int)
	 * (Float.valueOf(latStr) * 1e6), (int) (Float.valueOf(lonStr) * 1e6)); //
	 * 反Geo搜索 mSearch.reverseGeocode(ptCenter); }
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
		// mLocations = lg.getLocArr();//0512 注释了 监听写在activity里了
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
		// 获取维度信息
		double latitude = location.getLatitude();
		// 获取经度信息
		double longitude = location.getLongitude();
		// textView.setText("定位方式： "+providerName+"  维度："+latitude+"  经度："+longitude);
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

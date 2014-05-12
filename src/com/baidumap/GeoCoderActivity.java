package com.baidumap;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.lehuo.MyApplication;
import com.lehuo.R;
import com.lehuo.data.Const;
import com.lehuo.net.service.LocationService;
import com.lehuo.ui.tab.HubActivity;
import com.lehuo.util.JackUtils;
import com.lehuo.util.LocationGetter;

/**
 * 此demo用来展示如何进行地理编码搜索（用地址检索坐标）、反地理编码搜索（用坐标检索地址）
 * 同时展示了如何使用ItemizedOverlay在地图上标注结果点
 * [network 30.279750,120.110628 acc=88 et=+4d11h39m19s805ms]

 */
public class GeoCoderActivity extends Activity implements LocationListener{
	/*
	 * //UI相关 Button mBtnReverseGeoCode = null; // 将坐标反编码为地址 Button mBtnGeoCode
	 * = null; // 将地址编码为坐标
	 */
	TextView tv_title;
	// 地图相关
	MapView mMapView = null; // 地图View
	// 搜索相关
	MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用

	GeoPoint ptCenter;
	private double[] mLocations;
	
	LocationGetter lg;
	protected LocationService localService;

	
	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				if(null!=localService){
					mLocations = localService.getLoc();
					updatePtCenterWithLocations();
				}
				break;

			default:
				break;
			}
		};
		
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication app = (MyApplication) this.getApplication();
		setContentView(R.layout.map_geocoder);
		CharSequence titleLable = "地理编码功能";
		setTitle(titleLable);
		// tv_title = (TextView)findViewById(R.id.tv_maptitle);//TODO title
		getExtras();// 1213
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.getController().enableClick(true);
		mMapView.getController().setZoom(18);
		
		mLocations = new double[2];
		
//		lg = new LocationGetter(this,this);
		
		updatePtCenterWithLocations();
		bindService(new Intent(this,LocationService.class), new ServiceConnection(){
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder) {
                //调用bindService方法启动服务时候，如果服务需要与activity交互，
                //则通过onBind方法返回IBinder并返回当前本地服务
                localService=((LocationService.LocalBinder)binder).getService();
                localService.setHandler(mHandler);
                //这里可以提示用户,或者调用服务的某些方法
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                localService=null;
                //这里可以提示用户
            }     
		},  Context.BIND_AUTO_CREATE);
		
		
		
		/*// 初始化搜索模块，注册事件监听
		mSearch = new MKSearch();
		mSearch.init(app.mBMapManager, new MKSearchListener() {
			@Override
			public void onGetPoiDetailSearchResult(int type, int error) {
			}

			public void onGetAddrResult(MKAddrInfo res, int error) {
				if (error != 0) {
					String str = String.format("错误号：%d", error);
					Toast.makeText(GeoCoderActivity.this, str,
							Toast.LENGTH_LONG).show();
					return;
				}
				// 地图移动到该点
				mMapView.getController().animateTo(res.geoPt);
				if (res.type == MKAddrInfo.MK_GEOCODE) {
					// 地理编码：通过地址检索坐标点
					String strInfo = String.format("纬度：%f 经度：%f",
							res.geoPt.getLatitudeE6() / 1e6,
							res.geoPt.getLongitudeE6() / 1e6);
					Toast.makeText(GeoCoderActivity.this, strInfo,
							Toast.LENGTH_LONG).show();
				}
				if (res.type == MKAddrInfo.MK_REVERSEGEOCODE) {
					// 反地理编码：通过坐标点检索详细地址及周边poi
					String strInfo = res.strAddr;
					Toast.makeText(GeoCoderActivity.this, strInfo,
							Toast.LENGTH_LONG).show();

				}
				handleOverlay(res.geoPt);
			}

			public void onGetPoiResult(MKPoiResult res, int type, int error) {

			}

			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
			}

			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult result, int type,
					int error) {

			}

		});*/

		// mSearch.reverseGeocode(ptCenter);

		/*
		 * // 设定地理编码及反地理编码按钮的响应 mBtnReverseGeoCode =
		 * (Button)findViewById(R.id.reversegeocode); mBtnGeoCode =
		 * (Button)findViewById(R.id.geocode);
		 */

		/*
		 * OnClickListener clickListener = new OnClickListener(){ public void
		 * onClick(View v) { SearchButtonProcess(v); } };
		 */

		/*
		 * mBtnReverseGeoCode.setOnClickListener(clickListener);
		 * mBtnGeoCode.setOnClickListener(clickListener);
		 */
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
		Drawable marker = getResources().getDrawable(R.drawable.map_bluecar); // TODO
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

	private void searchByLatlon(String latStr, String lonStr) {
		if (null == latStr)
			latStr = Const.LOC_DEFAULT_LAT;
		if (null == lonStr)
			lonStr = Const.LOC_DEFAULT_LON;
		GeoPoint ptCenter = new GeoPoint((int) (Float.valueOf(latStr) * 1e6),
				(int) (Float.valueOf(lonStr) * 1e6));
		// 反Geo搜索
		mSearch.reverseGeocode(ptCenter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
//		if(null!=lg)lg.onPause();
		if(null!=localService) localService.onActivityPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
//		if(null!=lg)lg.onResume();
		if(null!=localService) localService.onActivityResume();
	}
	

	private void getExtras() {
//		Intent intent = getIntent();
//		mLocations = intent.getDoubleArrayExtra(NetConst.EXTRAS_LOCATION);
	}

	private void updatePtCenterWithLocations() {
//		mLocations = JackUtils.getLocation();
//		if(null==lg) return;
//		mLocations = lg.getLocArr();//0512 注释了 监听写在activity里了
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
		mMapView.destroy();
		mSearch.destory();
		super.onDestroy();
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
		if(keyCode==KeyEvent.KEYCODE_BACK){
			
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
}

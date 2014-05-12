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
 * ��demo����չʾ��ν��е�������������õ�ַ�������꣩����������������������������ַ��
 * ͬʱչʾ�����ʹ��ItemizedOverlay�ڵ�ͼ�ϱ�ע�����
 * [network 30.279750,120.110628 acc=88 et=+4d11h39m19s805ms]

 */
public class GeoCoderActivity extends Activity implements LocationListener{
	/*
	 * //UI��� Button mBtnReverseGeoCode = null; // �����귴����Ϊ��ַ Button mBtnGeoCode
	 * = null; // ����ַ����Ϊ����
	 */
	TextView tv_title;
	// ��ͼ���
	MapView mMapView = null; // ��ͼView
	// �������
	MKSearch mSearch = null; // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��

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
		CharSequence titleLable = "������빦��";
		setTitle(titleLable);
		// tv_title = (TextView)findViewById(R.id.tv_maptitle);//TODO title
		getExtras();// 1213
		// ��ͼ��ʼ��
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.getController().enableClick(true);
		mMapView.getController().setZoom(18);
		
		mLocations = new double[2];
		
//		lg = new LocationGetter(this,this);
		
		updatePtCenterWithLocations();
		bindService(new Intent(this,LocationService.class), new ServiceConnection(){
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder) {
                //����bindService������������ʱ�����������Ҫ��activity������
                //��ͨ��onBind��������IBinder�����ص�ǰ���ط���
                localService=((LocationService.LocalBinder)binder).getService();
                localService.setHandler(mHandler);
                //���������ʾ�û�,���ߵ��÷����ĳЩ����
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                localService=null;
                //���������ʾ�û�
            }     
		},  Context.BIND_AUTO_CREATE);
		
		
		
		/*// ��ʼ������ģ�飬ע���¼�����
		mSearch = new MKSearch();
		mSearch.init(app.mBMapManager, new MKSearchListener() {
			@Override
			public void onGetPoiDetailSearchResult(int type, int error) {
			}

			public void onGetAddrResult(MKAddrInfo res, int error) {
				if (error != 0) {
					String str = String.format("����ţ�%d", error);
					Toast.makeText(GeoCoderActivity.this, str,
							Toast.LENGTH_LONG).show();
					return;
				}
				// ��ͼ�ƶ����õ�
				mMapView.getController().animateTo(res.geoPt);
				if (res.type == MKAddrInfo.MK_GEOCODE) {
					// ������룺ͨ����ַ���������
					String strInfo = String.format("γ�ȣ�%f ���ȣ�%f",
							res.geoPt.getLatitudeE6() / 1e6,
							res.geoPt.getLongitudeE6() / 1e6);
					Toast.makeText(GeoCoderActivity.this, strInfo,
							Toast.LENGTH_LONG).show();
				}
				if (res.type == MKAddrInfo.MK_REVERSEGEOCODE) {
					// ��������룺ͨ������������ϸ��ַ���ܱ�poi
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
		 * // �趨������뼰��������밴ť����Ӧ mBtnReverseGeoCode =
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
		// ����ItemizedOverlayͼ��������ע�����
		ItemizedOverlay<OverlayItem> itemOverlay = new ItemizedOverlay<OverlayItem>(
				null, mMapView);
		// ����Item
		OverlayItem item = new OverlayItem(geoPt, "", null);
		// �õ���Ҫ���ڵ�ͼ�ϵ���Դ
		Drawable marker = getResources().getDrawable(R.drawable.map_bluecar); // TODO
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

	private void searchByLatlon(String latStr, String lonStr) {
		if (null == latStr)
			latStr = Const.LOC_DEFAULT_LAT;
		if (null == lonStr)
			lonStr = Const.LOC_DEFAULT_LON;
		GeoPoint ptCenter = new GeoPoint((int) (Float.valueOf(latStr) * 1e6),
				(int) (Float.valueOf(lonStr) * 1e6));
		// ��Geo����
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
//		mLocations = lg.getLocArr();//0512 ע���� ����д��activity����
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
		if(keyCode==KeyEvent.KEYCODE_BACK){
			
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
}

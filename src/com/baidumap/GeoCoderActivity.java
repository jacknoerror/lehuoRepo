package com.baidumap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
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
import com.lehuo.MyApplication;
import com.lehuo.R;
import com.lehuo.data.Const;
import com.lehuo.data.NetConst;
import com.lehuo.util.JackUtils;

/**
 * ��demo����չʾ��ν��е�������������õ�ַ�������꣩����������������������������ַ��
 * ͬʱչʾ�����ʹ��ItemizedOverlay�ڵ�ͼ�ϱ�ע�����
 * 
 */
public class GeoCoderActivity extends Activity {
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
		updatePtCenterWithLocations();
		

		// ��ʼ������ģ�飬ע���¼�����
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

		});

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
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {

		mMapView.onResume();
		super.onResume();
	}

	private void getExtras() {
//		Intent intent = getIntent();
//		mLocations = intent.getDoubleArrayExtra(NetConst.EXTRAS_LOCATION);
	}

	private void updatePtCenterWithLocations() {
		mLocations = JackUtils.getLocation(this);
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
}

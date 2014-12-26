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
		getLoc();
	}



	/**
	 * 
	 */
	public double[] getLoc() {
		Log.i("8023", "------λ�÷���" + providerName);
		if (providerName != null) {
			Location location = lm.getLastKnownLocation(providerName);
			// ��Ϊ���ص����ϴζ�λ��Ϣ ���Ժ��п��ܷ���Ϊnull
			if (location == null) {
				lm.requestLocationUpdates(providerName, 0,0, listener);
				return null;
			}
			Log.i("8023", "-------" + location);

			// ��ȡά����Ϣ
			double latitude = location.getLatitude();
			// ��ȡ������Ϣ
			double longitude = location.getLongitude();
			// textView.setText("��λ��ʽ�� "+providerName+"  ά�ȣ�"+latitude+"  ���ȣ�"+longitude);
			locationArr[0] = latitude;
			locationArr[1] = longitude;
			return locationArr;
		} else {
			Toast.makeText(context2, "1.������������ \n2.����ҵ�λ��", Toast.LENGTH_SHORT)
					.show();
			return null;
		}
	}



	/**
	 * �ǵõ���
	 */
	public void onResume() {
		lm.requestLocationUpdates(providerName, 0,0, listener);
	}

	/**
	 * �ǵõ���
	 */
	public void onPause() {
		lm.removeUpdates(listener);
	}

}

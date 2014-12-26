/**
 * 
 */
package com.lehuozu.net.location;

import android.os.Handler;

/**
 * @author tao
 *
 */
public class LocHandlerHelper {
	public interface GeoHolder{
		public void updatePtCenterWithLocations(double lati,double longi);
	}
	
	public interface GeoGetter{
		public double[] getLoc();
	}
	
	private static LocHandlerHelper helper;
	private LocHandlerHelper(){}
	public static LocHandlerHelper getInstance(){
		if(null==helper) helper = new LocHandlerHelper();
		return helper;
	}
	
	
	GeoHolder gHolder;
	GeoGetter gGetter;

	protected double[] mLocations;
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:case 1:
				if (null != gGetter) {
					mLocations = gGetter.getLoc();
					if(null!=gHolder&&null!=mLocations&&mLocations.length==2)gHolder.updatePtCenterWithLocations(mLocations[0],mLocations[1]);
				}
				break;
			
			default:
				break;
			}
		};

	};

	public Handler getHandler() {
		return mHandler;
	}

	public void setHolder(GeoHolder gHolder) {
		this.gHolder = gHolder;
	}

	public void setGetter(GeoGetter gGetter) {
		this.gGetter = gGetter;
	}
	
	
}

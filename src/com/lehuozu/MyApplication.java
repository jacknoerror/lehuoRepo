package com.lehuozu;


import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.lehuozu.data.Const;

/**
 * @author tao
 *MD5 : 4D:16:67:E9:30:5C:A9:18:35:E7:4B:C4:EB:E9:BE:7C
 *SHA1: BD:16:B6:07:43:A1:FA:C0:5F:2F:9C:9C:52:E1:90:E5:DA:7C:79:DB
 */
public class MyApplication extends Application {
	static MyApplication app;
	public static MyApplication app()	{
		return app;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		
		initScreenData();
		initEngineManager(this);
		
	}
	
	private void initScreenData(){
		DisplayMetrics dm = getResources().getDisplayMetrics();
		Const.SCREEN_WIDTH = dm.widthPixels;
		Const.SCREEN_HEIGHT= dm.heightPixels;
		Log.i("yft_app", dm.densityDpi+":dpi+=+desi:"+dm.density);
	}
	
	/** baiduMap */
	public boolean m_bKeyRight = true;
    public BMapManager mBMapManager = null;
    
    public static final String strKey = "9GprKFWrXYKCpQo71pLHUKal";//正式
//    public static final String strKey = "lspWjcpYnCBGmGTDUnhNfBRT";//pc
//    public static final String strKey = "9GprKFWrXYKCpQo71pLHUKal";//mac
    
    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(MyApplication.app(), 
                    "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
        }
	}
 // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(MyApplication.app(), "您的网络出错啦！",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(MyApplication.app(), "输入正确的检索条件！",
                        Toast.LENGTH_LONG).show();
            }
            // ...
        }

        @Override
        public void onGetPermissionState(int iError) {
                //授权Key错误：
        	if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
                Toast.makeText(MyApplication.app(), 
                        "百度地图授权Key有错误！", Toast.LENGTH_LONG).show();
                MyApplication.app().m_bKeyRight = false;
            }
        }
    }
}

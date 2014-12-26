package com.lehuozu.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.lehuozu.entity.MyEvent;
import com.lehuozu.util.JackUtils;

import de.greenrobot.event.EventBus;

public class StartActivity extends Activity {
	private Bitmap bm;
	private EventBus default1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		default1 = EventBus.getDefault();
		default1.register(this);
		
		default1.post(new MyEvent("getbm"));
		
	}
	
	public void onEventBackgroundThread(MyEvent event) {
		if(event.equalsMsg("dosth")){
			long starttime = System.currentTimeMillis();
			//do sth
//			ImageLoaderHelper.initImageLoader(this);
			
			while(System.currentTimeMillis()-starttime<2000){
			}
			
			default1.post(new MyEvent("end"));
		}else if(event.equalsMsg("getbm")){
			bm = JackUtils.getbmFromAssetsFile(getResources(), "start.jpg");
			default1.post(new MyEvent("setbm"));
		}
	}
	public void onEventMainThread(MyEvent event) {
		if(event.equalsMsg("end")){
			SharedPreferences pref = getSharedPreferences("start", Context.MODE_PRIVATE);
			if(!pref.contains("first")){
//				MyPortal.justGo(this, StartPagerActivity.class);
			}
			finish();
		}else if(event.equalsMsg("end")||event.equalsMsg("setbm")){
			ImageView startImg = new ImageView(this);
			startImg.setScaleType(ScaleType.CENTER_CROP);
			startImg.setImageBitmap(bm);
			setContentView(startImg);
			
			default1.post(new MyEvent("dosth"));
		}
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(null!=bm&&!bm.isRecycled()) bm.recycle();
		default1.unregister(this);
	}
}

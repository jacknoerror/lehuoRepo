package com.lehuo.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.util.Log;
import android.widget.ImageView;

import com.lehuo.MyApplication;
import com.lehuo.data.MyData;
import com.lehuo.net.ImageDownloaderTask;



public class JackImageLoader {
	private static final String TAG = JackImageLoader.class.getSimpleName();

	static Map<String, SoftReference<Bitmap>> bmCache = new HashMap<String, SoftReference<Bitmap>>();
	static WeakHashMap<String, ImageDownloaderTask> iDownMap = new WeakHashMap<String, ImageDownloaderTask>();
	
	public static Bitmap findBitmap(String url){
		MyData infoHouse = MyData.data();
		SoftReference<Bitmap> jwr = bmCache.get(url);
		if(jwr!=null&&jwr.get()!=null){
//			image.setImageBitmap(bm);
			Log.i(TAG, "from weakMap bitmap");
			return jwr.get();
		}else if(infoHouse.getMe()!=null) {
			/*Log.i(TAG, "try from sdcard");
			Bitmap bm = JackUtils.getBitmapFromSDCard(MyApplication.app().getPathUponUrl(infoHouse.getMe().getMemberId(), url));
			return bm;*/
			//TODO
		}
		
		return null;
	}
	
	@SuppressLint("NewApi")
	public static void sendRequest(String url,ImageView... imgs){
		ImageDownloaderTask task=iDownMap.get(url);
		if(task!=null){
			//already exists
			if(task.getStatus()!=Status.FINISHED){
				//still running
				task.addImage(imgs);
				return;//退出！
			}else{
				//结束了 应该不会进来
				Log.i(TAG, "should not be here 'cause bitmap should be prepared");
			}
		}
		Log.i(TAG, "start a task!");
		task = new ImageDownloaderTask(  imgs);
//		task.execute(url); 
		try{
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);//1118
		}catch(NoSuchFieldError e){
			task.execute(url);
		}
		iDownMap.put(url, task);
	}
	
	public static void justSetMeImage(String url, ImageView img){
		if(null==url||url.equals("")) {Log.i(TAG, "url nil");return;}//1209}
		Bitmap bm = JackImageLoader.findBitmap(url);
		if(bm==null){
			JackImageLoader.sendRequest(url, img);
		}else{
			img.setImageBitmap(bm);
		}
	}
	
	public static void addBitmap(String url,SoftReference<Bitmap> softBM){
		if(softBM!=null&&softBM.get()!=null){
			bmCache.put(url, softBM);
		}
	}
	public static void dispose(){
		Iterator<SoftReference<Bitmap>> it = bmCache.values().iterator();
		while(it.hasNext()){
			it.next().get().recycle();
		}
		bmCache.clear();
	}
}

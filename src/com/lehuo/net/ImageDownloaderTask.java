package com.lehuo.net;

import java.util.Stack;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.lehuo.util.JackImageUtils;


/** 
 * “Ï≤Ωœ¬‘ÿÕº∆¨ 
 */ 
public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> { 
    private final String TAG = ImageDownloaderTask.class.getSimpleName();
	
    private String url; 
    Stack<ImageView> imgStack;
    public ImageDownloaderTask(ImageView... imgs) { 
    	imgStack = new Stack<ImageView>();
    	for(ImageView img:imgs){
    		if(!imgStack.contains(img)){
    			imgStack.push(img);
    		}
    	}
    } 
    
   @Override 
    protected Bitmap doInBackground(String... params) {
	   
	   return JackImageUtils.getBitmapFromUrl(params[0]);
   } 
  

   @Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		Message msg = new Message();
		msg.obj = result;
		if(result==null){
			Log.e(TAG, "result bitmap nil");
			return;
		}
		while(imgStack.size()>0){
			ImageView img= imgStack.pop();
			if(img!=null) img.setImageBitmap(result);
			img=null;
		}
		imgStack=null;
		
	}
	public void addImage(ImageView... imgs){
		if(imgs==null) return;
		Log.i(TAG, "addimage 1!");
		for(ImageView img:imgs){
			imgStack.push(img );
		}
	}
   
   public interface IimgCallBack{
	   public void bind( Bitmap bm);
   }
}

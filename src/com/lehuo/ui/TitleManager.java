package com.lehuo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.ui.product.MyCartActivity;

/**
 * @author tao
 *
 */
public class TitleManager {
	JackTitle jackTitle;
	TextView tv_titlename,tv_right;
	ImageView btn_titleback;
	
	View cartView;
	TextView cartTv;
	
	Activity activity;
	View mView;
	
	public TitleManager(Activity activity) {
		super();
		this.activity = activity;
	}
	public TitleManager(View view){
		this.mView = view;
	}
	
	public void setTitleName(String name){
		if(null==tv_titlename)tv_titlename = (TextView) titleView().findViewById(R.id.tv_title);
		tv_titlename.setText(name);
	}
	public void setRightText(String text, View.OnClickListener listener){
		if(null==tv_right)tv_right =   (TextView) titleView().findViewById(R.id.tv_title_right);
		tv_right.setText(text);
		tv_right.setOnClickListener(listener);
		tv_right.setVisibility(View.VISIBLE);
	}
	public void initTitleBack(){
		btn_titleback = (ImageView) titleView().findViewById(R.id.btn_title_back);
		btn_titleback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(null!=activity)activity.finish();
			}
		});
		
	}
	public JackTitle titleView(){
		if(null==jackTitle) jackTitle = (JackTitle)findView(R.id.jacktitle);
		return jackTitle;
	}
	private View findView(int id){
		if(null!=activity) return activity.findViewById(id);
		if(null!=mView) return mView.findViewById(id);
		return null;
	}
	
	
	public void updateCart(){//0503
		View ttlv = titleView();
		if(null==cartView&&null!=ttlv){
			cartView = ttlv.findViewById(R.id.titleview_cart);
			cartTv = (TextView)cartView.findViewById(R.id.tv_titlecartcount);
			cartView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Context c = v.getContext();
					Intent i = new Intent();
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.setClass(c, MyCartActivity.class);
					c.startActivity(i);
				}
			});
		}
		cartView.setVisibility(View.VISIBLE);
		int count = MyData.data().getCartCount();
		cartTv.setVisibility(count==0?View.INVISIBLE:View.VISIBLE);
		cartTv.setText(""+count);
			
	}
}

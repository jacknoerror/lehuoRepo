package com.lehuozu.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lehuozu.R;
import com.lehuozu.data.MyData;
import com.lehuozu.ui.custom.JackTitle;
import com.lehuozu.ui.product.MyCartActivity;

/**
 * @author tao
 *should be no specific
 */
public class TitleManager implements View.OnClickListener{
	JackTitle jackTitle;
	TextView tv_titlename,tv_right;
	ImageView btn_titleback;
	
	View cartView;
	TextView cartTv;
	
	Activity activity;
	View mView;
	private ImageView btn_titlemenu;
	private ImageView img_logo;
	
	public TitleManager(Activity activity) {
		super();
		this.activity = activity;
	}
	public TitleManager(View view){
		this.mView = view;
	}
	
	public void setTitleName(String name){
		if(null==tv_titlename)tv_titlename = (TextView) titleView().findViewById(R.id.tv_title);
		if(null!=tv_titlename)tv_titlename.setText(name);
	}
	public void setRightText(String text, View.OnClickListener listener){
		if(null==tv_right)tv_right =   (TextView) titleView().findViewById(R.id.tv_title_right);
		tv_right.setText(text);
		tv_right.setOnClickListener(listener);
		tv_right.setVisibility(View.VISIBLE);
	}
	public void initTitleLogo(){
		img_logo = (ImageView) titleView().findViewById(R.id.img_title_logo);
		img_logo.setVisibility(View.VISIBLE);
	}
	public void initTitleBack(){
		btn_titleback = (ImageView) titleView().findViewById(R.id.btn_title_back);
		btn_titleback.setOnClickListener(this);
		btn_titleback.setVisibility(View.VISIBLE);
	}
	public void initTitleMenu(){
		btn_titlemenu = (ImageView) titleView().findViewById(R.id.btn_title_menu);
		btn_titlemenu.setOnClickListener(this);
		btn_titlemenu.setVisibility(View.VISIBLE);
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
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_menu:
			DrawerLayout drawer = MyData.data().getDrawer();
			if(null!=drawer){
				drawer.openDrawer(Gravity.LEFT);
			}
			break;
		case R.id.btn_title_back:
			if(null!=activity)activity.finish();
			break;
		default:
			break;
		}
		
	}
}

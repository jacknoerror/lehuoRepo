package com.lehuozu.ui.adapter;

import java.util.List;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class MyPagerAdapater extends PagerAdapter{
	List<View> viewList;
	
	public MyPagerAdapater(List<View> viewList){
		this.viewList = viewList;
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public int getCount() {
		return this.viewList.size();
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager)container).removeView(this.viewList.get(position));
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return position+"";
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager)container).addView(this.viewList.get(position));
		return this.viewList.get(position);
	}
}
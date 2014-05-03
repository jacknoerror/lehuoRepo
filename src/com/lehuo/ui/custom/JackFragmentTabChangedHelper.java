package com.lehuo.ui.custom;

import java.util.ArrayList;
import java.util.List;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TabHost.OnTabChangeListener;

/**
 * put onTabChanged method into listener
 * @author taotao
 *
 */
public class JackFragmentTabChangedHelper {
	final String TAG = JackFragmentTabChangedHelper.class.getSimpleName();
	
	/**
	 * 
	 * @author taotao
	 *
	 */
	public static class TabPack{
		public int icon;
		public String title;
		public Class  clazz;
		public TabPack(int icon, String title, Class  clazz) {
			super();
			this.icon = icon;
			this.title = title;
			this.clazz = clazz;
		}
	}
	
	FragmentActivity fa;
	Fragment currentFragment;
	int realtabcontentid;
	
	List<TabPack> tPackList;
	
	/**
	 * 
	 * @param fa
	 * @param realid realTabContentId
	 */
	public JackFragmentTabChangedHelper(FragmentActivity fa,int realid){
		this.fa = fa;
		this.realtabcontentid=realid;
		
		tPackList= new ArrayList<JackFragmentTabChangedHelper.TabPack>();
	}
	
	public Fragment getCurrent(){
		return currentFragment;
	}
	
	public void addTabPack(TabPack pack){
		if(null!=pack){
			tPackList.add(pack);
		}
	}
	
	public void onTabChanged(String tabId) {
		android.support.v4.app.FragmentManager fm =   fa.getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
		attachFragment(tabId, fm, ft);
		
	}

	private void attachFragment(String tabId,android.support.v4.app.FragmentManager fm,
    		android.support.v4.app.FragmentTransaction ft){
    	Fragment fra = fm.findFragmentByTag(tabId);
    	if(null!=currentFragment) ft.hide(currentFragment);//ft.detach(currentFragment);
    	if(null!=fra) {
//    		ft.attach(fra);
    		ft.show(fra);
    	}else{
    		fra = getNewFragment(tabId);
    		ft.add(realtabcontentid, fra, tabId);
    	}
    	currentFragment = fra;
    	ft.commit();
    	
    }
	
	private Fragment getNewFragment(String tabId) {
		int size =tPackList.size(); 
		if(size == 0) Log.e(TAG, "did you forget to add TabPack?");
		for(int i=0;i<size;i++){
			if(tabId.equalsIgnoreCase(tPackList.get(i).title)){
				try {
					return (Fragment)tPackList.get(i).clazz.newInstance();
				} catch (Exception e) {
					Log.e(TAG, "this TabPack contains an error: "+tPackList.get(i).clazz);
				}
			}
		}
		return null;
	}
	
	
}

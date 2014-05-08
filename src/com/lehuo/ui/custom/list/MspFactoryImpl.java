package com.lehuo.ui.custom.list;

import org.json.JSONObject;



public interface MspFactoryImpl {
	public MspAdapter getNewAdapter();
	public MspPage getMspPage(JSONObject job);
	public  MspJsonItem getMjiInstance();
	public MyScrollPageListView.OnGetPageListener getDefaultOnPageChangeListener();
	public String getPageName();
	
	/*public  int getLayoutId();
	public  void init();
	public  void setup(int position);*/
}

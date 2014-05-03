package com.lehuo.ui.custom.list;

import com.lehuo.entity.json.JsonImport;
import com.lehuo.ui.custom.list.MspAdapter.ViewHolderImpl;

public interface MspFactoryImpl {
	public MspAdapter getNewAdapter();
	public MspFactoryImpl getMspPage();
	public ViewHolderImpl getViewHolderInstance();
	public  MspJsonItem getMjiInstance();
	
	public  ViewHolderImpl getHolderInstance();
	
	public  int getLayoutId();
	public  void init();
	public  void setup(int position);
}

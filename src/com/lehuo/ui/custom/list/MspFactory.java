package com.lehuo.ui.custom.list;

import com.lehuo.entity.json.JsonImport;
import com.lehuo.ui.custom.list.MspAdapter.ViewHolderImpl;

public class MspFactory implements MspFactoryImpl {
	
	ListItemImpl.Type type;

	public MspFactory(ListItemImpl.Type type){
		this.type = type;
	}
	
	@Override
	public MspAdapter getNewAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MspFactoryImpl getMspPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewHolderImpl getViewHolderInstance() {
		ViewHolderImpl vh = null;
		switch (type) {
		case GOODS:
			
			break;

		default:
			break;
		}
		return null;
	}

	@Override
	public MspJsonItem getMjiInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewHolderImpl getHolderInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setup(int position) {
		// TODO Auto-generated method stub
		
	}

}

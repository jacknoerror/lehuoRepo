package com.lehuo.ui.login;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lehuo.vo.Place;

public class MySpinnerAdapter extends BaseAdapter{
	//list?
	
	private List<Place> jiList;

	public MySpinnerAdapter( List<Place> jiList) {
		super();
		this.jiList = jiList;
		Place place  = new Place();
		place.setRegion_id(0);//添加第一个选项为空
		place.setRegion_name("---");
		jiList.add(0, place);
	}
	
	
	@Override
	public int getCount() {
		return jiList.size();
	}


	@Override
	public Place getItem(int arg0) {
		return (Place)jiList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return getItem(arg0).getRegion_id();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
			arg1 = LayoutInflater.from(arg2.getContext()).inflate(android.R.layout.simple_spinner_item, null);
			((TextView)arg1).setText(getItem(arg0).getRegion_name());
		return arg1;
	}
	
}
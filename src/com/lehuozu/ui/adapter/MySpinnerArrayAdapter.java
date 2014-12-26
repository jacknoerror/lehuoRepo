package com.lehuozu.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lehuozu.vo.Place;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * @author taotao
 *
 */
public class MySpinnerArrayAdapter extends ArrayAdapter<String> {
//	private List<String> jiList;
	List<Place> placeList;
	public MySpinnerArrayAdapter(Context context
			, List<Place> list) {
		super(context, android.R.layout.simple_spinner_item, new ArrayList<String>());
		setDropDownViewResource(android.R.layout.select_dialog_item);
		this.placeList = list;
		if (list.size() > 0 && list.get(0).getRegion_id() != -1) {
			
			Place place = new Place();
			place.setRegion_id(-1);// 添加第一个选项为空
			place.setRegion_name("请选择");
			list.add(0, place);
		}
		for(Place p:list){
			add(p.getRegion_name());
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return placeList.size();
	}


	@Override
	public long getItemId(int arg0) {
		return placeList.get(arg0).getRegion_id();
	}
}

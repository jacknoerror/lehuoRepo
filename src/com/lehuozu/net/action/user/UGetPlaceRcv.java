package com.lehuozu.net.action.user;

import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.lehuozu.entity.json.JsonImport;
import com.lehuozu.net.action.JackDefDefJiArrRcv;
import com.lehuozu.vo.Place;

/**
 * @author taotao
 *
 */
@Deprecated
public class UGetPlaceRcv extends JackDefDefJiArrRcv {

	Spinner spinner;
	

	public UGetPlaceRcv(Context context, Spinner spinner) {
		super(context);
		this.spinner = spinner;
	}

	@Override
	public JsonImport initJi(JSONObject jb) {
		return new Place(jb);
	}

	@Override
	public boolean doSthMore() {
		if(null!=spinner){
			spinner.setAdapter(new MySpinnerAdapter());
			return false;
		}
		return true;
	}
	
	class MySpinnerAdapter extends BaseAdapter{
		//list?
		
		public MySpinnerAdapter() {
			super();
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
	
}

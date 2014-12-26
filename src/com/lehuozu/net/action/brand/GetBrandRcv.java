package com.lehuozu.net.action.brand;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.lehuozu.R;
import com.lehuozu.data.Const;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.ui.MyGate;
import com.lehuozu.ui.adapter.MyGridViewAdapter;
import com.lehuozu.util.JackUtils;
import com.lehuozu.vo.Brand;
import com.lehuozu.vo.LehuoPic;

public class GetBrandRcv implements ActionPhpReceiverImpl {

	GridView gv;
	Context context;
	private String[] pathStrings;
	

	public GetBrandRcv(Context context, GridView gv) {
		this.context = context;
		this.gv = gv;
	}




	@Override
	public boolean response(String result) throws JSONException {
		JSONObject job = NetStrategies.getResultObj(result);
		if(null!=job){
			Brand b = new Brand(job);
			if(null!=gv) {
				List<LehuoPic> certList = b.getCertList();
				if(null==certList) return true;
				gv.setAdapter(new MyGridViewAdapter(context, certList));
				gv.setNumColumns(2);
				gv.setSelector(R.drawable.selector_common_grey);//no yellow any more but nothing appears
				//make it ok in scroll
				gv.setOnTouchListener(new View.OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						return MotionEvent.ACTION_MOVE == event.getAction();
					}
				});
				int size = certList.size();
				gv.setLayoutParams(new FrameLayout.LayoutParams(
						(int) Const.SCREEN_WIDTH-JackUtils.dip2px(context, 12),
						JackUtils.dip2px(context, 160) * (size/2)));
				pathStrings = new String[size];
				for(int i=0;i<size;i++){
					pathStrings[i] = certList.get(i).getPicUrl();
				}
				gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if(null==pathStrings||position>=pathStrings.length);
						MyGate.goViewPager(context,pathStrings,position)	;
					}
				});
				return false;
			}
		}
		return true;
	}


	@Override
	public Context getReceiverContext() {
		return context;
	}



}

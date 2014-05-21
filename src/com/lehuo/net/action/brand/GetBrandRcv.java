package com.lehuo.net.action.brand;

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

import com.lehuo.R;
import com.lehuo.data.Const;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.ui.MyGate;
import com.lehuo.ui.adapter.MyGridViewAdapter;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.Brand;
import com.lehuo.vo.LehuoPic;

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
						(int) Const.SCREEN_WIDTH,
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

package com.lehuo.net.action.goods;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.JackDefJarRcv;
import com.lehuo.vo.ProductAttitute;

public class GetProductAttrRcv extends JackDefJarRcv {
	LinearLayout mLayout;



	public GetProductAttrRcv(Context context, LinearLayout mLayout) {
		super(context);
		this.mLayout = mLayout;
	}



	@Override
	public boolean respJar(JSONArray jar) throws JSONException {
		if(null!=jar){
			if(jar.length()>0&&null!=mLayout&&null!=context){
				for(int i = 0 ; i<jar.length();i++){
					ProductAttitute pa = new ProductAttitute(jar.getJSONObject(i));
//					if(i!=0) mLayout.addView(divider);
					TextView tempTv = new TextView(context);
					tempTv.setText(pa.getAttr_name()+":"+pa.getAttr_value());
					mLayout.addView(tempTv);
					
				}
				return false;//0?
			}
		}else{
		}
		return true;
	}



}

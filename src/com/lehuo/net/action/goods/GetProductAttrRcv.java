package com.lehuo.net.action.goods;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.JackDefJarRcv;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.ProductAttitute;

public class GetProductAttrRcv extends JackDefJarRcv {
	LinearLayout mLayout;

	public GetProductAttrRcv(Context context, LinearLayout mLayout) {
		super(context);
		this.mLayout = mLayout;
	}

	@Override
	public boolean respJar(JSONArray jar) throws JSONException {
		if (null != jar) {
			if (jar.length() > 0 && null != mLayout && null != context) {
				for (int i = 0; i < jar.length(); i++) {
					ProductAttitute pa = new ProductAttitute(
							jar.getJSONObject(i));
					// if(i!=0) mLayout.addView(divider);
					TextView tempTv = new TextView(context);
					int p = JackUtils.dip2px(context, 5);
					tempTv.setPadding(p, p*3, p, p*3);
					tempTv.setText(pa.getAttr_name() + ":\t\t\t\t\t" + pa.getAttr_value());
					View v = NetStrategies.getSimpleDivider(context);
					if (i != 0)
						mLayout.addView(v);
					mLayout.addView(tempTv);

				}
				return false;// 0?
			}
		} else {
		}
		return true;
	}

}

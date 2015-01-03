/**
 * 
 */
package com.lehuozu.ui.tab.deliver;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.lehuozu.R;
import com.lehuozu.ui.custom.list.ListItemImpl.Type;
import com.lehuozu.ui.tab.JackListAbsContFrag;

/**
 * @author taotao
 *
 */
public class TabFragDeliver extends JackListAbsContFrag {

	@Override
	public void initView() {
		super.initView();
		//1229
		LinearLayout el = new LinearLayout(getActivity());
		View eView =  LayoutInflater.from(getActivity()).inflate(R.layout.merge_nodataimg_follow, el);
		el.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		el.setGravity(Gravity.CENTER);
		mFrame.addView(el);
		mListView.setEmptyView(el);
		mListView.setTag(getActivity());
//		eView.setVisibility(View.GONE);
	}
	
	@Override
	public Type getType() {
		return Type.ORDER_DELIVER;
	}


}

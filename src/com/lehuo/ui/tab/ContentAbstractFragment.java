package com.lehuo.ui.tab;

import com.lehuo.ui.JackAbsFragment;
import com.lehuo.ui.TitleManager;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;


public abstract class ContentAbstractFragment extends JackAbsFragment {
	protected final String TAG = getClass().getSimpleName();
	
	protected TitleManager titleManager;
	
	protected void hideSoftKeyboard( ) {
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 
	 */
	protected void initTitleManager() {
		titleManager = new TitleManager(mView);
	}
	
	/*protected void setTitleImg(int rid){
		if(null==titleImg) titleImg = (ImageView)mView.findViewById(R.id.img_title);
		if(null!=titleImg) {
			titleImg.setImageResource(rid);
		}
	}*/
	/*protected void initBackBtn(){
		if(null==backBtn) backBtn = (ImageView)mView.findViewById(R.id.btn_title_back);
		if(null!=backBtn) {
			backBtn.setVisibility(View.VISIBLE);
			backBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TabHost th = MyData.data().getTabHost();
					if(null!=th){
						th.setCurrentTab(0);
					}
					//TODO
				}
			});
		}
	}*/
}

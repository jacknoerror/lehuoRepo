package com.lehuozu.ui.tab;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lehuozu.R;
import com.lehuozu.data.Const;
import com.lehuozu.data.MyData;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.order.UpdateCartRcv;
import com.lehuozu.net.action.order.UpdateCartReq;
import com.lehuozu.ui.JackAbsFragment;
import com.lehuozu.ui.TitleManager;
import com.lehuozu.ui.custom.JackTitle;
import com.lehuozu.util.JackUtils;

public abstract class ContentAbstractFragment extends JackAbsFragment {
	protected final String TAG = getClass().getSimpleName();

	protected TitleManager titleManager;

	protected void hideSoftKeyboard() {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
				.getWindowToken(), InputMethodManager.SHOW_FORCED);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (null != titleManager) {
			ActionBuilder.getInstance().request(
					new UpdateCartReq(MyData.data().getMe().getUser_id()),
					new UpdateCartRcv(getActivity(), titleManager));
		}
	}

	/**
	 * 
	 */
	protected void initTitleManager() {
		titleManager = new TitleManager(mView);
	}

	/**
	 *  middlelayout.layout(left.getRight(), 0, left.getRight() + display.getWidth(), display.getHeight());
	 * @param arg1
	 */
	public void moveLikeAJagger(float arg1){
		View view  ;
		if(null==mView||null==(view = mView.findViewById(R.id.layout_belowtitle))) return;
		final float drawerListWidth =getResources().getDimension(R.dimen.h_width_drawerlist);
		final float headerHeight =getResources().getDimension(R.dimen.v_titleheight);
		view.layout((int)(drawerListWidth*arg1), (int)headerHeight, (int)(drawerListWidth*arg1+Const.SCREEN_WIDTH), (int) (view.getHeight()+headerHeight));
	}
}

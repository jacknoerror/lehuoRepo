package com.lehuo.ui.tab;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.lehuo.data.MyData;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.order.UpdateCartRcv;
import com.lehuo.net.action.order.UpdateCartReq;
import com.lehuo.ui.JackAbsFragment;
import com.lehuo.ui.TitleManager;
import com.lehuo.ui.custom.JackTitle;

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
		if(null==mView) return;
		mView.layout((int)(540*arg1), 0, (int)(540*arg1+1080), mView.getHeight());
		JackTitle titleView = titleManager.titleView();
		titleView.layout(-(int)(540*arg1), 0, (int)(-540*arg1+1080), titleView.getHeight());
	}
}

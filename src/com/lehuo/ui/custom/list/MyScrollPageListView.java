package com.lehuo.ui.custom.list;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.R;
import com.lehuo.entity.json.JsonImport;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.vo.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class MyScrollPageListView extends ListView implements
		AbsListView.OnScrollListener, ActionPhpReceiverImpl {

	List<ListItemImpl> everythingList;
	MspFactoryImpl factory;

	MspAdapter mAdapter;
	MspPage currentPage;
	OnGetPageListener gpListener;

	View moreView;

	int requestingPage;
	boolean isSetup;
	ListItemImpl.Type mType;

	public interface OnGetPageListener {
		public void page(MyScrollPageListView qListView, int pageNo);
	}

	public MyScrollPageListView(Context context, ListItemImpl.Type type) {
		super(context);
		mType = type;
		init();
	}

	private void init() {
		factory = new MspFactory(mType);
		mAdapter = factory.getNewAdapter();

		setOnGetPageListener(factory.getDefaultOnPageChangeListener());
		
		setOnScrollListener(this);
		// setOnItemClickListener(this);
		setFastScrollEnabled(true);
		everythingList = new ArrayList<ListItemImpl>();
	}

	public void updateList(List<ListItemImpl> list) {
		if (null == getAdapter() && null != mAdapter)
			setAdapter(mAdapter);
		// 放在此处，在更新数据的时候再设置adapter
		if (mAdapter == null) {
		} else {
			List<ListItemImpl> lvList = ((MspAdapter) mAdapter).getList();
			if (lvList != list) {
				lvList.clear();
				lvList.addAll(list);
			}
		}
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 若有请求，使用控件前调用此方法
	 */
	public void setup() {
		isSetup = true;

		if (gpListener != null) {// 开始发请求
			requestingPage = 1;// 0227
			gpListener.page(this, 1);
			if (everythingList != null)
				everythingList.clear();// reset
		}
		addMoreView();// 开始时加入 ，不需要时消除 ， 注意位置，小心报错

	}

	public final boolean isSetup() {
		return isSetup;
	}

	private void addMoreView() {
		// footer //0224 放在if外防止再搜索时moreView的bug //0226 extract handle
		// visibility
		if (null == moreView) {
			moreView = LayoutInflater.from(getContext()).inflate(
					R.layout.footer_more, null);
			addFooterView(moreView);
		} else {
			moreView.setVisibility(View.VISIBLE);
		}
	}

	private void removeMoreView() {
		if (null != moreView) {
			removeFooterView(moreView);
			moreView = null;
		}
	}

	private void nextPage() {
		int requestpage = currentPage.curPageNo + 1;
		if (null == gpListener || requestingPage == requestpage
				|| !currentPage.hasNext)
			return;
		requestingPage = requestpage;
		gpListener.page(this, requestpage);
	}

	/**
	 * 将发请求的操作放入此处
	 * 
	 * @param gpListener
	 */
	public void setOnGetPageListener(OnGetPageListener gpListener) {
		this.gpListener = gpListener;
	}

	@Override
	public boolean response(String result) throws JSONException {
		currentPage = new MspPage( factory);
		currentPage.initJackJson(new JSONObject(result));
		if (currentPage.resultSign) {
			currentPage.curPageNo = requestingPage;
			everythingList.addAll(currentPage.getDataList());
			updateList(everythingList);
		}

		if (!currentPage.hasNext) {
			removeMoreView();
			// Log.i(TAG, "no Next");
		} else {
		}
		return false;
	}

	@Override
	public Context getReceiverContext() {
		return getContext();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (mAdapter == null || mAdapter.getCount() == 0)
			return;
		int count = mAdapter.getCount();
		int lastItem = firstVisibleItem + visibleItemCount;
		if (lastItem == count) {
			nextPage(); // 加载更多数据，这里可以使用异步加载
		}
	}

}

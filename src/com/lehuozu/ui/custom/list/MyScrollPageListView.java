package com.lehuozu.ui.custom.list;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.R;
import com.lehuozu.entity.json.JsonImport;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.util.JackUtils;
import com.lehuozu.vo.Product;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyScrollPageListView extends ListView implements
		AbsListView.OnScrollListener, ActionPhpReceiverImpl {
	private static final String HINT_DONE = "";

	private static final String HINT_PULLTOREFRESH = "下拉刷新";

	final String TAG = getClass().getSimpleName();

	List<ListItemImpl> everythingList;
	MspFactoryImpl factory;

	MspAdapter mAdapter;
	MspPage currentPage;
	OnGetPageListener gpListener;

	View moreView;
	// header
	LinearLayout mHeaderLinearLayout;
	private TextView mHeaderTextView;
	ProgressBar mHeaderProgressBar;
	private int mHeaderHeight;

	private int mCurrentScrollState;

	int requestingPage;
	boolean isSetup;
	ListItemImpl.Type mType;

	private LayoutInflater mInflater;

	public interface OnGetPageListener {
		public void page(MyScrollPageListView qListView, int pageNo);
	}

	public MyScrollPageListView(Context context, ListItemImpl.Type type) {
		super(context);
		mType = type;
		init();
	}

	private void init() {
		mInflater = LayoutInflater.from(getContext());

		factory = new MspFactory(mType);
		mAdapter = factory.getNewAdapter();
		if (null != mAdapter)
			mAdapter.setListView(this);

		setOnGetPageListener(factory.getDefaultOnPageChangeListener());

		setOnScrollListener(this);
		// setOnItemClickListener(this);
		setFastScrollEnabled(true);
		everythingList = new ArrayList<ListItemImpl>();

		addHeaderView();
		measureView(mHeaderLinearLayout);
		mHeaderHeight = mHeaderLinearLayout.getMeasuredHeight();
		setHeaderTopPadding(-mHeaderHeight);//0523 set minus header padding
//		setFooterDividersEnabled(false);
		setHeaderDividersEnabled(false);
		setSelector(R.drawable.selector_common_grey); 
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		if (adapter instanceof MspAdapter)
			mAdapter = (MspAdapter) adapter;
	}

	
	
	public void updateList(List<ListItemImpl> list) {
		if (null == getAdapter() && null != mAdapter)
			setAdapter(mAdapter);
		// 放在此处，在更新数据的时候再设置adapter
		if (mAdapter != null) {
			List<ListItemImpl> lvList = ((MspAdapter) mAdapter).getList();
			if (lvList != list) {
				lvList.clear();
				lvList.addAll(list);
			}
		}
		setSelection(mHeaderLinearLayout != null ? 1 : 0);// do this after// setAdapter// everytime
															
		mAdapter.notifyDataSetChanged();
	}

	public final boolean isSetup() {
		return isSetup;
	}

	/**
	 * 若有请求，使用控件前调用此方法
	 */
	public void setup() {

		addMoreView();// 开始时加入 ，不需要时消除 ， 注意位置，小心报错
		if (gpListener != null) {// 开始发请求
			requestingPage = 1;// 0227
			gpListener.page(this, 1);
			if (everythingList != null)
				everythingList.clear();// reset
		}

		isSetup = true;
	}

	/**
	 * 因为是在构造函数里测量高度，应该先measure一下
	 * 
	 * @param child
	 */
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 
	 */
	private void addHeaderView() {
		if (null == mHeaderLinearLayout) {
			mHeaderLinearLayout = (LinearLayout) mInflater.inflate(
					R.layout.header_mylist, null);
			mHeaderTextView = (TextView) mHeaderLinearLayout
					.findViewById(R.id.tv_header);
			mHeaderProgressBar = (ProgressBar) mHeaderLinearLayout
					.findViewById(R.id.progress_header);
			addHeaderView(mHeaderLinearLayout);
		}
	}

	private void addMoreView() {
		// footer //0224 放在if外防止再搜索时moreView的bug //0226 extract handle
		// visibility
		if (null == moreView) {
			if(isSetup&&iS_TOTAL_TOO_FEW) return; //0523 when few, no show. usually have to do this when "nextpage" results in errors
			moreView = mInflater.inflate(R.layout.footer_more, null);
			addFooterView(moreView);
		} else {//never happen
			
				moreView.setVisibility(View.VISIBLE);
		}
	}

	public void removeMoreView() {
		if (null != moreView) {
			removeFooterView(moreView);
			moreView = null;
		}
	}

	private void nextPage() {
		if (null == currentPage)
			return;// 不需要翻页
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

	public void setNoHeader(boolean noHeader) {
		this.noHeader = noHeader;
	}

	@Override
	public boolean response(String result) throws JSONException {
		resetHeader();

		currentPage = new MspPage(factory);
		if (!result.isEmpty()) {// 确保有pages
			currentPage.initJackJson(new JSONObject(result));
			if (currentPage.resultSign) {
				currentPage.curPageNo = requestingPage;
				everythingList.addAll(currentPage.getDataList());
				updateList(everythingList);
			}
		}

		if (null == currentPage || !currentPage.hasNext) {
			removeMoreView();
			 Log.i(TAG, "no Next");
		} else {
		}
		return false;
	}

	public void resetHeader() {
		// 如果头部刷新中
		if (mPullRefreshState == EXIT_PULL_REFRESH) {
			// mPullRefreshState = NONE_PULL_REFRESH;
			Message msg = mHandler.obtainMessage();
			msg.what = REFRESH_DONE;
			// 通知主线程加载数据完成
			mHandler.sendMessage(msg);
		}
	}

	@Override
	public Context getReceiverContext() {
		return (requestingPage > 1 || mPullRefreshState == EXIT_PULL_REFRESH) ? null
				: getContext();// 0512
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mCurrentScrollState = scrollState;
	}

	private final static int NONE_PULL_REFRESH = 0; // 正常状态
	private final static int ENTER_PULL_REFRESH = 1; // 进入下拉刷新状态
	private final static int OVER_PULL_REFRESH = 2; // 进入松手刷新状态
	private final static int EXIT_PULL_REFRESH = 3; // 松手后反弹后加载状态
	private int mPullRefreshState = 0; // 记录刷新状态

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
//		if(null!=mHeaderLinearLayout)Log.i(TAG, "b:"+mHeaderLinearLayout.getBottom()+"-"+mHeaderHeight);
		iS_TOTAL_TOO_FEW = visibleItemCount>=totalItemCount;
		// header
		if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
				&& firstVisibleItem == 0
				&& (mHeaderLinearLayout.getBottom() >= 0 && mHeaderLinearLayout
						.getBottom() < mHeaderHeight)) {
			// 进入且仅进入下拉刷新状态
			if (mPullRefreshState == NONE_PULL_REFRESH) {
				mPullRefreshState = ENTER_PULL_REFRESH;
				
			}
		} else if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
				&& firstVisibleItem == 0
				&& (mHeaderLinearLayout.getBottom() >= mHeaderHeight)) {
			// 下拉达到界限，进入松手刷新状态
			if (mPullRefreshState == ENTER_PULL_REFRESH
					|| mPullRefreshState == NONE_PULL_REFRESH) {
				mPullRefreshState = OVER_PULL_REFRESH;
				// 下面是进入松手刷新状态需要做的一个显示改变
				mDownY = mMoveY;// 用于后面的下拉特殊效果
				mHeaderTextView.setText("松开刷新");
				/*
				 * mHeaderPullDownImageView.setVisibility(View.GONE);
				 * mHeaderReleaseDownImageView.setVisibility(View.VISIBLE);
				 */
			}
		} else if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
				&& firstVisibleItem != 0) {
			// 不刷新了
			if (mPullRefreshState == ENTER_PULL_REFRESH) {
				mPullRefreshState = NONE_PULL_REFRESH;
			}
		} else if (mCurrentScrollState == SCROLL_STATE_FLING
				&& firstVisibleItem == 0) {
			// 飞滑状态，不能显示出header，也不能影响正常的飞滑
			// 只在正常情况下才纠正位置
			if (mPullRefreshState == NONE_PULL_REFRESH) {
				MotionEvent mEvent = MotionEvent.obtain(
						SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
						MotionEvent.ACTION_CANCEL, 0, 0, 0);
				dispatchTouchEvent(mEvent);//
				mEvent.recycle();
				setSelection(1);
			}
		} 

		// footer
		if (mAdapter == null || mAdapter.getCount() == 0)
			return;
		int count = mAdapter.getCount();
		int lastItem = firstVisibleItem + visibleItemCount -( mHeaderLinearLayout!=null?1:0) - (moreView!=null&&moreView.getVisibility()==View.VISIBLE?1:0);//TODO notice,test
		if (lastItem == count) {
			nextPage(); // 加载更多数据，这里可以使用异步加载
		}
	}

	private float mDownY;
	private float mMoveY;

	private boolean noHeader;

	/*
	 * 特殊处理，当header完全显示后，下拉只按下拉1/3的距离下拉，给用户一种艰难下拉，该松手的弹簧感觉。
	 * 这个在onTouchEvent里处理比较方便
	 * 
	 * @see android.widget.AbsListView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 记下按下位置
			// 改变
			mDownY = ev.getY();
			if(!iS_TOTAL_TOO_FEW) setHeaderTopPadding(0);//0523 abort minus header padding
			break;
		case MotionEvent.ACTION_MOVE:
			mMoveY = ev.getY();
			
			int padTop = (int)( (mMoveY - mDownY) / 3);// 1/3距离折扣
				// 移动时手指的位置
			if(padTop>0) {
			}
			if(null==gpListener||noHeader) break;//0605
			if (mPullRefreshState == OVER_PULL_REFRESH) {
				setHeaderTopPadding(padTop);
				mHeaderTextView.setText(HINT_PULLTOREFRESH);
			}
			//
			if(iS_TOTAL_TOO_FEW&&mCurrentScrollState==1&&mMoveY>mDownY){//0515 
				setHeaderTopPadding(padTop);
				if(mPullRefreshState == NONE_PULL_REFRESH){//每回基本运行一次
					mPullRefreshState = OVER_PULL_REFRESH;
					mHeaderTextView.setText(HINT_PULLTOREFRESH);
				}
				mHeaderTextView.setText(HINT_PULLTOREFRESH);
			}
			break;
		case MotionEvent.ACTION_UP:
			// when you action up, it will do these:
			// 1. roll back util header topPadding is 0
			// 2. hide the header by setSelection(1)
			if (mPullRefreshState == OVER_PULL_REFRESH
					|| mPullRefreshState == ENTER_PULL_REFRESH) {
				new Thread() {
					@Override
					public void run() {
						Message msg;
						while (mHeaderLinearLayout.getPaddingTop() > 1) {
							msg = mHandler.obtainMessage();
							msg.what = REFRESH_BACKING;
							mHandler.sendMessage(msg);
							try {
								sleep(5);// 慢一点反弹，别一下子就弹回去了
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						msg = mHandler.obtainMessage();
						if (mPullRefreshState == OVER_PULL_REFRESH) {
							msg.what = REFRESH_BACED;// 加载数据完成，结束返回
						} else {
							msg.what = REFRESH_RETURN;// 未达到刷新界限，直接返回
						}
						mHandler.sendMessage(msg);
					};
				}.start();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * @param padTop
	 */
	private void setHeaderTopPadding(int padTop) {
		if(null==mHeaderLinearLayout) return;
		// 注意下面的mDownY在onScroll的第二个else中被改变了
		mHeaderLinearLayout.setPadding(
				mHeaderLinearLayout.getPaddingLeft(),
				padTop,
				mHeaderLinearLayout.getPaddingRight(),
				mHeaderLinearLayout.getPaddingBottom());
	}

	// 因为涉及到handler数据处理，为方便我们定义如下常量
	private final static int REFRESH_BACKING = 0; // 反弹中
	private final static int REFRESH_BACED = 1; // 达到刷新界限，反弹结束后
	private final static int REFRESH_RETURN = 2; // 没有达到刷新界限，返回
	private final static int REFRESH_DONE = 3; // 加载数据结束

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_BACKING:
				/*mHeaderLinearLayout.setPadding(
						mHeaderLinearLayout.getPaddingLeft(),
						(int) (mHeaderLinearLayout.getPaddingTop() * 0.75f),
						mHeaderLinearLayout.getPaddingRight(),
						mHeaderLinearLayout.getPaddingBottom());*/
				setHeaderTopPadding((int) (mHeaderLinearLayout.getPaddingTop() * 0.75f));
				break;
			case REFRESH_BACED:
				mHeaderTextView.setText("正在加载...");
				mHeaderProgressBar.setVisibility(View.VISIBLE);
				/*
				 * mHeaderPullDownImageView.setVisibility(View.GONE);
				 * mHeaderReleaseDownImageView.setVisibility(View.GONE);
				 */
				mPullRefreshState = EXIT_PULL_REFRESH;
				//do refreshing work here
				setup();
				break;
			case REFRESH_RETURN:
				// 未达到刷新界限，返回
				mHeaderTextView.setText(HINT_PULLTOREFRESH);
				mHeaderProgressBar.setVisibility(View.INVISIBLE);
				/*
				 * mHeaderPullDownImageView.setVisibility(View.VISIBLE);
				 * mHeaderReleaseDownImageView.setVisibility(View.GONE);
				 */
				/*mHeaderLinearLayout.setPadding(
						mHeaderLinearLayout.getPaddingLeft(), 0,
						mHeaderLinearLayout.getPaddingRight(),
						mHeaderLinearLayout.getPaddingBottom());*/
				setHeaderTopPadding(iS_TOTAL_TOO_FEW?-mHeaderHeight:0);//过少时消去头部
				mPullRefreshState = NONE_PULL_REFRESH;
				setSelection(1);
				break;
			case REFRESH_DONE:
				// 刷新结束后，恢复原始默认状态
				mHeaderTextView.setText(HINT_DONE);
				mHeaderProgressBar.setVisibility(View.INVISIBLE);
				/*
				 * mHeaderPullDownImageView.setVisibility(View.VISIBLE);
				 * mHeaderReleaseDownImageView.setVisibility(View.GONE);
				 * mHeaderUpdateText.setText(getContext().getString(R.string.
				 * app_list_header_refresh_last_update,
				 * mSimpleDateFormat.format(new Date())));
				 */
				/*mHeaderLinearLayout.setPadding(
						mHeaderLinearLayout.getPaddingLeft(), 0,
						mHeaderLinearLayout.getPaddingRight(),
						mHeaderLinearLayout.getPaddingBottom());*/
				setHeaderTopPadding(iS_TOTAL_TOO_FEW?-mHeaderHeight:0);
				mPullRefreshState = NONE_PULL_REFRESH;
				setSelection(1);
				break;
			default:
				break;
			}
		}
	};

	private View makeItLongView;

	private boolean iS_TOTAL_TOO_FEW;
}

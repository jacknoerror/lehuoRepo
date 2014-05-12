package com.lehuo.ui.custom.list;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.R;
import com.lehuo.entity.json.JsonImport;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.Product;

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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyScrollPageListView extends ListView implements
		AbsListView.OnScrollListener, ActionPhpReceiverImpl {
	final String TAG = getClass().getSimpleName();

	List<ListItemImpl> everythingList;
	MspFactoryImpl factory;

	MspAdapter mAdapter;
	MspPage currentPage;
	OnGetPageListener gpListener;

	View moreView;
	//header
	LinearLayout mHeaderLinearLayout ;
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

		setOnGetPageListener(factory.getDefaultOnPageChangeListener());
		
		setOnScrollListener(this);
		// setOnItemClickListener(this);
		setFastScrollEnabled(true);
		everythingList = new ArrayList<ListItemImpl>();
		
		addHeaderView();
		measureView(mHeaderLinearLayout);
		mHeaderHeight = mHeaderLinearLayout.getMeasuredHeight();
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
		setSelection(mHeaderLinearLayout!=null?1:0);//do this after setAdapter everytime
		mAdapter.notifyDataSetChanged();
	}

	public final boolean isSetup() {
		return isSetup;
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

	/**
	 * 因为是在构造函数里测量高度，应该先measure一下
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
		if(null==mHeaderLinearLayout){
			mHeaderLinearLayout = (LinearLayout)mInflater .inflate(R.layout.header_mylist, null);
			mHeaderTextView = (TextView)mHeaderLinearLayout.findViewById(R.id.tv_header);
			mHeaderProgressBar = (ProgressBar )mHeaderLinearLayout.findViewById(R.id.progress_header);
			addHeaderView(mHeaderLinearLayout);
		}
	}

	private void addMoreView() {
		// footer //0224 放在if外防止再搜索时moreView的bug //0226 extract handle
		// visibility
		if (null == moreView) {
			
			moreView = mInflater.inflate(
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
		//如果头部刷新中
		if(mPullRefreshState == EXIT_PULL_REFRESH) {
//			mPullRefreshState = NONE_PULL_REFRESH;
			Message msg = mHandler.obtainMessage();
            msg.what = REFRESH_DONE;
            //通知主线程加载数据完成
            mHandler.sendMessage(msg);
		}
		
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
		return (requestingPage>1||mPullRefreshState == EXIT_PULL_REFRESH)?null:getContext();//0512
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mCurrentScrollState = scrollState;
	}
	private final static int NONE_PULL_REFRESH = 0;   //正常状态
	private final static int ENTER_PULL_REFRESH = 1;  //进入下拉刷新状态
	private final static int OVER_PULL_REFRESH = 2;   //进入松手刷新状态
	private final static int EXIT_PULL_REFRESH = 3;     //松手后反弹后加载状态
	private int mPullRefreshState = 0;                         //记录刷新状态
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		//header
		if (mCurrentScrollState ==SCROLL_STATE_TOUCH_SCROLL
	            && firstVisibleItem == 0
	            && (mHeaderLinearLayout.getBottom() >= 0 && mHeaderLinearLayout.getBottom() < mHeaderHeight)) {
	        //进入且仅进入下拉刷新状态
	        if (mPullRefreshState == NONE_PULL_REFRESH) {
	            mPullRefreshState = ENTER_PULL_REFRESH;
	        }
	    } else if (mCurrentScrollState ==SCROLL_STATE_TOUCH_SCROLL
	            && firstVisibleItem == 0
	            && (mHeaderLinearLayout.getBottom() >= mHeaderHeight)) {
	        //下拉达到界限，进入松手刷新状态
	        if (mPullRefreshState == ENTER_PULL_REFRESH || mPullRefreshState == NONE_PULL_REFRESH) {
	            mPullRefreshState = OVER_PULL_REFRESH;
	            //下面是进入松手刷新状态需要做的一个显示改变
	            mDownY = mMoveY;//用于后面的下拉特殊效果
	            mHeaderTextView.setText("松开刷新");
	            /*mHeaderPullDownImageView.setVisibility(View.GONE);
	            mHeaderReleaseDownImageView.setVisibility(View.VISIBLE);*/
	        }
	    } else if (mCurrentScrollState ==SCROLL_STATE_TOUCH_SCROLL && firstVisibleItem != 0) {
	        //不刷新了
	        if (mPullRefreshState == ENTER_PULL_REFRESH) {
	            mPullRefreshState = NONE_PULL_REFRESH;
	        }
	    } else if (mCurrentScrollState == SCROLL_STATE_FLING && firstVisibleItem == 0) {
	        //飞滑状态，不能显示出header，也不能影响正常的飞滑
	        //只在正常情况下才纠正位置
	        if (mPullRefreshState == NONE_PULL_REFRESH) {
	        	MotionEvent mEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0);
				dispatchTouchEvent(mEvent);//
				mEvent.recycle();
	            setSelection(1);
	        }
	    }

		//屏幕没有占满时的意外处理
		if(visibleItemCount==totalItemCount){
			if(null==makeItLongView||makeItLongView.getVisibility()!=View.VISIBLE){
				
				makeItLongView = new View(getContext());
				makeItLongView.setLayoutParams(new LayoutParams(0, JackUtils.dip2px(getContext(), 240)));
				addFooterView(makeItLongView);
			}
		}else{
			if(null!=makeItLongView) removeFooterView(makeItLongView); 
				//appearenceView.setVisibility(View.GONE);
		}
		
		//footer
		if (mAdapter == null || mAdapter.getCount() == 0)
			return;
		int count = mAdapter.getCount();
		int lastItem = firstVisibleItem + visibleItemCount;
		if (lastItem == count) {
			nextPage(); // 加载更多数据，这里可以使用异步加载
		}
	}

	private float mDownY;
	private float mMoveY;
	/* 特殊处理，当header完全显示后，下拉只按下拉1/3的距离下拉，给用户一种艰难下拉，该松手的弹簧感觉。
	 * 这个在onTouchEvent里处理比较方便
	 * @see android.widget.AbsListView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
	    switch (ev.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	            //记下按下位置
	            //改变
	            mDownY = ev.getY();
	            break;
	        case MotionEvent.ACTION_MOVE:
	            //移动时手指的位置
	            mMoveY = ev.getY();
	            if (mPullRefreshState == OVER_PULL_REFRESH) {
	                //注意下面的mDownY在onScroll的第二个else中被改变了
	                mHeaderLinearLayout.setPadding(mHeaderLinearLayout.getPaddingLeft(),
	                        (int)((mMoveY - mDownY)/3), //1/3距离折扣
	                        mHeaderLinearLayout.getPaddingRight(),
	                        mHeaderLinearLayout.getPaddingBottom());
	            }
	            break;
	        case MotionEvent.ACTION_UP:
	        	 //when you action up, it will do these:
	            //1. roll back util header topPadding is 0
	            //2. hide the header by setSelection(1)
	            if (mPullRefreshState == OVER_PULL_REFRESH || mPullRefreshState == ENTER_PULL_REFRESH) {
	                new Thread() {
	                	@Override
	                    public void run() {
	                        Message msg;
	                        while(mHeaderLinearLayout.getPaddingTop() > 1) {
	                            msg = mHandler.obtainMessage();
	                            msg.what = REFRESH_BACKING;
	                            mHandler.sendMessage(msg);
	                            try {
	                                sleep(5);//慢一点反弹，别一下子就弹回去了
	                            } catch (InterruptedException e) {
	                                e.printStackTrace();
	                            }
	                        }
	                        msg = mHandler.obtainMessage();
	                        if (mPullRefreshState == OVER_PULL_REFRESH) {
	                            msg.what = REFRESH_BACED;//加载数据完成，结束返回
	                        } else {
	                            msg.what = REFRESH_RETURN;//未达到刷新界限，直接返回
	                        }
	                        mHandler.sendMessage(msg);
	                    };
	                }.start();
	            }
	            break;
	    }
	    return super.onTouchEvent(ev);
	}
	
	//因为涉及到handler数据处理，为方便我们定义如下常量
		private final static int REFRESH_BACKING = 0;      //反弹中
		private final static int REFRESH_BACED = 1;        //达到刷新界限，反弹结束后
		private final static int REFRESH_RETURN = 2;       //没有达到刷新界限，返回
		private final static int REFRESH_DONE = 3;         //加载数据结束
		
		private Handler mHandler = new Handler(){
		    @Override
		    public void handleMessage(Message msg) {
		        switch (msg.what) {
		        case REFRESH_BACKING:
		            mHeaderLinearLayout.setPadding(mHeaderLinearLayout.getPaddingLeft(),
		                    (int) (mHeaderLinearLayout.getPaddingTop()*0.75f),
		                    mHeaderLinearLayout.getPaddingRight(),
		                    mHeaderLinearLayout.getPaddingBottom());
		            break;
		        case REFRESH_BACED:
		            mHeaderTextView.setText("正在加载...");
		             mHeaderProgressBar.setVisibility(View.VISIBLE);
		             /*mHeaderPullDownImageView.setVisibility(View.GONE);
		            mHeaderReleaseDownImageView.setVisibility(View.GONE);*/
		            mPullRefreshState = EXIT_PULL_REFRESH;
		           /* new Thread() {// replace this 
		            	@Override
		                public void run() {
		                    SystemClock.sleep(2000);//处理后台加载数据
		                    Message msg = mHandler.obtainMessage();
		                    msg.what = REFRESH_DONE;
		                    //通知主线程加载数据完成
		                    mHandler.sendMessage(msg);
		                };
		            }.start();*/
//		            if(null!=gpListener) gpListener.page(MyScrollPageListView.this,1);
		            setup();
		            break;
		        case REFRESH_RETURN:
		            //未达到刷新界限，返回
		            mHeaderTextView.setText("下拉刷新");
		            mHeaderProgressBar.setVisibility(View.INVISIBLE);
		            /* mHeaderPullDownImageView.setVisibility(View.VISIBLE);
		            mHeaderReleaseDownImageView.setVisibility(View.GONE);*/
		            mHeaderLinearLayout.setPadding(mHeaderLinearLayout.getPaddingLeft(),
		                    0,
		                    mHeaderLinearLayout.getPaddingRight(),
		                    mHeaderLinearLayout.getPaddingBottom());
		            mPullRefreshState = NONE_PULL_REFRESH;
		            setSelection(1);
		            break;
		        case REFRESH_DONE:
		            //刷新结束后，恢复原始默认状态
		            mHeaderTextView.setText("下拉刷新");
		            mHeaderProgressBar.setVisibility(View.INVISIBLE);
		            /*mHeaderPullDownImageView.setVisibility(View.VISIBLE);
		            mHeaderReleaseDownImageView.setVisibility(View.GONE);
		            mHeaderUpdateText.setText(getContext().getString(R.string.app_list_header_refresh_last_update, 
		                    mSimpleDateFormat.format(new Date())));*/
		            mHeaderLinearLayout.setPadding(mHeaderLinearLayout.getPaddingLeft(),
		                    0,
		                    mHeaderLinearLayout.getPaddingRight(),
		                    mHeaderLinearLayout.getPaddingBottom());
		            mPullRefreshState = NONE_PULL_REFRESH;
		            setSelection(1);
		            break;
		        default:
		            break;
		        }
		    }
		};

		private View makeItLongView;
}

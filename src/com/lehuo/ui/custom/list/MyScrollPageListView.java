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
		// ���ڴ˴����ڸ������ݵ�ʱ��������adapter
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
	 * ��������ʹ�ÿؼ�ǰ���ô˷���
	 */
	public void setup() {
		isSetup = true;
		
		
		if (gpListener != null) {// ��ʼ������
			requestingPage = 1;// 0227
			gpListener.page(this, 1);
			if (everythingList != null)
				everythingList.clear();// reset
		}
		
		addMoreView();// ��ʼʱ���� ������Ҫʱ���� �� ע��λ�ã�С�ı���
	}

	/**
	 * ��Ϊ���ڹ��캯��������߶ȣ�Ӧ����measureһ��
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
		// footer //0224 ����if���ֹ������ʱmoreView��bug //0226 extract handle
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
	 * ��������Ĳ�������˴�
	 * 
	 * @param gpListener
	 */
	public void setOnGetPageListener(OnGetPageListener gpListener) {
		this.gpListener = gpListener;
	}

	@Override
	public boolean response(String result) throws JSONException {
		//���ͷ��ˢ����
		if(mPullRefreshState == EXIT_PULL_REFRESH) {
//			mPullRefreshState = NONE_PULL_REFRESH;
			Message msg = mHandler.obtainMessage();
            msg.what = REFRESH_DONE;
            //֪ͨ���̼߳����������
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
	private final static int NONE_PULL_REFRESH = 0;   //����״̬
	private final static int ENTER_PULL_REFRESH = 1;  //��������ˢ��״̬
	private final static int OVER_PULL_REFRESH = 2;   //��������ˢ��״̬
	private final static int EXIT_PULL_REFRESH = 3;     //���ֺ󷴵������״̬
	private int mPullRefreshState = 0;                         //��¼ˢ��״̬
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		//header
		if (mCurrentScrollState ==SCROLL_STATE_TOUCH_SCROLL
	            && firstVisibleItem == 0
	            && (mHeaderLinearLayout.getBottom() >= 0 && mHeaderLinearLayout.getBottom() < mHeaderHeight)) {
	        //�����ҽ���������ˢ��״̬
	        if (mPullRefreshState == NONE_PULL_REFRESH) {
	            mPullRefreshState = ENTER_PULL_REFRESH;
	        }
	    } else if (mCurrentScrollState ==SCROLL_STATE_TOUCH_SCROLL
	            && firstVisibleItem == 0
	            && (mHeaderLinearLayout.getBottom() >= mHeaderHeight)) {
	        //�����ﵽ���ޣ���������ˢ��״̬
	        if (mPullRefreshState == ENTER_PULL_REFRESH || mPullRefreshState == NONE_PULL_REFRESH) {
	            mPullRefreshState = OVER_PULL_REFRESH;
	            //�����ǽ�������ˢ��״̬��Ҫ����һ����ʾ�ı�
	            mDownY = mMoveY;//���ں������������Ч��
	            mHeaderTextView.setText("�ɿ�ˢ��");
	            /*mHeaderPullDownImageView.setVisibility(View.GONE);
	            mHeaderReleaseDownImageView.setVisibility(View.VISIBLE);*/
	        }
	    } else if (mCurrentScrollState ==SCROLL_STATE_TOUCH_SCROLL && firstVisibleItem != 0) {
	        //��ˢ����
	        if (mPullRefreshState == ENTER_PULL_REFRESH) {
	            mPullRefreshState = NONE_PULL_REFRESH;
	        }
	    } else if (mCurrentScrollState == SCROLL_STATE_FLING && firstVisibleItem == 0) {
	        //�ɻ�״̬��������ʾ��header��Ҳ����Ӱ�������ķɻ�
	        //ֻ����������²ž���λ��
	        if (mPullRefreshState == NONE_PULL_REFRESH) {
	        	MotionEvent mEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0);
				dispatchTouchEvent(mEvent);//
				mEvent.recycle();
	            setSelection(1);
	        }
	    }

		//��Ļû��ռ��ʱ�����⴦��
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
			nextPage(); // ���ظ������ݣ��������ʹ���첽����
		}
	}

	private float mDownY;
	private float mMoveY;
	/* ���⴦����header��ȫ��ʾ������ֻ������1/3�ľ������������û�һ�ּ��������������ֵĵ��ɸо���
	 * �����onTouchEvent�ﴦ��ȽϷ���
	 * @see android.widget.AbsListView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
	    switch (ev.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	            //���°���λ��
	            //�ı�
	            mDownY = ev.getY();
	            break;
	        case MotionEvent.ACTION_MOVE:
	            //�ƶ�ʱ��ָ��λ��
	            mMoveY = ev.getY();
	            if (mPullRefreshState == OVER_PULL_REFRESH) {
	                //ע�������mDownY��onScroll�ĵڶ���else�б��ı���
	                mHeaderLinearLayout.setPadding(mHeaderLinearLayout.getPaddingLeft(),
	                        (int)((mMoveY - mDownY)/3), //1/3�����ۿ�
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
	                                sleep(5);//��һ�㷴������һ���Ӿ͵���ȥ��
	                            } catch (InterruptedException e) {
	                                e.printStackTrace();
	                            }
	                        }
	                        msg = mHandler.obtainMessage();
	                        if (mPullRefreshState == OVER_PULL_REFRESH) {
	                            msg.what = REFRESH_BACED;//����������ɣ���������
	                        } else {
	                            msg.what = REFRESH_RETURN;//δ�ﵽˢ�½��ޣ�ֱ�ӷ���
	                        }
	                        mHandler.sendMessage(msg);
	                    };
	                }.start();
	            }
	            break;
	    }
	    return super.onTouchEvent(ev);
	}
	
	//��Ϊ�漰��handler���ݴ���Ϊ�������Ƕ������³���
		private final static int REFRESH_BACKING = 0;      //������
		private final static int REFRESH_BACED = 1;        //�ﵽˢ�½��ޣ�����������
		private final static int REFRESH_RETURN = 2;       //û�дﵽˢ�½��ޣ�����
		private final static int REFRESH_DONE = 3;         //�������ݽ���
		
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
		            mHeaderTextView.setText("���ڼ���...");
		             mHeaderProgressBar.setVisibility(View.VISIBLE);
		             /*mHeaderPullDownImageView.setVisibility(View.GONE);
		            mHeaderReleaseDownImageView.setVisibility(View.GONE);*/
		            mPullRefreshState = EXIT_PULL_REFRESH;
		           /* new Thread() {// replace this 
		            	@Override
		                public void run() {
		                    SystemClock.sleep(2000);//�����̨��������
		                    Message msg = mHandler.obtainMessage();
		                    msg.what = REFRESH_DONE;
		                    //֪ͨ���̼߳����������
		                    mHandler.sendMessage(msg);
		                };
		            }.start();*/
//		            if(null!=gpListener) gpListener.page(MyScrollPageListView.this,1);
		            setup();
		            break;
		        case REFRESH_RETURN:
		            //δ�ﵽˢ�½��ޣ�����
		            mHeaderTextView.setText("����ˢ��");
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
		            //ˢ�½����󣬻ָ�ԭʼĬ��״̬
		            mHeaderTextView.setText("����ˢ��");
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

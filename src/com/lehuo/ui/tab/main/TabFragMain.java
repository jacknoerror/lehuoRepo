package com.lehuo.ui.tab.main;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.lehuo.R;
import com.lehuo.data.Const;
import com.lehuo.data.MyData;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.goods.GetTbarReq;
import com.lehuo.net.action.order.UpdateCartRcv;
import com.lehuo.net.action.order.UpdateCartReq;
import com.lehuo.ui.MyGate;
import com.lehuo.ui.adapter.MyGridViewAdapter;
import com.lehuo.ui.adapter.MyPagerAdapater;
import com.lehuo.ui.tab.ContentAbstractFragment;
import com.lehuo.util.JackImageLoader;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.Category;
import com.lehuo.vo.Product;
import com.lehuo.vo.TongData;
import com.lehuo.vo.User;

public class TabFragMain extends ContentAbstractFragment implements
		ViewPager.OnPageChangeListener, AdapterView.OnItemClickListener {
	/**
	 * suppose to be name&imgUrl
	 */
	List<ImageView> imgList;
	List<ImageView> spotList;
	List<Category> categoryList;
	User user;

	// DrawerLayout mDrawer;
	// ListView mDrawerList;
	// ActionBarDrawerToggle mDrawerToggle;
	GridView mGridView;
	ViewPager mViewPager;
	private LinearLayout spotLayout;
	private List<View> viewList;

	@Override
	public int getLayoutRid() {
		return R.layout.fragment_mainpage;
	}

	
	@Override
	public void initView() {
		initTitleManager();
//		titleManager.setTitleName("首页");
		titleManager.initTitleLogo();
		titleManager.initTitleMenu();
		//move request for cart to onresume 0522

		// mDrawer = (DrawerLayout) mView.findViewById(R.id.drawer_main);
		// mDrawer.setDrawerShadow(R.drawable.shadow, GravityCompat.START);

		// mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawer,
		// R.drawable.ic_launcher, R.string.app_name, R.string.app_name);
		// mDrawer.setDrawerListener(mDrawerToggle);

		// viewpager
		mViewPager = (ViewPager) mView.findViewById(R.id.viewpager_);
		viewList = new ArrayList<View>();
		imgList = new ArrayList<ImageView>();
		spotList = new ArrayList<ImageView>();
		spotLayout = (LinearLayout) mView.findViewById(R.id.layout_spots);

		mViewPager.setOnPageChangeListener(this);
		// request
		ActionPhpRequestImpl actReq = new GetTbarReq();
		ActionPhpReceiverImpl actRcv = new ActionPhpReceiverImpl() {

			@Override
			public boolean response(String result) throws JSONException {
				JSONArray jar = NetStrategies.getResultArray(result);
				if (null != jar) {
					for (int i = 0; i < jar.length(); i++) {
						TongData td = new TongData(jar.getJSONObject(i));
						addImg(td);
					}
					setSpotSelected(0);
					if (spotList.size() <= 1)
						spotLayout.setVisibility(View.INVISIBLE);// 只有一个则不显示
					mViewPager.setAdapter(new MyPagerAdapater(viewList));
					return false;
				}
				return true;
			}

			@Override
			public Context getReceiverContext() {
				return null;
			}
		};
		ActionBuilder.getInstance().request(actReq, actRcv);

		// gridview
		categoryList = MyData.data().getAllCate();
		if (categoryList.size() > 0)
			initGrid(categoryList);// else happen?
		// request

		// updateUI();
	}

	/**
	 * @param td
	 * 
	 */
	public void addImg(final TongData td) {
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setGravity(Gravity.CENTER);

		ImageView img = new ImageView(getActivity());
		img.setScaleType(ScaleType.CENTER_CROP);
		// img.setImageResource(R.drawable.ic_launcher);

		String img_s_path = td.getImg_small();
		JackImageLoader.justSetMeImage(img_s_path, img);
		// img.setLayoutParams(new LinearLayout.LayoutParams((int)
		// Const.SCREEN_WIDTH, JackUtils.dip2px(getActivity(), 125)));
		img.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));

		layout.addView(img);
		viewList.add(layout);
		imgList.add(img);

		if (td.isGoods()) {
			img.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Product product = new Product();
					product.setGoods_id(td.getGoods_id());// TODO
					MyGate.GoProduct(getActivity(), product);
				}
			});
		}

		// spot
		FrameLayout layoutWithImg = new FrameLayout(getActivity());
		layoutWithImg.setPadding(5, 5, 5, 5);
		ImageView im = new ImageView(getActivity());// (ImageView)LayoutInflater.from(this).inflate(R.layout.image_spot,
													// null);//@drawable/spot_selector
		im.setImageResource(R.drawable.spot_selector);
		layoutWithImg.addView(im);
		spotLayout.addView(layoutWithImg);
		spotList.add(im);
	}

	/**
	 * @param categoryList2
	 */
	private void initGrid(List<Category> categoryList2) {
		mGridView = (GridView) mView.findViewById(R.id.gridview_main);
		MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter(getActivity(), categoryList2);
		mGridView
				.setAdapter(myGridViewAdapter);
		mGridView.setOnItemClickListener(this);
		mGridView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return MotionEvent.ACTION_MOVE == event.getAction();
			}
		});
		mGridView.setLayoutParams(new LinearLayout.LayoutParams(
				(int) Const.SCREEN_WIDTH,
				JackUtils.dip2px(getActivity(), 175) * (categoryList2.size()/2)));//pic height 160
	}

	private void setSpotSelected(int index) {
		if (mViewPager == null || spotList == null || spotList.size() == 0)
			return;
		View viewSelected = (View) mViewPager.getTag();
		if (viewSelected != null) {
			viewSelected.setSelected(false);
		}
		spotList.get(index).setSelected(true);
		spotList.get(index).requestFocus();
		mViewPager.setTag(spotList.get(index));
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		if (arg0 >= spotList.size() || arg0 < 0 || spotList.size() <= 1)
			return;
		setSpotSelected(arg0);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MyGate.goCategory(getActivity(), categoryList.get(position));
	}

}

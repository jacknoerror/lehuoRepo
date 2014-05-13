package com.lehuo.ui.tab.main;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lehuo.R;
import com.lehuo.data.Const;
import com.lehuo.data.MyData;
import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.category.GetAllReq;
import com.lehuo.net.action.goods.GetTbarReq;
import com.lehuo.net.action.order.GetCartRcv;
import com.lehuo.net.action.order.GetCartReq;
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
		ViewPager.OnPageChangeListener, AdapterView.OnItemClickListener,
		ActionPhpReceiverImpl {
	/**
	 * suppose to be name&imgUrl
	 */
	String[] mPlanetTitles = new String[] { "1", "2", "3", "4", "m", "6", "7",
			"8", "9", "0", "11", "12", "13", "14" };
	List<ImageView> imgList;
	List<ImageView> spotList;
	List<Category> categoryList;
	User user;

	DrawerLayout mDrawer;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	GridView mGridView;
	ViewPager mViewPager;
	private LinearLayout spotLayout;
	private List<View> viewList;

	@Override
	public int getLayoutRid() {
		return R.layout.fragment_main;
	}

	@Override
	public void initView() {
		initTitleManager();
		titleManager.setTitleName("首页");
		// 应该改为开头发请求，再更新；cartActivity本身不发请求；观察updatecart接口；考虑把title放到hubActivity
		// 开头发的请求是updateCart --0505
		// titleManager.updateCart();
		ActionBuilder.getInstance().request(
				new UpdateCartReq(MyData.data().getMe().getUser_id()),
				new UpdateCartRcv(getActivity(), titleManager));

		mDrawer = (DrawerLayout) mView.findViewById(R.id.drawer_main);
		// init the ListView and Adapter, nothing new
		// initListView();

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawer.setDrawerShadow(R.drawable.shadow, GravityCompat.START);

		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawer,
				R.drawable.ic_launcher, R.string.app_name, R.string.app_name);
		/*
		 * {
		 *//** Called when a drawer has settled in a completely closed state. */
		/*
		 * public void onDrawerClosed(View view) {
		 * getActivity().invalidateOptionsMenu(); // creates call to //
		 * onPrepareOptionsMenu() }
		 *//** Called when a drawer has settled in a completely open state. */
		/*
		 * public void onDrawerOpened(View drawerView) {
		 * getActivity().invalidateOptionsMenu(); // creates call to //
		 * onPrepareOptionsMenu() } };
		 */

		// Set the drawer toggle as the DrawerListener
		mDrawer.setDrawerListener(mDrawerToggle);

		// enable ActionBar app icon to behave as action to toggle nav drawer
		// getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);//TODO
		// need or not?
		// getActionBar().setHomeButtonEnabled(true);
		// Note: getActionBar() Added in API level 11

		// viewpager
		mViewPager = (ViewPager) mView.findViewById(R.id.viewpager_);
		viewList = new ArrayList<View>();
		imgList = new ArrayList<ImageView>();
		spotList = new ArrayList<ImageView>();
		spotLayout = (LinearLayout) mView
				.findViewById(R.id.layout_spots);
//		for (int i = 0; i < 4; i++) {
//			addImg();
//		}
		
		mViewPager.setOnPageChangeListener(this);
		//request
		ActionPhpRequestImpl actReq = new GetTbarReq();
		ActionPhpReceiverImpl actRcv = new ActionPhpReceiverImpl() {
			
			@Override
			public boolean response(String result) throws JSONException {
				JSONArray jar = NetStrategies.getResultArray(result);
				if(null!=jar){
					for(int i=0;i<jar.length();i++){
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
		
			// request
		if ((categoryList = MyData.data().getAllCate()).size() == 0) {

			ActionPhpRequestImpl actReq2 = new GetAllReq();
			ActionBuilder.getInstance().request(actReq2, this);
		} else {
			updateUI();
		}
	}

	/**
	 * @param td 
	 * 
	 */
	public void addImg(final TongData td) {
		LinearLayout layout = new LinearLayout(getActivity());
		
		ImageView img = new ImageView(getActivity());
		img.setScaleType(ScaleType.CENTER_CROP);
//			img.setImageResource(R.drawable.ic_launcher);
		
		String img_s_path = td.getImg_small();
		JackImageLoader.justSetMeImage(img_s_path, img);
		img.setLayoutParams(new LinearLayout.LayoutParams((int) Const.SCREEN_WIDTH, JackUtils.dip2px(getActivity(), 125)));
		
		layout.addView(img);
		viewList.add(layout);
		imgList.add(img);

		if(td.isGoods()){
			img.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Product product = new Product();
					product.setGoods_id(td.getGoods_id());//TODO
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
		mGridView
				.setAdapter(new MyGridViewAdapter(getActivity(), categoryList2));
		mGridView.setOnItemClickListener(this);
	}

	private void initListView() {
		mDrawerList = (ListView) mView.findViewById(R.id.left_drawer);

		// mPlanetTitles = getResources().getStringArray(R.array.planets_array);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.item_drawerlist, mPlanetTitles));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(this);
		mDrawerList.setDivider(getResources()
				.getDrawable(android.R.color.black));
		mDrawerList.setDividerHeight(1);
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

	/*
	 * @Override //TODO protected void onPostCreate(Bundle savedInstanceState) {
	 * super.onPostCreate(savedInstanceState); // Sync the toggle state after
	 * onRestoreInstanceState has occurred. mDrawerToggle.syncState(); }
	 */

	private void updateUI() {
		mPlanetTitles = new String[categoryList.size()];
		int i = 0;
		for (Category cat : categoryList) {
			mPlanetTitles[i++] = cat.getCat_name();
		}
		initListView();
		initGrid(categoryList);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
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
		// TODO Auto-generated method stub

		// Highlight the selected item, update the title, and close the
		// drawer
		mDrawerList.setItemChecked(position, true);// delete?
		// setTitle(mPlanetTitles[position]);//TODO
		mDrawer.closeDrawer(mDrawerList);

		Log.i(TAG, "onItemClick:" + position);
		MyGate.goCategory(getActivity(),
				(Category) parent.getItemAtPosition(position));
	}

	@Override
	public boolean response(String result) throws JSONException {
		JSONObject job = new JSONObject(result);
		if (null != job && !job.isNull(NetConst.RESULT_OBJ)) { // 这里resultsign竟然是false
			// fetch data
			JSONArray jar = job.getJSONArray(RESULT_OBJ);
			categoryList = MyData.data().getAllCate();
			for (int i = 0; i < jar.length(); i++) {
				JSONObject catJob = jar.getJSONObject(i);
				Category cate = new Category(catJob);
				categoryList.add(cate);
			}
			// update UI
			updateUI();
		}
		return false;
	}

	@Override
	public Context getReceiverContext() {
		return getActivity();
	}
}

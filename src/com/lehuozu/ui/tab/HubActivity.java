package com.lehuozu.ui.tab;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

import com.lehuozu.R;
import com.lehuozu.data.Const;
import com.lehuozu.data.MyData;
import com.lehuozu.data.NetConst;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.category.GetAllReq;
import com.lehuozu.net.action.category.GetCatRcv;
import com.lehuozu.net.action.goods.GetTbarReq;
import com.lehuozu.ui.MyGate;
import com.lehuozu.ui.adapter.MyPagerAdapater;
import com.lehuozu.ui.custom.JackFragmentTabChangedHelper;
import com.lehuozu.ui.custom.JackFragmentTabChangedHelper.TabPack;
import com.lehuozu.ui.tab.deliver.TabFragDeliver;
import com.lehuozu.ui.tab.main.TabFragMain;
import com.lehuozu.ui.tab.more.TabFragMore;
import com.lehuozu.ui.tab.my.TabFragMy;
import com.lehuozu.ui.tab.order.TabFragOrder;
import com.lehuozu.util.JackUtils;
import com.lehuozu.util.TestDataTracker;
import com.lehuozu.vo.Category;
import com.lehuozu.vo.TongData;
import com.umeng.analytics.MobclickAgent;

public class HubActivity extends FragmentActivity implements OnTabChangeListener, OnItemClickListener{
	
	final TabPack[] hubs = new TabPack[]{
			new TabPack(R.drawable.selector_tab_home, "首页", TabFragMain.class),
			new TabPack(R.drawable.selector_tab_order, "订单", TabFragOrder.class),
			new TabPack(R.drawable.selector_tab_track, "送货跟踪", TabFragDeliver.class),
			new TabPack(R.drawable.selector_tab_center, "账户", TabFragMy.class),
			new TabPack(R.drawable.selector_tab_more, "更多", TabFragMore.class)
	};
	
	private TabHost mTabHost;
	JackFragmentTabChangedHelper jftcl;

	private DrawerLayout mDrawer;
	private ListView mDrawerList;

	private String[] mPlanetTitles;

	private List<Category> categoryList;

	public static final int AR_GOGEO = 0x002;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_hub);
		
		categoryList = MyData.data().getAllCate();
		mDrawer = (DrawerLayout) this.findViewById(R.id.drawer_main);
		mDrawer.setDrawerShadow(R.drawable.shadow, GravityCompat.START);
		MyData.data().setDrawer(mDrawer);
		
		mDrawer.setDrawerListener(new DrawerLayout.DrawerListener() {
			
			@Override
			public void onDrawerStateChanged(int arg0) {
				/*
				 * if (drawerLayout.isDrawerOpen(rela)) {
                        middlelayout.layout(left.getRight(), 0, left.getRight() + display.getWidth(), display.getHeight());
                        //  middlelayout  --->抽屉中间布局   left --->抽屉左边布局
                }
				 */
			}
			
			@Override
			public void onDrawerSlide(View arg0, float arg1) {
				Fragment frag = jftcl.getCurrent();
				if(frag instanceof ContentAbstractFragment){
					((ContentAbstractFragment) frag).moveLikeAJagger(arg1);
				}
//				Log.i("HubActivity-onDrawerSlide", "ai:"+arg1);
			}
			
			@Override
			public void onDrawerOpened(View arg0) {
			}
			
			@Override
			public void onDrawerClosed(View arg0) {
			}
		});
		
		requestCats();
//		
	}

	private void requestCats() {
		ActionPhpRequestImpl actReq2 = new GetAllReq();
		ActionPhpReceiverImpl actrcv = new GetCatRcv(this){
			@Override
			public boolean response(String result) throws JSONException {
				boolean response = super.response(result);
				if(!response){
					updateUI();
				}
				return response;
			}
		};
		ActionBuilder.getInstance().request(actReq2, actrcv);
	}

	private void updateUI() {
		mPlanetTitles = new String[categoryList.size()];
		int i = 0;
		for (Category cat : categoryList) {
			mPlanetTitles[i++] = cat.getCat_name();
		}
		initListView();
		//数据完成后再初始化分页
		initTabhost();
		
	}
	
	public void initListView() {//1230 public for main
		mDrawerList = (ListView) this.findViewById(R.id.left_drawer);

		// mPlanetTitles = getResources().getStringArray(R.array.planets_array);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.item_drawerlist, mPlanetTitles));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(this);
		mDrawerList.setDivider(getResources()
				.getDrawable(android.R.color.black));
		mDrawerList.setDividerHeight(1);
	}
	
	private void initTabhost() {
		int hub = getIntent().getIntExtra(Const.EXTRAS_HUB, 0);
		jftcl = new JackFragmentTabChangedHelper(this, R.id.realtabcontent);
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		for(int i=0;i<hubs.length;i++){
			jftcl.addTabPack(hubs[i]);
		}
		mTabHost.setOnTabChangedListener(this);
		mTabHost.setup( );
		for(int i=0;i<hubs.length;i++){
			addTabBtn(i);
		}
		if(hub<hubs.length)mTabHost.setCurrentTab(hub);
		
		
		MyData.data().setTabHost(mTabHost);
	}

	@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode==KeyEvent.KEYCODE_BACK){
				
				JackUtils.showDialog(this, "确定要退到登录界面么？", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
	//					YftValues.logout();
						HubActivity.this.finish();
						dialog.dismiss();
						mTabHost =null;//
	//					TODO
					}
				});
//				mDrawer.openDrawer(Gravity.LEFT);
				return true;
			}
			
			return super.onKeyDown(keyCode, event);
		}

	@Override
	public void onTabChanged(String tabId) {
		if(null!=jftcl) jftcl.onTabChanged(tabId);
		
	}

	private void addTabBtn(int index){
		if(index>=hubs.length) return;
		View view = LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
		ImageView img = (ImageView)view.findViewById(R.id.img_tab);
		img.setImageResource(hubs[index].icon);
		String title=hubs[index].title;
		TextView tv = (TextView)view.findViewById(R.id.tv_tab);
		tv.setTextColor(getResources().getColorStateList(R.color.selector_tab_textcolor_orange));
		tv.setText(title);
		
		mTabHost.addTab(
				mTabHost.newTabSpec(title)
    			.setIndicator(view)
//    			.setIndicator("",getResources().getDrawable(ICONS[index]))
    			.setContent(new DummiContiFac(getBaseContext())));
		
	}
	public class DummiContiFac implements TabContentFactory{
    	Context dcContext;
    	public DummiContiFac(Context context){
    		dcContext = context;
    	}

		@Override
		public View createTabContent(String tag) {
			return new View(dcContext);
		}
    	
    }
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// Highlight the selected item, update the title, and close the
				// drawer
				mDrawerList.setItemChecked(position, true);// delete?
				// setTitle(mPlanetTitles[position]);//TODO
				mDrawer.closeDrawer(mDrawerList);

				MyGate.goCategory(this,
//						(Category) parent.getItemAtPosition(position));
						categoryList.get(position));
	}

	@Override
	protected void onResume() {
		MobclickAgent.onResume(this);
		super.onResume();
	}
	@Override
	protected void onPause() {
		MobclickAgent.onPause(this);
		super.onPause();
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0==AR_GOGEO&&arg1==RESULT_OK){
			mTabHost.setCurrentTab(1);
		}
	}
}

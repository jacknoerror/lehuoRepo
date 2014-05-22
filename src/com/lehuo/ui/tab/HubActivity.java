package com.lehuo.ui.tab;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.lehuo.R;
import com.lehuo.data.Const;
import com.lehuo.data.MyData;
import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.category.GetAllReq;
import com.lehuo.net.action.category.GetCatRcv;
import com.lehuo.net.action.goods.GetTbarReq;
import com.lehuo.ui.MyGate;
import com.lehuo.ui.adapter.MyPagerAdapater;
import com.lehuo.ui.custom.JackFragmentTabChangedHelper;
import com.lehuo.ui.custom.JackFragmentTabChangedHelper.TabPack;
import com.lehuo.ui.tab.deliver.TabFragDeliver;
import com.lehuo.ui.tab.main.TabFragMain;
import com.lehuo.ui.tab.more.TabFragMore;
import com.lehuo.ui.tab.my.TabFragMy;
import com.lehuo.ui.tab.order.TabFragOrder;
import com.lehuo.util.JackUtils;
import com.lehuo.util.TestDataTracker;
import com.lehuo.vo.Category;
import com.lehuo.vo.TongData;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_hub);
		
		categoryList = MyData.data().getAllCate();
		mDrawer = (DrawerLayout) this.findViewById(R.id.drawer_main);
		mDrawer.setDrawerShadow(R.drawable.shadow, GravityCompat.START);
		MyData.data().setDrawer(mDrawer);
		
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
//		ActionBuilder.getInstance().request(actReq2, actrcv);
		TestDataTracker.simulateConnection(actrcv, actReq2.getApiName());//TODO delete this
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
	
	private void initListView() {
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

}

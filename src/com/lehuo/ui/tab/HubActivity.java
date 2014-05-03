package com.lehuo.ui.tab;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.ui.custom.JackFragmentTabChangedHelper;
import com.lehuo.ui.custom.JackFragmentTabChangedHelper.TabPack;
import com.lehuo.ui.tab.deliver.TabFragDeliver;
import com.lehuo.ui.tab.main.TabFragMain;
import com.lehuo.ui.tab.more.TabFragMore;
import com.lehuo.ui.tab.my.TabFragMy;
import com.lehuo.ui.tab.order.TabFragOrder;
import com.lehuo.util.JackUtils;

public class HubActivity extends FragmentActivity implements OnTabChangeListener{
	
	final TabPack[] hubs = new TabPack[]{
			new TabPack(R.drawable.selector_tab_home, "首页1", TabFragMain.class),
			new TabPack(R.drawable.selector_tab_order, "首页2", TabFragOrder.class),
			new TabPack(R.drawable.selector_tab_track, "首页3", TabFragDeliver.class),
			new TabPack(R.drawable.selector_tab_center, "首页4", TabFragMy.class),
			new TabPack(R.drawable.selector_tab_more, "首页5", TabFragMore.class)
	};
	
	private TabHost mTabHost;
	JackFragmentTabChangedHelper jftcl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_hub);
		
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
		mTabHost.setCurrentTab(0);
		
		
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
}

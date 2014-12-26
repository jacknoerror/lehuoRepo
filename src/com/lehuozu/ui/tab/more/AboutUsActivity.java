package com.lehuozu.ui.tab.more;

import android.webkit.WebView;

import com.lehuozu.R;
import com.lehuozu.ui.MyTitleActivity;

public class AboutUsActivity extends MyTitleActivity {

	@Override
	public int getLayoutRid() {
		return R.layout.activity_aboutus;
	}

	@Override
	public void initView() {
		titleManager.setTitleName("关于我们");
		titleManager.initTitleBack();

		WebView wv = (WebView) findViewById(R.id.wv_about);
		wv.loadDataWithBaseURL("file://", getIntent().getStringExtra("htmlcontent"),"text/html", "UTF-8", "about:blank");
		
	}

}

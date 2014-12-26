package com.lehuozu.ui.product;

import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.lehuozu.R;
import com.lehuozu.data.Const;
import com.lehuozu.ui.MyTitleActivity;
import com.lehuozu.util.JackImageLoader;

public class LongImgActivity extends MyTitleActivity {

	@Override
	public int getLayoutRid() {
		return R.layout.activity_longimg;
	}

	@Override
	public void initView() {
		titleManager.setTitleName("ÕºŒƒœÍ«È");
		titleManager.initTitleBack();
		
		ImageView img = (ImageView) findViewById(R.id.img_longimg);
		JackImageLoader.justSetMeImage(getIntent().getStringExtra("longimg"), img);
//		LayoutParams layoutParams = img.getLayoutParams();
//		layoutParams.width = (int) Const.SCREEN_WIDTH;
//		img.setLayoutParams(layoutParams);
//		img.setScaleType(ScaleType.CENTER_CROP);
	}

}

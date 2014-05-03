package com.lehuo.ui.product;

import android.util.Log;
import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.goods.GetProductListReq;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.ui.custom.GoodsListView;
import com.lehuo.vo.Category;

public class ProductListActivity extends MyTitleActivity implements
		OnCheckedChangeListener {
	final String[] TITLES = new String[] { "价格", "销量", "推荐", "评分" };
	// final String[] SORT = new String[]{};//price,sales,recommed

	Category mCategory;

	SparseArray<GoodsListView> jlvMap;
	GoodsListView currentJlv;
	FrameLayout frameContainer;
	RadioGroup rGroup;
	RadioButton[] rBtns;

	@Override
	public int getLayoutRid() {
		return R.layout.activity_productlist;
	}

	@Override
	public void initView() {
		mCategory = MyData.data().fetchCategory();
		titleManager.setTitleName(mCategory.getCat_name());
		// TODO backbtn,shopcart

		initBtnsOfTabs();
	}

	// text.setTextColor(resources.getColorStateList(R.color.selector_tab_textcolor_black
	// ));
	private void initBtnsOfTabs() {
		jlvMap = new SparseArray<GoodsListView>();
		frameContainer = (FrameLayout)this.findViewById(R.id.frame_lists);
		rBtns = new RadioButton[4];
		rGroup = (RadioGroup) this.findViewById(R.id.group_category);
		rBtns[0] = (RadioButton) this.findViewById(R.id.radio1);
		rBtns[1] = (RadioButton) this.findViewById(R.id.radio2);
		rBtns[2] = (RadioButton) this.findViewById(R.id.radio3);
		rBtns[3] = (RadioButton) this.findViewById(R.id.radio4);
//			rBtns[i].setId(ListItemImpl.ITEMTYPE_PRODUCT + i);//setId rbtn
		TextView tempEmpty = new TextView(this);
		frameContainer.addView(tempEmpty);
		for (int i = 0; i < rBtns.length; i++) {
			rBtns[i].setTextColor(getResources().getColorStateList(R.color.selector_tab_textcolor_black));
			GoodsListView jlv = new GoodsListView(this);
			jlv.setEmptyView(tempEmpty);
			jlvMap.put(rBtns[i].getId(), jlv);
			jlv.setOnGetPageListener(new GoodsListView.OnGetPageListener() {

				@Override
				public void page(GoodsListView qListView, int pageNo) {

					ActionBuilder.getInstance().request(
							new GetProductListReq(10, pageNo, mCategory
									.getCat_id(), "", "ASC"), qListView);//TODO sort

				}
			});
		}
		rGroup.setOnCheckedChangeListener(this);
		rGroup.check(rBtns[0].getId());
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		Log.i(TAG, "checkId:"+checkedId);
		if(null!=currentJlv)frameContainer.removeView(currentJlv);// TODO tobe test
		GoodsListView view = jlvMap.get(checkedId);
		frameContainer.addView(view);
		currentJlv = (GoodsListView)view;
		if(!currentJlv.isSetup()) currentJlv.setup();//0422
	}

}

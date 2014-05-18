package com.lehuo.ui.product;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.goods.GetProductListReq;
import com.lehuo.ui.MyGate;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.ui.TitleManager;
import com.lehuo.ui.custom.list.ListItemImpl.Type;
import com.lehuo.ui.custom.list.MyScrollPageListView;
import com.lehuo.vo.Category;
import com.lehuo.vo.Product;

public class ProductListActivity extends MyTitleActivity implements
		OnCheckedChangeListener ,View.OnClickListener{
	final String[] TITLES = new String[] { "价格", "销量", "推荐", "评分" };

	Category mCategory;

	SparseArray<MyScrollPageListView> jlvMap;
	MyScrollPageListView currentJlv;
	FrameLayout frameContainer;
	RadioGroup rGroup;
	RadioButton[] rBtns;

	@Override
	protected void onResume() {
		super.onResume();
		titleManager.updateCart();
	}

	@Override
	public int getLayoutRid() {
		return R.layout.activity_productlist;
	}

	@Override
	public void initView() {
		mCategory = MyData.data().fetchCategory();
		titleManager.setTitleName(mCategory.getCat_name());
		titleManager.initTitleBack();

		initBtnsOfTabs();
	}

	// text.setTextColor(resources.getColorStateList(R.color.selector_tab_textcolor_black
	// ));
	private void initBtnsOfTabs() {
		jlvMap = new SparseArray<MyScrollPageListView>();
		frameContainer = (FrameLayout) this.findViewById(R.id.frame_lists);
		rBtns = new RadioButton[4];
		rGroup = (RadioGroup) this.findViewById(R.id.group_category);
		rBtns[0] = (RadioButton) this.findViewById(R.id.radio1);
		rBtns[1] = (RadioButton) this.findViewById(R.id.radio2);
		rBtns[2] = (RadioButton) this.findViewById(R.id.radio3);
		rBtns[3] = (RadioButton) this.findViewById(R.id.radio4);
		TextView tempEmpty = (TextView)this.findViewById(R.id.empty_tv);
		tempEmpty.setText("暂无产品");
		for (int i = 0; i < rBtns.length; i++) {
			rBtns[i].setTextColor(getResources().getColorStateList(
					R.color.selector_tab_textcolor_green));
			rBtns[i].setText(TITLES[i]);
//			GoodsListView jlv = new GoodsListView(this);
			MyScrollPageListView jlv = new MyScrollPageListView(this,Type.GOODS);
			jlv.setEmptyView(tempEmpty);
			jlvMap.put(rBtns[i].getId(), jlv);
			jlv.setTag(this);// 把index设置进去在监听中用
//			jlv.setTag(i);// 把index设置进去在监听中用
			jlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View arg1,
						int position, long arg3) {
					Product p =(Product) (parent.getAdapter()).getItem(position);
					MyGate.GoProduct(ProductListActivity.this, p);
					
				}
				
			});
			jlv.setOnGetPageListener(new MyScrollPageListView.OnGetPageListener() {

				@Override
				public void page(MyScrollPageListView qListView, int pageNo) {
					String sort = "price";
					String sc = "";
					int index = rGroup.getCheckedRadioButtonId();
//					int index = (Integer) qListView.getTag();
					switch (index) {
					case R.id.radio1:
						sort = GetProductListReq.SORT_PRICE;
						sc = "ASC";
						break;
					case R.id.radio2:
						sort = GetProductListReq.SORT_SALES;
						break;
					case R.id.radio3:
						sort = GetProductListReq.SORT_RECOMMED;
						break;
					case R.id.radio4:
						sort = GetProductListReq.SORT_RECOMMED;
						break;

					default:
						break;
					}
					ActionBuilder.getInstance().request(
							new GetProductListReq(10, pageNo, mCategory
									.getCat_id(), sort, sc), qListView);

				}
			});
		}
		rGroup.setOnCheckedChangeListener(this);
		rGroup.check(rBtns[0].getId());
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		Log.i(TAG, "checkId:" + checkedId);
		if (null != currentJlv)
			frameContainer.removeView(currentJlv);
		MyScrollPageListView msp = jlvMap.get(checkedId);
		frameContainer.addView(msp);
		currentJlv = msp;
		if (!currentJlv.isSetup())
			currentJlv.setup();// 0422
	}

	@Override
	public void onClick(View v) {
		NetStrategies.addToCart(this,
				(Integer) v.getTag(), titleManager);
	}

}

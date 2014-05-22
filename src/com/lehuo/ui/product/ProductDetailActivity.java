package com.lehuo.ui.product;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.goods.GetProductDetailReq;
import com.lehuo.ui.MyGate;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.ui.adapter.MyPagerAdapater;
import com.lehuo.ui.custom.JackRadios;
import com.lehuo.util.JackImageLoader;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.LehuoPic;
import com.lehuo.vo.Product;
import com.lehuo.vo.User;

public class ProductDetailActivity extends MyTitleActivity implements OnPageChangeListener,ActionPhpReceiverImpl {

	User mUser;
	Product mProduct;
	List<ImageView> spotList;
	
	FrameLayout frameContainer;
	ViewPager mViewPager;
	List<ImageView> imgList;
	
	JackRadios jRa;
	
	TextView tv_pn,tv_pd,tv_pold,tv_pnew;
	private String[] pathArrs;
	
	@Override
	protected void onResume() {
		super.onResume();
		if(null!=titleManager) titleManager.updateCart();
	}
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_productdetail;
	}

	@Override
	public void initView() {
		mUser = MyData.data().getMe();
		mProduct = MyData.data().fetchProduct();
		if(null==mProduct||null==mUser) return;
		titleManager.setTitleName("宝贝详情");
		titleManager.initTitleBack();
//		titleManager.updateCart(); // onresume
		
		Button btn_buy;
		tv_pn = (TextView)this.findViewById(R.id.tv_productdetail_productname);
		tv_pd = (TextView)this.findViewById(R.id.tv_productdetail_productdesc);
		tv_pold = (TextView)this.findViewById(R.id.tv_productdetail_priceold);
		tv_pnew = (TextView)this.findViewById(R.id.tv_productdetail_pricenew);
		btn_buy = (Button)this.findViewById(R.id.btn_productdetail_buynow);
		
		updateProductUI();
		
		btn_buy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int user_id = mUser.getUser_id();
				int goods_id = mProduct.getGoods_id();
				if(null==mUser||user_id==0) return;//
				NetStrategies.addToCart(ProductDetailActivity.this, goods_id, titleManager);
			}

			
		});
		ActionPhpRequestImpl req = new GetProductDetailReq(mProduct.getGoods_id());
		ActionBuilder.getInstance().request(req, this);
		
	}

	/**
	 * @param tv_pn
	 * @param tv_pd
	 * @param tv_pold
	 * @param tv_pnew
	 */
	public void updateProductUI() {
		tv_pn.setText(mProduct.getGoods_name());
		tv_pd.setText(mProduct.getGoods_brief());
		tv_pold.setText("原价：￥"+mProduct.getMarket_price());
		JackUtils.textpaint_deleteLine(tv_pold);
		tv_pnew.setText("现价："+mProduct.getRealPriceStr());
	}

	/**
	 * 
	 */
	private void initJackRadios() {
		frameContainer = (FrameLayout) this.findViewById(R.id.frame_productdetail);
		RadioGroup rGroup = (RadioGroup) this.findViewById(R.id.radiogroup_productdetail);
		jRa = new JackRadios(frameContainer,rGroup);
	}


	/**
	 * 
	 */
	private void initPagers() {
		mViewPager = (ViewPager)this.findViewById(R.id.viewpager_);
		List<View> viewList = new ArrayList<View>();
		imgList = new ArrayList<ImageView>();
		spotList = new ArrayList<ImageView>();
		LinearLayout spotLayout = (LinearLayout) this
				.findViewById(R.id.layout_spots);
		//数据
		JSONArray jar = mProduct.getGallery();
		pathArrs = new String[jar.length()];
		for (int i = 0; i < jar.length(); i++) {//TODO test data
			LehuoPic gp=null;
			try {
				gp= new LehuoPic(jar.getJSONObject(i));
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
			
			LinearLayout layout = new LinearLayout(this);
			layout.setOnClickListener(new View.OnClickListener() {//按图片响应 
				
				@Override
				public void onClick(View v) {
					int index = mViewPager.getCurrentItem();
					if(null==imgList||imgList.size()<=index) return;
					MyGate.goViewPager(ProductDetailActivity.this, pathArrs, index);
					
				}
			});
			ImageView img = new ImageView(this);
			img.setScaleType(ScaleType.CENTER_CROP);
//			img.setImageResource(R.drawable.ic_launcher);
			String img_url = gp.getImg_url();
			pathArrs[i] = img_url;
			JackImageLoader.justSetMeImage(img_url, img);//
			layout.addView(img);
			viewList.add(layout);
			imgList.add(img);
			img.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT));
			// spot
			FrameLayout layoutWithImg = new FrameLayout(this);
			layoutWithImg.setPadding(5, 5, 5, 5);
			ImageView im = new ImageView(this);// (ImageView)LayoutInflater.from(this).inflate(R.layout.image_spot,
														// null);//@drawable/spot_selector
			im.setImageResource(R.drawable.spot_selector);
			layoutWithImg.addView(im);
			spotLayout.addView(layoutWithImg);
			spotList.add(im);
		}
		
		setSpotSelected(0);
		if (spotList.size() <= 1)
			spotLayout.setVisibility(View.INVISIBLE);
		mViewPager.setAdapter(new MyPagerAdapater(viewList));
		mViewPager.setOnPageChangeListener(this);
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
	public boolean response(String result) throws JSONException {
		JSONObject job = NetStrategies.getResultObj(result);
		if(null!=job){
			mProduct.initJackJson(job);
			
			updateProductUI(	);
			
			initPagers();
			initJackRadios();
		}
		return false;
	}

	@Override
	public Context getReceiverContext() {
		return this;
	}
}

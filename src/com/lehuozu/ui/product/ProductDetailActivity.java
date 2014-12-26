package com.lehuozu.ui.product;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lehuozu.R;
import com.lehuozu.data.MyData;
import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.BareReceiver;
import com.lehuozu.net.action.goods.GetLongImgRequest;
import com.lehuozu.net.action.goods.GetProductDetailReq;
import com.lehuozu.ui.MyGate;
import com.lehuozu.ui.MyTitleActivity;
import com.lehuozu.ui.adapter.MyPagerAdapater;
import com.lehuozu.ui.custom.JackRadios;
import com.lehuozu.ui.login.LoginActivity;
import com.lehuozu.util.JackImageLoader;
import com.lehuozu.util.JackUtils;
import com.lehuozu.vo.LehuoPic;
import com.lehuozu.vo.Product;
import com.lehuozu.vo.User;

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
	private TextView tv_pd_pull;
	
	@Override
	protected void onResume() {
		super.onResume();
		slideDone = false;
		if(null!=titleManager) titleManager.updateCart();
	}
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_productdetail;
	}
	boolean slideDone;

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
		tv_pd_pull = (TextView)this.findViewById(R.id.tv_pd_pull);
		btn_buy = (Button)this.findViewById(R.id.btn_productdetail_buynow);
		
		//1222
		tv_pd_pull.setOnTouchListener(new OnTouchListener() {
			
			private float ox;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					ox = event.getX();
					break;
				case MotionEvent.ACTION_MOVE:
					if(event.getX()-ox<-100&&!slideDone){
						Intent intent = new Intent();
						intent.setClass(ProductDetailActivity.this, LongImgActivity.class);
						intent.putExtra("longimg", longimg);
						startActivity(intent);
//						overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
						slideDone = true;
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
		
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

	private String longimg;
	private void doLongimgRequest() {
		if(null==mProduct) return;
		ActionPhpRequestImpl actReq = new GetLongImgRequest(mProduct.getGoods_id());
		ActionPhpReceiverImpl actRcv = new BareReceiver(null){//{"result":true,"data":[{"good_long_img":"http:\/\/ilehuozu.com\/images\/201412\/1419216721958273034_800X5876.jpg"}]}


			@Override
			public boolean response(String result) throws JSONException {
				boolean response = super.response(result);
				try{
					
				if(!response&&result.contains("good_long_img")){
					JSONObject job0 = new JSONObject(result);
					JSONArray jar = job0.optJSONArray("data");
					longimg = jar.getJSONObject(0).optString("good_long_img");
					if(mViewPager.getAdapter().getCount()==1) tv_pd_pull.setVisibility(View.VISIBLE);
					
				}
				}catch(Exception e){
				}
				return response;
			}
		};
		ActionBuilder.getInstance().request(actReq, actRcv);
	}

	/**
	 * @param tv_pn
	 * @param tv_pd
	 * @param tv_pold
	 * @param tv_pnew
	 */
	public void updateProductUI() {
		tv_pn.setText(mProduct.getGoods_name());
		String goods_brief = mProduct.getGoods_brief();
		if(null!=goods_brief&&!goods_brief.isEmpty()){
			tv_pd.setText(goods_brief);
			tv_pd.setVisibility(View.VISIBLE);
		}
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
		if(longimg!=null&&!longimg.isEmpty()){
			if(arg0==mViewPager.getAdapter().getCount()-1){
				tv_pd_pull.setVisibility(View.VISIBLE);
			}else{
				tv_pd_pull.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public boolean response(String result) throws JSONException {
		JSONObject job = NetStrategies.getResultObj(result);
		if(null!=job){
			mProduct.initJackJson(job);
			
			updateProductUI(	);
			
			initPagers();
			initJackRadios();
			doLongimgRequest();
		}
		return false;
	}

	@Override
	public Context getReceiverContext() {
		return this;
	}
}

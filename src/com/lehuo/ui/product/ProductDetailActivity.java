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
import com.lehuo.net.action.JackShowToastReceiver;
import com.lehuo.net.action.NextActionRcv;
import com.lehuo.net.action.BareReceiver;
import com.lehuo.net.action.goods.GetProductDetailReq;
import com.lehuo.net.action.order.AddCartReq;
import com.lehuo.net.action.order.UpdateCartReq;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.ui.adapter.MyPagerAdapater;
import com.lehuo.ui.custom.JackRadios;
import com.lehuo.util.JackImageLoader;
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
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_productdetail;
	}

	@Override
	public void initView() {
		mUser = MyData.data().getMe();
		mProduct = MyData.data().fetchProduct();
		if(null==mProduct) return;
		titleManager.setTitleName(mProduct.getGoods_name());
		titleManager.updateCart();
		
		TextView tv_pn,tv_pd,tv_pold,tv_pnew;
		Button btn_buy;
		tv_pn = (TextView)this.findViewById(R.id.tv_productdetail_productname);
		tv_pd = (TextView)this.findViewById(R.id.tv_productdetail_productdesc);
		tv_pold = (TextView)this.findViewById(R.id.tv_productdetail_priceold);
		tv_pnew = (TextView)this.findViewById(R.id.tv_productdetail_pricenew);
		btn_buy = (Button)this.findViewById(R.id.btn_productdetail_buynow);
		
		tv_pn.setText(mProduct.getGoods_name());
		tv_pd.setText(mProduct.getGoods_brief());
		tv_pold.setText(mProduct.getMarket_price());
		tv_pnew.setText(mProduct.getPromote_price());
		
		btn_buy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int user_id = mUser.getUser_id();
				int goods_id = mProduct.getGoods_id();
				if(null==mUser||user_id==0) return;//
				//  添加到购物车
				ActionPhpRequestImpl req = null;
				ActionPhpReceiverImpl rcv= null;
				ActionPhpRequestImpl nReq = null;
				ActionPhpReceiverImpl nRcv= null;
				req = new AddCartReq(
						new AddCartReq.CartGoods(goods_id), 
						user_id);
				rcv = new JackShowToastReceiver(ProductDetailActivity.this);
				nReq = new UpdateCartReq(user_id);
				nRcv = new BareReceiver(ProductDetailActivity.this){
					@Override
					public boolean response(String result) throws JSONException {
						
						if(!super.response(result)){
							MyData.data().setCartCount(resultJob.optInt(RESULT_OBJ));//没错就是这样
							titleManager.updateCart();
							return false;
						}
						return true;
					};
				};
				rcv = new NextActionRcv(rcv, nReq, nRcv);
				ActionBuilder.getInstance().request(req, rcv);
			}
		});
		ActionPhpRequestImpl req = new GetProductDetailReq(mProduct.getGoods_id());
		ActionBuilder.getInstance().request(req, this);
		
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
		for (int i = 0; i < jar.length(); i++) {//TODO test data
			LehuoPic gp=null;
			try {
				gp= new LehuoPic(jar.getJSONObject(i));
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
			
			LinearLayout layout = new LinearLayout(this);
			ImageView img = new ImageView(this);
			img.setScaleType(ScaleType.CENTER);
//			img.setImageResource(R.drawable.ic_launcher);
			JackImageLoader.justSetMeImage(gp.getImg_url(), img);//
			layout.addView(img);
			viewList.add(layout);
			imgList.add(img);
			
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

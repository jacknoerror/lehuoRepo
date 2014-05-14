/**
 * 
 */
package com.lehuo.ui.custom;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.JackDefJobRcv;
import com.lehuo.net.action.JackShowToastReceiver;
import com.lehuo.net.action.brand.GetBrandRcv;
import com.lehuo.net.action.brand.GetBrandReq;
import com.lehuo.net.action.goods.GetProductAttrRcv;
import com.lehuo.net.action.goods.GetProductAttrReq;
import com.lehuo.net.action.goods.GetProductCommentReq;
import com.lehuo.vo.Product;
import com.lehuo.vo.ProductComment;

/**
 * @author taotao
 *
 */
public class JackRadios implements OnCheckedChangeListener {
	protected final String TAG = getClass().getSimpleName();
	
	protected Context context;
	FrameLayout frameContainer;
	RadioButton[] rBtns;
	RadioGroup rGroup;
	View currentView;
	
	SparseArray<View> viewMap;
	
	public JackRadios(FrameLayout frameContainer, RadioGroup rGroup2){
		this.frameContainer = frameContainer;
		context = frameContainer.getContext();
		rGroup = rGroup2;
		init();
	}
	protected int[] getBtnRids(){
		return new int[]{R.id.radio1,R.id.radio2,R.id.radio3,R.id.radio4};
	}
	
	private void init() {
		if(null==frameContainer) return;
		final int[] ids = getBtnRids();
		rBtns = new RadioButton[ids.length];
		viewMap = new SparseArray<View>();
		
		for (int i = 0; i < rBtns.length; i++) {
			/*View view = initView(i);
			if(null!=view) jlvMap.put(getBtnId(i), view);*/
			rBtns[i] = (RadioButton)rGroup.findViewById(ids[i]);
			editRadioBtn(rBtns[i], i);
		}
		
		rGroup.setOnCheckedChangeListener(this);
		rGroup.check(rBtns[0].getId());
	}
	/**
	 * @param radioButton TODO
	 * @param i
	 * @throws NotFoundException
	 */
	protected void editRadioBtn(RadioButton radioButton, int i) throws NotFoundException {
		radioButton.setTextColor(context.getResources().getColorStateList(
				R.color.selector_tab_textcolor_green));
	}

	protected View initView(int i) {
		Product p = MyData.data().fetchProduct();
		View view = null;
		ActionPhpRequestImpl actReq=null;
		ActionPhpReceiverImpl actRcv=null;
		switch (i) {
		case R.id.radio1:
			WebView mWebView = new WebView(context);
			mWebView.getSettings().setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
			mWebView.loadData(p.getGoods_desc(), "text/html; charset=UTF-8", null);//这种写法可以正确解码 TODO
			WebSettings ws = mWebView.getSettings();
//	        ws.setJavaScriptEnabled(true);
	        //设置可以支持缩放   
	        ws.setSupportZoom(true);   
	        //设置默认缩放方式尺寸是far   
	        ws.setDefaultZoom(WebSettings.ZoomDensity.FAR);  
	        //设置出现缩放工具   
	        ws.setBuiltInZoomControls(true);
	     // 让网页自适应屏幕宽度
	        ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			view = mWebView;
			break;
		case R.id.radio2:
			LinearLayout mLayout = new LinearLayout(context);
			mLayout.setOrientation(LinearLayout.VERTICAL);
			actReq = new GetProductAttrReq(p.getGoods_id());
			actRcv = new GetProductAttrRcv(context,mLayout);
			ActionBuilder.getInstance().request(actReq, actRcv);
			view = mLayout;
			break;
		case R.id.radio3:
			GridView mGrid = new GridView(context);
			actReq = new GetBrandReq(p.getBrand_id());
			actRcv = new GetBrandRcv(context,mGrid);
			ActionBuilder.getInstance().request(actReq, actRcv);
			view = mGrid;
			break;
		case R.id.radio4:
			final LinearLayout fml = new LinearLayout(context);
			fml.setOrientation(LinearLayout.VERTICAL);
			actReq = new GetProductCommentReq(p.getGoods_id(), 1);
			actRcv = new JackDefJobRcv(context) {
				
				@Override
				public boolean respJob(JSONObject job) throws JSONException {
					if(null!=job&&job.has("comments")){
						Iterator<String> it = (job=job.getJSONObject("comments")).keys();
						while(it.hasNext()){
							String name = it.next();
							if(!name.equals("next")){
								ProductComment pc = new ProductComment(job.getJSONObject(name));
								View cv = LayoutInflater.from(context).inflate(R.layout.item_comment_goods, null);
								TextView tv_name = (TextView)cv.findViewById(R.id.tv_item_commentgoods_username);
								LinearLayout layout_star = (LinearLayout)cv.findViewById(R.id.layout_item_commentgoods_stars);
								TextView tv_comment = (TextView)cv.findViewById(R.id.tv_item_commentgoods_comment);
								TextView tv_date = (TextView)cv.findViewById(R.id.tv_item_commentgoods_date);
								tv_name.setText(pc.getUsername());
								tv_comment.setText(pc.getContent());
								tv_date.setText(pc.getAdd_time());
								for(int i=0;i<pc.getRank();i++){
									ImageView star = new ImageView(context);
									star.setImageResource(android.R.drawable.star_big_on);
									layout_star.addView(star);
								}
								fml.addView(cv);
								fml.addView(NetStrategies.getSimpleDivider(context));
								
							}
						}
						
						return false;
					}
					return true;
				}
			};
			ActionBuilder.getInstance().request(actReq, actRcv);
			view = fml;
			break;
		default:
			break;
		}
		if(null!=view)viewMap.put(i, view);
		return view;
	}
	
	private int getBtnId(int i){//userless?
		if(rBtns.length<=i||i<0)return 0;
		return rBtns[i].getId();
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		Log.i(TAG, "checkId:"+checkedId);
		if(null!=currentView)frameContainer.removeView(currentView);// TODO tobe test
		View view = viewMap.get(checkedId);
		if(null==view) {
			view = initView(checkedId);
			viewMap.put(checkedId, view);//0514
		}
		frameContainer.addView(view);
		currentView = view;
		onSelected();
	}

	/**
	 * 
	 */
	protected void onSelected() {
//		if(!currentView.isSetup()) currentView.setup();//0422
	}
}

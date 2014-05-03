/**
 * 
 */
package com.lehuo.ui.custom;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.brand.GetBrandRcv;
import com.lehuo.net.action.brand.GetBrandReq;
import com.lehuo.net.action.goods.GetProductAttrRcv;
import com.lehuo.net.action.goods.GetProductAttrReq;
import com.lehuo.net.action.goods.GetProductCommentReq;
import com.lehuo.vo.Product;

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
	
	SparseArray<View> jlvMap;
	
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
		jlvMap = new SparseArray<View>();
		
		for (int i = 0; i < rBtns.length; i++) {
			/*View view = initView(i);
			if(null!=view) jlvMap.put(getBtnId(i), view);*/
			rBtns[i] = (RadioButton)rGroup.findViewById(ids[0]);
		}
		
		rGroup.setOnCheckedChangeListener(this);
		rGroup.check(rBtns[0].getId());
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
//			actReq = new GetProductCommentReq(p.getGoods_id(), page);
			break;
		default:
			break;
		}
		if(null!=view)jlvMap.put(i, view);
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
		View view = jlvMap.get(checkedId);
		if(null==view) view = initView(checkedId);
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

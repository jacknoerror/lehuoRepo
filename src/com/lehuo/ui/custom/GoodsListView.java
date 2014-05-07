package com.lehuo.ui.custom;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.entity.json.JsonImport;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.ui.MyGate;
import com.lehuo.util.JackImageLoader;
import com.lehuo.vo.Product;

public class GoodsListView extends ListView implements
		AbsListView.OnScrollListener, ActionPhpReceiverImpl,AdapterView.OnItemClickListener {

	public interface OnGetPageListener{
		public void page(GoodsListView qListView,int pageNo);
	}
	
	List<Product> everythingList;
	GoodsListAdapter omgAdapter;
	
	GoodsPage currentPage;
	OnGetPageListener gpListener;
	
	View moreView;

	int requestingPage;
	boolean isSetup;
	
	public GoodsListView(Context context) {
		super(context);
		init();
	}

	public GoodsListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public GoodsListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		omgAdapter = new GoodsListAdapter();
		
		setOnScrollListener(this); 
		setOnItemClickListener(this);
		setFastScrollEnabled(true);
		everythingList = new ArrayList<Product>();
	}

	/**
	 * ��������ʹ�ÿؼ�ǰ���ô˷���
	 */
	public void setup(){
		isSetup=true;
		
		if(gpListener!=null){//��ʼ������
			gpListener.page(this, 1);
			if(everythingList!=null) everythingList.clear();//reset 
			requestingPage=1;//0227
		}
		addMoreView();//��ʼʱ���� ������Ҫʱ���� �� ע��λ�ã�С�ı���
		
	}
	
	public final boolean isSetup() {
		return isSetup;
	}

	private void nextPage() {
		int requestpage = currentPage.curPageNo+1;
		if(null==gpListener||requestingPage==requestpage||!currentPage.hasNext) return; 
		requestingPage = requestpage;
		gpListener.page(this,requestpage);
	}
	
	/**
	 * ��������Ĳ�������˴�
	 * @param gpListener
	 */
	public void setOnGetPageListener(OnGetPageListener gpListener){
		this.gpListener = gpListener;
	}
	
	public void updateList(List<Product> list){
		if(null==getAdapter() &&null!=omgAdapter) setAdapter(omgAdapter);
		//���ڴ˴����ڸ������ݵ�ʱ��������adapter
		if(omgAdapter==null){
		}else{
			List<Product> lvList = ((GoodsListAdapter)omgAdapter).getList();
			if(lvList!=list){
				lvList.clear();
				lvList.addAll(list);
			}
		}
		omgAdapter.notifyDataSetChanged();
	}
	private void addMoreView() {
		//footer   //0224 ����if���ֹ������ʱmoreView��bug  //0226 extract handle visibility
		if(null==moreView){
			moreView = LayoutInflater.from(getContext()).inflate(R.layout.footer_more, null);
			addFooterView(moreView);
		}else{
			moreView.setVisibility(View.VISIBLE);
		}
	}

	private void removeMoreView() {
		if(null!=moreView){
			removeFooterView(moreView);
			moreView=null;
		}
	}
	@Override
	public boolean response(String result) throws JSONException {
		currentPage = new GoodsPage(new JSONObject(result));
		if(currentPage.resultSign){
			currentPage.curPageNo=requestingPage;
			everythingList.addAll(currentPage.getDataList());
			updateList(everythingList);
		}
		
		if(!currentPage.hasNext){
			removeMoreView();
//			Log.i(TAG, "no Next");
		}else{
		}
		return false;
	}

	@Override
	public Context getReceiverContext() {
		return getContext();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (omgAdapter == null || omgAdapter.getCount() == 0)
			return;
		int count = omgAdapter.getCount();
		int lastItem = firstVisibleItem + visibleItemCount;
		if (lastItem == count) {
			nextPage(); // ���ظ������ݣ��������ʹ���첽����
		}
		// Log.i(TAG, "last::"+lastItem+"___count::"+count);
	}

	class GoodsPage extends JsonImport {
		public int curPageNo;

		boolean resultSign;
		boolean hasNext;
		JSONArray infoArr;

		public GoodsPage() {
			super();
		}

		public GoodsPage(JSONObject job) {
			super(job);
		}

		@Override
		public void initJackJson(JSONObject job) throws JSONException {
			if (job.has("result"))
				resultSign = job.getBoolean("result");
			if (job.has("next"))
				hasNext = job.getBoolean("next");
			if (job.has("data"))
				infoArr = job.getJSONArray("data");

		}
		
		List<Product> LiiList;
		public  List<Product> getDataList(){
			if(LiiList!=null) return LiiList;//������ˣ�ֱ�ӷ���
			
			LiiList = new ArrayList<Product>();//
			for(int i=0;i<(null!=infoArr?infoArr.length():0);i++){
				try {
					
					Product pp = new Product();
					pp.initJackJson(infoArr.getJSONObject(i));
					LiiList.add(pp);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//
				
			}
			return LiiList;
		}
	}

	class GoodsListAdapter extends BaseAdapter {

		SparseArray<View> viewMap;
		
		public GoodsListAdapter() {
			if (everythingList == null)
				everythingList = new ArrayList<Product>();
			viewMap = new SparseArray<View>();
		}

		@Override
		public int getCount() {
			return everythingList.size();
		}

		@Override
		public Product getItem(int position) {
			return everythingList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=viewMap.get(position);
			ViewHolder holder;
			
			if(null==view){
				holder= new ViewHolder();
				view = View.inflate(getContext(), R.layout.item_seriesproduct, null);
				holder.icon = (ImageView)view.findViewById(R.id.img_sprod_icon);
				holder.tv_goodsname = (TextView)view.findViewById(R.id.tv_item_pname);
				holder.tv_goodsdesc = (TextView)view.findViewById(R.id.tv_item_pdesc);
				holder.tv_priceold = (TextView)view.findViewById(R.id.tv_item_sprod_priceold);
				holder.tv_pricenew = (TextView)view.findViewById(R.id.tv_item_sprod_pricenew);
				viewMap.put(position, view);
				view.setTag(holder);
			}else{
				holder= (ViewHolder)view.getTag();
			}
			
//			holder.setup(position);
			Product p = getItem(position);
			JackImageLoader.justSetMeImage(p.getGoods_img(), holder.icon);
			holder.tv_goodsname.setText(p.getGoods_name());
			holder.tv_goodsdesc.setText(p.getGoods_desc());
			holder.tv_priceold.setText(p.getMarket_price());//TODO delete line
			holder.tv_pricenew.setText(p.getRealPriceStr());
			
			return view;
		}
		
		public List<Product> getList(){
			return everythingList;
		}
	}
	static class ViewHolder{
		ImageView icon;
		TextView tv_goodsname,tv_goodsdesc,tv_priceold,tv_pricenew;
		Button btn;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(null==omgAdapter)return;
		Product p =omgAdapter.getItem(position);
		MyGate.GoProduct(getContext(), p);
		
	}
}

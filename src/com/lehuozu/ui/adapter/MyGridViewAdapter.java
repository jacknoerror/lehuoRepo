package com.lehuozu.ui.adapter;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lehuozu.R;
import com.lehuozu.entity.json.JsonImport;
import com.lehuozu.util.JackImageLoader;
import com.lehuozu.util.JackUtils;
import com.lehuozu.vo.Category;

/**
 * @author taotao
 *
 */
public class MyGridViewAdapter extends BaseAdapter {
	public abstract static class MyGridAbsJsonPic extends JsonImport{
		
		public MyGridAbsJsonPic() {
			super();
		}

		public MyGridAbsJsonPic(JSONObject job) {
			super(job);
		}

		public abstract String getPicUrl();
	}
	static class ViewHolder {
		ImageView prizImg;
	}

	/**
	 * 
	 */
	public List<MyGridAbsJsonPic> contentList;
	SparseArray<View> viewMap;
	private Context context;
	public int itemHeight;

	/**
	 * notice the class casts here
	 * @param context
	 * @param list
	 */
	public MyGridViewAdapter(Context context, List list) {
		this.context = context;
		this.contentList = list;
		viewMap = new SparseArray<View>();
	}

	@Override
	public int getCount() {
		return contentList.size();
	}

	@Override
	public MyGridAbsJsonPic getItem(int position) {
		return contentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = viewMap.get(position);
		MyGridViewAdapter.ViewHolder holder;

		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_grid_main, null);// ????????

			holder = new MyGridViewAdapter.ViewHolder();
			holder.prizImg = (ImageView) view
					.findViewById(R.id.img_item_grid_main);

			viewMap.put(position, view);

			view.setTag(holder);
		} else {
			view = viewMap.get(position);
			holder = (MyGridViewAdapter.ViewHolder) view.getTag();
		}

		// 哟啊不要换地方？
		MyGridAbsJsonPic itm = contentList.get(position);
		if(null!=itm){
			JackImageLoader.justSetMeImage(itm.getPicUrl(), holder.prizImg);
			holder.prizImg.setLayoutParams(new LinearLayout.LayoutParams(JackUtils.dip2px(
					context, 175), JackUtils.dip2px(context, 160)));
		}
		itemHeight = view.getMeasuredHeight();
		return view;
	}

}
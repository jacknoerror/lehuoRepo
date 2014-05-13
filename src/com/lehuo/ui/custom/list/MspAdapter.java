package com.lehuo.ui.custom.list;

import java.util.ArrayList;
import java.util.List;

import com.lehuo.MyApplication;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MspAdapter extends BaseAdapter {

	public abstract class ViewHolderImpl{
		View holderView;
		public ViewHolderImpl(){
			holderView = LayoutInflater.from(context).inflate(getLayoutId(), null);
			init();
			holderView.setTag(this);
		}
		
		public abstract void init()  ;
		public abstract void setup(int position);
		public abstract int getLayoutId();
		
		public View getHolderView(){
			return holderView;
		}
	}
	
	

	SparseArray<View> viewMap;
	List<ListItemImpl> contentList;
	protected Context context;
	
	public MspAdapter(){
		this(new ArrayList<ListItemImpl>());
	}
	public MspAdapter(List<ListItemImpl> contentList){
		this.contentList = contentList;
		viewMap = new SparseArray<View>();
		this.context = MyApplication.app();
	}
	

	@Override
	public int getCount() {
		return getList().size();
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public ListItemImpl getItem(int position) {
		return getList().get(position);//
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=viewMap.get(position);
		ViewHolderImpl holder;
		
		if(null==view){
			holder= getHolderInstance();
			view = holder.getHolderView();
			viewMap.put(position, view);
		}else{
			holder= (ViewHolderImpl)view.getTag();
		}
		
//		holder.setup(position);
		holder.setup(position);
		
		return view;
	}
	
	public List<ListItemImpl> getList(){
		if(contentList==null) contentList=new ArrayList<ListItemImpl>();
		return contentList;
	}
	
	public abstract ViewHolderImpl getHolderInstance();
	
}

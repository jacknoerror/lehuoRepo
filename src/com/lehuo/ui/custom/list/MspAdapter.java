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

public class MspAdapter extends BaseAdapter {

	public abstract class ViewHolderImpl{
		View holderView;
		public ViewHolderImpl(){
			holderView = LayoutInflater.from(context).inflate(factory.getLayoutId(), null);
			factory.init();
			holderView.setTag(this);
		}
		
		public View getHolderView(){
			return holderView;
		}
	}
	
	

	SparseArray<View> viewMap;
	List<ListItemImpl> contentList;
	Context context;
	private MspFactoryImpl factory;
	
	public MspAdapter(MspFactoryImpl factory){
		this(new ArrayList<ListItemImpl>(),factory);
	}
	public MspAdapter(List<ListItemImpl> contentList,MspFactoryImpl factory){
		this.contentList = contentList;
		viewMap = new SparseArray<View>();
		this.context = MyApplication.app();
		this.factory = factory;
		if(null==factory) throw new IllegalStateException("factory cannot be null");
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
			holder= factory.getHolderInstance();
			view = holder.getHolderView();
			viewMap.put(position, view);
		}else{
			holder= (ViewHolderImpl)view.getTag();
		}
		
//		holder.setup(position);
		factory.setup(position);
		
		return view;
	}
	
	public List<ListItemImpl> getList(){
		if(contentList==null) contentList=new ArrayList<ListItemImpl>();
		return contentList;
	}
	
}

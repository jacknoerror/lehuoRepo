package com.lehuozu.ui.custom.list;

import java.util.ArrayList;
import java.util.List;

import com.lehuozu.MyApplication;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MspAdapter extends BaseAdapter {

	/**
	 * @author tao
	 * 
	 *         ViewHolder to hold itemView in MspAdapter, do not set tag to
	 *         itemView, which is already done in another way
	 */
	public abstract class ViewHolderImpl {
		View holderView;

		public ViewHolderImpl() {
			holderView = LayoutInflater.from(getContextInAdapter()).inflate(
					getLayoutId(), null);
			init();
			holderView.setTag(this);
		}

		public abstract void init();

		public abstract void setup(int position);

		public abstract int getLayoutId();

		public View getHolderView() {
			return holderView;
		}
	}

	SparseArray<View> viewMap;
	List<ListItemImpl> contentList;
	private Context contextInAdapter;
	protected MyScrollPageListView myScrollPageListView;// 0513

	public MspAdapter() {
		this(new ArrayList<ListItemImpl>());
	}

	public MspAdapter(List<ListItemImpl> contentList) {
		this.contentList = contentList;
		viewMap = new SparseArray<View>();

	}

	protected Context getContextInAdapter() {
		Object tag = null;
		if (null != myScrollPageListView
				&& (tag = myScrollPageListView.getTag()) instanceof Context) {
			contextInAdapter = (Context) tag;
		} else {
			this.contextInAdapter = MyApplication.app();
		}
		return contextInAdapter;
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
		View view = viewMap.get(position);
		ViewHolderImpl holder;

		if (null == view) {
			holder = getHolderInstance();
			view = holder.getHolderView();
			viewMap.put(position, view);
		} else {
			holder = (ViewHolderImpl) view.getTag();
		}

		// holder.setup(position);
		holder.setup(position);

		return view;
	}

	public List<ListItemImpl> getList() {
		if (contentList == null)
			contentList = new ArrayList<ListItemImpl>();
		return contentList;
	}

	public abstract ViewHolderImpl getHolderInstance();

	public void setListView(MyScrollPageListView myScrollPageListView) {
		this.myScrollPageListView = myScrollPageListView;

	}

}

package com.lehuo.ui.tab.my;

import java.util.List;

import org.json.JSONException;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.order.GetBonusRcv;
import com.lehuo.net.action.order.GetBonusReq;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.ui.custom.JackRadios;
import com.lehuo.ui.custom.list.ListItemImpl;
import com.lehuo.ui.custom.list.MyScrollPageListView;
import com.lehuo.ui.custom.list.ListItemImpl.Type;
import com.lehuo.vo.Coupon;
import com.lehuo.vo.User;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MyCouponActivity extends MyTitleActivity {
	
	
	
	private JackRadios jRa;
	private User me;
	private GetBonusRcv getbonusrcv;

	List<ListItemImpl> aList,uList,eList;
	
	/**
	 * 
	 */
	private void initJackRadios() {
		jRa = new JackRadios((FrameLayout) this.findViewById(R.id.frame_lists),(RadioGroup) this.findViewById(R.id.group_common)){
			@Override
			protected int[] getBtnRids() {
				return new int[]{R.id.radio1,R.id.radio2,R.id.radio3};
			}
			
			@Override
			protected View initView(int i) {
				MyScrollPageListView lv = new MyScrollPageListView(MyCouponActivity.this, Type.COUPON);
				lv.setDivider(null);
				lv.setFooterDividersEnabled(false);
//				lv.setTag(MyCouponActivity.this);
				switch (i) {
				case R.id.radio1:
					lv.updateList(aList);
					lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							
						}
					});
					break;
				case R.id.radio2:
					lv.updateList(uList);
					break;
				case R.id.radio3:
					lv.updateList(eList);
					break;

				default:
					break;
				}
				return lv ;
			}
			@Override
			protected void editRadioBtn(RadioButton radioButton, int i)
					throws NotFoundException {
				super.editRadioBtn(radioButton, i);
				final String[] TIT = new String[]{"可使用","已使用","已过期"};
				radioButton.setText(TIT [i]);
			}
		};
	}

	@Override
	public int getLayoutRid() {
		return R.layout.activity_coupon;
	}

	@Override
	public void initView() {
		me = MyData.data().getMe();
		if(me==null) return;
		titleManager.setTitleName("我的优惠券");
		titleManager.initTitleBack();
		
		GetBonusReq actReq = new GetBonusReq(me.getUser_id());
		getbonusrcv = new GetBonusRcv(this){
			@Override
			public boolean response(String result) throws JSONException {
				boolean response = super.response(result);
				if(!response){
					aList = super.availableBonusList;
					uList = super.usedBonusList;
					eList = super.expiredBonusList;
					initJackRadios();
				}
				return response;
			}
		};
		ActionBuilder.getInstance().request(actReq, getbonusrcv );
	}
	
}

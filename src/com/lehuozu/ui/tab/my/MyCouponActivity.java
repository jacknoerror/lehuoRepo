package com.lehuozu.ui.tab.my;

import java.util.List;

import org.json.JSONException;

import com.lehuozu.R;
import com.lehuozu.data.MyData;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.order.GetBonusRcv;
import com.lehuozu.net.action.order.GetBonusReq;
import com.lehuozu.ui.MyTitleActivity;
import com.lehuozu.ui.custom.JackRadios;
import com.lehuozu.ui.custom.list.ListItemImpl;
import com.lehuozu.ui.custom.list.MyScrollPageListView;
import com.lehuozu.ui.custom.list.ListItemImpl.Type;
import com.lehuozu.vo.Coupon;
import com.lehuozu.vo.User;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 *只是查看 不需要点击
 * @author tao
 */
public class MyCouponActivity extends MyTitleActivity implements OnItemClickListener {
	
	
	
	private JackRadios jRa;
	private User me;
	private GetBonusRcv getbonusrcv;

	List<ListItemImpl> aList,uList,eList;
	private RadioGroup mRadioGroup;
	
	/**
	 * 
	 */
	private void initJackRadios() {
		mRadioGroup = (RadioGroup) this.findViewById(R.id.group_common);
		jRa = new JackRadios((FrameLayout) this.findViewById(R.id.frame_lists),mRadioGroup){
			@Override
			protected int[] getBtnRids() {
				return new int[]{R.id.radio1,R.id.radio2,R.id.radio3};
			}
			
			@Override
			protected View initView(int i) {
				MyScrollPageListView lv = new MyScrollPageListView(MyCouponActivity.this, Type.COUPON);
				lv.setDivider(null);
				lv.setFooterDividersEnabled(false);
				lv.setOnGetPageListener(new MyScrollPageListView.OnGetPageListener() {
					
					@Override
					public void page(final MyScrollPageListView qListView, int pageNo) {
						requestData();//XXX when no gplistener, but need pull-to-refresh
					}
				});
//				lv.setTag(MyCouponActivity.this);
				updateLv(i, lv);
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
		
		requestData();
	}

	private void requestData() {
		GetBonusReq actReq = new GetBonusReq(me.getUser_id());
		getbonusrcv = new GetBonusRcv(null==jRa?this:null){
			@Override
			public boolean response(String result) throws JSONException {
				boolean response = super.response(result);
				if(!response){
					aList = super.availableBonusList;
					uList = super.usedBonusList;
					eList = super.expiredBonusList;
					if(null==jRa)initJackRadios();
					else updateJackRadios(result);
				}
				return response;
			}
		};
		ActionBuilder.getInstance().request(actReq, getbonusrcv );
	}

	protected void updateJackRadios(String result) {
		View currentView = jRa.getCurrentView();
		if(currentView instanceof MyScrollPageListView){
			MyScrollPageListView msplv = (MyScrollPageListView)currentView;
			try {
				msplv.response(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			updateLv(mRadioGroup.getCheckedRadioButtonId(), msplv);
			
		}
	}

	private void updateLv(int i, MyScrollPageListView lv) {
		switch (i) {
		case R.id.radio1:
			lv.updateList(aList);
			lv.setOnItemClickListener(this);
			break;
		case R.id.radio2:
			lv.updateList(uList);
			lv.setOnItemClickListener(null);
			break;
		case R.id.radio3:
			lv.updateList(eList);
			lv.setOnItemClickListener(null);
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent data = new Intent();
		Coupon coupon = (Coupon)parent.getItemAtPosition(position);
		data.putExtra("bonus_id", coupon.getBonus_id());
		data.putExtra("bonus_name", coupon.getType_name());
		setResult(RESULT_OK, data);
		finish();
	}

	
}

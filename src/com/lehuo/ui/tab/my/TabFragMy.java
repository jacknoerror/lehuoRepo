/**
 * 
 */
package com.lehuo.ui.tab.my;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.ui.tab.ContentAbstractFragment;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.User;

/**
 * @author taotao
 *
 */
public class TabFragMy extends ContentAbstractFragment implements OnClickListener {

	TextView tv_name,tv_score,tv_phone;
	TextView tv_address,tv_birthday,tv_coupon;
	private User user;
	
	@Override
	public void onResume() {
		super.onResume();
		titleManager.updateCart();
	}
	@Override
	public int getLayoutRid() {
		return R.layout.fragment_my;
	}

	@Override
	public void initView() {
		user = MyData.data().getMe();
		if(null==user) return;
		initTitleManager();
		titleManager.setTitleName(getString(R.string.titlename_my));
		
		tv_name=(TextView)mView.findViewById(R.id.tv_account1_username);
		tv_score=(TextView)mView.findViewById(R.id.tv_account1_score);
		tv_phone=(TextView)mView.findViewById(R.id.tv_account1_phone);
		tv_address=(TextView)mView.findViewById(R.id.tv_account2_address);
		tv_birthday=(TextView)mView.findViewById(R.id.tv_account2_birthday);
		tv_coupon=(TextView)mView.findViewById(R.id.tv_account2_coupon);
		
		tv_score.setOnClickListener(this);
		tv_address.setOnClickListener(this);
		tv_birthday.setOnClickListener(this);
		tv_coupon.setOnClickListener(this);
		
		tv_name.setText(user.getUser_name());
		tv_score.setText("乐活积分>  "+user.getPay_points());
		tv_phone.setText(user.getMobile_phone());
		tv_birthday.setText("我的生日\t\t\t"+JackUtils.getDate());//TODO 
		
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tv_account1_score:
			
			break;
		case R.id.tv_account2_address:
			
			break;
		case R.id.tv_account2_birthday:
			
			break;
		case R.id.tv_account2_coupon:
			
			break;

		default:
			break;
		}
		
	}

}

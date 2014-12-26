/**
 * 
 */
package com.lehuozu.ui.tab.my;

import org.json.JSONException;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.lehuozu.R;
import com.lehuozu.data.MyData;
import com.lehuozu.net.action.ActionBuilder;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.JackShowToastReceiver;
import com.lehuozu.net.action.user.UserEdituserReq;
import com.lehuozu.ui.MyGate;
import com.lehuozu.ui.address.MyAddressActivity;
import com.lehuozu.ui.tab.ContentAbstractFragment;
import com.lehuozu.util.JackButtonColorFilter;
import com.lehuozu.util.JackUtils;
import com.lehuozu.vo.User;

/**
 * @author taotao
 *
 */
public class TabFragMy extends ContentAbstractFragment implements OnClickListener {

	TextView tv_name,tv_score,tv_phone;
	TextView tv_address,tv_birthday,tv_coupon;
	Button btn_logout;
	private User user;
	private String birthdayString;
	
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
		titleManager.initTitleMenu();
		titleManager.updateCart();
		
		tv_name=(TextView)mView.findViewById(R.id.tv_account1_username);
		tv_score=(TextView)mView.findViewById(R.id.tv_account1_score);
		tv_phone=(TextView)mView.findViewById(R.id.tv_account1_phone);
		tv_address=(TextView)mView.findViewById(R.id.tv_account2_address);
		tv_birthday=(TextView)mView.findViewById(R.id.tv_account2_birthday);
		tv_coupon=(TextView)mView.findViewById(R.id.tv_account2_coupon);
		btn_logout=(Button)mView.findViewById(R.id.btn_logout);
		JackButtonColorFilter.setButtonFocusChanged(btn_logout);
		
		tv_score.setOnClickListener(this);
		tv_address.setOnClickListener(this);
		tv_birthday.setOnClickListener(this);
		tv_coupon.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
		
		tv_name.setText(user.getUser_name());
		tv_score.setText("乐活积分>  "+user.getRank_points());
		tv_phone.setText(user.getMobile_phone());
		birthdayString = user.getBirthday();
		tv_birthday.setText("我的生日\t\t\t"+birthdayString);  
		
	}
	@Override
	public void onClick(View arg0) {
		Intent intent = null;
		switch (arg0.getId()) {
		case R.id.btn_logout:
			MyData.data().destroy();
			getActivity().finish();
			break;
		case R.id.tv_account1_score:
			intent = new Intent();
			intent.setClass(getActivity(), ScoreMarcketActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_account2_address:
			intent = new Intent();
			intent.setClass(getActivity(), MyAddressActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_account2_birthday:
			OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					String string = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
					if(string.equals(birthdayString)) return;
					birthdayString = string;
					ActionPhpRequestImpl actReq = new UserEdituserReq(user.getUser_id(), birthdayString);
					ActionPhpReceiverImpl actRcv = new JackShowToastReceiver(getActivity()){
						@Override
						public boolean response(String result)
								throws JSONException {
							
							boolean response = super.response(result);
							if(!response){
								user.setBirthday(birthdayString);
								tv_birthday.setText("我的生日\t\t\t"+birthdayString);
							}
							return response;
						}
					};
					ActionBuilder.getInstance().request(actReq, actRcv);
				}
			};
			//showdialog
			String[] strs= new String[3];
			int[] ints = new int[3];
			try{
				strs = birthdayString.split("-");
				for(int i=0;i<3;i++){
					ints[i] = Integer.parseInt(strs[i]);
				}
			}catch (Exception e) {
				ints[0]=1970;ints[1]=01;ints[2]=01;
			}
			new DatePickerDialog(getActivity(), onDateSetListener , ints[0], ints[1]-1, ints[2]).show();
			break;
		case R.id.tv_account2_coupon:
			MyGate.goCoupon(getActivity());
			break;

		default:
			break;
		}
		
	}
	
	
}

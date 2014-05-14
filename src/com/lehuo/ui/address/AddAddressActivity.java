/**
 * 
 */
package com.lehuo.ui.address;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.entity.LocalDistrictGetter;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.JackFinishActivityReceiver;
import com.lehuo.net.action.user.UserAddAddressReq;
import com.lehuo.net.action.user.UserSelectAddressReq;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.ui.login.MySpinnerAdapter;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.Place;
import com.lehuo.vo.User;

/**
 * @author tao
 * 
 */
public class AddAddressActivity extends MyTitleActivity implements
		AdapterView.OnItemSelectedListener {

	EditText et_consignee, et_mobile, et_detail;
	// shengshiqu
	CheckBox cb_default;

	private User user;

	public Spinner spinnerPrv, spinnerCt, spinnerDist;

	@Override
	public int getLayoutRid() {
		return R.layout.activity_addaddr;
	}

	@Override
	public void initView() {
		user = MyData.data().getMe();
		if (null == user)
			return;
		titleManager.setTitleName(getResources().getString(
				R.string.titlename_addaddr));
		titleManager.initTitleBack();
		titleManager.setRightText("保存", new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String name = et_consignee.getText().toString();
				String detail = et_detail.getText().toString();
				String mobile = et_mobile.getText().toString();
				if (name.isEmpty()) {
					et_consignee.setError("请填写完整");
					return;
				}
				if (detail.isEmpty()) {
					et_detail.setError("请填写完整");
					return;
				}
				if (mobile.isEmpty()) {
					et_mobile.setError("请填写完整");
					return;
				}

				// spinner check
				if (null == spinnerPrv
						|| null == spinnerCt
						|| null == spinnerDist
						|| spinnerPrv.getSelectedItemPosition()
								* spinnerCt.getSelectedItemPosition()
								* spinnerDist.getSelectedItemPosition() == 0) {
					JackUtils.showToast(AddAddressActivity.this, "信息填写不完整");
					return;
				}

				ActionPhpRequestImpl req = new UserAddAddressReq(user
						.getUser_id(), name, getItemId(spinnerPrv),
						getItemId(spinnerCt), getItemId(spinnerDist), detail,
						mobile,cb_default.isChecked());// TODO 地址
				ActionPhpReceiverImpl rcv = new JackFinishActivityReceiver(
						AddAddressActivity.this);
				ActionBuilder.getInstance().request(req, rcv);

			}
		});

		et_consignee = (EditText) this.findViewById(R.id.et_aa_name);
		et_mobile = (EditText) this.findViewById(R.id.et_aa_mobile);
		et_detail = (EditText) this.findViewById(R.id.et_aa_detail);
		cb_default = (CheckBox) this.findViewById(R.id.cb_addaddr);

		// spinner construct
		spinnerPrv = (Spinner) this.findViewById(R.id.spinner_cuser_province);
		spinnerCt = (Spinner) this.findViewById(R.id.spinner_cuser_city);
		spinnerDist = (Spinner) this.findViewById(R.id.spinner_cuser_district);
		spinnerPrv.setOnItemSelectedListener(this);
		spinnerCt.setOnItemSelectedListener(this);
		spinnerDist.setOnItemSelectedListener(this);
		
		spinnerPrv.setAdapter(new MySpinnerAdapter(LocalDistrictGetter.getInstance().getPlaceList(0)));
	}

	private int getItemId(Spinner sp) {// one alternate is recording when
										// selected
		return ((Place) sp.getSelectedItem()).getRegion_id();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Log.i(TAG, arg0.getId() + "-" + arg1.getId() + "-" + arg2 + "-" + arg3);
		if (arg2 == 0)
			return;// 第一个选项不做响应
		switch (arg0.getId()) {
		case R.id.spinner_cuser_province:
			spinnerCt.setAdapter(new MySpinnerAdapter(LocalDistrictGetter
					.getInstance().getPlaceList((int) arg3)));
			break;
		case R.id.spinner_cuser_city:
			spinnerDist.setAdapter(new MySpinnerAdapter(LocalDistrictGetter
					.getInstance().getPlaceList((int) arg3)));
			break;
		case R.id.spinner_cuser_district:

			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
}

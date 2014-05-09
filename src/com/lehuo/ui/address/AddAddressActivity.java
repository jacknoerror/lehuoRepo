/**
 * 
 */
package com.lehuo.ui.address;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.JackFinishActivityReceiver;
import com.lehuo.net.action.user.UserAddAddressReq;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.vo.User;

/**
 * @author tao
 * 
 */
public class AddAddressActivity extends MyTitleActivity {

	EditText et_consignee, et_mobile, et_detail;
	// shengshiqu
	CheckBox cb_default;

	private User user;

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
				if(name.isEmpty()){et_consignee.setError("请填写完整");return;}
				if(detail.isEmpty()){et_detail.setError("请填写完整");return;}
				if(mobile.isEmpty()){et_mobile.setError("请填写完整");return;}
				ActionPhpRequestImpl req = new UserAddAddressReq(user
						.getUser_id(), name, 31, 383, 3234, detail, mobile);//TODO 地址
				ActionPhpReceiverImpl rcv = new JackFinishActivityReceiver(AddAddressActivity.this);
				ActionBuilder.getInstance().request(req, rcv);

			}
		});

		et_consignee = (EditText) this.findViewById(R.id.et_aa_name);
		et_mobile = (EditText) this.findViewById(R.id.et_aa_mobile);
		et_detail = (EditText) this.findViewById(R.id.et_aa_detail);
		cb_default = (CheckBox) this.findViewById(R.id.cb_addaddr);

	}

}

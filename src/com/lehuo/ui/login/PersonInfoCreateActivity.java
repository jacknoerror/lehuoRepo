package com.lehuo.ui.login;

import java.net.Authenticator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.lehuo.R;
import com.lehuo.data.MyData;
import com.lehuo.data.NetConst;
import com.lehuo.entity.LocalDistrictGetter;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionBuilder;
import com.lehuo.net.action.ActionPhpReceiverImpl;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.user.UserRegistReq;
import com.lehuo.ui.MyTitleActivity;
import com.lehuo.ui.adapter.MySpinnerArrayAdapter;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.Place;
import com.lehuo.vo.User;

public class PersonInfoCreateActivity extends MyTitleActivity implements ActionPhpReceiverImpl,AdapterView.OnItemSelectedListener {

	EditText et_pwd,et_name,et_address;
//	View layout_allareas;
	public Spinner spinnerPrv,spinnerCt,spinnerDist;
	RadioGroup group;
	private String phone;
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_createuserinfo;
	}

	@Override
	public void initView() {
		phone = getIntent().getStringExtra(NetConst.EXTRAS_PHONE);
		if(null==phone) return ;//
		titleManager.setTitleName(getString(R.string.titlename_commitcheck));
		titleManager.initTitleBack();
		titleManager.setRightText("完成", new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				done();
				
			}

		});
//		titleManager.
		
		et_pwd = (EditText)this.findViewById(R.id.et_cuser_pwd);
		et_name = (EditText)this.findViewById(R.id.et_cuser_name);
		et_address = (EditText)this.findViewById(R.id.et_cuser_address);
//		layout_allareas = (View)this.findViewById(R.id.relative_createuser_allareas);
		spinnerPrv = (Spinner)this.findViewById(R.id.spinner_cuser_province);
		spinnerCt = (Spinner)this.findViewById(R.id.spinner_cuser_city);
		spinnerDist = (Spinner)this.findViewById(R.id.spinner_cuser_district);
		group = (RadioGroup)this.findViewById(R.id.radiogroup_createuser);
		group.check(R.id.radio_man);
		//give it colors
		RadioButton rb1 = (RadioButton) this.findViewById(R.id.radio_man);
		RadioButton rb2 = (RadioButton) this.findViewById(R.id.radio_woman);
		rb1.setTextColor(getResources().getColorStateList(R.color.selector_tab_textcolor_green));
		rb2.setTextColor(getResources().getColorStateList(R.color.selector_tab_textcolor_green));
		
		spinnerPrv.setOnItemSelectedListener(this); 
		spinnerCt.setOnItemSelectedListener(this); 
		spinnerDist.setOnItemSelectedListener(this); 
		
		//get省份
		/*ActionPhpRequestImpl req = new UGetprovinceReq();
		ActionPhpReceiverImpl rcv= new UGetPlaceRcv(this, this.spinnerPrv);
		ActionBuilder.getInstance().request(req, rcv);*/
//		spinnerPrv.setAdapter(new MySpinnerAdapter(LocalDistrictGetter.getInstance().getPlaceList(0)));
		spinnerPrv.setAdapter(new MySpinnerArrayAdapter(this, LocalDistrictGetter.getInstance().getPlaceList(0)));
		
	}
	private void done() {
		if(null==spinnerPrv||null==spinnerCt||null==spinnerDist
				||spinnerPrv.getSelectedItemPosition()*
					spinnerCt.getSelectedItemPosition()*
					spinnerDist.getSelectedItemPosition()==0){
			fail();
			return;
		}
//		String p,c,d;
		int pp,cc,dd;//turns out to be ids
//		p = getItemName(spinnerPrv);
//		c = getItemName(spinnerCt);
//		d = getItemName(spinnerDist);
		pp=getItemId(spinnerPrv);
		cc=getItemId(spinnerCt);
		dd=getItemId(spinnerDist);
		ActionPhpRequestImpl actReq = new UserRegistReq(
				phone, 
				et_name.getText().toString(), 
				"310000",
				pp, cc, dd, 
				et_address.getText().toString(), 
				JackUtils.getMD5(et_pwd.getText().toString()),
				group.getCheckedRadioButtonId()==R.id.radio_man?1:2);
		ActionBuilder.getInstance().request(actReq, this);
		
	}
	private int getItemId(Spinner sp){//one alternate is recording when selected
		return  (int) sp.getAdapter().getItemId(sp.getSelectedItemPosition());
//		return ((Place)sp.getSelectedItem()).getRegion_id();
	}
	private void fail(){
		JackUtils.showToast(this, "信息填写不完整");
	}
	
	@Override
	public boolean response(String result) throws JSONException {
		JSONObject job = NetStrategies.getResultObj(result);
		if(null!=job){
			User user = new User(job);
			MyData.data().setCurrentUser(user);//id>0?
//			MyGate.login();//
			return false;
		}
		return true;
	}

	@Override
	public Context getReceiverContext() {
		return this;
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Log.i(TAG, arg0.getId()+"-"+arg1.getId()+"-"+arg2+"-"+arg3);
		if(arg2 == 0) return;//第一个选项不做响应
		ActionPhpRequestImpl req = null;
		ActionPhpReceiverImpl rcv= null;
		Context oisCntxt = this;
		switch (arg0.getId()) {
		case R.id.spinner_cuser_province:
			/*req = new UGetcityReq((int) arg3);
			rcv = new UGetPlaceRcv(oisCntxt, spinnerCt);
			ActionBuilder.getInstance().request(req, rcv);*/
//			spinnerCt.setAdapter(new MySpinnerAdapter(LocalDistrictGetter.getInstance().getPlaceList((int) arg3)));
			spinnerCt.setAdapter(new MySpinnerArrayAdapter(this,LocalDistrictGetter.getInstance().getPlaceList((int) arg3)));
			break;
		case R.id.spinner_cuser_city:
			/*req = new UGetDistrictReq((int) arg3);
			rcv = new UGetPlaceRcv(oisCntxt, spinnerDist);
			ActionBuilder.getInstance().request(req, rcv);*/
//			spinnerDist.setAdapter(new MySpinnerAdapter(LocalDistrictGetter.getInstance().getPlaceList((int) arg3)));
			spinnerDist.setAdapter(new MySpinnerArrayAdapter(this,LocalDistrictGetter.getInstance().getPlaceList((int) arg3)));
			break;
		case R.id.spinner_cuser_district:
	
			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
//		arg0.set
		Log.i(TAG, "on nothing"+arg0.getId());
	}
}

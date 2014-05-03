package com.lehuo;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.json.JSONArray;

import android.test.AndroidTestCase;
import android.util.Log;

import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;
import com.lehuo.net.action.brand.GetBrandReq;
import com.lehuo.net.action.category.GetAllReq;
import com.lehuo.net.action.goods.GetProductAttrReq;
import com.lehuo.net.action.goods.GetProductCommentReq;
import com.lehuo.net.action.goods.GetProductDetailReq;
import com.lehuo.net.action.goods.GetProductListReq;
import com.lehuo.net.action.order.AddCartReq;
import com.lehuo.net.action.order.GetCartReq;
import com.lehuo.net.action.user.GetUserAddrReq;
import com.lehuo.net.action.user.LoginReq;
import com.lehuo.net.action.user.UGetDistrictReq;
import com.lehuo.net.action.user.UGetcityReq;
import com.lehuo.net.action.user.UGetprovinceReq;
import com.lehuo.util.JackUtils;
import com.lehuo.vo.AddCartGoodsStandard;

public class NetStrategiesTest extends AndroidTestCase {
	private final String TAG  = "UNIT_TEST";

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	
	private String actionChoser(int actNo) throws SocketTimeoutException, UnknownHostException, IOException{
		String result = "";
		ActionPhpRequestImpl ari = null;
		switch (actNo) {
		case 0:
			ari = new GetUserAddrReq(1);
			break;
		case 1:
			ari = new GetAllReq();
			break;
		case 2:
			ari = new GetProductListReq(10, 1,2, "price", "ASC");
			break;
		case 3://TODO
			AddCartGoodsStandard goods;
			goods = new AddCartGoodsStandard(new JSONArray(), 0, null, 1, 8);
			ari = new AddCartReq(goods, 1);
			break;
		case 4:
			ari = new GetProductAttrReq(2);
			break;
		case 5:
			ari = new GetProductDetailReq(2);
			break;
		case 6:
			ari = new GetBrandReq(1);
			break;
		case 7:
			ari = new GetProductCommentReq(2, 1);
			break;
		case 8:
			ari = new GetCartReq(4);
			break;
		case 9://provice
			ari = new UGetprovinceReq();
			break;
		case 10:
			ari = new UGetcityReq(32);
			break;
		case 11:
			ari = new UGetDistrictReq(394);
			break;
		case 12://login
			ari = new LoginReq("15858173770", JackUtils.getMD5("111222"));
			break;
		case 13://getcart
			ari = new GetCartReq(28);
			break;
		case 14:
//			ari = new AddCartReq(, 28);
			break;
		default:
			break;
		}
		
		result = NetStrategies.doHttpRequest(ari.toHttpBody());
		if(null!=ari ) JackUtils.writeToFile(getContext(), ari.getApiName(), result);
		return result;
	}
	
	public void actionTest() throws SocketTimeoutException, UnknownHostException, IOException{
		String result="";
		
		result  = actionChoser(13);
			
		Log.i(TAG, "result=>"+result);
		assertNotSame(result, "");
	}
	
	public void getPhpHttpBodyTest(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("paramname1", "value1");
		map.put("paramname2", "value2");
		String result = NetStrategies.getPhpHttpBody("phpname", "actionname", map);
		Log.i(TAG, result);
	}
}
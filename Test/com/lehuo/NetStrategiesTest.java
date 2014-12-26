package com.lehuo;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.json.JSONArray;

import android.test.AndroidTestCase;
import android.util.Log;

import com.lehuozu.net.NetStrategies;
import com.lehuozu.net.action.ActionPhpRequestImpl;
import com.lehuozu.net.action.brand.GetBrandReq;
import com.lehuozu.net.action.category.GetAllReq;
import com.lehuozu.net.action.courier.DeliverListReq;
import com.lehuozu.net.action.goods.GetLongImgRequest;
import com.lehuozu.net.action.goods.GetProductAttrReq;
import com.lehuozu.net.action.goods.GetProductCommentReq;
import com.lehuozu.net.action.goods.GetProductDetailReq;
import com.lehuozu.net.action.goods.GetProductListReq;
import com.lehuozu.net.action.goods.GetTbarReq;
import com.lehuozu.net.action.order.AddCartReq;
import com.lehuozu.net.action.order.CheckoutOrderReq;
import com.lehuozu.net.action.order.GetBonusReq;
import com.lehuozu.net.action.order.GetCartReq;
import com.lehuozu.net.action.order.GetOrderListReq;
import com.lehuozu.net.action.order.UpdateCartReq;
import com.lehuozu.net.action.user.GetUserAddrReq;
import com.lehuozu.net.action.user.LoginReq;
import com.lehuozu.net.action.user.UGetDistrictReq;
import com.lehuozu.net.action.user.UGetcityReq;
import com.lehuozu.net.action.user.UGetprovinceReq;
import com.lehuozu.util.JackUtils;
import com.lehuozu.vo.AddCartGoodsStandard;

public class NetStrategiesTest extends AndroidTestCase {
	private final String TAG  = "UNIT_TEST";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	private String actionChoser(int actNo) throws SocketTimeoutException, UnknownHostException, IOException{
		String result = "";
		ActionPhpRequestImpl ari = null;
		switch (actNo) {
		case 0:
			ari = new GetUserAddrReq(28);
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
			ari = new GetProductCommentReq(3, 1);
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
//			ari = new LoginReq("15858173770", JackUtils.getMD5("111222"));
			ari = new LoginReq("15906669497", JackUtils.getMD5("123456"));
			break;
		case 13://getcart
			ari = new GetCartReq(28);
			break;
		case 14:
			ari = new AddCartReq(new AddCartReq.CartGoods(2),28);
			break;
		case 15:
			ari = new UpdateCartReq(28);
			break;
		case 16:
			ari = new GetOrderListReq(0, 1, 17);
			break;
		case 17:
			ari = new CheckoutOrderReq(28);
			break;
		case 18://»ý·Ö³¬ÊÐ
			ari = new GetProductListReq(1);
			break;
		case 19:
			ari = new GetBonusReq(17);
			break;
		case 20://deliver
			ari = new DeliverListReq(1);
			break;
		case 21:
			ari = new GetTbarReq();
			break;
		case 22:
			ari = new GetOrderListReq(1, 1, 17);
			break;
		case 23:
			ari = new GetLongImgRequest(11);
			break;
		default:
			break;
		}
		
		result = NetStrategies.doHttpRequest(ari.toHttpBody());
		if(null!=ari ) JackUtils.writeToFile(getContext(), ari.getApiName(), result);
		return result;
	}
	
	/**
	 * login:12;GetAllReq:1
	 * @throws SocketTimeoutException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void acttionTest() throws SocketTimeoutException, UnknownHostException, IOException{
		String result="";
		
		result  = actionChoser(23);
			
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
	
	public void actionDpNpx(){
//		int a = JackUtils.px2dip(getContext(), 400);
//		Log.i(TAG, a+"dp");
		int a = JackUtils.dip2px(getContext(), 260);
		Log.i(TAG, a+"px");
//		assertNotSame(a, 0);
		assertTrue(a>0);
	}
	
}

package com.lehuo.ui.product;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuo.data.MyData;
import com.lehuo.ui.tab.HubActivity;
import com.lehuo.vo.Product;

import android.content.Intent;
import android.test.InstrumentationTestCase;

public class ProductDetailActivityTest extends InstrumentationTestCase {
	ProductDetailActivity app;
	public ProductDetailActivityTest(String name) {
		super();
		final String productJson = "{\"goods_id\":\"8\",\"goods_name\":\"SUPER LOVER\\u65b0\\u54c1\\u65e5\\u7cfb\\u590d\\u53e4\\u649e\\u8272\\u7cd6\\u679c\\u8272\\u5c0f\\u80e1\\u5b50\\u80cc\\u5305\\u53cc\\u80a9\\u659c\\u8de8\\u591a\\u7528\\u5305\",\"goods_number\":\"999\",\"shop_price\":\"120.00\",\"market_price\":\"144.00\",\"promote_price\":\"0.00\",\"promote_start_date\":\"0\",\"promote_end_date\":\"0\",\"keywords\":\"\",\"goods_brief\":\" \\u8d75\\u654f\\u82f1\\u540c\\u6b3e\\u5b66\\u9662\\u98ce\\u53ef\\u7231\\u7c73\\u5947\\u5370\\u82b1\\u767e\\u642d\\u6761\\u7eb9T\\u6064\\u5973 \",\"integral\":\"120\",\"goods_desc\":\"\\u8d75\\u654f\\u82f1\\u540c\\u6b3e\\u5b66\\u9662\\u98ce\\u53ef\\u7231\\u7c73\\u5947\\u5370\\u82b1\\u767e\\u642d\\u6761\\u7eb9T\\u6064\\u5973\",\"goods_thumb\":\"http:\\/\\/58.64.178.2\\/images\\/201404\\/thumb_img\\/8_thumb_G_1398127791468445668_100X100.jpg\",\"goods_img\":\"http:\\/\\/58.64.178.2\\/images\\/201404\\/goods_img\\/8_G_1398127791062153407_320X200.jpg\",\"last_update\":\"1398127847\",\"integral_price\":{\"shop_price\":0,\"integral_need\":12000}";
		try {
			Product p = new Product(new JSONObject(productJson));
			MyData.data().storeProduct(p);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent();
		intent.setClassName("com.lehuo", HubActivity.class.getName());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		app = (ProductDetailActivity) getInstrumentation().startActivitySync(intent);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		app.finish();
	}

	
	
	
}

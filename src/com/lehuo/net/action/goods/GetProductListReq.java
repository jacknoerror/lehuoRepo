package com.lehuo.net.action.goods;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;
/**
 * 
 * @author tao
 *获取产品列表接口
 */
public class GetProductListReq implements ActionPhpRequestImpl {
	public static String SORT_PRICE="price";
	public static String SORT_SALES="sales";
	public static String SORT_RECOMMED="recommed";

	int record_number ;//每页显示的数量（默认20，即如果该参数木有提交，则自动赋值为20）
	int page_number ;//页数（默认第一页）
	Integer catid ;//分类ID （默认没有，则获取全部数据）
	String sort ;//排序字段 （可选值有：price价钱,sales 销量,recommed 推荐排序.默认情况下安装商品最新添加和修改时间排序）
	String sc ;//排序方式 升序或者降序（该参数只有sort 值是price是才生效 可选值：ASC,DESC）

	
	public GetProductListReq(int record_number, int page_number, int catid,
			String sort, String sc) {
		super();
		this.record_number = record_number;
		this.page_number = page_number;
		this.catid = catid;
		this.sort = sort;
		this.sc = sc;
	}

	/**
	 * 积分超市
	 * @param page_number
	 */
	public GetProductListReq(int page_number){
		this.record_number = 10;//
		this.page_number = page_number;
		this.catid = null;
		this.sort = "";
		this.sc = "";
	}
	
	@Override
	public String getPhpName() {
		return PHPNAME_GOODS;
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_GET_GOODS;
	}

	@Override
	public String toHttpBody() {
		return NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put(NetConst.PARAMS_RECORD_NUMBER, record_number+"");
		halfway.put(NetConst.PARAMS_PAGE_NUMBER, page_number+"");
		halfway.put(NetConst.PARAMS_CATID, catid+"");
		halfway.put(NetConst.PARAMS_SORT, sort);
		halfway.put(NetConst.PARAMS_SC, sc);
		return halfway;
	}

}

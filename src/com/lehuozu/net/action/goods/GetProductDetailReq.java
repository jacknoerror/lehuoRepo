package com.lehuozu.net.action.goods;


import com.lehuozu.data.NetConst;
import com.lehuozu.net.action.ActionPhpRequestImpl;

/**
 * @author tao
 *��ȡ��Ʒ��ϸ�ӿ�
 */
public class GetProductDetailReq extends GoodsIdParentAbsReq{

	public GetProductDetailReq(int goods_id) {
		super(goods_id);
	}

	@Override
	public String getApiName() {
		return NetConst.ACTION_GOOD_INFO;
	}

}

package com.lehuo.net.action.goods;


import com.lehuo.data.NetConst;
import com.lehuo.net.action.ActionPhpRequestImpl;

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

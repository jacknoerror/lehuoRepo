package com.lehuo.net.action.goods;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;
import com.lehuo.net.action.ActionPhpRequestImpl;
/**
 * 
 * @author tao
 *��ȡ��Ʒ�б�ӿ�
 */
public class GetProductListReq implements ActionPhpRequestImpl {
	public static String SORT_PRICE="price";
	public static String SORT_SALES="sales";
	public static String SORT_RECOMMED="recommed";

	int record_number ;//ÿҳ��ʾ��������Ĭ��20��������ò���ľ���ύ�����Զ���ֵΪ20��
	int page_number ;//ҳ����Ĭ�ϵ�һҳ��
	Integer catid ;//����ID ��Ĭ��û�У����ȡȫ�����ݣ�
	String sort ;//�����ֶ� ����ѡֵ�У�price��Ǯ,sales ����,recommed �Ƽ�����.Ĭ������°�װ��Ʒ������Ӻ��޸�ʱ������
	String sc ;//����ʽ ������߽��򣨸ò���ֻ��sort ֵ��price�ǲ���Ч ��ѡֵ��ASC,DESC��

	
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
	 * ���ֳ���
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

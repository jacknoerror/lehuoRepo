package com.lehuozu.vo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.data.NetConst;
import com.lehuozu.entity.json.JackJson;

public class AddCartGoodsStandard extends JackJson {

	private JSONArray spec; // Ϊ��Ʒ��������Ϊ��ȡ��Ʒ�����Ϊ�գ�
	private int parent;// ��Ʒ�ĸ�ID�����û�о�Ϊ0
	private Integer number;// �ڻ�ȡ��Ʒ����ǿ���Ϊ�գ����߲�Ҫ
	private int getattr;// ��ȡ��Ʒ���
	private int goods_id;

	public AddCartGoodsStandard(JSONArray spec, int parent, Integer number,
			int getattr, int goods_id) {
		super();
		this.spec = spec;
		this.parent = parent;
		this.number = number;
		this.getattr = getattr;
		this.goods_id = goods_id;
	}

	public final JSONArray getSpec() {
		return spec;
	}

	public final void setSpec(JSONArray spec) {
		this.spec = spec;
	}

	public final int getParent() {
		return parent;
	}

	public final void setParent(int parent) {
		this.parent = parent;
	}

	public final Integer getNumber() {
		return number;
	}

	public final void setNumber(Integer number) {
		this.number = number;
	}

	public final int getGetattr() {
		return getattr;
	}

	public final void setGetattr(int getattr) {
		this.getattr = getattr;
	}

	public final int getGoods_id() {
		return goods_id;
	}

	public final void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	/*
	 * private String[] spec; //Ϊ��Ʒ��������Ϊ��ȡ��Ʒ�����Ϊ�գ� private int
	 * parent;//��Ʒ�ĸ�ID�����û�о�Ϊ0 private Integer number;//�ڻ�ȡ��Ʒ����ǿ���Ϊ�գ����߲�Ҫ
	 * private int getattr;//��ȡ��Ʒ��� private int goods_id;@see
	 * com.lehuo.entity.json.JackJson#toJsonObj()
	 */
	@Override
	public JSONObject toJsonObj() {
		JSONObject job = new JSONObject();
		try {
			job.put(NetConst.PARAMS_GOODS_ID, goods_id);
			job.put(NetConst.PARAMS_GETATTR, getattr);
			job.put("spec", spec);
			job.put("number", number);
			job.put("parent", parent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return job;
	}

	@Override
	public void initJackJson(JSONObject job) throws JSONException {// δ������
	// if(job.has("spec")) setSpec(job.getString("spec"));//TODO
		if (job.has("parent"))
			setParent(job.getInt("parent"));
		if (job.has("number"))
			setNumber(job.optInt("number"));
		if (job.has("getattr"))
			setGetattr(job.getInt("getattr"));
		if (job.has("goods_id"))
			setGoods_id(job.getInt("goods_id"));

	}

}

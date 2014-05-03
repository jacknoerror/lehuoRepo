package com.lehuo.net.action;

import java.util.Map;

import com.lehuo.data.NetConst;


/**
 * 
 * @author taotao
 *
 */
public interface ActionPhpRequestImpl extends NetConst{
	public String getPhpName();
	
	public String getApiName();
	public String toHttpBody();
	/**
	 * halfway.put("",xx);
	 * @param halfway
	 * @return halfway
	 */
	public Map<String, String> halfwayParamMap(Map<String,String> halfway);
}

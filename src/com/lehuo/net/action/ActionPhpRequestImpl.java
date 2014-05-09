package com.lehuo.net.action;

import java.util.HashMap;
import java.util.Map;

import com.lehuo.data.NetConst;
import com.lehuo.net.NetStrategies;


/**
 * 
 * @author taotao
 *
 */
public interface ActionPhpRequestImpl extends NetConst{
	public String getPhpName();
	
	public String getApiName();
	/**
	 * NetStrategies.getPhpHttpBody(getPhpName(), getApiName(), halfwayParamMap(new HashMap<String, String>()));
	 * @return
	 */
	public String toHttpBody();
	/**
	 * halfway.put("",xx);
	 * @param halfway
	 * @return halfway
	 */
	public Map<String, String> halfwayParamMap(Map<String,String> halfway);
}

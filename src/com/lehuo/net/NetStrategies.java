package com.lehuo.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.lehuo.MyApplication;
import com.lehuo.data.Const;
import com.lehuo.data.NetConst;
import com.lehuo.util.JackUtils;
/**
 * 
 * @author taotao
 *
 */
public class NetStrategies implements NetConst{
	final static String TAG ="NetS_";
	final static int TIMEOUT = 1000*30;
	
	/**
	 * 与openAPI不同，固定参数和浮动参数直接相连 
	 * @param phpName
	 * @param actionName
	 * @param hpMap
	 * @return
	 */
	public static String getPhpHttpBody(String phpName,String actionName, Map<String, String> hpMap){
		
		StringBuffer url = new StringBuffer( )
						 	.append(phpName)
							.append(".php?");
		if(Const._DEBUG)url.append("debug").append("=").append("1&");
		url.append("action").append("=").append(actionName);
		for(String pn : hpMap.keySet()){
			url.append("&")
				.append(pn)
				.append("=")
				.append(hpMap.get(pn));
		}
		return url.toString();	
	}
	//http://58.64.178.2/api/user.php?debug=1&action=edituser
	
	/**
	 * @param result
	 * @param params
	 * @return
	 */
	public static String doHttpRequest( String... params) throws UnknownHostException,SocketTimeoutException,IOException{
		URL url = null;
		HttpURLConnection connection = null;
		InputStreamReader in = null;
		
		//format params
		String temp = params[0];
		if (!temp.contains(".php?")) {
			Log.e("doHttpRequest", "param error");
			return "";
		}
		params = new String[2];
		int index = temp.indexOf("?");
		params[1] = temp.substring(0, index);// ?.php
		params[0] = temp.substring(index + 1);// x=y

		try {
			
			url = new URL(String.format("%s%s", Const._URL,params[1]));
			//proxy
//			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(YftValues.PROXY_HOST, 80)); 
//			connection = (HttpURLConnection) url.openConnection(proxy);
			//no proxy
			connection = (HttpURLConnection) url.openConnection();
			
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Charset", "utf-8");
			//设置连接服务器超时时间
			connection.setConnectTimeout(TIMEOUT);
			//设置从服务器读取数据超时时间
			connection.setReadTimeout(TIMEOUT);
			DataOutputStream dop = new DataOutputStream(
					connection.getOutputStream());
			Log.i("doHttpRequest", params[0]);//
			dop.write(params[0].getBytes());//转utf-8在这里
			dop.flush();
			dop.close();

			in = new InputStreamReader(connection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(in);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}
			return  strBuffer.toString();//new String(line.getBytes("utf-8"),"gbk")
		
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * @param result
	 * @throws JSONException
	 */
	public static JSONObject getResultObj(String result) throws JSONException {
		JSONObject job = new JSONObject(result);
		if (dataAvailable(job)) {
			return job.getJSONObject(NetConst.RESULT_OBJ);//
		} else {
			return new JSONObject();// 0416
		}
	}
	public static JSONArray getResultArray(String result) throws JSONException{
		JSONObject job = new JSONObject(result);
		if (dataAvailable(job)) {
			return job.getJSONArray(NetConst.RESULT_OBJ);//
		} else {
			return new JSONArray();// 0416
		}
	}
	
	public static boolean dataAvailable(JSONObject job) throws JSONException{//0505 public
		if(null!=job&&job.has(NetConst.RESULT_SIGN)&&job.getBoolean(NetConst.RESULT_SIGN)){
			if(!job.isNull(NetConst.RESULT_OBJ)){
				return true;
			}else{//返回正确但是obj为空
			}
		}else{
			JackUtils.showToast(MyApplication.app(),job.optString(NetConst.RESULT_ERROR_MSG));
		}
		return false;
	}
	
}

package com.lehuozu.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.lehuozu.data.Const;
import com.lehuozu.net.action.ActionPhpReceiverImpl;
import com.lehuozu.util.JackUtils;




public class HttpRequestTask extends AsyncTask<String, Integer, String>{
	private final String TAG = HttpRequestTask.class.getSimpleName(); 
	private static final int TIMEOUT = 30*1000;
	
	private ActionPhpReceiverImpl receiver;
	public HttpRequestTask(ActionPhpReceiverImpl receiver){
		this.receiver = receiver;
	}
	private ProgressDialog pDialog;
	@Override
	protected String doInBackground(String... params) {
		String result = null;
		try {
			result = NetStrategies.doHttpRequest( params);
		} catch(UnknownHostException e){
			publishProgress(1);
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			publishProgress(2);
			e.printStackTrace();
		} catch (IOException e) {
			publishProgress(10);
			e.printStackTrace();
		}
		if(null==result) result="";
		return result;
	}

	/**
	 * @param result
	 * @param params
	 * @return
	 *//*
	public static String doHttpRequest( String... params) throws UnknownHostException,SocketTimeoutException,IOException{
		URL url = null;
		HttpURLConnection connection = null;
		InputStreamReader in = null;
		//format params
		if(!params[0].contains(".php?")){
			Log.e("doHttpRequest", "param error");
			return "";
		}
		int index = params[0].indexOf("?");
		params[1] = params[0].substring(0,index);//?.php
		params[0] = params[0].substring(index+1);//x=y
				
		try {
			
			url = new URL(String.format("%s%s", Const._URL,params[1]));
//			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(YftValues.PROXY_HOST, 80)); 
//			connection = (HttpURLConnection) url.openConnection(proxy);
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
			dop.write(params[0].getBytes("utf-8"));//把中文转成utf-8
			dop.flush();
			dop.close();

			in = new InputStreamReader(connection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(in);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}
			return  strBuffer.toString();
		
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
	}*/

	/**
	 * 这里是最终用户调用Excute时的接口，当任务执行之前开始调用此方法，可以在这里显示进度对话框。
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Context context = receiver.getReceiverContext();
		if(context!=null){
			pDialog = JackUtils.showProgressDialog(context, "载入中");//0520
		}
	}

	/**
	 * 相当于Handler处理UI的方式，在这里面可以使用在doInBackground 得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
	 */
	@Override
	protected void onPostExecute(String result) {
		Log.i(TAG, "result::	"+result);
		super.onPostExecute(result);
		
		try {
			receiver.response(result);
		} catch (JSONException e) {
			Log.e(TAG, "result json error");
			e.printStackTrace();
		}
		
		if(pDialog!=null) pDialog.dismiss();
		
		receiver=null;
		pDialog=null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		Context pContext = receiver.getReceiverContext();
		
		if(null==pContext) return;//0224
		switch (values[0]) {
		case 1:
			if(pDialog!=null) pDialog.dismiss();
			JackUtils.showToast(pContext, "请检查您的网络，或稍后再试");
			break;
		case 2:
			if(pDialog!=null) pDialog.dismiss();
			JackUtils.showToast(pContext, "连接超时，请重试");
			break;
		default:
			if(pDialog!=null) pDialog.dismiss();
			JackUtils.showToast(pContext, "对不起，连接失败，请重试");
			break;
		}
	}

	
}

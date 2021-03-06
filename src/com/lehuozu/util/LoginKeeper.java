package com.lehuozu.util;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginKeeper {
	static SharedPreferences sp ;
//	private TokenKeeper(){};
	public static SharedPreferences getSpInstance(Context context){
		if(null==sp){
			sp = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
		}
		return sp;
	}
	
	public static boolean putValue(Context context , String key,String value){
		return getSpInstance(context).edit().putString(key, value).commit();
	}
	public static String getValue(Context context , String key){
		return getSpInstance(context).getString(key, "");
	}
	public static final String PREF_LOGINJOB = "loginjob";
}

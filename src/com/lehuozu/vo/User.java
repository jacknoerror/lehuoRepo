package com.lehuozu.vo;

import org.json.JSONException;
import org.json.JSONObject;

import com.lehuozu.entity.json.JsonImport;

/**
 * @author tao
 *
 */
public class User extends JsonImport {
//{"user_id":"28","user_name":"15858173770","sex":"2","mobile_phone":"15858173770",
	//"truename":"\u9676\u9676","rank_points":"0"}
	//{"user_id":"28","user_name":"15858173770","sex":"2",
	//"user_money":"0.00","pay_points":"0","rank_points":"0",
	//"reg_time":"1399115789","last_login":"0","user_rank":"0",
	//"mobile_phone":"15858173770","truename":"\u9676\u9676","is_courier":"0"}
	int user_id;
	String user_name;
	int sex;//1.man;2.woman;
	String mobile_phone;
	String truename;
	int rank_points;
	
	String user_money;
	int pay_points;
	long reg_time;
	long last_login;
	int user_rank;
	int is_courier;
	
	String birthday;
	
	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		if(job.has("user_id")) user_id = job.getInt("user_id");
		if(job.has("sex")) sex = job.getInt("sex");
		if(job.has("rank_points")) rank_points = job.getInt("rank_points");
		if(job.has("user_name")) user_name = job.getString("user_name");
		if(job.has("mobile_phone")) mobile_phone = job.getString("mobile_phone");
		if(job.has("truename")) truename = job.getString("truename");
		//only login has below
		if(job.has("birthday")) birthday = job.getString("birthday");
		if(job.has("user_money")) user_money = job.getString("user_money");
		if(job.has("rank_points")) rank_points = job.getInt("rank_points");
		if(job.has("user_rank")) user_rank = job.getInt("user_rank");
		if(job.has("is_courier")) is_courier = job.getInt("is_courier");
		if(job.has("reg_time")) reg_time = job.getLong("reg_time");
		if(job.has("last_login")) last_login = job.getLong("last_login");
		
	}

	public User() {
		super();
		
	}

	public User(JSONObject job) {
		super(job);
	}

	
	
	public final String getBirthday() {
		if(null==birthday) birthday="";;
		return birthday;
	}
	

	public final void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getUser_id() {
		return user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public int getSex() {
		return sex;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public String getTruename() {
		return truename;
	}

	public int getRank_points() {
		return rank_points;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public void setRank_points(int rank_points) {
		this.rank_points = rank_points;
	}

	public String getUser_money() {
		return user_money;
	}

	public int getPay_points() {
		return pay_points;
	}

	public long getReg_time() {
		return reg_time;
	}

	public long getLast_login() {
		return last_login;
	}

	public int getUser_rank() {
		return user_rank;
	}

	public int getIs_courier() {
		return is_courier;
	}
	public boolean isCourier(){
		return is_courier==1;
	}

	public void setUser_money(String user_money) {
		this.user_money = user_money;
	}

	public void setPay_points(int pay_points) {
		this.pay_points = pay_points;
	}

	public void setReg_time(long reg_time) {
		this.reg_time = reg_time;
	}

	public void setLast_login(long last_login) {
		this.last_login = last_login;
	}

	public void setUser_rank(int user_rank) {
		this.user_rank = user_rank;
	}

	public void setIs_courier(int is_courier) {
		this.is_courier = is_courier;
	}
	public void setIs_courier(boolean iscourier) {
		is_courier = iscourier?1:0;
	}
}

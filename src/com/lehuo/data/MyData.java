package com.lehuo.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.lehuo.vo.Category;
import com.lehuo.vo.Product;
import com.lehuo.vo.User;
import com.lehuo.vo.UserAddress;
import com.lehuo.vo.cart.DataCart;

import android.widget.TabHost;

public class MyData {
	private static MyData data;
	private MyData(){}
	public static MyData data(){
		if(null==data) data = new MyData();
		return data;
	}
	
	TabHost mTabHost;
	public void setTabHost(TabHost mTabHost) {
		this.mTabHost = mTabHost;
	}
	public TabHost getTabHost(){
		return mTabHost;
	}
	
	
	List<Category> categoryList;
	public List<Category> getAllCate() {
		if(null==categoryList) categoryList = new LinkedList<Category>();
		return categoryList;
	}
	Category currentCategory;
	public void storeCategory(Category category) {
		currentCategory = category;
	}
	public Category fetchCategory(){
		return currentCategory;
	}
	Product currentProduct;
	public void storeProduct(Product product) {
		currentProduct = product;
	}
	public Product fetchProduct(){
		return currentProduct;
	}
	
	User user;
	public void setCurrentUser(User user) {
		this.user = user;
	}
	public User getMe(){
		return user;
	}
	
	int cartCount;
	public int getCartCount() {
		return cartCount;
	}
	public void setCartCount(int cc){
		cartCount = cc;
	}
	
	DataCart currentCart;
	public void setCurrentCart(DataCart dc) {
		currentCart = dc;
	}
	public DataCart getCurrentCart(){
		return currentCart;
	}
	
	List<UserAddress> addressList;
	public void setMyAddrs(List<UserAddress> addressList) {
		this.addressList = addressList;
		
	}
	public List<UserAddress> getMyAddrs(){
		if(null==addressList) addressList = new ArrayList<UserAddress>();
		return addressList;
	}
	public UserAddress getMyDefaultAddr(){
		if(getMyAddrs().size()==0	)return null;
		UserAddress dAddr = addressList.get(0);
		for(UserAddress addr:addressList	){
			if(addr.isDefault()){
				dAddr = addr;
				break;
			}
		}
		return dAddr;
	}
	
}

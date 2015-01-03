package com.lehuozu.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.support.v4.widget.DrawerLayout;
import android.widget.TabHost;

import com.lehuozu.util.LoginKeeper;
import com.lehuozu.vo.Category;
import com.lehuozu.vo.Product;
import com.lehuozu.vo.User;
import com.lehuozu.vo.UserAddress;
import com.lehuozu.vo.cart.DataCart;

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
	private DrawerLayout mDrawer;
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
	public void setDrawer(DrawerLayout mDrawer) {
		this.mDrawer = mDrawer;
	}
	public DrawerLayout getDrawer() {
		return mDrawer;
	}
	
	public void destroy(){
		data = null;
	}
}

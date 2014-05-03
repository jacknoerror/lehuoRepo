package com.lehuo.data;

import java.util.LinkedList;
import java.util.List;

import com.lehuo.vo.Category;
import com.lehuo.vo.Product;
import com.lehuo.vo.User;

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
	public User getCurrentUser(){
		return user;
	}
	
	int cartCount;
	public int getCartCount() {
		return cartCount;
	}
	public void setCartCount(int cc){
		cartCount = cc;
	}
	
}

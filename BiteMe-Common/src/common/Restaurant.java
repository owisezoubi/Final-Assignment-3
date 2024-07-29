package common;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private static final long serialVersionUID = 1L;
    private String restaurant_id;
    private String user_name;
    private String name;
    private String branchName;
    private Menu menu;
    
    
    
    
	
	public Restaurant(String restaurant_id, String user_name, String name, String branchName, Menu menu) {
		super();
		this.restaurant_id = restaurant_id;
		this.user_name = user_name;
		this.name = name;
		this.branchName = branchName;
		this.menu = menu;
	}
	
	
	public String getRestaurant_id() {
		return restaurant_id;
	}
	public void setRestaurant_id(String restaurant_id) {
		this.restaurant_id = restaurant_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	
	@Override
	public String toString() {
		return "Restaurant [restaurant_id=" + restaurant_id + ", user_name=" + user_name + ", name=" + name
				+ ", branchName=" + branchName + ", menu=" + menu + "]";
	}
	
}
    
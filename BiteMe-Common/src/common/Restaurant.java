package common;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String user_name;
    private String password;
    private String is_logged_in;
    private String user_type;
    private String home_branch;
    private String restaurant_name;
    private String menu_id;
    private String phone_number; 
    private String email;
    
    
    
    
    
	public Restaurant(String id, String user_name, String password, String is_logged_in, String user_type,
			String home_branch, String restaurant_name, String menu_id, String phone_number, String email) {
		super();
		this.id = id;
		this.user_name = user_name;
		this.password = password;
		this.is_logged_in = is_logged_in;
		this.user_type = user_type;
		this.home_branch = home_branch;
		this.restaurant_name = restaurant_name;
		this.menu_id = menu_id;
		this.phone_number = phone_number;
		this.email = email;
	}
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIs_logged_in() {
		return is_logged_in;
	}
	public void setIs_logged_in(String is_logged_in) {
		this.is_logged_in = is_logged_in;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getHome_branch() {
		return home_branch;
	}
	public void setHome_branch(String home_branch) {
		this.home_branch = home_branch;
	}
	public String getRestaurant_name() {
		return restaurant_name;
	}
	public void setRestaurant_name(String restaurant_name) {
		this.restaurant_name = restaurant_name;
	}
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", user_name=" + user_name + ", password=" + password + ", is_logged_in="
				+ is_logged_in + ", user_type=" + user_type + ", home_branch=" + home_branch + ", restaurant_name="
				+ restaurant_name + ", menu=" + menu_id + ", phone_number=" + phone_number + ", email=" + email + "]";
	}

    
	
	

    
    
    
	
	
}
    
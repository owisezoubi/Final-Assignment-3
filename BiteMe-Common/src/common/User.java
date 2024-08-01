package common;

import java.io.Serializable;

public class User {
	private String id;
	private String user_name;
	private String password;
	private String is_logged_in;
	private String user_type;
	private String home_branch;
	
	
	public User(String id, String user_name, String password, String is_logged_in, String user_type,
			String home_branch) {
		super();
		this.id = id;
		this.user_name = user_name;
		this.password = password;
		this.is_logged_in = is_logged_in;
		this.user_type = user_type;
		this.home_branch = home_branch;
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
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", user_name=" + user_name + ", password=" + password + ", is_logged_in="
				+ is_logged_in + ", user_type=" + user_type + ", home_branch=" + home_branch + "]";
	}
	
	
	
	
	
	
	
}
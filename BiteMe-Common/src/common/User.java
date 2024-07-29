package common;

import java.io.Serializable;

public class User {
	private String id;
	private String user_name;
	private String password;
	private String is_logged_in;
	private String user_type;
	private String first_name;
	private String last_name;
	private String phone_number;
	private String home_branch;
	private String email;
	
	
	public User(String id, String user_name, String password, String is_logged_in, String user_type, String first_name, String last_name,
			String phone_number, String home_branch, String email) {
		super();
		this.id = id;
		this.user_name = user_name;
		this.password = password;
		this.is_logged_in = is_logged_in;
		this.user_type = user_type;
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone_number = phone_number;
		this.home_branch = home_branch;
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
	
	public void setUser_name(String user_name_for_login) {
		this.user_name = user_name_for_login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirst_name() {
		return first_name;
	}
	
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	public String getLast_name() {
		return last_name;
	}
	
	public void setLast_name(String last_name) {
		this.last_name = last_name;
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
	
	public String getIs_logged_in() {
		return is_logged_in;
	}
	
	public void setIs_logged_in(String is_logged_in) {
		this.is_logged_in = is_logged_in;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", user_name=" + user_name + ", password=" + password + ", is_logged_in="
				+ is_logged_in + ", user_type=" + user_type + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", phone_number=" + phone_number + ", home_branch=" + home_branch + ", email=" + email + "]";
	}
	
	
}
package common;

import java.io.Serializable;

public class User {
	private String id;
	private String user_name;
	private String password;
	private String first_name;
	private String last_name;
	private String phone_number;
	private String email;
	private String position;
	private String home_branch;
	private int is_logged_in;
	
	public User(String id, String user_name, String password, String first_name, String last_name,
			String phone_number, String email, String position, String home_branch, int is_logged_in) {
		super();
		this.id = id;
		this.user_name = user_name;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone_number = phone_number;
		this.email = email;
		this.position = position;
		this.home_branch = home_branch;
		this.is_logged_in = is_logged_in;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUser_name_for_login() {
		return user_name;
	}
	
	public void setUser_name_for_login(String user_name_for_login) {
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
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getHome_branch() {
		return home_branch;
	}
	
	public void setHome_branch(String home_branch) {
		this.home_branch = home_branch;
	}
	
	public int getIs_logged_in() {
		return is_logged_in;
	}
	
	public void setIs_logged_in(int is_logged_in) {
		this.is_logged_in = is_logged_in;
	}
	
	@Override
	public String toString() {
	    return getClass().getSimpleName() + " [id=" + id + ", user_name_for_login=" + user_name + ", password=" + password
	      + ", first_name=" + first_name + ", last_name=" + last_name + ", phone_number=" + phone_number
	      + ", email=" + email + ", position=" + position + ", home_branch=" + home_branch + ", is_logged_in="
	      + is_logged_in + "]";
	}
}
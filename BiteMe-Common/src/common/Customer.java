package common;

import java.io.Serializable;

public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String user_name;
	private String password;
	private String is_logged_in;
	private String user_type;
	private String home_branch;
	private String first_name;
	private String last_name;
	private String phone_number;
	private String email;
	private String credit_card_number;
	private String credit_card_cvv;
	private String credit_card_month;
	private String credit_card_year;
	private String is_eligible_for_refund;
	private String customer_type;

	public Customer(String id, String user_name, String password, String is_logged_in, String user_type,
			String home_branch, String first_name, String last_name, String phone_number, String email,
			String credit_card_number, String credit_card_cvv, String credit_card_month, String credit_card_year,
			String is_eligible_for_refund, String customer_type) {
		super();
		this.id = id;
		this.user_name = user_name;
		this.password = password;
		this.is_logged_in = is_logged_in;
		this.user_type = user_type;
		this.home_branch = home_branch;
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone_number = phone_number;
		this.email = email;
		this.credit_card_number = credit_card_number;
		this.credit_card_cvv = credit_card_cvv;
		this.credit_card_month = credit_card_month;
		this.credit_card_year = credit_card_year;
		this.is_eligible_for_refund = is_eligible_for_refund;
		this.customer_type = customer_type;
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

	public String getCredit_card_number() {
		return credit_card_number;
	}

	public void setCredit_card_number(String credit_card_number) {
		this.credit_card_number = credit_card_number;
	}

	public String getCredit_card_cvv() {
		return credit_card_cvv;
	}

	public void setCredit_card_cvv(String credit_card_cvv) {
		this.credit_card_cvv = credit_card_cvv;
	}

	public String getCredit_card_month() {
		return credit_card_month;
	}

	public void setCredit_card_month(String credit_card_month) {
		this.credit_card_month = credit_card_month;
	}

	public String getCredit_card_year() {
		return credit_card_year;
	}

	public void setCredit_card_year(String credit_card_year) {
		this.credit_card_year = credit_card_year;
	}

	public String getIs_eligible_for_refund() {
		return is_eligible_for_refund;
	}

	public void setIs_eligible_for_refund(String is_eligible_for_refund) {
		this.is_eligible_for_refund = is_eligible_for_refund;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", user_name=" + user_name + ", password=" + password + ", is_logged_in="
				+ is_logged_in + ", user_type=" + user_type + ", home_branch=" + home_branch + ", first_name="
				+ first_name + ", last_name=" + last_name + ", phone_number=" + phone_number + ", email=" + email
				+ ", credit_card_number=" + credit_card_number + ", credit_card_cvv=" + credit_card_cvv
				+ ", credit_card_month=" + credit_card_month + ", credit_card_year=" + credit_card_year
				+ ", is_eligible_for_refund=" + is_eligible_for_refund + ", customer_type=" + customer_type + "]";
	}

}

package common;

import java.io.Serializable;

public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
	private String order_id;
	private String restaurant_id;
	private String user_id;
	private String date;
	private String desired_time;
	private String arrival_time;
	private String price;
	private String restaurant_confirmed_receiving;
	private String customer_confirmed_receiving;
	private String is_ready;
	private String is_late;
	private String order_type;
	private String order_recieving_method;
	private String delivery_address;
	private String recipient_name;
	private String recipient_phone_number;

	public Order(String order_id, String restaurant_id, String user_id, String date, String desired_time,
			String arrival_time, String price, String restaurant_confirmed_receiving,
			String customer_confirmed_receiving, String is_ready, String is_late, String order_type,
			String order_recieving_method, String delivery_address, String recipient_name,
			String recipient_phone_number) {
		super();
		this.order_id = order_id;
		this.restaurant_id = restaurant_id;
		this.user_id = user_id;
		this.date = date;
		this.desired_time = desired_time;
		this.arrival_time = arrival_time;
		this.price = price;
		this.restaurant_confirmed_receiving = restaurant_confirmed_receiving;
		this.customer_confirmed_receiving = customer_confirmed_receiving;
		this.is_ready = is_ready;
		this.is_late = is_late;
		this.order_type = order_type;
		this.order_recieving_method = order_recieving_method;
		this.delivery_address = delivery_address;
		this.recipient_name = recipient_name;
		this.recipient_phone_number = recipient_phone_number;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getRestaurant_id() {
		return restaurant_id;
	}

	public void setRestaurant_id(String restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDesired_time() {
		return desired_time;
	}

	public void setDesired_time(String desired_time) {
		this.desired_time = desired_time;
	}

	public String getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRestaurant_confirmed_receiving() {
		return restaurant_confirmed_receiving;
	}

	public void setRestaurant_confirmed_receiving(String restaurant_confirmed_receiving) {
		this.restaurant_confirmed_receiving = restaurant_confirmed_receiving;
	}

	public String getCustomer_confirmed_receiving() {
		return customer_confirmed_receiving;
	}

	public void setCustomer_confirmed_receiving(String customer_confirmed_receiving) {
		this.customer_confirmed_receiving = customer_confirmed_receiving;
	}

	public String getIs_ready() {
		return is_ready;
	}

	public void setIs_ready(String is_ready) {
		this.is_ready = is_ready;
	}

	public String getIs_late() {
		return is_late;
	}

	public void setIs_late(String is_late) {
		this.is_late = is_late;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getOrder_recieving_method() {
		return order_recieving_method;
	}

	public void setOrder_recieving_method(String order_recieving_method) {
		this.order_recieving_method = order_recieving_method;
	}

	public String getDelivery_address() {
		return delivery_address;
	}

	public void setDelivery_address(String delivery_address) {
		this.delivery_address = delivery_address;
	}

	public String getRecipient_name() {
		return recipient_name;
	}

	public void setRecipient_name(String recipient_name) {
		this.recipient_name = recipient_name;
	}

	public String getRecipient_phone_number() {
		return recipient_phone_number;
	}

	public void setRecipient_phone_number(String recipient_phone_number) {
		this.recipient_phone_number = recipient_phone_number;
	}

	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", restaurant_id=" + restaurant_id + ", user_id=" + user_id + ", date="
				+ date + ", desired_time=" + desired_time + ", arrival_time=" + arrival_time + ", price=" + price
				+ ", restaurant_confirmed_receiving=" + restaurant_confirmed_receiving
				+ ", customer_confirmed_receiving=" + customer_confirmed_receiving + ", is_ready=" + is_ready
				+ ", is_late=" + is_late + ", order_type=" + order_type + ", order_recieving_method="
				+ order_recieving_method + ", delivery_address=" + delivery_address + ", recipient_name="
				+ recipient_name + ", recipient_phone_number=" + recipient_phone_number + "]";
	}

}
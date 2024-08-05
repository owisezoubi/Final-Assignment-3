package common;

public class Order {
    private String order_id;
    private String restaurant_id;
    private String user_id;
    private String date;
    private String start_time;
    private String arrival_time;
    private String price;
    private String total_price;
    private String restaurant_confirmed;
    private String customer_confirmed;
    private String is_ready;
    private String is_late;
    private String order_type;
    private String order_recieving_method;
    
    
    
	public Order(String order_id, String restaurant_id, String user_id, String date, String start_time,
			String arrival_time, String price, String total_price, String restaurant_confirmed,
			String customer_confirmed, String is_ready, String is_late, String order_type,
			String order_recieving_method) {
		super();
		this.order_id = order_id;
		this.restaurant_id = restaurant_id;
		this.user_id = user_id;
		this.date = date;
		this.start_time = start_time;
		this.arrival_time = arrival_time;
		this.price = price;
		this.total_price = total_price;
		this.restaurant_confirmed = restaurant_confirmed;
		this.customer_confirmed = customer_confirmed;
		this.is_ready = is_ready;
		this.is_late = is_late;
		this.order_type = order_type;
		this.order_recieving_method = order_recieving_method;
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
	
	public String getStart_time() {
		return start_time;
	}
	
	public void setStart_time(String start_time) {
		this.start_time = start_time;
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
	
	public String getTotal_price() {
		return total_price;
	}
	
	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}
	
	public String getRestaurant_confirmed() {
		return restaurant_confirmed;
	}
	
	public void setRestaurant_confirmed(String restaurant_confirmed) {
		this.restaurant_confirmed = restaurant_confirmed;
	}
	
	public String getCustomer_confirmed() {
		return customer_confirmed;
	}
	
	public void setCustomer_confirmed(String customer_confirmed) {
		this.customer_confirmed = customer_confirmed;
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
    
    
	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", restaurant_id=" + restaurant_id + ", user_id=" + user_id + ", date="
				+ date + ", start_time=" + start_time + ", arrival_time=" + arrival_time + ", price=" + price
				+ ", total_price=" + total_price + ", restaurant_confirmed=" + restaurant_confirmed
				+ ", customer_confirmed=" + customer_confirmed + ", is_ready=" + is_ready + ", is_late=" + is_late
				+ ", order_type=" + order_type + ", order_recieving_method=" + order_recieving_method + "]";
	}

}
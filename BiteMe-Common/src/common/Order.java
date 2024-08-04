package common;

public class Order {
    private String orderId;
    private String restaurantId;
    private String userId;
    private String date;
    private String startTime;
    private String arrivalTime;
    private String price;
    private String totalPrice;
    private String restaurantConfirmed;
    private String customerConfirmed;
    private String isReady;
    private String isLate;

    // Default constructor
    public Order() {
    }

    // Parameterized constructor
    public Order(String orderId, String restaurantId, String userId, String date, 
                 String startTime, String arrivalTime, String price, String totalPrice, 
                 String restaurantConfirmed, String customerConfirmed, 
                 String isReady, String isLate) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.date = date;
        this.startTime = startTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.totalPrice = totalPrice;
        this.restaurantConfirmed = restaurantConfirmed;
        this.customerConfirmed = customerConfirmed;
        this.isReady = isReady;
        this.isLate = isLate;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRestaurantConfirmed() {
        return restaurantConfirmed;
    }

    public void setRestaurantConfirmed(String restaurantConfirmed) {
        this.restaurantConfirmed = restaurantConfirmed;
    }

    public String getCustomerConfirmed() {
        return customerConfirmed;
    }

    public void setCustomerConfirmed(String customerConfirmed) {
        this.customerConfirmed = customerConfirmed;
    }

    public String getIsReady() {
        return isReady;
    }

    public void setIsReady(String isReady) {
        this.isReady = isReady;
    }

    public String getIsLate() {
        return isLate;
    }

    public void setIsLate(String isLate) {
        this.isLate = isLate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", userId='" + userId + '\'' +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", price='" + price + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", restaurantConfirmed='" + restaurantConfirmed + '\'' +
                ", customerConfirmed='" + customerConfirmed + '\'' +
                ", isReady='" + isReady + '\'' +
                ", isLate='" + isLate + '\'' +
                '}';
    }
}

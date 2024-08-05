package common;

import java.io.Serializable;

public class QuarterlyOrdersReport implements Serializable {
    private static final long serialVersionUID = 1L;
	private String orderDate;
    private String restaurantName;
    private int numberOfOrders;

    public QuarterlyOrdersReport(String orderDate, String restaurantName, int numberOfOrders) {
        this.orderDate = orderDate;
        this.restaurantName = restaurantName;
        this.numberOfOrders = numberOfOrders;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getNumberOfOrders() {
        return numberOfOrders; 
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

	@Override
	public String toString() {
		return "QuarterlyOrdersReport [orderDate=" + orderDate + ", restaurantName=" + restaurantName
				+ ", numberOfOrders=" + numberOfOrders + "]";
	}

    
}

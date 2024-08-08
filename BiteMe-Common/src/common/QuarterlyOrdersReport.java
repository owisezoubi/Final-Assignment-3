package common;

import java.io.Serializable;

public class QuarterlyOrdersReport implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String restaurantName;
    private int ordersMonth1;
    private int ordersMonth2;
    private int ordersMonth3;

    // Constructor
    public QuarterlyOrdersReport(String restaurantName, int ordersMonth1, int ordersMonth2, int ordersMonth3) {
        this.restaurantName = restaurantName;
        this.ordersMonth1 = ordersMonth1;
        this.ordersMonth2 = ordersMonth2;
        this.ordersMonth3 = ordersMonth3;
    }

    // Getters and Setters
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getOrdersMonth1() {
        return ordersMonth1;
    }

    public void setOrdersMonth1(int ordersMonth1) {
        this.ordersMonth1 = ordersMonth1;
    }

    public int getOrdersMonth2() {
        return ordersMonth2;
    }

    public void setOrdersMonth2(int ordersMonth2) {
        this.ordersMonth2 = ordersMonth2;
    }

    public int getOrdersMonth3() {
        return ordersMonth3;
    }

    public void setOrdersMonth3(int ordersMonth3) {
        this.ordersMonth3 = ordersMonth3;
    }

    @Override
    public String toString() {
        return "QuarterlyOrdersReport{" +
               "restaurantName='" + restaurantName + '\'' +
               ", ordersMonth1=" + ordersMonth1 +
               ", ordersMonth2=" + ordersMonth2 +
               ", ordersMonth3=" + ordersMonth3 +
               '}';
    }
}

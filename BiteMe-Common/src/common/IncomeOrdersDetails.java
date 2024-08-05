package common;

import java.io.Serializable;

public class IncomeOrdersDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String orderDate;
    private int numberOfOrders;
    private double totalPrice;

    public IncomeOrdersDetails(String orderDate, int numberOfOrders, double totalPrice) {
        this.orderDate = orderDate;
        this.numberOfOrders = numberOfOrders;
        this.totalPrice = totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "IncomeOrdersDetails{" +
                "orderDate='" + orderDate + '\'' +
                ", numberOfOrders=" + numberOfOrders +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

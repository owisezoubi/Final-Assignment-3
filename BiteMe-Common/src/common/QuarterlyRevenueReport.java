package common;

import java.io.Serializable;

public class QuarterlyRevenueReport implements Serializable {

    private static final long serialVersionUID = 1L;

    private String restaurantName;
    private double revenueMonth1;
    private double revenueMonth2;
    private double revenueMonth3;

    /**
     * Constructs a QuarterlyRevenueReport with specified revenues for each month.
     * 
     * @param restaurantName The name of the restaurant.
     * @param revenueMonth1 Revenue for the first month of the quarter.
     * @param revenueMonth2 Revenue for the second month of the quarter.
     * @param revenueMonth3 Revenue for the third month of the quarter.
     */
    public QuarterlyRevenueReport(String restaurantName, double revenueMonth1, double revenueMonth2, double revenueMonth3) {
        this.restaurantName = restaurantName;
        this.revenueMonth1 = revenueMonth1;
        this.revenueMonth2 = revenueMonth2;
        this.revenueMonth3 = revenueMonth3;
    }

    /**
     * Gets the name of the restaurant.
     * 
     * @return The restaurant name.
     */
    public String getRestaurantName() {
        return restaurantName;
    }

    /**
     * Gets the revenue for the first month of the quarter.
     * 
     * @return The revenue for month 1.
     */
    public double getRevenueMonth1() {
        return revenueMonth1;
    }

    /**
     * Gets the revenue for the second month of the quarter.
     * 
     * @return The revenue for month 2.
     */
    public double getRevenueMonth2() {
        return revenueMonth2;
    }

    /**
     * Gets the revenue for the third month of the quarter.
     * 
     * @return The revenue for month 3.
     */
    public double getRevenueMonth3() {
        return revenueMonth3;
    }

    /**
     * Provides a string representation of the quarterly revenue report.
     *
     * @return A string detailing the restaurant's revenue report.
     */
    @Override
    public String toString() {
        return "QuarterlyRevenueReport{" +
               "restaurantName='" + restaurantName + '\'' +
               ", revenueMonth1=" + revenueMonth1 +
               ", revenueMonth2=" + revenueMonth2 +
               ", revenueMonth3=" + revenueMonth3 +
               '}';
    }
}

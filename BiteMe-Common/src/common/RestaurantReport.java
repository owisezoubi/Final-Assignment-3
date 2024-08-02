package common;

import java.io.Serializable;

public class RestaurantReport implements Serializable {
    private static final long serialVersionUID = 1L;
    private String month;
    private String year;
    private String restaurant_name;
    private String branch;

    public RestaurantReport(String month,String year, String restaurant_name, String branch) {
        super();
        this.month = month;
        this.restaurant_name = restaurant_name;
        this.branch = branch;
        this.year=year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "RestaurantReport [month=" + month + ", year=" + year + ", restaurant_name=" + restaurant_name
                + ", branch=" + branch + "]";
    }

}
package common;

import java.io.Serializable;

public class PerformanceReport implements Serializable{

	private static final long serialVersionUID = 1L;
	private String date;
    private int lateOrders;
    private int notLateOrders;

    public PerformanceReport(String date, int lateOrders, int notLateOrders) {
        this.date = date;
        this.lateOrders = lateOrders;
        this.notLateOrders = notLateOrders;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLateOrders() {
        return lateOrders;
    }

    public void setLateOrders(int lateOrders) {
        this.lateOrders = lateOrders;
    }

    public int getNotLateOrders() {
        return notLateOrders;
    }

    public void setNotLateOrders(int notLateOrders) {
        this.notLateOrders = notLateOrders;
    }

    @Override
    public String toString() {
        return "PerformanceReport{" +
                "date='" + date + '\'' +
                ", lateOrders=" + lateOrders +
                ", notLateOrders=" + notLateOrders +
                '}';
    }
}

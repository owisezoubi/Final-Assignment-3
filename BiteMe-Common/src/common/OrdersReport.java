package common;

import java.io.Serializable;

public class OrdersReport implements Serializable {

    private static final long serialVersionUID = 1L;
    private String date;
    private String mainDish;
    private String salad;
    private String drink;
    private String dessert;

    public OrdersReport(String date, String mainDish, String salad, String drink, String dessert) {
        this.date = date;
        this.mainDish = mainDish;
        this.salad = salad;
        this.drink = drink;
        this.dessert = dessert;
    }

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMainDish() {
		return mainDish;
	}

	public void setMainDish(String mainDish) {
		this.mainDish = mainDish;
	}

	public String getSalad() {
		return salad;
	}

	public void setSalad(String salad) {
		this.salad = salad;
	}

	public String getDrink() {
		return drink;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}

	public String getDessert() {
		return dessert;
	}

	public void setDessert(String dessert) {
		this.dessert = dessert;
	}

	@Override
	public String toString() {
		return "OrdersReport [date=" + date + ", mainDish=" + mainDish + ", salad=" + salad + ", drink=" + drink
				+ ", dessert=" + dessert + "]";
	}

    
}

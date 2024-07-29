package common;

import java.io.Serializable;
import java.util.Arrays;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    public enum Category {
        SALAD, DRINK, DESSERT, MAIN_DISH
    }

    private int item_id;
    private String item_name;
    private Double item_price;
    private String[] optional_additions;
    private Category category;
    private static int current_item_id = 1;

    // Constructor
    public Item(String item_name, Double item_price, String[] optional_additions, Category category) {
        this.item_id = current_item_id++;
        this.item_name = item_name;
        this.item_price = item_price;
        this.optional_additions = optional_additions;
        this.category = category;
    }

    // Getters and setters
    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Double getItem_price() {
        return item_price;
    }

    public void setItem_price(Double item_price) {
        this.item_price = item_price;
    }

    public String[] getOptional_additions() {
        return optional_additions;
    }

    public void setOptional_additions(String[] optional_additions) {
        this.optional_additions = optional_additions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Item [item_id=" + item_id + ", item_name=" + item_name + ", item_price=" + item_price
                + ", optional_additions=" + Arrays.toString(optional_additions) + ", category=" + category + "]";
    }
}

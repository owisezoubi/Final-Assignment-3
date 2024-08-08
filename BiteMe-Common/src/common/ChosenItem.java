package common;

import java.io.Serializable;
import java.util.ArrayList;

public class ChosenItem implements Serializable {
    private static final long serialVersionUID = 1L;
	private Item item;
    private ArrayList<String> item_additions;

    public ChosenItem(Item item, ArrayList<String> item_additions) {
        this.item = item;
        this.item_additions = item_additions;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ArrayList<String> getItem_additions() {
        return item_additions;
    }

    public void setItem_additions(ArrayList<String> item_additions) {
        this.item_additions = item_additions;
    }

    // Getter methods for TableView
    public String getItemName() {
        return item.getItem_name();
    }

    public String getItemPrice() {
        return item.getItem_price();
    }

    public String getItemCategory() {
        return item.getCategory();
    }

    @Override
    public String toString() {
        return "ChosenItem [item=" + item + ", item_additions=" + item_additions + "]";
    }
}

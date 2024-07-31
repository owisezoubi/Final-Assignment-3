package common;

import java.io.Serializable;
import java.util.Arrays;

public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    private int menu_id;
    private Item[] items;
    private int number_of_items;
    private static int current_menu_id=1;

    public Menu(Item[] items) {
        this.menu_id =current_menu_id++;
        this.items = items;
        number_of_items+=items.length;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public int getNumber_of_items() {
        return number_of_items;
    }

    public void setNumber_of_items(int number_of_items) {
        this.number_of_items = number_of_items;
    }

    @Override
    public String toString() {
        return "Menu [menu_id=" + menu_id + ", items=" + Arrays.toString(items) + ", number_of_items=" + number_of_items
                + "]";
    }

}

package common;

import java.io.Serializable;
import java.util.Arrays;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private String item_id;
    private String item_name;
    private String description;
    private String item_price;
    private String category;
      
    
    
	public Item(String item_id, String item_name, String description, String item_price, String category) {
		super();
		this.item_id = item_id;
		this.item_name = item_name;
		this.description = description;
		this.item_price = item_price;
		this.category = category;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItem_price() {
		return item_price;
	}

	public void setItem_price(String item_price) {
		this.item_price = item_price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Item [item_id=" + item_id + ", item_name=" + item_name + ", description=" + description
				+ ", item_price=" + item_price + ", category=" + category + "]";
	}

   
}

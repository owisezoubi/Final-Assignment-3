package common;

public class ChosenItem {
	private Item item;
    private String item_additions;
    
    
    
	
	public ChosenItem(Item item, String item_additions) {
		super();
		this.item = item;
		this.item_additions = item_additions;
	}
	
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public String getItem_additions() {
		return item_additions;
	}
	public void setItem_additions(String item_additions) {
		this.item_additions = item_additions;
	}
    
	
	@Override
	public String toString() {
		return "ChosenItem [item=" + item + ", item_additions=" + item_additions + "]";
	}
}
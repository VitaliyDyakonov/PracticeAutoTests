package UItest.PageObjects.SwagLabs;

public class Item {

    private String itemName = "div[data-test='inventory-item-name']";
    private String itemDesc = "div[data-test='inventory-item-desc']";
    private String itemPrice = "div[data-test='inventory-item-price']";
    private String addButton = ".btn.btn_primary.btn_small.btn_inventory";
    private String removeButton = "button[data-test='remove-sauce-labs-backpack']";

    public String getItemName() {
        return itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getAddButton() {
        return addButton;
    }

    public String getRemoveButton() {
        return removeButton;
    }
}

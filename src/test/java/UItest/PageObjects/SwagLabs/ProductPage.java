package UItest.PageObjects.SwagLabs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;

import static com.codeborne.selenide.Selenide.*;

public class ProductPage extends BasePage{

    private SelenideElement sortButton = $(".product_sort_container");

    private SelenideElement cart = $(".shopping_cart_link");

    private ElementsCollection itemList = $$(".inventory_item");

    private Item item = new Item();

    private SelenideElement cartCount = $(".shopping_cart_badge");

    public static void openPage(){open(getProductUrl());}

    public ElementsCollection getItemList() {
        return itemList;
    }

    public Item getItem() {
        return item;
    }
    public SelenideElement getCartCount() {
        return cartCount;
    }
    public ProductPage sortItem(String filter){
        Allure.step(String.format("Отфильтровать список продуктов по [%s]",filter), ()-> sortButton.selectOption(filter));
        return this;
    }

    public ProductPage goToCart(){
        Allure.step("Перейти в корзину", ()->cart.click());
        return this;
    }

}

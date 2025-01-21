package UItest.PageObjects.SwagLabs;

import com.codeborne.selenide.*;
import io.qameta.allure.Allure;

import static com.codeborne.selenide.Selenide.*;

public class CartPage extends BasePage {

    private ElementsCollection itemList = $$(".cart_item");
    private Item item = new Item();
    private SelenideElement cartCount = $(".shopping_cart_badge");
    private SelenideElement continueShopping = $("#continue-shopping");
    private SelenideElement checkout = $("#checkout");

    public Item getItem() {
        return item;
    }

    public ElementsCollection getItemList() {
        return itemList;
    }

    public static void openPage(){open(getCartUrl());}

    public ProductPage backShopping(){
        Allure.step("Вернуться к списку продуктов",()-> continueShopping.click());
        return new ProductPage();
    }

    public void goToCheckout(){

    }
}

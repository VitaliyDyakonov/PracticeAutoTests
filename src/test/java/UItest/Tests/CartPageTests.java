package UItest.Tests;

import UItest.PageObjects.SwagLabs.CartPage;
import UItest.PageObjects.TestBase;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CartPageTests extends TestBase {

    CartPage cartPage = new CartPage();

    @BeforeAll
    static void setUp(){
        setup();
        CartPage.setCookies();
    }

    @BeforeEach
    public void prepareForTest(){
        Selenide.open("https://www.saucedemo.com/");
        Selenide.webdriver().driver().getWebDriver().manage().addCookie(CartPage.cookies);
        Selenide.localStorage().setItem("cart-contents", "[4,0,5,1,2,3]");
        CartPage.openPage();
    }

    @AfterEach
    public void closePage(){
        closeWindow();
    }

    @Test
    void openItemList() {
        $(".title").shouldHave(text("Your Cart"));
        Allure.step("Открыть корзину",()->{
            cartPage.getItemList().stream().forEach(item -> item.$(cartPage.getItem().getRemoveButton()).shouldHave(text("Remove")));
        });
        Allure.step("Открыть список продуктов",()->{
            cartPage.backShopping().getItemList().stream().forEach(item -> item.$(cartPage.getItem().getRemoveButton()).shouldHave(text("Remove")));
        });
    }

    @Test
    void removeItemsFromCart() {
        while (cartPage.getItemList().size() != 0){
            String removedItemName = cartPage.getItemList().get(0).$(cartPage.getItem().getItemName()).text();
            Allure.step("Удалить продукт из корзины",()->{
                cartPage.getItemList().get(0).$(cartPage.getItem().getRemoveButton()).click();
            });
            Allure.step("Открыть список продуктов", ()->{
                cartPage.backShopping().getItemList().stream().filter(item -> item.$(cartPage.getItem().getItemName()).text().equals(removedItemName)).toList().get(0).$(cartPage.getItem().getAddButton()).shouldHave(text("Add to cart"));
                $(".shopping_cart_link").click();
            });

        }
    }

    @Test
    void goToCheckout() {
        cartPage.goToCheckout();
    }
}

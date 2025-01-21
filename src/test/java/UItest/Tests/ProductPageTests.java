package UItest.Tests;

import UItest.PageObjects.SwagLabs.DetailedItemPage;
import UItest.PageObjects.SwagLabs.ProductPage;
import UItest.PageObjects.SwagLabs.Sidebar;
import UItest.PageObjects.TestBase;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProductPageTests extends TestBase {

    ProductPage productPage = new ProductPage();


    @BeforeAll
    static void setUp(){
        setup();
        ProductPage.setCookies();
    }

    @BeforeEach
    public void prepareForTest(){
        Selenide.open("https://www.saucedemo.com/");
        Selenide.webdriver().driver().getWebDriver().manage().addCookie(ProductPage.cookies);
        ProductPage.openPage();
    }

    @AfterEach
    public void closePage(){
        closeWindow();
    }

    @Test
    void openSidebar() {
        Sidebar sidebar = new Sidebar();
        sidebar.openBurger();
        sidebar.getItemsSidebar().shouldBe(visible, enabled);
        sidebar.getAboutSidebar().shouldBe(visible, enabled);
        sidebar.getLogoutSidebar().shouldBe(visible, enabled);
        sidebar.getResetSidebar().shouldBe(visible, enabled);
        sidebar.getCloseSidebar().shouldBe(visible, enabled);
    }

    @Test
    void closeSidebar() {
        Sidebar sidebar = new Sidebar();
        sidebar.openBurger();
        sidebar.closeBurger();
        sidebar.getItemsSidebar().shouldNotBe(visible);
        sidebar.getAboutSidebar().shouldNotBe(visible);
        sidebar.getLogoutSidebar().shouldNotBe(visible);
        sidebar.getResetSidebar().shouldNotBe(visible);
        sidebar.getCloseSidebar().shouldNotBe(visible);
    }

    @Test
    void goAboutPage() {
        Sidebar sidebar = new Sidebar();
        sidebar.openBurger();
        sidebar.openAbout();
    }

    @Test
    void goToAuthPage() {
        Sidebar sidebar = new Sidebar();
        sidebar.openBurger();
        sidebar.logout().getLoginButton().shouldBe(visible, enabled);
    }

    @Test
    void openItems() {
        DetailedItemPage detailedItemPage = new DetailedItemPage();
        for (int id=0; id < productPage.getItemList().size(); id++){
            String name = productPage.getItemList().get(id).$(productPage.getItem().getItemName()).text();
            String desc = productPage.getItemList().get(id).$(productPage.getItem().getItemDesc()).text();
            String price = productPage.getItemList().get(id).$(productPage.getItem().getItemPrice()).text();
            int finalId = id;
            Allure.step(String.format("Открыть детальную информацию по продукту: %s",name),()->{
            productPage.getItemList().get(finalId).$(productPage.getItem().getItemName()).click();
            $(detailedItemPage.getItem().getItemName()).shouldHave(text(name));
            $(detailedItemPage.getItem().getItemDesc()).shouldHave(text(desc));
            $(detailedItemPage.getItem().getItemPrice()).shouldHave(text(price));
            detailedItemPage.backToProductList();
            });
        }
    }

    @Test
    void filterByName() {
        productPage.sortItem("Name (A to Z)");
        List<String> sortedNames = productPage.getItemList().stream().map(item -> item.$(productPage.getItem().getItemName())).map(item -> item.text()).sorted().toList();
        for (int id=0; id < productPage.getItemList().size(); id++){
            productPage.getItemList().get(id).$(productPage.getItem().getItemName()).shouldHave(text(sortedNames.get(id)));
        }
        productPage.sortItem("Name (Z to A)");
        List<String> reverseSortedNames = productPage.getItemList().stream().map(item -> item.$(productPage.getItem().getItemName())).map(item -> item.text()).sorted(Comparator.reverseOrder()).toList();
        for (int id=0; id < productPage.getItemList().size(); id++){
            productPage.getItemList().get(id).$(productPage.getItem().getItemName()).shouldHave(text(reverseSortedNames.get(id)));
        }
    }

    @Test
    void filterByPrice() {
        productPage.sortItem("Price (low to high)");
        List<Float> sortedPrice = productPage.getItemList().stream().map(item -> item.$(productPage.getItem().getItemPrice())).map(item -> item.text()).map(item -> Float.parseFloat(item.substring(1))).sorted().toList();
        for (int id=0; id < productPage.getItemList().size(); id++){
            productPage.getItemList().get(id).$(productPage.getItem().getItemPrice()).shouldHave(text("$"+sortedPrice.get(id)));
        }
        productPage.sortItem("Price (high to low)");
        List<Float> reverseSortedPrice = productPage.getItemList().stream().map(item -> item.$(productPage.getItem().getItemPrice())).map(item -> item.text()).map(item -> Float.parseFloat(item.substring(1))).sorted(Comparator.reverseOrder()).toList();
        for (int id=0; id < productPage.getItemList().size(); id++){
            productPage.getItemList().get(id).$(productPage.getItem().getItemPrice()).shouldHave(text("$"+reverseSortedPrice.get(id)));
        }
    }

    @Test
    void addItemToCart() {
        DetailedItemPage detailedPage = new DetailedItemPage();
        for (int id=0; id < productPage.getItemList().size(); id++) {
            int finalId = id;
            Allure.step("Добавить продукт в корзину", ()->{
                productPage.getItemList().get(finalId).$(productPage.getItem().getAddButton()).shouldHave(text("Add to cart"));
                productPage.getItemList().get(finalId).$(productPage.getItem().getAddButton()).click();
                productPage.getItemList().get(finalId).$(productPage.getItem().getRemoveButton()).shouldHave(text("Remove"));
                productPage.getCartCount().shouldHave(text(""+(finalId +1)));
            });
            Allure.step("Открыть детальную карточку продукта", ()->{
                productPage.getItemList().get(finalId).$(productPage.getItem().getItemName()).click();
                $(detailedPage.getItem().getRemoveButton()).shouldHave(text("Remove"));
                detailedPage.backToProductList();
            });

        }
    }

    @Test
    void removeItemFromCart() {
        for (int id=0; id < productPage.getItemList().size(); id++) {
            productPage.getItemList().get(id).$(productPage.getItem().getAddButton()).click();
            productPage.getCartCount().shouldHave(text(""+(id+1)));
        }
        DetailedItemPage detailedPage = new DetailedItemPage();
        for (int id=productPage.getItemList().size()-1; id > 0; id--){
            int finalId = id;
            Allure.step("Открыть детальную карточку продукта", ()-> {
                productPage.getItemList().get(finalId).$(productPage.getItem().getItemName()).click();
            });
            Allure.step("Удалить продукт из корзины", ()-> {
                $(detailedPage.getItem().getRemoveButton()).shouldHave(text("Remove")).click();
                detailedPage.backToProductList();
            });
            productPage.getCartCount().shouldHave(text(""+id));
        }
    }
}

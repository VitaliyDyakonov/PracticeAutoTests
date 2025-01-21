package UItest.Tests;

import UItest.PageObjects.SwagLabs.CheckoutPage;
import UItest.PageObjects.SwagLabs.ProductPage;
import UItest.PageObjects.TestBase;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.InvalidStateError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CheckoutPageTests extends TestBase {

    CheckoutPage checkoutPage = new CheckoutPage();


    @BeforeAll
    static void setUp(){
        CheckoutPage.setCookies();
        setup();
    }

    @BeforeEach
    public void prepareForTest(){
        Selenide.open("https://www.saucedemo.com/");
        Selenide.webdriver().driver().getWebDriver().manage().addCookie(ProductPage.cookies);
        Selenide.localStorage().setItem("cart-contents", "[4,0,5,1,2,3]");
        CheckoutPage.openPage();
    }

    @AfterEach
    public void closePage(){
        closeWindow();
    }

    @Test
    void validateForm() {
        checkoutPage.submit();
        checkoutPage.getErrorContainer().shouldHave(text("Error: First Name is required"));
        checkoutPage.enterFirstName("test");
        checkoutPage.submit();
        checkoutPage.getErrorContainer().shouldHave(text("Error: Last Name is required"));
        checkoutPage.enterLastName("test");
        checkoutPage.submit();
        checkoutPage.getErrorContainer().shouldHave(text("Error: Postal Code is required"));
    }

    @Test
    void checkSum() {
        checkoutPage.enterFirstName("test");
        checkoutPage.enterLastName("test");
        checkoutPage.enterZipCode("test");
        checkoutPage.submit();
        $(".title").shouldHave(text("Checkout: Overview"));
        Float sum = (float) 0;
        for(int id=0; id< checkoutPage.getCartList().size();id++){
            sum += Float.parseFloat(checkoutPage.getCartList().get(id).$(checkoutPage.getItem().getItemPrice()).text().replace("$",""))*Integer.parseInt(checkoutPage.getCartList().get(id).$(checkoutPage.getQuantity()).text());
        }
        if(sum.equals(Float.parseFloat(checkoutPage.getItemTotal().text().replace("Item total: $","")))){}else{throw new InvalidStateError("Item total","should have value "+sum);}
    }

    @Test
    void finishCheckout() {
        checkoutPage.enterFirstName("test");
        checkoutPage.enterLastName("test");
        checkoutPage.enterZipCode("test");
        checkoutPage.submit();
        checkoutPage.finish();
        $(".complete-header").shouldHave(text("Thank you for your order!"));
        $(".complete-text").shouldHave(text("Your order has been dispatched, and will arrive just as fast as the pony can get there!"));
        $("#back-to-products").shouldBe(visible,enabled).shouldHave(text("Back Home"));
    }
}

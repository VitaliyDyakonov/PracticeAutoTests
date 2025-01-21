package UItest.PageObjects.SwagLabs;

import com.codeborne.selenide.Config;
import com.codeborne.selenide.SelenideConfig;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.*;

public class BasePage {

    private static String baseUrl = "https://www.saucedemo.com/";

    private static String cartUrl = "https://www.saucedemo.com/cart.html";

    private static String productUrl = "https://www.saucedemo.com/inventory.html";

    private static String checkoutUrl = "https://www.saucedemo.com/checkout-step-one.html";

    public static Cookie cookies;

    public static void setCookies(){
        open("https://www.saucedemo.com/");
        $("#user-name").setValue("standard_user");
        $("#password").setValue("secret_sauce");
        $("#login-button").click();
        cookies = webdriver().driver().getWebDriver().manage().getCookieNamed("session-username");
    }


    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getCartUrl() {
        return cartUrl;
    }

    public static String getProductUrl() {
        return productUrl;
    }

    public static String getCheckoutUrl() {
        return checkoutUrl;
    }
}

package UItest.Tests;

import UItest.PageObjects.SwagLabs.AuthPage;

import UItest.PageObjects.TestBase;
import com.codeborne.selenide.SetValueOptions;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AuthPageTests extends TestBase {

    AuthPage authPage = new AuthPage();

    @BeforeAll
    static void setUp(){
        setup();
    }

    @BeforeEach
    public void prepareForTest(){
        Allure.step("Открыть страницу авторизации", ()->{
            AuthPage.openPage();
        });
    }

    @AfterEach
    public void closePage(){
        Allure.step("Закрыть страницу", ()->{
            closeWindow();
        });
    }

    @Test
    @Tag("UI test")
    public void successfulAuth() {
        authPage.enterUsername("standard_user");
        authPage.getUsername().shouldHave(value("standard_user"));
        authPage.enterPassword("secret_sauce");
        authPage.login();
        $(".title").shouldHave(text("Products"));
    }

    @Test
    @Tag("UI test")
    public void errorAuth() {
        authPage.login();
        authPage.getErrorContainer().shouldHave(text("Epic sadface: Username is required"));
        authPage.enterUsername("test");
        authPage.login();
        authPage.getErrorContainer().shouldHave(text("Epic sadface: Password is required"));
        authPage.getUsername().setValue(SetValueOptions.withText(""));
        authPage.enterPassword("test");
        authPage.login();
        authPage.getErrorContainer().shouldHave(text("Epic sadface: Username and password do not match any user in this service"));
        authPage.enterUsername("locked_out_user");
        authPage.enterPassword("secret_sauce");
        authPage.login();
        authPage.getErrorContainer().shouldHave(text("Epic sadface: Sorry, this user has been locked out."));
    }

    @Test
    @Tag("UI test")
    public void closingError() {
        authPage.login();
        authPage.getUsername().shouldHave(cssValue("border-bottom-color", "rgba(226, 35, 26, 1)"));
        authPage.getPassword().shouldHave(cssValue("border-bottom-color", "rgba(226, 35, 26, 1)"));
        authPage.closeError();
        authPage.getUsername().shouldHave(cssValue("border-bottom-color", "rgba(237, 237, 237, 1)"));
        authPage.getPassword().shouldHave(cssValue("border-bottom-color", "rgba(237, 237, 237, 1)"));
    }
}

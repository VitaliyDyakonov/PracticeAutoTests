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
    @DisplayName("Успешная авторизация")
    public void successfulAuth() {
        authPage.enterUsername("standard_user");
        Allure.step("Имя ввелось корректно", ()->authPage.getUsername().shouldHave(value("standard_user")));
        authPage.enterPassword("secret_sauce");
        authPage.login();
        Allure.step("Открылась страница Products", ()->$(".title").shouldHave(text("Products")));
    }

    @Test
    @Tag("UI test")
    @DisplayName("Валидация формы авторизации")
    public void errorAuth() {
        authPage.login();
        Allure.step("Отобразилась ошибка - Epic sadface: Username is required", ()-> authPage.getErrorContainer().shouldHave(text("Epic sadface: Username is required")));
        authPage.enterUsername("test");
        authPage.login();
        Allure.step("Отобразилась ошибка - Epic sadface: Password is required", ()-> authPage.getErrorContainer().shouldHave(text("Epic sadface: Password is required")));
        Allure.step("Очищаем поля UserName",()-> authPage.getUsername().setValue(SetValueOptions.withText("")));
        authPage.enterPassword("test");
        authPage.login();
        Allure.step("Отобразилась ошибка - Epic sadface: Username and password do not match any user in this service", ()-> authPage.getErrorContainer().shouldHave(text("Epic sadface: Username and password do not match any user in this service")));
        authPage.enterUsername("locked_out_user");
        authPage.enterPassword("secret_sauce");
        authPage.login();
        Allure.step("Отобразилась ошибка - Epic sadface: Sorry, this user has been locked out", ()-> authPage.getErrorContainer().shouldHave(text("Epic sadface: Sorry, this user has been locked out.")));
    }

    @Test
    @Tag("UI test")
    @DisplayName("Визуальное отображение ошибки")
    public void closingError() {
        authPage.login();
        Allure.step("Ошибка визуально отобразилась",()->{
            authPage.getUsername().shouldHave(cssValue("border-bottom-color", "rgba(226, 35, 26, 1)"));
            authPage.getPassword().shouldHave(cssValue("border-bottom-color", "rgba(226, 35, 26, 1)"));
        });
        authPage.closeError();
        Allure.step("Ошибка визуально отобразилась",()->{
        authPage.getUsername().shouldHave(cssValue("border-bottom-color", "rgba(237, 237, 237, 1)"));
        authPage.getPassword().shouldHave(cssValue("border-bottom-color", "rgba(237, 237, 237, 1)"));
        });
    }
}

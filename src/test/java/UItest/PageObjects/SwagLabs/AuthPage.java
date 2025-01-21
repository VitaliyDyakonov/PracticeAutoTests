package UItest.PageObjects.SwagLabs;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthPage extends BasePage {

    private SelenideElement username = $("#user-name");

    private SelenideElement password = $("#password");

    private SelenideElement loginButton = $("#login-button");

    private SelenideElement errorContainer = $("h3[data-test='error']");

    private SelenideElement closeError = $(".error-button");

    public static void openPage(){open(getBaseUrl());}

    public AuthPage enterUsername(String name){
        Allure.step(String.format("Ввести имя пользователя: [%s]",name), ()-> username.setValue(name));
        return this;
    }

    public AuthPage enterPassword(String pass){
        Allure.step(String.format("Ввести пароль пользователя: [%s]",pass), ()-> password.setValue(pass));
        return this;
    }

    public AuthPage login(){
        Allure.step("Нажать кнопку Login", ()->{
        loginButton.click();
        }
        );
        return this;
    }

    public AuthPage closeError(){
        Allure.step("Закрыть окно с ошибкой", ()-> closeError.click());
        return this;
    }

    public SelenideElement getErrorContainer() {
        return errorContainer;
    }

    public SelenideElement getUsername() {
        return username;
    }

    public SelenideElement getPassword() {
        return password;
    }

    public SelenideElement getLoginButton() {
        return loginButton;
    }
}

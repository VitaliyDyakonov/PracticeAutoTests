package UItest.PageObjects.SwagLabs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;

import static com.codeborne.selenide.Selenide.*;

public class CheckoutPage extends BasePage {

    private SelenideElement firstName = $("#first-name");
    private SelenideElement lastName = $("#last-name");
    private SelenideElement zip = $("#postal-code");
    private SelenideElement cancelButton = $("#cancel");
    private SelenideElement continueButton = $("#continue");
    private SelenideElement errorContainer = $("h3[data-test='error']");
    private SelenideElement closeError = $(".error-button");
    private ElementsCollection cartList = $$(".cart_item");

    private String quantity = "div[data-test='item-quantity']";
    private SelenideElement itemTotal = $("div[data-test='subtotal-label']");
    private SelenideElement tax = $("div[data-test='tax-label']");
    private SelenideElement totalPrice = $("div[data-test='total-label']");

    private SelenideElement finishButton = $("#finish");
    public CheckoutPage finish(){
        Allure.step("Завершить формирование заказа", ()->{finishButton.click();});
        return this;
    }

    private Item item = new Item();


    public static void openPage(){open(getCheckoutUrl());}
    public CheckoutPage enterFirstName(String fName){
        Allure.step(String.format("Заполнить поле First name: [%s]",fName), ()->firstName.setValue(fName));
        return this;
    }

    public CheckoutPage enterLastName(String lName){
        Allure.step(String.format("Заполнить поле Last name: [%s]",lName), ()-> lastName.setValue(lName));
        return this;
    }
    public CheckoutPage enterZipCode(String zipCode){
        Allure.step(String.format("Заполнить поле Zip Code: [%s]",zipCode), ()-> zip.setValue(zipCode));
        return this;
    }
    public CheckoutPage submit(){
        Allure.step("Завершить формирование заказа", ()-> continueButton.click());
        return this;
    }

    public CheckoutPage closeErrorMessage(){
        Allure.step("Закрыть окно с ошибкой", ()-> closeError.click());
        return this;
    }

    public SelenideElement getErrorContainer() {
        return errorContainer;
    }

    public CartPage backToCart(){
        Allure.step("Вернуться в корзину", ()-> cancelButton.click());
        return new CartPage();
    }

    public SelenideElement getItemTotal() {
        return itemTotal;
    }

    public SelenideElement getTax() {
        return tax;
    }

    public SelenideElement getTotalPrice() {
        return totalPrice;
    }

    public ElementsCollection getCartList() {
        return cartList;
    }

    public Item getItem() {
        return item;
    }

    public String getQuantity() {
        return quantity;
    }
}

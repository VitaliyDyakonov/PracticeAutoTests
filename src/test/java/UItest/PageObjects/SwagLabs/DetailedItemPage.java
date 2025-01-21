package UItest.PageObjects.SwagLabs;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;

import static com.codeborne.selenide.Selenide.$;

public class DetailedItemPage {

    private Item item = new Item();
    private SelenideElement backButton = $("#back-to-products");

    public Item getItem() {
        return item;
    }

    public ProductPage backToProductList(){
        Allure.step("Вернуться к списку продуктов", ()-> backButton.click());
        return new ProductPage();
    }
}

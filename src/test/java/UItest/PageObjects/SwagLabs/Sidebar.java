package UItest.PageObjects.SwagLabs;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class Sidebar {

    private SelenideElement burgerMenu = $("#react-burger-menu-btn");

    private SelenideElement itemsSidebar = $("#inventory_sidebar_link");

    private SelenideElement aboutSidebar = $("#about_sidebar_link");

    private SelenideElement logoutSidebar = $("#logout_sidebar_link");

    private SelenideElement resetSidebar = $("#reset_sidebar_link");

    private SelenideElement closeSidebar = $("#react-burger-cross-btn");

    public SelenideElement getItemsSidebar() {
        return itemsSidebar;
    }

    public SelenideElement getAboutSidebar() {
        return aboutSidebar;
    }

    public SelenideElement getLogoutSidebar() {
        return logoutSidebar;
    }

    public SelenideElement getResetSidebar() {
        return resetSidebar;
    }

    public SelenideElement getCloseSidebar() {
        return closeSidebar;
    }
    public Sidebar openBurger(){
        burgerMenu.click();
        return this;
    }

    public Sidebar openAbout(){
        aboutSidebar.click();
        return this;
    }

    public AuthPage logout(){
        logoutSidebar.click();
        return new AuthPage();
    }

    public Sidebar closeBurger(){
        closeSidebar.click();
        return this;
    }
}

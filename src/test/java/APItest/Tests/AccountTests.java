package APItest.Tests;

import APItest.PageObjects.AccountApi;
import APItest.PojoClasses.Account;

import io.qameta.allure.Allure;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTests extends TestBase{

    AccountApi accountApi = new AccountApi();

    @BeforeEach
    public void prepareForTest(){
        Account acc = new Account();
        acc.setPassword("Test1234!");
        acc.setUserName("VVDyakonov");
        accountApi.setSpec(AccountTests.createSpec(accountApi.generateToken(acc).body().jsonPath().get("token")));
    }

    @Test
    @Tag("API test")
    void incorrectGenerateToken() {
        Account account = new Account();
        Allure.step("Отправить запрос на получение токена с пустыми атрибутами", ()->{
            ExtractableResponse response = accountApi.generateToken(account);
            assertEquals(400,response.statusCode());
            assertEquals("UserName and Password required.",response.body().jsonPath().get("message"));
        });
        account.setUserName("testAcc");
        account.setPassword("testAcc");
        Allure.step("Отправить запрос на получение токена для несуществующего пользователя", ()->{
            ExtractableResponse response = accountApi.generateToken(account);
            assertEquals(response.statusCode(),200);
            assertNull(response.body().jsonPath().get("token"));
            assertNull(response.body().jsonPath().get("expires"));
            assertEquals("Failed",response.body().jsonPath().get("status"));
            assertEquals("User authorization failed.",response.body().jsonPath().get("result"));
        });
    }

    @Test
    @Tag("API test")
    void correctGenerateToken() {
        Account account = new Account();
        account.setUserName("VVDyakonov");
        account.setPassword("Test1234!");
        Allure.step("Отправить запрос на получение токена для существующего пользователя", ()-> {
            ExtractableResponse response = accountApi.generateToken(account);
            assertEquals(response.statusCode(), 200);
            assertNotNull(response.body().jsonPath().get("token"));
            assertNotNull(response.body().jsonPath().get("expires"));
            assertEquals("Success",response.body().jsonPath().get("status"));
            assertEquals("User authorized successfully.",response.body().jsonPath().get("result"));
        });
    }

    @Test
    @Tag("API test")
    void correctLogin() {
        Account account = new Account();
        account.setUserName("VVDyakonov");
        account.setPassword("Test1234!");
        Allure.step("Отправить запрос на логин существующего пользователя", ()-> {
            ExtractableResponse response = accountApi.login(account);
            assertEquals(200,response.statusCode());
            assertEquals(account.getUserName(),response.body().jsonPath().get("username"));
            assertEquals(account.getPassword(),response.body().jsonPath().get("password"));
        });
    }

    @Test
    @Tag("API test")
    void incorrectUserCreate() {
        Account account = new Account();
        account.setUserName("VVDyakonov");
        account.setPassword("Test1234!");
        Allure.step("Отправить запрос на создание существующего пользователя", ()-> {
            ExtractableResponse response = accountApi.createUser(account);
            assertEquals(406,response.statusCode());
            assertEquals("User exists!",response.body().jsonPath().get("message"));
        });
        account.setPassword(null);
        Allure.step("Отправить запрос на создание пользователя с незаполненными параметрами", ()-> {
            ExtractableResponse response = accountApi.createUser(account);
            assertEquals(400, response.statusCode());
            assertEquals("UserName and Password required.", response.body().jsonPath().get("message"));
        });
        account.setUserName("test");
        account.setPassword("test");
        Allure.step("Отправить запрос на создание пользователя с невалидными параметрами", ()-> {
            ExtractableResponse response = accountApi.createUser(account);
            assertEquals(400, response.statusCode());
            assertEquals("Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.", response.body().jsonPath().get("message"));
        });
    }

    @Test
    @Tag("API test")
    void userCreateDelete() {
        Account account = new Account();
        account.setUserName("CreatedUser"+new Random().nextInt(100));
        account.setPassword("Test1234!");
        String userId = accountApi.createUser(account).body().jsonPath().get("userID");
        Allure.step("Отправить запрос на удаление пользователя без авторизации", ()-> {
            ExtractableResponse response = accountApi.deleteUser(userId);
            assertEquals(401,response.statusCode());
            assertEquals("User not authorized!",response.body().jsonPath().get("message"));
        });
        accountApi.setSpec(AccountTests.createSpec(accountApi.generateToken(account).body().jsonPath().get("token")));
        Allure.step("Отправить запрос на удаление пользователя с авторизацией", ()-> {
            accountApi.login(account);
            ExtractableResponse response = accountApi.deleteUser(userId);
            assertEquals(204,response.statusCode());
            assertEquals("User authorization failed.",accountApi.generateToken(account).body().jsonPath().get("result"));
        });
    }

    @Test
    @Tag("API test")
    void getUser() {
        Account account = new Account();
        account.setUserName("CreatedUser"+Math.random()*100);
        account.setPassword("Test1234!");
        String userId = accountApi.createUser(account).body().jsonPath().get("userID");
        Allure.step("Отправить запрос на получение информации пользователя без авторизации", ()-> {
            ExtractableResponse response = accountApi.getUser(userId);
            assertEquals(401,response.statusCode());
            assertEquals("User not authorized!",response.body().jsonPath().get("message"));
        });
        accountApi.setSpec(AccountTests.createSpec(accountApi.generateToken(account).body().jsonPath().get("token")));
        Allure.step("Отправить запрос на получение информации пользователя с авторизации", ()-> {
            ExtractableResponse response = accountApi.getUser(userId);
            assertEquals(200,response.statusCode());
            assertEquals(account.getUserName(),response.body().jsonPath().get("username"));
        });
        accountApi.login(account);
        accountApi.deleteUser(userId);
    }
}

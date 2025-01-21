package APItest.Tests;

import APItest.PageObjects.AccountApi;
import APItest.PageObjects.BookApi;
import APItest.PojoClasses.Account;
import APItest.PojoClasses.AddBookList;
import APItest.PojoClasses.Book;
import APItest.PojoClasses.Books;
import io.qameta.allure.Allure;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTests extends TestBase{

    BookApi bookTests = new BookApi();

    @BeforeEach
    public void prepareForTest(){
        Account acc = new Account();
        acc.setPassword("Test1234!");
        acc.setUserName("VVDyakonov");
        bookTests.setSpec(BookTests.createSpec(bookTests.generateToken(acc).body().jsonPath().get("token")));
    }

    @Test
    void getBooks() {
        Allure.step("Отправить запрос на получение списка книг", ()->{
        ExtractableResponse response=bookTests.getBooks();
        assertEquals(200,response.statusCode());
        });
    }

    @Test
    void getBook() {
        Books books = bookTests.getBooks().body().jsonPath().getObject("", Books.class);
        Book book = books.getBooks().get(new Random().nextInt(books.getBooks().size()));
        Allure.step(String.format("Отправить запрос на получение детальной информации о книге с ISBN: %s",book.getIsbn()), ()-> {
            ExtractableResponse response = bookTests.getBook(book.getIsbn());
            assertEquals(200,response.statusCode());
            assertEquals(book,response.body().jsonPath().getObject("", Book.class));
        });
    }

    @Test
    void addListBooks() {
        Account acc = new Account();
        AccountApi accApi = new AccountApi();
        accApi.setSpec(BookTests.createSpec(bookTests.generateToken(acc).body().jsonPath().get("token")));
        acc.setPassword("Test1234!");
        acc.setUserName("VVDyakonov");
        String userId = accApi.login(acc).body().jsonPath().get("userId");
        bookTests.deleteBooks(userId);
        Books books = bookTests.getBooks().body().jsonPath().getObject("", Books.class);
        AddBookList list = new AddBookList();
        list.setUserId(userId);
        list.setCollectionOfIsbns(books.getBooks());
        Allure.step(String.format("Отправить запрос на добавление пользователю с userId:%s",userId), ()-> {
            ExtractableResponse response = bookTests.addListOfBooks(list);
            assertEquals(response.statusCode(), 201);
        });

    }
}

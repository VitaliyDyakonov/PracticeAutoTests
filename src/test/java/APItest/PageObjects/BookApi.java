package APItest.PageObjects;

import APItest.PojoClasses.AddBookList;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BookApi extends BaseApi {

    public ExtractableResponse getBooks(){
        return given()
                .spec(this.specification)
                .filter(new AllureRestAssured())
                .basePath(BookApi.getBookstoreUrl()+"/Books")
                .when()
                .get()
                .then().extract();
    }
    public ExtractableResponse addListOfBooks(AddBookList bookList){
        return given()
                .spec(this.specification)
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .basePath(BookApi.getBookstoreUrl()+"/Books")
                .body(bookList)
                .when()
                .post()
                .then().extract();
    }
    public ExtractableResponse deleteBooks(String userId){
        return given()
                .spec(this.specification)
                .filter(new AllureRestAssured())
                .basePath(BookApi.getBookstoreUrl()+"/Books")
                .queryParam("UserId", userId)
                .when()
                .delete()
                .then().extract();
    }
    public ExtractableResponse deleteBook(String userId, String isbn){
        return given()
                .spec(this.specification)
                .filter(new AllureRestAssured())
                .basePath(BookApi.getBookstoreUrl()+"/Book")
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"isbn\": \""+isbn+"\",\n" +
                        "    \"userId\": \""+userId+"\"\n" +
                        "}")
                .when()
                .delete()
                .then().extract();
    }
    public ExtractableResponse getBook(String isbn){
        return given()
                .spec(this.specification)
                .filter(new AllureRestAssured())
                .basePath(BookApi.getBookstoreUrl()+"/Book")
                .queryParam("ISBN", isbn)
                .when()
                .get()
                .then().extract();
    }
    public ExtractableResponse replaceBook(String isbnOld, String isbnNew, String userId){
        return given()
                .spec(this.specification)
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .basePath(BookApi.getBookstoreUrl()+"/Books/"+isbnOld)
                .body("{\n" +
                        "    \"userId\": \""+userId+"\",\n" +
                        "    \"isbn\": \""+isbnNew+"\"\n" +
                        "}")
                .when()
                .put()
                .then().extract();
    }

}

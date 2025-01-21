package APItest.PageObjects;

import APItest.PojoClasses.Account;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseApi {

    private static String baseUrl = "https://demoqa.com";

    private static String accountUrl = "/Account/v1";

    private static String bookstoreUrl = "/BookStore/v1";

    public RequestSpecification specification;
    public ExtractableResponse generateToken(Account acc){
        return given()
                .baseUri(AccountApi.getBaseUrl())
                .contentType(ContentType.JSON)
                .basePath(AccountApi.getAccountUrl()+"/GenerateToken")
                .body(acc)
                .when()
                .post()
                .then().extract();
    }
    public void setSpec(RequestSpecification spec){
        specification = spec;
    }
    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getAccountUrl() {
        return accountUrl;
    }

    public static String getBookstoreUrl() {
        return bookstoreUrl;
    }
}

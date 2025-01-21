package APItest.PageObjects;

import APItest.PojoClasses.Account;
import APItest.PojoClasses.User;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;

import static io.restassured.RestAssured.given;

public class AccountApi extends BaseApi {

    public ExtractableResponse login(Account acc){
        return given()
                .spec(this.specification)
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .basePath(AccountApi.getAccountUrl()+"/login")
                .body(acc)
                .when()
                .post()
                .then().extract();//.statusCode(200).body().jsonPath().get("token");
    }

    public ExtractableResponse createUser(Account acc){
        return given()
                .spec(this.specification)
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .basePath(AccountApi.getAccountUrl()+"/User")
                .body(acc)
                .when()
                .post()
                .then().extract();//.statusCode(200).body().jsonPath().get("token");
    }
    public ExtractableResponse deleteUser(String userId){
        return given()
                .spec(this.specification)
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .basePath(AccountApi.getAccountUrl()+"/User/"+userId)
                .when()
                .delete()
                .then().extract();//.statusCode(200).body().jsonPath().get("token");
    }
    public ExtractableResponse getUser(String userId){
        return given()
                .spec(this.specification)
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .basePath(AccountApi.getAccountUrl()+"/User/"+userId)
                .when()
                .get()
                .then().extract();//.body();//.jsonPath().getObject("", User.class);
    }
}

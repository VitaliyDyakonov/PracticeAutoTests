package APItest.Tests;

import APItest.PageObjects.BaseApi;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class TestBase {

    protected static RequestSpecification REQUEST_SPECIFICATION;
    public static RequestSpecification createSpec(){
        return new RequestSpecBuilder().setBaseUri(BaseApi.getBaseUrl()).build();
    }
    public static RequestSpecification createSpec(String token){
        return new RequestSpecBuilder().setBaseUri(BaseApi.getBaseUrl()).addHeader("Authorization", "Bearer " + token).build();
    }
}

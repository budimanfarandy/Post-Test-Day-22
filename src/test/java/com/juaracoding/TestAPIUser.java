package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestAPIUser {

    String endpoint = "https://reqres.in/api/users?page1";

    @Test
    public void testGetListUsers(){
        Response response = RestAssured.get(endpoint);
        System.out.println(response.getStatusCode());
        System.out.println(response.getTime());
        System.out.println(response.getBody().asString());
        System.out.println(response.getHeaders());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
        // validasi pada response body use json path
    }

    @Test
    public void testListUsers(){
        given().get(endpoint)
                .then()
                .statusCode(200)
                .body("data.id[0]",equalTo(1));
    }

    @Test
    public void testAddUser(){
        JSONObject request = new JSONObject();
        request.put("name", "John");
        request.put("job", "QA Engineer");
        System.out.println(request.toJSONString());
        given()
                // .header("Authorization", "Bearer token")
                .header("Content-Type", "application/json")
                .body(request.toJSONString())
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .log().all();
    }

    @Test
    public void testLoginSuccessful() {
        RestAssured.baseURI = "https://reqres.in/api";
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", "cityslicka");
        request.body(requestBody.toJSONString());
        request.header("Content-Type", "application/json");
        Response response = request.post("/login");
        Assert.assertEquals(response.getStatusCode(), 200);
        String token = response.getBody().jsonPath().getString("token");
        System.out.println(token);
    }

}

package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class MovieDb {

    String endpoint = "https://api.themoviedb.org";
    String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2MDhhOGNmYTY1MGY3MmQ0N2NiMGQyMmUyNDE1NzM2MSIsInN1YiI6IjY1MmZkMGJmZWE4NGM3MDBlYmEzZWQ0ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.w32U7erVJ80xzLyqyrmod43t5ucvYuq426LBeW43WGE";


    // validasi pada response body use json path


    @Test
    public void testGetMovieNowPlaying(){
        JSONObject request = new JSONObject();
        Response response = given()
                // .header("Authorization", "Bearer token")
                .baseUri(endpoint)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .when()
                .get("/3/movie/now_playing");
        response.then().assertThat().statusCode(200);
        response.then()
                .assertThat()
                .body("page", equalTo(1));
    }

    @Test
    public void testGetMoviePopular() {
        Response response = given()
                .baseUri(endpoint)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .when()
                .get("/3/movie/popular");
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void testPostMovieRating() {
        String movieId = "238";
        String requestBody = "{\"value\": 9.5}";

                Response response = given()
                .baseUri(endpoint)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(requestBody)
                .when()
                .post("/3/movie/"+movieId+"/rating");
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("success",equalTo(true));

    }
}
package com.piotrkluz.api;

import com.piotrkluz.Config;
import com.piotrkluz.models.User;

import static com.jayway.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assumptions.*;

import com.jayway.restassured.response.Response;
import org.json.JSONObject;

public class ApiClient {
    public static void deleteAllUsers() {
        given().delete(Config.BASE_URL + "/user/all").asString();
    }

    public static void addUser(User user) {
        JSONObject json = new JSONObject();
        json.put("name", user.name);
        json.put("email", user.email);
        json.put("password", user.password);
        json.put("passwordConfirm", user.password);


        Response res = given()
                .body(json.toString())
                .post(Config.BASE_URL + "/user/save/json");

        assumeTrue(res.statusCode() == 200);
    }
}

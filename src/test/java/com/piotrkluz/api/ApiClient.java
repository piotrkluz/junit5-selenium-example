package com.piotrkluz.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.piotrkluz.Config;
import com.piotrkluz.models.User;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assumptions.*;

public class ApiClient {
    public static void deleteAllUsers() throws UnirestException {
        Unirest.delete(Config.BASE_URL + "/user/all").asString();
    }

    public static void addUser(User user) throws UnirestException {
        JSONObject json = new JSONObject();
        json.put("name", user.name);
        json.put("email", user.email);
        json.put("password", user.password);
        json.put("passwordConfirm", user.password);

        HttpResponse<String> res = Unirest.post(Config.BASE_URL + "/user/save/json")
                .body(json)
                .asString();

        assumeTrue(res.getStatus() == 200);
    }
}

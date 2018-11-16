package com.piotrkluz.steps;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.piotrkluz.api.ApiClient;
import com.piotrkluz.models.User;
import com.piotrkluz.pages.AllUsersPage;
import com.piotrkluz.pages.FormPage;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommonSteps {
    private ChromeDriver driver;
    private FormPage formPage;
    private AllUsersPage usersPage;

    public CommonSteps(ChromeDriver driver) {
        this.driver = driver;
        this.formPage = new FormPage(driver);
        this.usersPage = new AllUsersPage(driver);
    }

    public void validateUser(User user) {
        usersPage.open();
        User foundUser = usersPage.tryFindUser(user.name);
        assertNotNull(foundUser, "Not found user by name: " + user.name);

        assertAll("Found user should be identical as created one: ", () -> {
            assertEquals(user.name, foundUser.name);
            assertEquals(user.email, foundUser.email);
            assertEquals(user.password, foundUser.password);
        });
    }

    public List<User> generateUsers(int count) throws UnirestException {
        List<User> users = new LinkedList<>();

        for (int i = 0; i < count; i++) {
            User user = new User();
            ApiClient.addUser(user);
            users.add(user);
        }

        return users;
    }
}

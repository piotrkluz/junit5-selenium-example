package com.piotrkluz.steps;

import com.piotrkluz.api.ApiClient;
import com.piotrkluz.models.User;
import com.piotrkluz.pages.AllUsersPage;
import org.openqa.selenium.WebDriver;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommonSteps {
    private AllUsersPage usersPage;

    public CommonSteps(WebDriver driver) {
        this.usersPage = new AllUsersPage(driver);
    }

    public void validateUser(User user) {
        User foundUser = usersPage.tryFindUser(user.name);
        assertNotNull(foundUser, "Not found user by name: " + user.name);

        assertAll("Found user should be identical as created one: ", () -> {
            assertEquals(user.name, foundUser.name);
            assertEquals(user.email, foundUser.email);
            assertEquals(user.password, foundUser.password);
        });
    }

    public List<User> generateUsers(int count) {
        List<User> users = new LinkedList<>();

        for (int i = 0; i < count; i++) {
            User user = new User();
            ApiClient.addUser(user);
            users.add(user);
        }

        return users;
    }
}

package com.piotrkluz.tests;

import com.piotrkluz.SeleniumProvider;
import com.piotrkluz.api.ApiClient;
import com.piotrkluz.models.User;
import com.piotrkluz.pages.AllUsersPage;
import com.piotrkluz.pages.FormPage;
import com.piotrkluz.steps.CommonSteps;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SeleniumProvider.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersListTests {
    private CommonSteps steps;
    private FormPage formPage;
    private AllUsersPage allUsersPage;

    public UsersListTests(RemoteWebDriver driver) {
        this.steps = new CommonSteps(driver);
        this.formPage = new FormPage(driver);
        this.allUsersPage = new AllUsersPage(driver);
    }

    @Test
    public void openUsersList() {
        formPage.open();
        formPage.form.allUserButton.click();

        assertTrue(allUsersPage.isOpened(), "All Users page should open.");
    }

    @Test
    public void verifyUsersOnList() {
        ApiClient.deleteAllUsers();
        List<User> users = steps.generateUsers(30);

        allUsersPage.open();
        for(User user : users) {
            steps.validateUser(user);
        }
    }
}

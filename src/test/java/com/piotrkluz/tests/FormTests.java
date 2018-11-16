package com.piotrkluz.tests;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.piotrkluz.api.ApiClient;
import com.piotrkluz.models.User;
import com.piotrkluz.pages.AllUsersPage;
import com.piotrkluz.pages.FormPage;
import com.piotrkluz.steps.CommonSteps;
import io.github.bonigarcia.SeleniumExtension;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@ExtendWith(SeleniumExtension.class)
public class FormTests {
    private ChromeDriver driver;
    private FormPage formPage;
    private CommonSteps steps;

    public FormTests(ChromeDriver driver) {
        this.driver = driver;
        this.formPage = new FormPage(driver);
        this.steps = new CommonSteps(driver);
    }

    @BeforeAll
    public static void clearUsers() throws UnirestException {
        ApiClient.deleteAllUsers();
    }

    @BeforeEach
    public void loadPage() {
        driver.get("http://85.93.17.135:9000/user/new");
    }

    @AfterEach
    public void afterfunc() {
        System.out.print("vdfsdfds");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "simpleUser",
            "a",
            "100CharsNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
            "Numb3rs1234567890",
            "SpecialChars!@#$%^&*()_+",
            "XSS<script>alert(\"this should not execute\");</script>",
    })
    public void userNameValidation(String userName) {
        User user = new User();
        user.name = userName;

        formPage.createUser(user);

        steps.validateUser(user);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "aaaaaa",
            "aa1234",
            "a$%~`a",
            "100CharsPasswordeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
    })
    public void passwordValidation(String userName) {
        User user = new User();
        user.name = userName;

        formPage.createUser(user);

        steps.validateUser(user);
    }

    @Test
    public void trimSpaces() {
        User user = new User();
        User userWithWhiteChars = new User(
                "  \t " + user.name + "  \t ",
                "  \t " + user.email + "  \t ",
                user.password
        );

        formPage.createUser(userWithWhiteChars);

        steps.validateUser(user);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "email",
            "email@com",
            "email.@",
    })
    public void invalidEmail(String email) {
        User user = new User();
        user.email = email;

        formPage.form.fill(user);
        formPage.form.submit();

        assertTrue(formPage.isOpened(), "Page should be still opened after submit.");
        assertEquals("Invalid email address", formPage.form.email.getError());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "a",
            "passw",
    })
    public void tooShortPassword(String password) {
        User user = new User();
        user.password = password;

        formPage.form.fill(user);
        formPage.form.submit();

        assertTrue(formPage.isOpened(), "Page should be still opened after submit.");
        assertEquals("Minimum size is 6", formPage.form.password.getError());
    }

    @Test
    public void notMatchPasswords() {
        User user = new User();

        formPage.form.fill(user);
        formPage.form.passwordConfirm.fill("otherPassword");
        formPage.form.submit();

        assertTrue(formPage.isOpened(), "Page should be still opened after submit.");
        assertEquals("passwords are not the same", formPage.form.passwordConfirm.getError());
    }

    @Test
    public void emptyFields() {
        formPage.form.submit();

        assertTrue(formPage.isOpened(), "Page should be still opened after submit.");

        assertAll("All fields should contain Required validation", () -> {
            assertEquals("Required", formPage.form.name.getError());
            assertEquals("Required", formPage.form.email.getError());
            assertEquals("Required", formPage.form.password.getError());
        });
    }
}

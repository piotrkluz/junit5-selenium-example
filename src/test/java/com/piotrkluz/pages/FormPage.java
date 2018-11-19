package com.piotrkluz.pages;
import com.piotrkluz.Config;
import com.piotrkluz.models.User;
import com.piotrkluz.pages.components.UserForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class FormPage extends AbstractPage {
    @FindBy(id = "registrationForm")
    public UserForm form;

    @FindBy(css = "h1")
    private HtmlElement pageTitle;

    public FormPage(WebDriver driver) {
        super(driver);
    }

    public String getUrl() {
        return Config.BASE_URL + "/user/new";
    }

    public boolean isOpened() {
        return pageTitle.getText().equals("New User");
    }

    public AllUsersPage createUser(User user) {
        form.fill(user);
        form.submit();

        AllUsersPage nextPage = new AllUsersPage(this.driver);
        assumeTrue(
                nextPage.isOpened(),
                () -> "Create user failed. Following errors were displayed on form: " + form.getErrors()
        );

        return nextPage;
    }
}

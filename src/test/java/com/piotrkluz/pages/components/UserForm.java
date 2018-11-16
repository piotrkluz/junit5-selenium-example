package com.piotrkluz.pages.components;

import com.piotrkluz.models.User;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

public class UserForm extends HtmlElement {
    @FindBy(xpath = "//div[contains(./label, 'Name')]")
    public FormField name;

    @FindBy(xpath = "//div[contains(./label, 'Email')]")
    public FormField email;

    @FindBy(xpath = "//div[contains(./label, 'Password')]")
    public FormField password;

    @FindBy(xpath = "//div[contains(./label, 'Confirmation password')]")
    public FormField passwordConfirm;

    @FindBy(css = "button[type=submit]")
    public Button submitButton;

    @FindBy(css = "a.btn-primary")
    public Button allUserButton;

    public void fill(User user) {
        name.fill(user.name);
        email.fill(user.email);
        password.fill(user.password);
        passwordConfirm.fill(user.password);
    }

    public void submit() {
        this.submitButton.click();
    }

    public String getErrors() {
        String[] errors = {
                name.getErrorWithDescription(),
                email.getErrorWithDescription(),
                password.getErrorWithDescription(),
                passwordConfirm.getErrorWithDescription()
        };

        return String.join("\r\n", errors);
    }
}

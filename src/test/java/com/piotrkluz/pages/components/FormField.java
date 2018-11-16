package com.piotrkluz.pages.components;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.*;

public class FormField extends HtmlElement {

    @FindBy(css = "label.control-label")
    private HtmlElement label;

    @FindBy(css = ".controls > input")
    private HtmlElement input;

    @FindBy(css = "p.alert-error")
    private HtmlElement errorMessage;

    public void fill(String text) {
        input.sendKeys(text);
    }

    /**
     * @return Empty message if not error.
     */
    public String getErrorWithDescription() {
        String err = errorMessage.getText();

        return err.length() > 0
                ? String.format("\r\nField: '%s': value: '%s' ERROR: '%s'", label.getText(), input.getAttribute("value"), err)
                : "";
    }

    public String getError() {
        return errorMessage.getText();
    }
}

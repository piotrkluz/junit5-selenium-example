package com.piotrkluz.pages;

import com.piotrkluz.Config;
import com.piotrkluz.models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;


public class AllUsersPage extends AbstractPage {
    @FindBy(css = "h1")
    private HtmlElement pageTitle;

    public AllUsersPage(WebDriver driver) {
        super(driver);
    }

    public String getUrl() {
        return Config.BASE_URL + "/users/all";
    }

    public boolean isOpened() {
        return pageTitle.getText().equals("All User");
    }

    public User tryFindUser(String name) {
        String xpath = String.format("//table[@id='users']//tr[contains(.,'%s')]", name);

        try {
            WebElement found = driver.findElement(By.xpath(xpath));
            return new User(
                    found.findElement(By.xpath("./td[1]")).getText(),
                    found.findElement(By.xpath("./td[2]")).getText(),
                    found.findElement(By.xpath("./td[3]")).getText()
            );
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}

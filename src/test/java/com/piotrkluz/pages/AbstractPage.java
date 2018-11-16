package com.piotrkluz.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public abstract class AbstractPage {
    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(
                new HtmlElementDecorator(
                        new HtmlElementLocatorFactory(driver)), this
        );
    }

    public void open() {
        driver.get(getUrl());
        assumeTrue(isOpened(), "Page should open.");
    }

    public abstract boolean isOpened();
    public abstract String getUrl();
}

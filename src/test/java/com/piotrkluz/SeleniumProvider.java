package com.piotrkluz;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumProvider implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Class<?> type = parameterContext.getParameter().getType();
        return (WebDriver.class.isAssignableFrom(type));
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        if(System.getenv("HUB_URL") == null) {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        }
        return new RemoteWebDriver(getHubUrl(), DesiredCapabilities.chrome());
    }

    private URL getHubUrl() {
        String url = System.getenv("HUB_URL"); //"http://localhost:4444/wd/hub";
        try { //Checked exception  -> RuntimeException
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid HUB URL: " + url);
        }
    }
}

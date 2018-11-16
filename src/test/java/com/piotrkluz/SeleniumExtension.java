package com.piotrkluz;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Class for change behavior of junit5-selenium module to close browser after all tests, not every each test.
 * Requires to set @TestInstance(TestInstance.Lifecycle.PER_CLASS) on test classes
 * It greatly increases performance.
 */
public class SeleniumExtension extends io.github.bonigarcia.SeleniumExtension implements AfterAllCallback, AfterEachCallback {
    public void afterEach(ExtensionContext context) {}
    public void afterAll(ExtensionContext context) {
        super.afterEach(context);
    }
}

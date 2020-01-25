package com.main.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static Logger logger = LogManager.getLogger(LoginPage.class.getName());

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void loginInTwitter(String username, String password) {
        setTextInElement(By.cssSelector(".StaticLoggedOutHomePage-login > form > div:nth-child(1) > input"), username);
        setTextInElement(By.cssSelector(".StaticLoggedOutHomePage-login > form > div:nth-child(2) > input"), password);
        clickOnElement(By.cssSelector(".StaticLoggedOutHomePage-login > form > input[type='submit']"));
    }

}

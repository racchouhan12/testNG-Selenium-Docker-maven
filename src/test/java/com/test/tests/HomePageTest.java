package com.test.tests;

import com.main.pages.HomePage;
import com.main.pages.LoginPage;
import com.main.utilities.ExtentReporter.ExtentTestManager;
import com.main.utilities.ExternalDataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;


public class HomePageTest extends BaseTest {

    private static Logger logger = LogManager.getLogger(HomePageTest.class.getName());

    //@Test
    public void googleSearch(Method method) {
        logger.info("Running Test test1()");
        ExtentTestManager.startTest(method.getName(), "Google Search test - " + browser.toUpperCase());
        new HomePage(getDriver()).searchTextFromGoogleHomePage("Selenium HQ");
        Assert.assertTrue(true, "Test failed");
        ExtentTestManager.endTest();
    }

    //@Test
    public void mattermarkTest(Method method) {
        logger.info("Running Test test1()");
        ExtentTestManager.startTest(method.getName(), "Google Search test - " + browser.toUpperCase());
        Assert.assertTrue(new HomePage(getDriver()).matterMarkTests(), "Test failed");
        ExtentTestManager.endTest();
    }

    //@Test
    public void netFlixTest(Method method) {
        logger.info("Running Test test1()");
        ExtentTestManager.startTest(method.getName(), "Google Search test - " + browser.toUpperCase());
        Assert.assertTrue(new HomePage(getDriver()).netflixTest().contains("Sign up to start your membership"), "Test failed");
        ExtentTestManager.endTest();
    }

    @Test(dataProvider = "data-commonFileOfAllEnvWithDataInDifferentSheet", dataProviderClass = ExternalDataProvider.class)
    public void twitterTest(String username, String password, Method method) {
        HomePage homePage = new HomePage(getDriver());
       // ExtentTestManager.startTest(method.getName(), "Twitter - " + browser.toUpperCase());
        new LoginPage(getDriver()).loginInTwitter(username, password);
        Assert.assertTrue(homePage.twitterHome().equals("Home"), "Home not found");
        Assert.assertTrue(homePage.clickOnNotification().equals("Notifications"), "Notifications not found");
        homePage.logout();
    }

    @Test(description = "Testing multiple select dropdown")
    public void testMultiSelectDropdown(Method method) {
        HomePage homePage = new HomePage(getDriver());
        homePage.selectCountryDropdown();
    }

    // @Test
    public void airBnb1(Method method) {
        logger.info("Running Test AirBnb");
        HomePage homePage = new HomePage(getDriver());
        ExtentTestManager.startTest(method.getName(), "Google Search test - " + browser.toUpperCase());
        homePage.clickOnShowAdventure();
        Assert.assertTrue(homePage.getRecommendationText().equals("Atlas Obscura x Airbnb Adventures"), "Test failed");
        ExtentTestManager.endTest();
    }

    //  @Test
    public void airBnb2(Method method) {
        logger.info("Running Test AirBnb");
        HomePage homePage = new HomePage(getDriver());
        ExtentTestManager.startTest(method.getName(), "Google Search test - " + browser.toUpperCase());
        homePage.clickOnShowAdventure();
        Assert.assertTrue(homePage.getRecommendationText().equals("Atlas Obscura x Airbnb Adventures"), "Test failed");
        ExtentTestManager.endTest();
    }

    //  @Test
    public void airBnb3(Method method) {
        logger.info("Running Test AirBnb");
        HomePage homePage = new HomePage(getDriver());
        ExtentTestManager.startTest(method.getName(), "Google Search test - " + browser.toUpperCase());
        homePage.clickOnShowAdventure();
        Assert.assertTrue(homePage.getRecommendationText().equals("Atlas Obscura x Airbnb Adventures"), "Test failed");
        ExtentTestManager.endTest();
    }

    //  @Test
    public void airBnb4(Method method) {
        logger.info("Running Test AirBnb");
        HomePage homePage = new HomePage(getDriver());
        ExtentTestManager.startTest(method.getName(), "Google Search test - " + browser.toUpperCase());
        homePage.clickOnShowAdventure();
        Assert.assertTrue(homePage.getRecommendationText().equals("Atlas Obscura x Airbnb Adventures"), "Test failed");
        ExtentTestManager.endTest();
    }

    //  @Test
    public void airBnb5(Method method) {
        logger.info("Running Test AirBnb");
        HomePage homePage = new HomePage(getDriver());
        ExtentTestManager.startTest(method.getName(), "Google Search test - " + browser.toUpperCase());
        homePage.clickOnShowAdventure();
        Assert.assertTrue(homePage.getRecommendationText().equals("Atlas Obscura x Airbnb Adventures"), "Test failed");
        ExtentTestManager.endTest();
    }

    // @Test
    public void googleSearch3(Method method) {
        logger.info("Running Test test2()");
        ExtentTestManager.startTest(method.getName(), "Google Search test - " + browser.toUpperCase());
        new HomePage(getDriver()).searchTextFromGoogleHomePage("Selenium");
        Assert.assertTrue(false, "Test failed");
        ExtentTestManager.endTest();
    }
}

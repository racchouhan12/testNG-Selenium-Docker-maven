package com.main.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

	private static Logger logger = LogManager.getLogger(HomePage.class.getName());

	private final String searchTextBoxLocator = "p";
	private final String searchButtonLocator = "uh-search-button";
	private final String showAllAdventure = "Show all adventures";
	private final String recommendationLocator = "//h3/div[text()='Atlas Obscura x Airbnb Adventures']";

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	
	private void enterTextInSearchBox(String textToSearch) {
		setTextInElement(By.name(searchTextBoxLocator), textToSearch);
	}
	
	
	private void clickOnSearch() {
		clickOnElement(By.id(searchButtonLocator));
	}
	
	public void searchTextFromGoogleHomePage(String searchText) {
		logger.info("Now inside searchTextFromGoogleHomePage()");
		enterTextInSearchBox(searchText);
		clickOnSearch();
	}

	public void clickOnShowAdventure() {
		scrollDownIntoViewBy(By.linkText(showAllAdventure));
	    clickOnElement(By.linkText(showAllAdventure));
    }

    public String getRecommendationText() {
		scrollDownIntoViewBy(By.xpath(recommendationLocator));
		logger.debug(getTextFromElementBy(By.xpath(recommendationLocator)));
		return getWebElementWithFluentWaitBy(By.xpath(recommendationLocator)).getText();
	}

	public Boolean matterMarkTests() {
		clickOnElement(By.cssSelector("#mm-navbar > ul > li:nth-child(2) > a > i"));
		clickOnElement(By.cssSelector("li[class='dropdown open'] > ul > li > a "));
		scrollDownIntoViewBy(By.xpath("//h2[text()='Your Ideal Customer Intelligence']"));
		return isWebElementWithFluentWaitPresentBy(By.xpath("//h2[text()='Your Ideal Customer Intelligence']"));
	}

	public String netflixTest() {
		clickOnElement(By.cssSelector(".our-story-cards > div:nth-child(1) > div:nth-child(2) > div > div > div > div > form > button > span"));
		clickOnElement(By.cssSelector("button[placeholder='button_see_plans']"));
		scrollDown(2);
		clickOnElement(By.cssSelector("button[placeholder='planSelection_button_continue']"));
		clickOnElement(By.cssSelector("button[placeholder='registration_button_continue']"));
		setTextInElement(By.cssSelector("input[type='email']"), "racchouha12@gmail.com");
		setTextInElement(By.cssSelector("input[type='password']"), "C00l@rac12");
		clickOnElement(By.cssSelector("button[type='submit']"));
		String val = "Sign up to start your membership";
		logger.debug("----" + val + "-------");
		return val;
	}

	public String twitterHome() {
		return getTextFromElementBy(By.xpath("//h2[@dir='ltr']/span"));
	}

	public String clickOnNotification() {
		clickOnElement(By.cssSelector("a[aria-label='Notifications']"));
		return getTextFromElementBy(By.xpath("//h2[@dir='ltr']/span"));
	}

	public void logout() {
		clickOnElement(By.cssSelector("div[aria-label='More menu items'] > div > div:nth-child(2) > span"));
		clickOnElement(By.cssSelector("div[title='Log out'] > span"));
		clickOnElement(By.cssSelector("div[data-testid='confirmationSheetConfirm'] > div > span"));
	}

	public void selectCountryDropdown() {
		//scrollDown(1);
		scrollDownIntoViewBy(By.cssSelector("div[id=types-multiple-selection] > div >div:nth-child(2)"));
		clickOnElement(By.cssSelector("div[id=types-multiple-selection] > div >div:nth-child(2) > div > div > i"));
		selectElementsByTextName(By.xpath("//div[@class='visible menu transition']/div/span"), "Angular", "CSS", "Ruby");
	}
}

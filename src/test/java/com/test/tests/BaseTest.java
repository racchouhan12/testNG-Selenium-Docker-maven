package com.test.tests;

import com.main.utilities.ExtentReporter.ExtentTestManager;
import com.main.utilities.helpers.KEYS;
import com.main.utilities.Command;
import com.main.utilities.OneInstance;
import com.main.utilities.PropertyFileReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collections;

public class BaseTest {
    private final static String RESOURCES_PATH = "\\src\\test\\resources";
    private final static String PROPERTY_FILE_PATH = "/propertyfiles";
    protected String browser;
    OneInstance oneInstance = OneInstance.getInstance();
    private Logger logger = LogManager.getLogger(BaseTest.class.getName());
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected static String env = null;
    protected static String system = null;

    @BeforeSuite
    @Parameters({"env", "system"})
    public void preSetup(String env, String system) throws IOException {
        this.env = env;
        this.system = system;
        logger.info("Before Suite is called...");
       // PropertyFileReader pr = new PropertyFileReader(getPropertyFilesDirectory()
       //         + "/CommonAppProperties.properties");
      //  oneInstance.add(KEYS.STANDALONE_SERVER_PATH, oneInstance.getAsString(KEYS.PROJECT_PATH.name()) + pr.getProperty(KEYS.STANDALONE_SERVER_PATH.name()));
      //  oneInstance.add(KEYS.REMOTEWEBDRIVER_BROWSERS, pr.getProperty(KEYS.REMOTEWEBDRIVER_BROWSERS.name()));
        oneInstance.add(KEYS.ENVIRONMENT, env);
        oneInstance.add(KEYS.SYSTEM, system);
       // pr.tearDown();
    }


    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(String browser, Method method) throws IOException {
        logger.info("Before Method is called..");
        String toLowercaseBrowser = browser.toLowerCase();
        this.browser = toLowercaseBrowser;
        logger.info(toLowercaseBrowser + " browser will be launched.");
        ExtentTestManager.startTest(method.getName(), method.getName() + " - " + browser.toUpperCase());

        if (oneInstance.getAsString(KEYS.SYSTEM.name()).equalsIgnoreCase("local")) {
            switch (toLowercaseBrowser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver(setChromeOptions()));
                    getDriver().manage().window().fullscreen();
                    getDriver().get(setAppURL());
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver.set(new FirefoxDriver());
                    getDriver().manage().window().fullscreen();
                    getDriver().get(setAppURL());
                    break;
                default:
                    throw new InvalidArgumentException("browser : " + browser + " is invalid.");
            }
        } else {
                instantiateRemoteWebDriver(toLowercaseBrowser);
                getDriver().manage().window().fullscreen();
                getDriver().get(setAppURL());
        }

    }

    protected String getUserCurrentDirectory() {
        return System.getProperty("user.dir");
    }

    protected String getUserResourceDirectory() {
        return getUserCurrentDirectory() + RESOURCES_PATH;
    }

    protected String getPropertyFilesDirectory() {
        return getUserResourceDirectory() + PROPERTY_FILE_PATH;
    }

    private void instantiateRemoteWebDriver(String browserName) throws IOException {
        try {
            //StartRemoteDriverServer.startServer();
            oneInstance.waitFor(5);
            //StartRemoteDriverServer.startNode();
            driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), getBrowserCapabilities(browserName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DesiredCapabilities getBrowserCapabilities(String browserType) {
        switch (browserType) {
            case "firefox":
              //  System.setProperty("webdriver.gecko.driver",
                //        oneInstance.getAsString(KEYS.TEST_RESOURCES.toString()) + "/Exe/" + "geckodriver26.exe");
                logger.info("Opening firefox driver in Node" + oneInstance.getAsString(KEYS.TEST_RESOURCES.toString()) + "/Exe/" + "geckodriver26.exe");
                return DesiredCapabilities.firefox();
            case "chrome":
             //   System.setProperty("webdriver.chrome.driver",
              //          oneInstance.getAsString(KEYS.TEST_RESOURCES.toString()) + "/Exe/" + "chromedriver.exe");
                logger.info("Opening chrome driver in Node");
                return DesiredCapabilities.chrome();
            case "IE":
                logger.info("Opening IE driver in Node");
                return DesiredCapabilities.internetExplorer();
            default:
                throw new InvalidArgumentException("browser : " + browserType + " is invalid.");
        }
    }


    private ChromeOptions setChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        return options;
    }

    private String getBrowserNamesForRemoteWebdriver() throws IOException {
        PropertyFileReader pr = new PropertyFileReader(getPropertyFilesDirectory() + "/CommonAppProperties.properties");
        String remoteWebdriver_Browsers = pr.getProperty("REMOTEWEBDRIVER_BROWSERS");
        pr.tearDown();
        logger.info(" Remote Webdriver browsers are:" + remoteWebdriver_Browsers);
        return remoteWebdriver_Browsers;
    }


    private String setAppURL() throws IOException {
        String filePath = oneInstance.getAsString(KEYS.TEST_RESOURCES.name())
                + "/TestSuite/" + env
                + "/" + env +".properties";
        logger.info(filePath);
        PropertyFileReader pr = new PropertyFileReader(filePath);
        String appURL = pr.getProperty("APP_URL");
        pr.tearDown();
        logger.info("Opening: " + appURL);
        return appURL;
    }

    public WebDriver getDriver() {
        return driver.get();
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        ExtentTestManager.endTest();
        logger.info("Browser will be killed now.");
        getDriver().close();
    }

    @AfterSuite
    public void postRun() throws IOException {
        logger.info("Inside driver cleanUp");
       /* PropertyFileReader pr = new PropertyFileReader(getPropertyFilesDirectory()
                + "/email.properties");
        Email.triggerEmail(
                oneInstance.getAsString(KEYS.REPORT_PATH.name()) + "/ExtentReportResults.html",
                "AutomationReport.html", "Email For Automation Run", "("
                        + oneInstance.getCurrentDateAndTime()
                        + ") Automation Run ",
                pr.getProperty("TOLIST")
                        .trim(), pr
                        .getProperty("FromMail").trim());
        pr.tearDown();*/
        Command.execCommand("taskkill /IM chromedriver.exe /F");
        Command.execCommand("taskkill /IM geckodriver.exe /F");

    }

}

package com.test.testlisteners;


import com.relevantcodes.extentreports.LogStatus;
import com.test.tests.BaseTest;
import com.main.utilities.ExtentReporter.ExtentManager;
import com.main.utilities.ExtentReporter.ExtentTestManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;


public class TestListeners extends BaseTest implements ITestListener {
    private static Logger logger = LogManager.getLogger(TestListeners.class.getName());

    private static String getTestMethodName(ITestResult iTestResult) {
        String methodName = iTestResult.getMethod().getConstructorOrMethod().getName();
        String methodDescription = iTestResult.getMethod().getDescription();
        if (null == methodDescription || "".equals(methodDescription)) {
            return methodName;
        }
        return methodDescription;
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        logger.info("I am in onStart method " + iTestContext.getName());
        ExtentTestManager.addMainTest(iTestContext.getName(), "Status of " + iTestContext.getName());

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        logger.info("I am in onFinish method " + iTestContext.getName());
        ExtentTestManager.endMainTest();
        ExtentManager.getReporter().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
        Object testClass = iTestResult.getInstance();
        WebDriver webDriver = ((BaseTest) testClass).getDriver();

        /*BufferedImage buffImg = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(3000))
                .takeScreenshot(webDriver).getImage();
*/

        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);

        //String base64Screenshot = "data:image/png;base64," + imgToBase64String(buffImg, "png");


        ExtentTestManager.getTest().log(LogStatus.PASS, getTestMethodName(iTestResult) + ": Passed",
                ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
        ExtentTestManager.getMainTest().log(LogStatus.PASS, getTestMethodName(iTestResult) + ": Passed");
        ExtentTestManager.getMainTest().appendChild(ExtentTestManager.getTest());

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        try {
            Object testClass = iTestResult.getInstance();
            WebDriver webDriver = ((BaseTest) testClass).getDriver();

            /*BufferedImage buffImg = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(3000))
                    .takeScreenshot(webDriver).getImage();
*/

            String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) webDriver).
                    getScreenshotAs(OutputType.BASE64);

            //String base64Screenshot = "data:image/png;base64," + imgToBase64String(buffImg, "png");

            ExtentTestManager.getTest().log(LogStatus.FAIL, "Reason:" + iTestResult.getThrowable().getMessage(),
                    ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
            ExtentTestManager.getMainTest().log(LogStatus.FAIL, getTestMethodName(iTestResult) + ": Failed");
            ExtentTestManager.getMainTest().appendChild(ExtentTestManager.getTest());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public synchronized String imgToBase64String(RenderedImage img, String formatName) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            ImageIO.write(img, formatName, os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        ExtentTestManager.getTest().log(LogStatus.SKIP, getTestMethodName(iTestResult) + ": Skipped");
        ExtentTestManager.getMainTest().appendChild(ExtentTestManager.getTest());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }
}

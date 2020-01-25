package com.main.utilities.ExtentReporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class ExtentTestManager {
    static Logger logger = LogManager.getLogger(ExtentTestManager.class.getName());
    static HashMap<Integer, ExtentTest> extentTestMap = new HashMap<>();
    static HashMap<Integer, ExtentTest> extentMainTestMap = new HashMap<>();
    static ExtentReports extent = ExtentManager.getReporter();

    public static synchronized ExtentTest getTest() {

        return extentTestMap.get((int) (Thread.currentThread().getId()));
    }

    public static synchronized void endTest() {
        extent.endTest(extentTestMap.get((int) (Thread.currentThread().getId())));
    }

    public static synchronized void endMainTest(int key) {
        extent.endTest(extentMainTestMap.get(key));
    }

    public static synchronized void endMainTest() {
        extent.endTest(extentMainTestMap.get((int) (Thread.currentThread().getId())));
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.startTest(testName, desc);
        logger.debug("Thread IDs are : " + (int) (Thread.currentThread().getId()));
        extentTestMap.put((int) (Thread.currentThread().getId()), test);
        return test;
    }

    public static synchronized ExtentTest addMainTest(String testName, String desc) {
        ExtentTest test = extent.startTest(testName, desc);
        extentMainTestMap.put((int) (Thread.currentThread().getId()), test);
        return test;
    }

    public static synchronized ExtentTest getMainTest() {
        ExtentTest test = extentMainTestMap.get((int) (Thread.currentThread().getId()));
        if(null == test) {
            for(ExtentTest value : extentMainTestMap.values()) {
                test = value;
            }
        }
        return test;
    }
}
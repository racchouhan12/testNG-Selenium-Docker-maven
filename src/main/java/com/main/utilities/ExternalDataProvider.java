package com.main.utilities;

import com.main.utilities.helpers.KEYS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.lang.reflect.Method;

public class ExternalDataProvider {
    private static Logger logger = LogManager.getLogger(ExternalDataProvider.class.getName());
    private static OneInstance oneInstance = OneInstance.getInstance();

    /**
     * Here .xlsx file name should be same as Test method name.
     * and .xlsx file should be saved inside Environment folder
     * @param method
     * @return
     */
    @DataProvider(name = "data-dataFileSpecificToSingleTest")
    public synchronized static Object[][] dataFileSpecificToSingleTest(Method method) {
        String fileName = oneInstance.getAsString(KEYS.TEST_SUITE.name())
                + "/" + oneInstance.getAsString(KEYS.ENVIRONMENT.name())
                + "/" + method.getName() + ".xlsx";
        logger.info("Test data filename is " + fileName);
        return ExcelUtils.getAllDataFromSheet(new File(fileName), "Sheet1", true);
    }

    /**
     * Here .xlsx file name should be testDatafile.
     * and .xlsx file should be saved inside Environment folder
     * and sheet name should be same as Test method name
     * @param method
     * @return
     */

    @DataProvider(name = "data-commonFileInEnvWithDataInDifferentSheet")
    public synchronized static Object[][] commonFileInEnvWithDataInDifferentSheet(Method method) {
        String fileName = oneInstance.getAsString(KEYS.TEST_SUITE.name())
                + "/" + oneInstance.getAsString(KEYS.ENVIRONMENT.name())
                + "/testDatafile"  + ".xlsx";
        logger.info("Test data filename is " + fileName);
        return ExcelUtils.getAllDataFromSheet(new File(fileName), method.getName(), true);
    }

    /**
     * Here .xlsx file name should be commonTestData.
     * and .xlsx file should be saved inside TestSuite folder
     * and sheet name should be same as Test method name
     * @param method
     * @return
     */
    @DataProvider(name = "data-commonFileOfAllEnvWithDataInDifferentSheet")
    public synchronized static Object[][] commonFileOfAllEnvWithDataInDifferentSheet(Method method) {
        String fileName = oneInstance.getAsString(KEYS.TEST_SUITE.name())
                + "/commonTestData"  + ".xlsx";
        logger.info("Test data filename is " + fileName);
        return ExcelUtils.getAllDataFromSheet(new File(fileName), method.getName(), true);
    }
}

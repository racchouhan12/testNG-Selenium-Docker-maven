package com.main.utilities.ExtentReporter;


import com.relevantcodes.extentreports.ExtentReports;
import com.main.utilities.helpers.KEYS;
import com.main.utilities.OneInstance;

//OB: ExtentReports extent instance created here. That instance can be reachable by getReporter() method.
public class ExtentManager {

    private static ExtentReports extent;
    private static OneInstance oneInstance = OneInstance.getInstance();

    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            //Set HTML reporting file location
            String workingDir = System.getProperty("user.dir");
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                extent = new ExtentReports(oneInstance.getAsString(KEYS.REPORT_PATH.name()) + "\\ExtentReportResults.html", true);
            }
            else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                extent = new ExtentReports(workingDir + "/ExtentReports/ExtentReportResults.html", true);
            }
        }
        return extent;
    }
}
package com.main.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;

public class FileUtils {

    private static Logger logger = LogManager.getLogger(FileUtils.class.getName());
    private FileUtils() {

    }

    public static boolean createFolder(String nameOfFolder) {
        File file = new File(nameOfFolder);
        if (!file.exists()) {
            if (file.mkdir()) {
                logger.info(nameOfFolder + "created");
                return true;
            } else {
                logger.error("Fail to create " +nameOfFolder);

                return false;
            }
        }
        return false;
    }

    public static void uploadFileUsingRobot(String filePath) throws AWTException {
        StringSelection ss = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

        Robot robot = new Robot();
        robot.delay(250);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
}

package com.main.utilities;

import com.main.utilities.helpers.KEYS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Command {
    private static Logger logger = LogManager.getLogger(Command.class.getName());
    private static Runtime runtime = Runtime.getRuntime();
    private static OneInstance oneInstance = OneInstance.getInstance();

    private Command() {

    }

    public static void execTCommand(String command) throws IOException, InterruptedException {
        String cmd = "java -jar " + oneInstance.getAsString(KEYS.PROJECT_PATH.toString())
                + oneInstance.getAsString(KEYS.STANDALONE_SERVER_PATH.toString()) + " -role hub -hubConfig "
                + oneInstance.getAsString(KEYS.PROJECT_PATH.toString()) + "/defaultHubConfig.json";
        logger.debug("Command generated------------------------------" + cmd);
        Process process = runtime.exec(cmd);
        process.waitFor();
        oneInstance.waitFor(10);
        //logger.debug(process.getInputStream().);

        logger.debug(process.exitValue());
        // process.
    }

    public static void execCommand(String command) {
        try {
            logger.debug("Command generated------------------------------" + command);
            Process process = runtime.exec(command);
            process.waitFor();
            oneInstance.waitFor(1);
            logger.debug(process.exitValue());
        } catch (IOException | InterruptedException e) {
            logger.error("Exception occured while killing process" + e.getMessage());
        }
    }
}

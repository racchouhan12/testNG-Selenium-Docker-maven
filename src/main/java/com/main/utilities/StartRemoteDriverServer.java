package com.main.utilities;

import com.main.utilities.helpers.KEYS;

import org.openqa.grid.selenium.GridLauncherV3;

public class StartRemoteDriverServer {
    private static OneInstance oneInstance = OneInstance.getInstance();

    public static void startServer() {
        String serverJarPath = oneInstance.getAsString(KEYS.STANDALONE_SERVER_PATH.name());
        String hubJsonPath = oneInstance.getAsString(KEYS.TEST_RESOURCES.name()) + "\\remotewebdriver\\hubConfig.json";
        GridLauncherV3.main(new String [] { "-role", "hub", "-hubConfig", hubJsonPath});
    }

    public static void startNode() {
        String serverJarPath = oneInstance.getAsString(KEYS.STANDALONE_SERVER_PATH.name());
        String nodeJsonPath = oneInstance.getAsString(KEYS.TEST_RESOURCES.name()) + "\\remotewebdriver\\nodeConfig.json";
        GridLauncherV3.main(new String [] {  "-role", "node", "-nodeConfig", nodeJsonPath});
    }
}

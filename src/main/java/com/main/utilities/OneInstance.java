package com.main.utilities;


import com.main.utilities.helpers.KEYS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class OneInstance {

    private static OneInstance ourInstance;
    private static Logger logger = LogManager.getLogger(OneInstance.class.getName());
    private HashMap<String, Object> sessionState = new HashMap<>();

    private OneInstance() {
        sessionState.clear();
        add(KEYS.PROJECT_PATH, System.getProperty("user.dir"));
        add(KEYS.TEST_RESOURCES, getAsString("PROJECT_PATH") + "\\src\\test\\resources");
        add(KEYS.REPORT_PATH, getAsString("PROJECT_PATH") + "\\reports");
        add(KEYS.OS_NAME, System.getProperty("os.name"));
        add(KEYS.PROPERTY_FILE_PATH, getAsString("TEST_RESOURCES") + "\\propertyfiles");
        add(KEYS.TEST_SUITE, getAsString("TEST_RESOURCES") + "\\TestSuite");
    }

    public static OneInstance getInstance() {

        if (ourInstance == null) {
            logger.debug("OneInstance is null intialize it....");
            return ourInstance = new OneInstance();
        }
        return ourInstance;
    }

    public void add(String key, Object value) {
        sessionState.put(key, value);
    }

    public void add(KEYS key, Object value) {
        add(key.name(), value);
    }

    public Object get(String key) {
        return sessionState.get(key);
    }

    public String getAsString(String key) {
        return sessionState.get(key).toString();
    }

    public String getCurrentDateAndTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }


    public void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

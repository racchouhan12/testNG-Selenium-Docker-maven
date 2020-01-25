package com.main.utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class CommonUtils {
    private CommonUtils() {

    }

    public static void main(String[] arg) {
        System.out.println(getRandomAlphaStringInUpperCaseOfLen(5));
    }

    public static synchronized String getRandomAlphaStringInUpperCaseOfLen(int length) {
        return RandomStringUtils.randomAlphabetic(length).toUpperCase();
    }

    public static synchronized String getRandomAlphaStringInLowerCaseOfLen(int length) {
        return RandomStringUtils.randomAlphabetic(length).toLowerCase();
    }

    public static synchronized String getRandomAlphaStringOfLen(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static synchronized String getRandomAlphaNumStringOfLen(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}

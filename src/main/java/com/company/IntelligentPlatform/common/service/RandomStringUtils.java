package com.company.IntelligentPlatform.common.service;

import java.util.Random;

/**
 * TODO-LEGACY: Stub replacing org.apache.commons.lang.RandomStringUtils.
 */
public class RandomStringUtils {

    private static final Random RANDOM = new Random();

    public static String randomNumeric(int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }

    public static String randomAlphanumeric(int count) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(chars.charAt(RANDOM.nextInt(chars.length())));
        }
        return sb.toString();
    }
}

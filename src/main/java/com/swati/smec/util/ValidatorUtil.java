package com.swati.smec.util;

import org.apache.commons.lang3.StringUtils;

public class ValidatorUtil {

    private ValidatorUtil() {
        throw new IllegalStateException("ValidatorUtil is a Utility class");
    }

    public static boolean isValidParam(final String paramToValidate) {
        return StringUtils.isNotBlank(paramToValidate);
    }
    public static boolean isNotValidParam(final String paramToValidate) {
        return !isValidParam(paramToValidate);
    }
}

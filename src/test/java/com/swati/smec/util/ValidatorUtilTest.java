package com.swati.smec.util;

import org.junit.Assert;
import org.junit.Test;

public class ValidatorUtilTest {

    @Test
    public void testIsValidParam() throws Exception {
        boolean result = ValidatorUtil.isValidParam("paramToValidate");
        Assert.assertEquals(true, result);
    }

    @Test
    public void testIsNotValidParam() throws Exception {
        boolean result = ValidatorUtil.isNotValidParam(" ");
        Assert.assertEquals(true, result);
    }
}

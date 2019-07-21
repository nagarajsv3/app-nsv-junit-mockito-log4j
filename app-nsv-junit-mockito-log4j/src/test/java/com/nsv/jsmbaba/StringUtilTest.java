package com.nsv.jsmbaba;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {
    private StringUtil stringUtil = new StringUtil();

    @Test
    public void checkIfStringHasDuplicateCharsTest(){
        Assert.assertTrue(stringUtil.checkIfStringHasDuplicateChars("hello"));
        Assert.assertFalse(stringUtil.checkIfStringHasDuplicateChars("bye"));
        Assert.assertFalse(stringUtil.checkIfStringHasDuplicateChars(null));
        Assert.assertFalse(stringUtil.checkIfStringHasDuplicateChars(""));
    }
}

package com.nsv.jsmbaba;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {
    private Calculator calculator = new Calculator();

    @Test
    public void addTest(){
        Assert.assertEquals(3, calculator.add(1,2));
    }

    @Test
    public void setPriceTest(){
        calculator.setPrice(21);
    }
}

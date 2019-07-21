package com.nsv.jsmbaba.mockito;

import com.nsv.jsmbaba.com.nsv.jsmbaba.mockito.Calculate;
import com.nsv.jsmbaba.com.nsv.jsmbaba.mockito.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.verification.VerificationMode;

public class CalculateTest {
    @Mock
    private Calculate calculate;

    @Spy
    private Employee employee = new Employee("Naga","Varnekar");

    private Employee employee1 = new Employee("Naga","Varnekar");

    private Employee spyEmployee;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        Mockito.when(calculate.add(1,2)).thenReturn(3);
        Mockito.when(calculate.add(2,3)).thenReturn(5);
        Mockito.when(calculate.div(10,5)).thenReturn(2);
        Mockito.when(calculate.div(Mockito.anyInt(),Mockito.eq(0))).thenThrow(new ArithmeticException());
        Mockito.when(employee.getFirstName()).thenReturn("Nagaraja");
        spyEmployee = Mockito.spy(employee1);
        Mockito.when(spyEmployee.getLastName()).thenReturn("NSV");
    }

    @Test
    public void addTest(){
        Assert.assertEquals(3,calculate.add(1,2));
        Assert.assertEquals(5,calculate.add(2,3));
        Mockito.verify(calculate, Mockito.times(2)).add(Mockito.anyInt(),Mockito.anyInt());
    }

    @Test
    public void divTest(){
        Assert.assertEquals(2,calculate.div(10,5));
    }

    @Test(expected = ArithmeticException.class)
    public void divTestE(){
        calculate.div(10,0);
    }

    @Test
    public void mockitoSpyTest(){
        Assert.assertEquals("Nagaraja",employee.getFirstName());
        Assert.assertEquals("NSV",spyEmployee.getLastName());
    }


}

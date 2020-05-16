package com.example.unittest;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PositiveNumvbervalidatorTest {

    PositiveNumvbervalidator positiveNumvbervalidator;
    @Before
    public void setUp() throws Exception {
        positiveNumvbervalidator = new PositiveNumvbervalidator();
    }

    @Test
    public void checkIfPositiveNumberWorksForNegative() {
        boolean isValid = positiveNumvbervalidator.isPositiveNumber(-10);
        Assert.assertThat(isValid, is(false));
    }

    @Test
    public void name() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    public  void checkIfPositiveNumberWorksForZero(){
        boolean result = positiveNumvbervalidator.isPositiveNumber(0);
        assertThat(result, is(false));

    }

    @Test
    public  void checkIfPositiveNumberWorksForPositive(){
        boolean result = positiveNumvbervalidator.isPositiveNumber(10);
        assertThat(result, CoreMatchers.is(true));
    }
}
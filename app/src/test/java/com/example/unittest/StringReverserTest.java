package com.example.unittest;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringReverserTest {

    StringReverser stringReverser;
    @Before
    public void setUp() throws Exception {
        stringReverser = new StringReverser();
    }

    @Test
    public void reverse_emptyString_emptyStringReturned(){
        String text = "";
        assertThat(stringReverser.reverse(text), CoreMatchers.equalTo(text));
    }

    @Test
    public void reverse_singleCharacter_sameCharacterReturned(){
        String result = stringReverser.reverse("a");
        assertThat(result, CoreMatchers.is("a"));
    }

    @Test
    public void reverse_string_reversedStringReturned(){
        String result = stringReverser.reverse("tadese");
        assertThat(result, CoreMatchers.is("esedat"));
    }
}
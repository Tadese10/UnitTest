package com.example.unittest;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StringDuplicatorTest {

    StringDuplicator SUT;
    @Before
    public void setUp() throws Exception {
        SUT = new StringDuplicator();
    }

    @Test
    public void duplicator_onEmptyString_emptyStringReturned() {
        String result  = SUT.duplicate("");
        assertThat(result, is(""));
    }

    @Test
    public void duplicator_onACharacter_characterReturned() {
        String result  = SUT.duplicate("a");
        assertThat(result, is("aa"));
    }

    @Test
    public void duplicator_onAString_stringReturned() {
        String result  = SUT.duplicate("Tadese");
        assertThat(result, is("TadeseTadese"));
    }
}
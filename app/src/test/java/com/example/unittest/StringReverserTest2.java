package com.example.unittest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class StringReverserTest2 {

    // region constants

    // endregion constants

    // region helper fields

    // endregion helper fields

    StringReverser SUT;

    @Before
    public void setup() throws Exception {
        SUT = new StringReverser();

    }

}
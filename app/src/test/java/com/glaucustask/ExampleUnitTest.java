package com.glaucustask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    MainActivity mainActivity;

    @Before
    public void tearUp() {
        mainActivity = new MainActivity();
    }

    @After
    public void tearDown() {
        mainActivity = null;
    }

    @Test
    public void isEmailCorrect() {
        assertEquals(false, mainActivity.validateEmailAddress("abc"));
        assertEquals(true, mainActivity.validateEmailAddress("abc@gmail.com"));
    }

    @Test
    public void testGetIndexById() {
        ArrayList<Data> list = new ArrayList<>();
        assertEquals(-1, mainActivity.getIndexById(list, 8));
        Data data = new Data(true, "");
        data.setIdtableEmail(8);
        list.add(data);
        assertEquals(1, mainActivity.getIndexById(list, 8));
    }
}
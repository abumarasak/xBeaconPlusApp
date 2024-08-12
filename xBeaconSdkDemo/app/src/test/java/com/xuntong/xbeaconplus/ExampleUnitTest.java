package com.xuntong.xbeaconplus;

import com.xuntong.xbeaconplus.util.MyUtil;
import com.xuntong.xbeaconplus.util.OrderUtil;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        System.out.print(MyUtil.array2Str(OrderUtil.getSetPowerOrder(7)));
        System.out.print("\r\n");
        System.out.print((byte) 0xFF);
    }
}
package com.tianhy.study;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testPath(){
//        System.out.println(System.getProperty("user.dir"));

        //左边大于右边的情况
        System.out.println(10%1);
        System.out.println(10%2);
        System.out.println(10%3);
        System.out.println(10%4);
        System.out.println(10%5);
        System.out.println(10%10);
    }
}

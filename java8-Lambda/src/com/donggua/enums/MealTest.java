package com.donggua.enums;

import org.junit.Test;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-12-15 下午 03:29
 */
public class MealTest {

    @Test
    public void test1() {
        Meal[] values = Meal.values();
        for (Meal value : values) {
            System.out.println(value.name() + "==" + value.ordinal());
        }
    }

    @Test
    public void test2() {
        System.out.println(40 >> 5);
        System.out.println(40 & 2 * 5 - 1);
    }

}

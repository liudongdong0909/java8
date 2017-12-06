package com.donggua.enums;

import org.junit.Test;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-11-28 上午 11:02
 */
public class TestEnums {

    @Test
    public void test1() {

        JSONDataObject jsonDataObject = new JSONDataObject<>(ValidStatus.DISABLE, null);
        System.out.println(jsonDataObject.getRetCode() + "===" + jsonDataObject.getRetMsg() + "===" + jsonDataObject.getData());
    }

    @Test
    public void test2() {

        JSONDataObject jsonDataObject = new JSONDataObject<>(Status.SUCCESS, null);
        System.out.println(jsonDataObject.getRetCode() + "===" + jsonDataObject.getRetMsg() + "===" + jsonDataObject.getData());
    }
}

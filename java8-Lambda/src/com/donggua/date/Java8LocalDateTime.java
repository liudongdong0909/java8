package com.donggua.date;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by donggua on 2017/8/20.
 */
public class Java8LocalDateTime {

    /**
     * LocalDate
     * LocalTime
     * Java8LocalDateTime
     */
    @Test
    public void test1() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        System.out.println("-----------------");

        LocalDateTime localDateTime1 = LocalDateTime.of(2016, 8, 20, 03, 12, 25);
        System.out.println(localDateTime1);
        System.out.println("年:" + localDateTime1.getYear());
        System.out.println("月:" + localDateTime1.getMonth().getValue());
        System.out.println("月日：" + localDateTime1.getDayOfMonth());
        System.out.println("周日：" + localDateTime1.getDayOfWeek().getValue());
        System.out.println("年日：" + localDateTime1.getDayOfYear());
        System.out.println("小时：" + localDateTime1.getHour());
        System.out.println("分钟：" + localDateTime1.getMinute());
        System.out.println("秒：" + localDateTime1.getSecond());


        // 加5天
        LocalDateTime localDateTime2 = localDateTime1.plusDays(5);
        System.out.println(localDateTime2);

        //前一年
        LocalDateTime localDateTime3 = localDateTime1.minusYears(1);
        System.out.println(localDateTime3);

        //修改为几月
        LocalDateTime localDateTime4 = localDateTime1.withMonth(9);
        System.out.println(localDateTime4);

        //比较两个日期
        boolean isBefore = localDateTime1.isBefore(localDateTime3);
        System.out.println(isBefore);

        //是否闰年
        boolean isLeapYear = LocalDate.now().isLeapYear();
        System.out.println(isLeapYear);
    }

}

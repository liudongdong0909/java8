package com.donggua.date;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

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

    /**
     * Instant : 时间戳（按 Unix 元年 1970年1月1日 00:00:00 所经历的毫秒进行计算）
     */
    @Test
    public void test2(){
        Instant instant = Instant.now();// 默认获取按 UTC 的世界时间
        System.out.println(instant);

        System.out.println("-------------");

        OffsetDateTime offsetDateTime = Instant.now().atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);

        System.out.println("-------------");

        Instant epochSecond = Instant.ofEpochSecond(5);
        System.out.println(epochSecond);

        System.out.println("-------------");


        Instant ofEpochSecond = Instant.ofEpochSecond(1, 1000000000);
        System.out.println(ofEpochSecond);

        System.out.println("-------------");

        int ins = instant.get(ChronoField.NANO_OF_SECOND);
        System.out.println(ins);

        System.out.println("-------------");

        long epochMilli = instant.toEpochMilli();
        System.out.println(epochMilli);

    }

    /*
	 * Duration : 计算两个“时间”的间隔
	 * Period : 计算两个“日期”的间隔
	 */
    @Test
    public  void test3(){
        LocalTime localTime = LocalTime.now();
        LocalTime localTime1 = LocalTime.of(22, 22, 22);

        Duration duration = Duration.between(localTime1, localTime);
        System.out.println(duration);
        System.out.println(duration.getSeconds());
        System.out.println(duration.getNano());
        System.out.println(duration.toHours());
        System.out.println(duration.toMillis());
        System.out.println(duration.toMinutes());
        System.out.println(duration.toDays());
    }

    @Test
    public  void test4(){
        LocalDate localDate = LocalDate.now();
        LocalDate localDate1 = LocalDate.of(2017, 8, 29);

        Period period = Period.between(localDate1, localDate);
        System.out.println(period);

        System.out.println(period.getDays());
        System.out.println(period.getMonths());
        System.out.println(period.getYears());
    }

    /**
     * TemporalAdjusters: 时间校正器
     */
    @Test
    public  void  test5(){
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime dateTime = localDateTime.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(dateTime);

        System.out.println("---------------");

        LocalDateTime localDateTime2 = localDateTime.with((t) -> {
            LocalDateTime localDateTime1 = (LocalDateTime) t;
            DayOfWeek dayOfWeek = localDateTime1.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return localDateTime1.plusDays(3);
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                return localDateTime1.plusDays(2);
            } else {
                return localDateTime1.plusDays(1);
            }
        });
        System.out.println(localDateTime2);
    }

    /**
     * DateTimeFormatter : 用于日期时间解析格式化
     */
    @Test
    public void test6(){
        //DateTimeFormatter isoDateTime = DateTimeFormatter.ISO_DATE_TIME;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTime = localDateTime.format(dateTimeFormatter);
        System.out.println(dateTime);

        System.out.println("----------------");

        LocalDateTime parse = LocalDateTime.parse(dateTime, dateTimeFormatter);
        System.out.println(parse);
    }

    @Test
    public  void test7(){
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        availableZoneIds.forEach(System.out::println);
        System.out.println(availableZoneIds.size());
    }

    /**
     * 带时区的时间日期
     */
    @Test
    public  void test8(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Europe/Monaco"));
        System.out.println(zonedDateTime);


        System.out.println("------------");


        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Europe/Monaco"));
        System.out.println(dateTime);
    }
}

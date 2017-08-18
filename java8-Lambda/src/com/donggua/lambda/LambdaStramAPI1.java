package com.donggua.lambda;

import com.donggua.employee.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * StramAPI 测试
 *
 * @author IT_donggua
 * @version V1.0
 * @create 2017-08-18 下午 02:16
 */
public class LambdaStramAPI1 {

    /**
     * 1. 创建流
     * <p>
     * 2. 中间操作
     * <p>
     * 3. 终止操作（终端操作）
     */

    //１.创建流
    @Test
    public void test1() {
        // ① 通过 Collection 系列集合提供的方法 stream() 或者 parallelStream()
        List<Employee> list = new ArrayList<>();
        Stream<Employee> stream = list.stream();
        Stream<Employee> parallelStream = list.parallelStream();

        // ② 通过 Arrays中的静态方法 stream() 创建数据源
        Integer[] num = new Integer[23];
        Stream<Integer> stream1 = Arrays.stream(num);

        // ③ 通过 Stream 中的静态方法 of()
        Stream<Integer> stream2 = Stream.of(1, 5, 7);

        // ④ 创建无限流
        // 迭代
        Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(2);
        stream3.forEach(System.out::println);
    }
}

package com.donggua.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * java8 内置4大核心函数式接口
 *
 * @author IT_donggua
 * @version V1.0
 * @create 2017-08-18 上午 09:43
 */
public class LambdaCoreFunction {

    /**
     * java8 内置的四大核心函数式接口
     * 1. Consumer<T>    : 消费型接口    void accept(T t)
     * 2. Supplier<T>    : 供给型接口    T get()
     * 3. Function<T, R> : 函数型接口    R apply(T t)
     * 4. Predicate<T>   : 断言型接口    boolean test(T t)
     */

    @Test
    public void test1() {
        // Consumer<T> : 消费型接口    void accept(T t)
        Consumer<String> consumer = (str) -> System.out.println(str.length());
        consumer.accept("hello world Lambda !");
        // 执行结果: 20
    }

    @Test
    public void test2() {
        // Supplier<T>  : 供给型接口    T get()
        List<Integer> list = this.numberHandler(() -> (int) (Math.random() * 100), 10);
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    private List<Integer> numberHandler(Supplier<Integer> supplier, int num) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    @Test
    public void test3() {
        // Function<T, R> : 函数型接口    R apply(T t)
        // 执行一个整数的开放
        Function<Integer, Double> function = (a) -> Math.sqrt(a);
        Double aDouble = function.apply(100);
        System.out.println(aDouble);
    }

    @Test
    public void test4() {
        // Predicate<T>   : 断言型接口    boolean test(T t)
        List<String> list = Arrays.asList("lambda", "java55", "java7", "java88", "hello");

        Predicate<String> predicate = (str) -> str.length() > 5;
        for (String str : list) {
            if (predicate.test(str)){
                System.out.println(str);
            }

        }
    }
}

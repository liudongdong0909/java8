package com.donggua.lambda;

import org.junit.Test;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-08-11 下午 03:49
 */
public class LambdaGrammer {

    /**
     * Lambda 基础语法： java8中引入了一个新的操作符 "->", 该操作符称为 箭头操作符 或者 Lambda操作符
     * Lanbda 操作符将Lambda 表达式拆分成两部分
     * 左侧： Lambda 表达式需要的所有参数列表
     * 右侧： Lambda 表达式要执行的功能， 即Lambda 体， 方法体
     * <p>
     * Lambda 表达式的语法格式：
     * 语法格式一： 无参， 无返回值， Lambda体只需要一条语句
     * 语法格式二： Lambda 需要一个参数
     * 语法格式三： Lambda只需要一个参数时，Lambda操作符左侧参数的小括号可以省略
     * 语法格式四： Lambda 需要两个参数， 并且有返回值
     * 语法格式五： 当Lambda体只有一条语句时， return 与 大括号可以省略
     */
    @Test
    public void test1() {
        // 语法格式一： 无参， 无返回值， Lambda体只需要一条语句
        Runnable runnable = () -> System.out.println("Hello World Lambda 语法一");
        runnable.run();
    }

    @Test
    public void test2() {
        // 语法格式二： Lambda 需要一个参数
        Consumer<String> consumer = (args) -> System.out.println(args);
        consumer.accept("Hello World Lambda 语法二");
    }

    @Test
    public void test3() {
        // 语法格式三： Lambda只需要一个参数时，Lambda操作符左侧参数的小括号可以省略
        Consumer<String> consumer = args -> System.out.println(args);
        consumer.accept("Hello World Lambda 语法三");
    }

    @Test
    public void test4() {
        // 语法格式四： Lambda 需要两个参数， 并且有返回值
        BinaryOperator<Integer> binaryOperator = (x, y) -> {
            System.out.println("Hello World Lambda 语法四");
            return x + y;
        };
        System.out.println(binaryOperator.apply(1, 9));
    }

    @Test
    public void test5() {
        // 语法格式五： 当Lambda体只有一条语句时， return 与 大括号可以省略
        BinaryOperator<Integer> binaryOperator = (Integer x, Integer y) -> x + y;
        System.out.println(binaryOperator.apply(3, 8));
    }

    @Test
    public void test6() {
        // 语法格式六： Lambda 操作符左侧的参数列表的数据类型可以省略，因为可以由编译器推断得出， 称为： “类型推断"
        //BinaryOperator<Long> binaryOperator = (Long x, Long y) -> {

        BinaryOperator<Long> binaryOperator = (x, y) -> {
            System.out.println("Hello World Lambda 语法六");
            return x + y;
        };
        System.out.println(binaryOperator.apply(5L, 8L));
    }
    /*
    * 类型推断： 上面案例中， Lambda 表达式中的参数类型都是由编译器推断得出的。
    * Lambda 表达式中无需指定类型， 程序依然可以编译， 这是因为 javac 根据程序的上下文，
    * 在后台推断出了参数的类型。 Lambda 表达式的类型依赖于上下文环境， 是由编译器推断出来的。
    * 这就是所谓的 -- 类型推断
    * */
}

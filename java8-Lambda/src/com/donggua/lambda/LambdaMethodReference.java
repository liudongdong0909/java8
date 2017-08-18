package com.donggua.lambda;

import com.donggua.employee.Employee;
import org.junit.Test;

import java.io.PrintStream;
import java.util.function.*;

/**
 * 方法引用：
 *
 * @author IT_donggua
 * @version V1.0
 * @create 2017-08-18 上午 10:13
 */
public class LambdaMethodReference {

    /**
     * 当要传递给Lambda体的操作， 已经有实现的方法了，可以使用方法引用！ --- 可以把 方法引用理解为Lambda表达式的另外一种表现形式
     * 【实现抽象方法的参数列表， 必须与方法引用方法的参数列表保持一致！！！】
     * <p>
     * 方法引用： 使用操作符 " :: " 将方法名和对象或者类的名字分隔开来
     * 语法格式：
     * 1.对象 :: 实例方法名
     * 2.类名 :: 静态方法名
     * 3.类名 :: 实例方法名
     *
     * 例如：
     * 1. (x) -> System.out.println(x);   ===>   System.out::println;
     * 2. BinaryOperator<Double> bo = (x, y) -> Math.pow(x, y); ===>  BinaryOperator<Double> bo = Math::pow;
     *
     * 方法引用的注意：
     *  ① 方法引用的方法参数列表与返回值类型， 需要与函数式接口中抽象方法的参数列表和返回值类型保持一致
     *  ② 若Lambda 的参数列表的第一个参数， 是方法引用方法的调用者， 第二个参数是方法引用方法的参数时(或无参数)。 可以使用： ClassName::methodName
     *   例如： compare((x, y) -> x.equals(y), "adc", "dfdf"); ===> compare(String::equals, "adc", "dfdf");
     */

    // 1.对象 :: 实例方法名
    @Test
    public void test1() {

        PrintStream out = System.out;
        Consumer<String> consumer = (x) -> out.println(x);
        consumer.accept("hello lamdba");

        System.out.println("---------------------------");

        // 1.对象 :: 实例方法名
        Consumer<String> consumer1 = out::println;
        consumer1.accept("hello method reference");

        System.out.println("---------------------------");

        Consumer<String> consumer2 = System.out::println;
        consumer2.accept("hello method reference println()");
    }

    // 1.对象 :: 实例方法名
    @Test
    public void test2() {
        Employee employee = new Employee(11, "林青霞", 28, 5555);
        Supplier<String> supplier = () -> employee.getName();
        System.out.println(supplier.get());

        System.out.println("--------------------");

        Supplier<String> supplier1 = employee::getName;
        System.out.println(supplier1.get());

        System.out.println("--------------------");

        Supplier<Integer> supplier2 = employee::getAge;
        System.out.println(supplier2.get());
    }

    // 2.类名 :: 静态方法名
    @Test
    public void test3() {
        BiFunction<Integer, Integer, Integer> biFunction = (x, y) -> Math.max(x, y);
        System.out.println(biFunction.apply(24, 9));

        System.out.println("---------------------");

        BiFunction<Integer, Integer, Integer> biFunction1 = Math::max;
        System.out.println(biFunction1.apply(89, 22));

        System.out.println("---------------------");

        Supplier<Double> random = Math::random;
        System.out.println(random.get());
    }

    // 3.类名 :: 实例方法名
    @Test
    public  void test4(){
        BiPredicate<String, String> biPredicate = (x, y) -> x.equals(y);
        System.out.println(biPredicate.test("dd", "DD"));

        System.out.println("--------------");

        BiPredicate<String, String> biPredicate1 = String::equals;
        System.out.println(biPredicate1.test("DD", "DD"));

        System.out.println("--------------");

        Consumer<Employee> consumer = (e) -> e.show();
        consumer.accept(new Employee());

        System.out.println("--------------");

        Consumer<Employee> consumer1 = Employee::show;
        consumer1.accept(new Employee());
    }


}

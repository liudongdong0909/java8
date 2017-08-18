package com.donggua.lambda;

import com.donggua.employee.Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 构造器引用
 *
 * @author IT_donggua
 * @version V1.0
 * @create 2017-08-18 下午 01:43
 */
public class LambdaStructureReference {

    /**
     * Lambda 构造器引用
     * <p>
     * 格式： ClassName :: new
     * <p>
     * 注意： 构造器引用的参数列表， 必须与函数式接口中抽象方法的参数列表保持一致
     * <p>
     * 数组应用：
     * <p>
     * 格式： tyep[] :: new
     */

    @Test
    public void test1() {
        Supplier<Employee> supplier = () -> new Employee();
        System.out.println(supplier.get());

        System.out.println("---------------------");

        Supplier<Employee> supplier1 = Employee::new;
        System.out.println(supplier1.get());

        System.out.println("------------------------");

        Function<String, Employee> function = (name) -> new Employee(name);

        System.out.println("============================");

        Function<String, Employee> function1 = Employee::new;
        Employee employee = function1.apply("冬瓜");
        System.out.println(employee.getName());

        System.out.println("--------------------");

        BiFunction<Integer, Integer, Employee> biFunction = Employee::new;
        System.out.println(biFunction.apply(23, 34));
    }


    // 数组引用
    @Test
    public void test2() {
        Function<Integer, String[]> function = String[]::new;
        String[] apply = function.apply(90);
        System.out.println(apply.length);
    }

    @Test
    public void test3() {
        List<Employee> emps = Arrays.asList(
                new Employee(101, "林青霞", 28, 9889.99),
                new Employee(102, "东方不败", 29, 4329.85),
                new Employee(103, "周星驰", 40, 1233.88),
                new Employee(104, "大圣", 500, 5000.44),
                new Employee(105, "张无忌", 15, 3000.09)
        );

        emps.stream()
                .forEach((x) -> System.out.println(x));

        System.out.println("-----------------------");

        emps.stream()
                .forEach(System.out::println);

        System.out.println("-----------------------");

        emps.stream()
                .map((e) -> e.getAge())
                .sorted((x, y) -> Integer.compare(x, y))
                .forEach(System.out::println);

        System.out.println("------------------------");

        emps.stream()
                .map(Employee::getAge)
                .sorted(Integer::compare)
                .forEach(System.out::println);

    }

}

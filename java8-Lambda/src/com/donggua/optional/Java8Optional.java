package com.donggua.optional;

import com.donggua.employee.Employee;
import org.junit.Test;

import java.util.Optional;

/**
 * Created by donggua on 2017/8/20.
 */
public class Java8Optional {

    /**
     * Optional 不能被序列化
     */
    /**
     * Optional.of(T t) : 创建一个 Optional 实例
     * Optional.empty() : 创建一个空的 Optional 实例
     * Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
     * isPresent() : 判断是否包含值
     * orElse(T t) :  如果调用对象包含值，返回该值，否则返回t
     * orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
     * map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
     * flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
     *
     */
    @Test
    public void test1(){
        /*Optional<Employee> employee = Optional.of(null);
        Employee e = employee.get();
        System.out.println(e);*/

        /*Optional<Employee> employee1 = Optional.empty();
        System.out.println(employee1.get());*/

        Optional<Employee> employee = Optional.ofNullable(new Employee());
        System.out.println(employee.get());

        Employee employee1 = employee.orElse(new Employee(101, "东嘎", 18, 999.00));
        System.out.println(employee1);

        Employee employee2 = employee.orElseGet(() -> new Employee());
        System.out.println(employee2);

        Optional<String> optional = Optional.ofNullable(null);
        Optional<String> newOptional = optional.map((s) -> s.toUpperCase());
        System.out.println(newOptional.get());

    }
}

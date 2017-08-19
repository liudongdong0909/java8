package com.donggua.lambda;

import com.donggua.employee.Employee;
import com.donggua.funtionalinterface.MyFuncitonInterface;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lambad 函数式接口
 *
 * @author IT_donggua
 * @version V1.0
 * @create 2017-08-17 下午 01:18
 */
public class LambdaFunctionalInterface {

    /**
     *
     * 什么是函数式接口
     * 1. 只包含一个抽象方法的接口， 称为函数式接口。
     * 2. 可以通过 Lambda 表达式来创建该接口的对象。（若 Lambda 表达式抛出一个受检异常， 那么该异常需要在目标接口的抽象方法上进行声明）
     * 3. 可以再任意函数式接口上使用 @FunctionalInterface 注解， 这样可以检查他是否是一个函数式接口， 同时 javadoc 也会包含一条声明， 说明这个接口是一个函数式接口。
     *
     *
     */

    /**
     * 作为参数传递 Lambda 表达式：为了将 Lambda 表达式作为参数传递， 接收 lambda 表达式的参数类型必须是与该 Lambda 表达式兼容的函数式接口的类型
     */
    @Test
    public void test() {
        // 作为参数传递 Lambda 表达式
        String newStr = this.toUpperString(str -> str.toUpperCase(), "ggfhwertsdfqwerwwefsdvfds");
        System.out.println(newStr);
    }

    //作为参数传递 Lambda 表达式
    private String toUpperString(MyFuncitonInterface<String> mf, String str) {
        return mf.getValue(str);

    }

    // 来一个综合案例看看
    List<Employee> emps = Arrays.asList(
            new Employee(101, "林青霞", 28, 9889.99),
            new Employee(102, "东方不败", 29, 4329.85),
            new Employee(103, "周星驰", 40, 1233.88),
            new Employee(104, "大圣", 500, 5000.44),
            new Employee(105, "张无忌", 15, 3000.09)
    );

    // 获取所有年龄小于30 的 Employee
    public List<Employee> filterEmployeeAge(List<Employee> list) {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : list) {
            if (employee.getAge() <= 30) {
                employees.add(employee);
            }
        }
        return employees;
    }

    @Test
    public void test4() {
        List<Employee> employees = this.filterEmployeeAge(emps);
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    // 获取工资大于 4000 的 Employee
    public List<Employee> filterEmployeeSalary(List<Employee> list) {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : list) {
            if (employee.getSalary() >= 4000) {
                employees.add(employee);
            }
        }
        return employees;
    }

    @Test
    public void test5() {
        List<Employee> employees = this.filterEmployeeSalary(emps);
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    // 上面两种根据不同条件过滤数据的方法 代码严重冗余
    // 优化方式一： 使用策略模式 进行重构
    // 第一步：创建一个自定义接口 MyPredicate<Employee>
    // 第二步：分别创建根据年龄和工资过滤的类实现自定义函数式接口

    public List<Employee> filterEmployee(List<Employee> list, MyPredicate<Employee> myPredicate) {
        List<Employee> employees = new ArrayList<>();

        for (Employee employee : list) {
            if (myPredicate.apply(employee)) {
                employees.add(employee);
            }
        }
        return employees;
    }

    @Test
    public void test6() {
        // age 小于 30 的数据
        List<Employee> ageEmployees = this.filterEmployee(emps, new FilterEmployeeForAge());
        for (Employee ageEmployee : ageEmployees) {
            System.out.println(ageEmployee);
        }

        System.out.println("-------------------------");

        // salary 大于4000的数据
        List<Employee> salaryEmployee = this.filterEmployee(emps, new FilterEmployeeForSalary());
        for (Employee employee : salaryEmployee) {
            System.out.println(employee);
        }
    }

    // 优化方式二：
    @Test
    public void test7() {
        List<Employee> employees = this.filterEmployee(emps, new MyPredicate<Employee>() {
            @Override
            public boolean apply(Employee employee) {
                return employee.getSalary() >= 4000;
            }
        });

        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    // 优化方式三： Lambd 表达式
    @Test
    public void test8() {
        List<Employee> employees = this.filterEmployee(emps, t -> t.getSalary() >= 4000);
        employees.forEach(System.out::println);
    }

    // 优化方式四： Stream API
    @Test
    public void test9() {
        emps.stream()
                .filter(t -> t.getSalary() >= 4000)
                .forEach(System.out::println);

        System.out.println("----------------------");

        emps.stream()
                .map(t -> t.getName())
                .forEach(System.out::println);
    }
}

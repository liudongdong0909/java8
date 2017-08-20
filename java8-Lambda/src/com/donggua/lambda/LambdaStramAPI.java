package com.donggua.lambda;

import com.donggua.employee.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * StramAPI 测试
 *
 * @author IT_donggua
 * @version V1.0
 * @create 2017-08-18 下午 02:16
 */
public class LambdaStramAPI {

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

        System.out.println("-------------");

        // 生成
        Stream<Double> stream4 = Stream.generate(Math::random).limit(4);
        stream4.forEach(System.out::println);
    }

    /**
     * 2. 中间操作
     */

    /**
     * 筛选与切片
     * filter——接收 Lambda ， 从流中排除某些元素。
     * limit——截断流，使其元素不超过给定数量。
     * skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
     * distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
     */
    List<Employee> emps = Arrays.asList(
            new Employee(101, "林青霞", 28, 9889.99),
            new Employee(102, "东方不败", 29, 4329.85),
            new Employee(103, "周星驰", 40, 1233.88),
            new Employee(104, "大圣", 500, 5000.44),
            new Employee(105, "张无忌", 15, 3000.09),
            new Employee(102, "东方不败", 29, 4329.85)
    );

    // 内部迭代 -  迭代操作由Stream API 完成操作
    @Test
    public void test2() {
        // 中间操作不会做任何处理
        Stream<Employee> stream = emps.stream()
                .filter((e) -> {
                    System.out.println("惰性求值");
                    return e.getAge() < 30;
                });
        System.out.println("--------------------");

        // 终止操作，一次性执行全部功能， 称为 "惰性求值"
        stream.forEach(System.out::println);
    }

    // 外部迭代
    @Test
    public void test3() {
        Iterator<Employee> iterator = emps.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    // 中间操作 - 过滤 ， 截断流
    @Test
    public void test4() {
        emps.stream()
                .filter(employee -> employee.getAge() < 30) // 过滤年龄小于30的人
                .limit(1) // 截取一个
                .forEach(System.out::println);
    }

    // 中间操作 - 跳过
    @Test
    public void test5() {

        emps.stream()
                .filter(employee -> employee.getAge() < 30)
                .skip(2)
                .forEach(System.out::println);
    }

    // 中间操作 - 筛选去重
    @Test
    public void test6() {
        emps.stream()
                .distinct()
                .forEach(System.out::println);
    }


    // 中间操作 - 映射

    /**
     * 映射
     * map——接收 Lambda ， 将元素转换成其他形式或提取信息。
     * 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     * <p>
     * flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void test7() {
        List<String> list = Arrays.asList("aaa", "java", "ccc", "java8", "hello world");

        list.stream()
                .map((x) -> x.toUpperCase())
                .forEach(System.out::println);

        System.out.println("-------------");

        emps.stream()
                .map(Employee::getAge)
                .forEach(System.out::println);
    }

    @Test
    public void test8() {
        List<String> list = Arrays.asList("aaa", "java", "ccc", "java8", "hello world");

        Stream<Stream<Character>> streamStream = list.stream()
                .map(LambdaStramAPI::filterCharacter);

        streamStream.forEach((s) -> {
            s.forEach((c) -> System.out.println(c + ""));
            System.out.println();
        });

        System.out.println("----------------------");

        list.stream()
                .flatMap(LambdaStramAPI::filterCharacter)
                .forEach(System.out::println);


    }

    private static Stream<Character> filterCharacter(String str) {
        List<Character> characters = new ArrayList<>();

        for (Character character : str.toCharArray()) {
            characters.add(character);
        }
        return characters.stream();
    }

    // 中间操作 - 排序

    /**
     * 排序
     * sorted()——自然排序
     * sorted(Comparator com)——定制排序
     */
    @Test
    public void test9() {
        emps.stream()
                .map(Employee::getSalary)
                .sorted()
                .forEach(System.out::println);

        System.out.println("-----------------");

        emps.stream()
                .map(Employee::getAge)
                .sorted(Integer::compare)
                .forEach(System.out::println);
    }

    // 终止操作

    /**
     * 查找与匹配
     * allMatch——检查是否匹配所有元素
     * anyMatch——检查是否至少匹配一个元素
     * noneMatch——检查是否没有匹配所有元素
     * findFirst——返回第一个元素
     * findAny——返回当前流中的任意元素
     * count——返回流中元素的总个数
     * max——返回流中最大值
     * min——返回流中最小值
     */
    @Test
    public void test10() {
        boolean allMatch = emps.stream()
                .allMatch((employee -> employee.getName().equals("林青霞")));
        System.out.println(allMatch);

        System.out.println("-----------------");

        boolean anyMatch = emps.stream()
                .anyMatch(employee -> employee.getName().equals("林青霞"));
        System.out.println(anyMatch);

        System.out.println("-----------------");

        boolean noneMatch = emps.stream()
                .noneMatch(employee -> employee.getName().equals("林青霞"));
        System.out.println(noneMatch);
    }

    @Test
    public void test12() {
        Optional<String> first = emps.stream()
                .map(Employee::getName)
                .sorted()
                .findFirst();
        System.out.println(first.get());

        System.out.println("-----------------");

        Optional<Employee> findAny = emps.parallelStream()
                .filter(employee -> employee.getName().equals("林青霞"))
                .findAny();
        System.out.println(findAny.get());
    }

    // 注意： 流一旦执行终止操作后， 就不能在重复使用
    @Test
    public void test13() {
        Stream<Employee> stream = emps.stream();
        long count = stream.count();
        System.out.println(count);

        System.out.println("-----------------");

        Optional<Double> doubleOptional = emps.stream()
                .map(Employee::getSalary)
                .max(Double::compare);
        System.out.println(doubleOptional.get());

        System.out.println("-----------------");

        Optional<Employee> employeeOptional = emps.stream()
                .min((x, y) -> Double.compare(x.getSalary(), y.getSalary()));
        System.out.println(employeeOptional.get());
    }

    //归约
    // reduce(T identity, BinaryOperator) / reduce(BinaryOperator)
    @Test
    public void test14() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(sum);
    }

    @Test
    public void test15() {
        Optional<Double> doubleOptional = emps.stream()
                .map(Employee::getSalary)
                .reduce(Double::max);
        System.out.println(doubleOptional);

        System.out.println("-----------------");

        //查看 东方不败 出现的次数
        Optional<Integer> sumOptional = emps.stream()
                .map(Employee::getName)
                .flatMap(LambdaStramAPI::filterCharacter)
                .map((c) -> {
                    if (c.equals("东")) return 1;
                    else return 0;
                }).reduce(Integer::sum);
        System.out.println(sumOptional.get());
    }

    //收集
    //collect(Collector c) : 将流转换为其他形式。
    //接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
    @Test
    public void test16(){
        List<String> collect = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        collect.forEach(System.out::println);

        System.out.println("-------------------");

        Set<String> set = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        set.forEach(System.out::println);

        System.out.println("-------------------");

        HashSet<String> hashSet = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        hashSet.forEach(System.out::println);
    }

    // 收集统计
    @Test
    public  void test17(){
        // 统计总个数
        Long count = emps.stream()
                .collect(Collectors.counting());
        System.out.println(count);

        System.out.println("-------------------");

        // 求平均值
        Double avg = emps.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);

        System.out.println("-------------------");

        // 求和
        Double sum = emps.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);

        System.out.println("-------------------");

        //求最大值
        Optional<Employee> max = emps.stream()
                .collect(Collectors.maxBy((x, y) -> Double.compare(x.getSalary(), y.getSalary())));
        System.out.println(max.get());

        System.out.println("-------------------");

        //求最小值
        Optional<Double> min = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.minBy(Double::compare));
        System.out.println(min.get());


        System.out.println("-------------------");

        //统计分析
        DoubleSummaryStatistics doubleSummaryStatistics = emps.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(doubleSummaryStatistics.getAverage());

        System.out.println("-------------------");

        //拼接
        String join = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(",", "--", "--"));
        System.out.println(join);
    }

    // 分组
    @Test
    public  void test18(){
        Map<String, List<Employee>> group = emps.stream()
                .collect(Collectors.groupingBy(Employee::getName));
        System.out.println(group);
    }

    // 多级分组
    @Test
    public void test19(){
        Map<String, Map<String, List<Employee>>> group = emps.stream()
                .collect(Collectors.groupingBy(Employee::getName, Collectors.groupingBy((e) -> {
                    if (e.getAge() < 30) return "青年";
                    else if (e.getAge() < 50) return "中年";
                    else return "老年";
                })));
        System.out.println(group);
    }

    //分区
    @Test
    public void test20(){
        Map<Boolean, List<Employee>> partition = emps.stream()
                .collect(Collectors.partitioningBy((e) -> e.getSalary() > 4000));
        System.out.println(partition);
    }
}

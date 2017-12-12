package com.donggua.regex;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.regex.Pattern.compile;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-12-12 上午 11:28
 */
public class RegexAsPredicate {

    @Test
    public void test1() {
        List<String> emails = Arrays.asList("alex@example.com", "bob@yahoo.com", "cat@google.com", "david@example.com");

        Predicate<String> predicate = compile("^(.+)@example.com$").asPredicate();

        List<String> emailList = emails.stream().filter(predicate).collect(Collectors.toList());
        emailList.forEach(System.out::println);
    }
}

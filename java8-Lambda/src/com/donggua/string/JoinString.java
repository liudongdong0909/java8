package com.donggua.string;

import org.junit.Test;

import java.time.ZoneId;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-12-12 下午 01:24
 */
public class JoinString {

    @Test
    public void test() {
        String joined = String.join("/", "user", "local", "nginx");
        System.out.println(joined); // user/local/nginx

        String ids = String.join(",", ZoneId.getAvailableZoneIds().stream().limit(4).collect(Collectors.toList()));
        System.out.println(ids); // Asia/Aden,America/Cuiaba,Etc/GMT+9,Etc/GMT+8
    }

    @Test
    public void test2(){
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        joiner.add("HOW").add("TO").add("DO").add("IN").add("JAVA");

        System.out.println(joiner);
    }
}

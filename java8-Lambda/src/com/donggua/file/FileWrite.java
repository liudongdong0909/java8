package com.donggua.file;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-12-12 下午 01:58
 */
public class FileWrite {

    @Test
    public void test() throws Exception {
        /**
         * 文件不存在会创建文件
         */
        Path path = Paths.get("C:\\Users\\zjcap_03\\Desktop\\2.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("dfdfdfdf");
        }
    }

    @Test
    public void test2() throws Exception {
        String content = "hello world";
        Files.write(Paths.get("C:\\Users\\zjcap_03\\Desktop\\2.txt"), content.getBytes());
    }

    @Test
    public void test3() throws Exception {
        // 文件全路径
        Files.list(Paths.get("C:\\Users\\zjcap_03\\Desktop")).forEach(System.out::println);

        System.out.println("=======================");
        // 文件名
        Files.list(Paths.get("d:")).filter(Files::isRegularFile).forEach(System.out::println);
    }

    @Test
    public void test4() {
        // 查找隐藏文件
        File[] files = new File("c:").listFiles(File::isHidden);
        Arrays.stream(files).forEach(System.out::println);
    }

    @Test
    public void test5() throws Exception {
        Files.newDirectoryStream(Paths.get("d:")).forEach(System.out::println);

        System.out.println("==================");

        Files.newDirectoryStream(Paths.get("d:"), path -> path.toFile().isFile()).forEach(System.out::println);

        System.out.println("==================");

        Files.newDirectoryStream(Paths.get("d:"), path -> path.toString().endsWith(".sql")).forEach(System.out::println);
    }

    @Test
    public void test6() throws Exception{
        Path path = Paths.get("C:\\Users\\zjcap_03\\Desktop", "2.txt");
        Stream<String> lines = Files.lines(path);
        try (Stream<String> lines1 = Files.lines(path)){
            Optional<String> optional = lines.filter(s -> s.contains("he")).findFirst();
            if (optional.isPresent()){
                System.out.println(optional.get());
            }
        }
    }

    /**
     * 一个流生成另一个流， close方法也会自动跟随， 所以可以写为 test7 方式， 具体可以看 test8
     * @throws Exception
     */
    @Test
    public void test7() throws Exception{
        Path path = Paths.get("C:\\Users\\zjcap_03\\Desktop", "2.txt");
        try (Stream<String> filteredLines = Files.lines(path).filter(s -> s.contains("he"))){
            Optional<String> optional = filteredLines.findFirst();
            if (optional.isPresent()){
                System.out.println(optional.get());
            }
        }
    }

    @Test
    public void test8() throws Exception{
        Path path = Paths.get("C:\\Users\\zjcap_03\\Desktop", "2.txt");
        try (Stream<String> filteredLines = Files.lines(path).onClose(() -> System.out.println(" file closed")).filter(s -> s.contains("he"))){
            Optional<String> optional = filteredLines.findFirst();
            if (optional.isPresent()){
                System.out.println(optional.get());
            }
        }
    }
}

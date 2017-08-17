package com.donggua.lambda;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-08-17 下午 02:57
 */
@FunctionalInterface
public interface MyPredicate<T> {

    public boolean apply(T t);
}

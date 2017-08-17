package com.donggua.funtionalinterface;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-08-17 下午 01:27
 */
@FunctionalInterface
public interface MyFuncitonInterface<T> {

    public T getValue(T t);
}

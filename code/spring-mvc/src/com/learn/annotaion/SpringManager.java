package com.learn.annotaion;

/**
 * Description:
 * Created by caojx on 17-1-8.
 */
public class SpringManager implements ISpring {

    @Override
    public String get() {
        System.out.println("---------------I am SpringManager-----");
        return "I am getMethod";
    }
}

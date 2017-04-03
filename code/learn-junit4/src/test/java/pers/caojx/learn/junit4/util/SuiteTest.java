package pers.caojx.learn.junit4.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Description: JUnit测试套件
 *
 * @author caojx
 * Created by caojx on 2017年04月03 下午6:14:14
 */
@RunWith(Suite.class) //这里些测试类运行器，Suite.class使用测试套件
@Suite.SuiteClasses({TaskTest1.class,TaskTest2.class,TaskTest3.class}) //传入需要测试的测试类
public class SuiteTest {

	/*
	 * 1.测试套件就是组织测试类一起运行的
	 * 
	 * 写一个作为测试套件的入口类，这个类里不包含其他的方法,包含了也不会执行
	 * @RunWith()更改测试运行器为Suite.class
	 * 将要测试的类作为数组传入到Suite.SuiteClasses（{}）
	 */

}

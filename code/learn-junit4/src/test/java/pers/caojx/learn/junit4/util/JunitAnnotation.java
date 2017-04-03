package pers.caojx.learn.junit4.util;

import org.junit.*;
import pers.java.caojx.learn.junit4.util.Calculate;

import static org.junit.Assert.assertEquals;

/**
 * Description: JUnit常用注解
 *
 * @author caojx
 * Created by caojx on 2017年04月03 下午6:14:14
 */
public class JunitAnnotation {

    /*
     * 常用注解如下：
	 * @Test:将一个普通的方法修饰成为一个测试方法
	 * 		@Test(expected=XX.class) 捕获异常
	 * 		@Test(timeout=毫秒 ) 设置方法的测试时间，如果方法的运行时间操作设置的测试时间则会出错
	 * @BeforeClass：它会在所有的方法运行前被执行，static修饰
	 * @AfterClass:它会在所有的方法运行结束后被执行，static修饰
	 * @Before：会在每一个测试方法被运行前执行一次
	 * @After：会在每一个测试方法运行后被执行一次
	 * @Ignore:所修饰的测试方法会被测试运行器忽略,意思是这个方法不会被执行
	 * @RunWith:可以更改测试运行器 org.junit.runner.Runner
	 */

    @Test(expected = ArithmeticException.class)
    public void testDivide() {
        assertEquals(3, new Calculate().divide(6, 0));
    }


    @Ignore("这里可以写说明，为什么不执行")
    @Test(timeout = 2000)
    public void testWhile() {
        while (true) {
            System.out.println("run forever...");
        }
    }

    /**
     * 下边这个测试方法不会通过，因为测试方法只有3s，而方法需要4s才可以执行完成
     */
    @Test(timeout = 3000)
    public void testReadFile() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}